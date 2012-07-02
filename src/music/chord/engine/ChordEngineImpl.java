package music.chord.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.DerivedChordBuilder;
import music.chord.arrangement.QualityRegistry;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.ChordType;
import music.chord.engine.protocol.ChordRequest;
import music.chord.engine.protocol.CreateChordRequest;
import music.chord.engine.protocol.DurationRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.InsertChordRequest;
import music.chord.engine.protocol.LoadRequest;
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
    private QualityRegistry qualities;
    
    public ChordEngineImpl(
            VoicedChordBuilder triadBuilder,
            VoicedChordBuilder seventhBuilder,
            VoicedChordBuilder ninthBuilder,
            DerivedChordBuilder derivedBuilder,
            ChordListRegistry registry,
            ChordVoicer voicer,
            QualityRegistry qualities) {
    
        builderMap = new HashMap<ChordType, VoicedChordBuilder>();
        builderMap.put(ChordType.TRIAD, triadBuilder);
        builderMap.put(ChordType.SEVENTH, seventhBuilder);
        builderMap.put(ChordType.NINTH, ninthBuilder);
        this.derivedBuilder = derivedBuilder;
        this.registry = registry;
        this.voicer = voicer;
        this.qualities = qualities;
    }

    @Override
    public final ChordEngineImpl addChords(ChordRequest request) {
        Identifier identifier = request.getIdentifier();
        
        for(VoicedChord chord : request.getChordList()) {
            registry.add(identifier, chord);            
        }
        
        return this;
    }
    
    @Override
    public final List<VoicedChord> byIdentifier(Identifier id) {
        return registry.byIdentifier(id);
    }

    @Override
    public final VoicedChord createChord(CreateChordRequest request) {
        VoicedChordBuilder builder = builderMap.get(request.getType());
        return builder.setRoot(request.getRoot())
            .setQuality(request.getQuality())
            .buildVoicedChord();
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
    public final ChordEngineImpl load(LoadRequest request) {
        try {
            CharStream charStream = new ANTLRFileStream(request.getFileName());
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
            
            List<VoicedChord> chordList = walker.compilationUnit();
            registry.put(request.getIdentifier(), chordList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }  
        
        return this;
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
