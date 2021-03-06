/*******************************************************************************
 * Copyright (c) 2012 Chris Barthauer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Chris Barthauer - initial API and implementation
 ******************************************************************************/
package music.chord.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.ChordFinder;
import music.chord.arrangement.ChordMemberFilter;
import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.DerivedChordBuilder;
import music.chord.arrangement.QualityRegistry;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.arrangement.VoicingComparison;
import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.engine.protocol.ChordRequest;
import music.chord.engine.protocol.DurationRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.InsertChordRequest;
import music.chord.engine.protocol.OctaveRequest;
import music.chord.engine.protocol.RemoveChordRequest;
import music.chord.engine.protocol.VoicingRequest;
import music.chord.grammar.ChordLexer;
import music.chord.grammar.ChordListRegistry;
import music.chord.grammar.ChordParser;
import music.chord.grammar.ChordParser.compilationUnit_return;
import music.chord.grammar.ChordWalker;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

class ChordEngineImpl implements ChordEngine {

    private Map<ChordType, VoicedChordBuilder> builderMap;
    private DerivedChordBuilder derivedBuilder;
    private ChordListRegistry registry;
    private ChordVoicer voicer;
    private ChordFinder finder;
    private QualityRegistry qualities;    
    
    public ChordEngineImpl(
            VoicedChordBuilder triadBuilder,
            VoicedChordBuilder seventhBuilder,
            VoicedChordBuilder ninthBuilder,
            DerivedChordBuilder derivedBuilder,
            ChordListRegistry registry,
            ChordVoicer voicer,
            ChordFinder finder,
            QualityRegistry qualities) {
    
        builderMap = new HashMap<ChordType, VoicedChordBuilder>();
        builderMap.put(ChordType.TRIAD, triadBuilder);
        builderMap.put(ChordType.SEVENTH, seventhBuilder);
        builderMap.put(ChordType.NINTH, ninthBuilder);
        this.derivedBuilder = derivedBuilder;
        this.registry = registry;
        this.voicer = voicer;
        this.finder = finder;
        this.qualities = qualities;
    }

    @Override
    public final List<VoicedChord> byIdentifier(Identifier id) {
        return registry.byIdentifier(id);
    }
    
    @Override
    public final VoicedChord byIdentifier(Identifier id, int index) {
        return byIdentifier(id).get(index);
    }

    @Override
    public final List<VoicedChord> byIdentifier(Identifier id, List<Integer> indexes) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        List<VoicedChord> existingList = byIdentifier(id);
        
        for(Integer index : indexes) {
            result.add(existingList.get(index));
        }
        
        return result;
    }

    @Override
    public final List<VoicedChord> chordsByFilter(ChordMemberFilter filter) {
        return finder.find(filter);
    }

    @Override
    public final List<VoicedChord> chordsContaining(List<NoteName> noteList) {
        return finder.find(noteList);
    }

    @Override
    public final List<VoicingComparison> compareVoicings(
            VoicedChord first,
            VoicedChord second) {
        return voicer.voicingComparisonList(first, second);
    }

    @Override
    public final VoicedChord createChord(NoteName root, Quality quality) {
        VoicedChordBuilder builder = builderMap.get(quality.getType());
        return builder.setRoot(root)
            .setQuality(quality)
            .buildVoicedChord();
    }

    @Override
    public final ChordEngineImpl createChordList(ChordRequest request) {
        registry.put(request.getIdentifier(), request.getChordList());
        return this;
    }

    @Override
    public final ChordEngineImpl insertChords(InsertChordRequest request) {
        Identifier id = request.getIdentifier();
        LinkedList<VoicedChord> chords = new LinkedList<VoicedChord>(registry.byIdentifier(id));      
        chords.addAll(request.getPosition(), request.getChordList());
        registry.put(id, chords);
        return this;
    }

    @Override
    public final List<VoicedChord> load(String fileName) {
        List<VoicedChord> chordList = new ArrayList<VoicedChord>();
        
        try {
            CharStream charStream = new ANTLRFileStream(fileName);
            ChordLexer lexer = new ChordLexer(charStream);
            TokenStream tokenStream = new CommonTokenStream(lexer);
            ChordParser parser = new ChordParser(tokenStream);
            compilationUnit_return compilationUnit = parser.compilationUnit();
            CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(compilationUnit.getTree());
            
            ChordWalker walker = new ChordWalker(nodeStream);
            walker.setTriadBuilder(builderMap.get(ChordType.TRIAD));
            walker.setSeventhBuilder(builderMap.get(ChordType.SEVENTH));
            walker.setNinthBuilder(builderMap.get(ChordType.NINTH));
            walker.setQualityRegistry(qualities);
            
            chordList = walker.compilationUnit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }  
        
        return chordList;
    }

    @Override
    public final ChordEngineImpl removeChords(RemoveChordRequest request) {
        Identifier identifier = request.getIdentifier();
        List<VoicedChord> existingList = registry.byIdentifier(identifier);
        List<VoicedChord> newList = new ArrayList<VoicedChord>();
        List<Integer> positions = request.getPositions();
        
        for(int i = 0; i < existingList.size(); i++) {
            if(!positions.contains(i)) {
                newList.add(existingList.get(i));
            }
        }
        
        registry.put(identifier, newList);
        return this;
    }

    @Override
    public final ChordEngineImpl setDurations(DurationRequest request) {
        Identifier identifier = request.getIdentifier();
        
        for(Integer index : request.getPositions()) {
            VoicedChord chord = 
                derivedBuilder.setChord(registry.getChord(identifier, index))
                    .setDuration(request.getDuration())
                    .buildVoicedChord();
            registry.set(identifier, index, chord);
        }
        
        return this;
    }

    @Override
    public final ChordEngineImpl setOctaves(OctaveRequest request) {
        Identifier identifier = request.getIdentifier();
        
        for(Integer index : request.getPositions()) {
            VoicedChord chord = 
                derivedBuilder.setChord(registry.getChord(identifier, index))
                    .setOctave(request.getOctave())
                    .buildVoicedChord();
            registry.set(identifier, index, chord);
        }
        
        return this;
    }

    @Override
    public final ChordEngineImpl setVoicings(VoicingRequest request) {
        Identifier identifier = request.getIdentifier();
        
        for(Integer index : request.getPositions()) {
            VoicedChord chord = 
                derivedBuilder.setChord(registry.getChord(identifier, index))
                    .setVoicing(request.getVoicing())
                    .buildVoicedChord();
            registry.set(identifier, index, chord);
        }
        
        return this;
    }

    @Override
    public final List<VoicedChord> voiceAll(List<VoicedChord> chordList) {
        return voicer.voice(chordList);
    }

}
