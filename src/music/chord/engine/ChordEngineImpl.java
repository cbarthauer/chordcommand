package music.chord.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.DerivedChordBuilder;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.ChordSpec;
import music.chord.base.ChordType;
import music.chord.engine.protocol.AddChordRequest;
import music.chord.engine.protocol.DurationRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.InsertChordRequest;
import music.chord.engine.protocol.LoadRequest;
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
    private ChordDefinitionStructure struct;
    
    public ChordEngineImpl(
            VoicedChordBuilder triadBuilder,
            VoicedChordBuilder seventhBuilder,
            VoicedChordBuilder ninthBuilder,
            ChordDefinitionStructure struct) {
    
        builderMap = new HashMap<ChordType, VoicedChordBuilder>();
        builderMap.put(ChordType.TRIAD, triadBuilder);
        builderMap.put(ChordType.SEVENTH, seventhBuilder);
        builderMap.put(ChordType.NINTH, ninthBuilder);
        this.derivedBuilder = new DerivedChordBuilder();
        registry = new ChordListRegistry();
        this.struct = struct;
    }
    
    @Override
    public final ChordEngineImpl addChords(AddChordRequest... requests) {
        for(AddChordRequest request : requests) {
            VoicedChordBuilder builder = builderMap.get(request.getType());
            ChordSpec spec = struct.getChordSpec(request.getType(), request.getQuality());
            VoicedChord chord = builder.setRoot(request.getNoteName())
                .setChordSpec(spec)
                .setQuality(request.getQuality())
                .buildVoicedChord();
            registry.add(request.getIdentifier(), chord);
        }
        
        return this;
    }

    @Override
    public final List<VoicedChord> byIdentifier(Identifier id) {
        return registry.byIdentifier(id);
    }

    @Override
    public ChordListRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public final ChordEngineImpl insertChords(InsertChordRequest... requests) {
        if(requests.length == 0) {
            throw new IllegalArgumentException("Parameter 'requests' cannot be zero-length.");
        }
        
        Identifier identifier = requests[0].getIdentifier();
        LinkedList<VoicedChord> existingList = 
                new LinkedList<VoicedChord>(registry.byIdentifier(identifier));
        List<VoicedChord> newChords = new ArrayList<VoicedChord>();
        
        for(InsertChordRequest request : requests) {
            VoicedChordBuilder builder = builderMap.get(request.getType());
            ChordSpec spec = struct.getChordSpec(request.getType(), request.getQuality());
            VoicedChord chord = builder.setRoot(request.getNoteName())
                .setChordSpec(spec)
                .setQuality(request.getQuality())
                .buildVoicedChord();
            newChords.add(chord);
        }
        
        int position = requests[0].getPosition();
        existingList.addAll(position, newChords);
        registry.put(identifier, existingList);
        
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
            walker.setChordDefinitionStructure(struct);
            walker.setTriadBuilder(BuilderFactory.getTriadBuilder(struct));
            walker.setSeventhBuilder(BuilderFactory.getSeventhBuilder(struct));
            walker.setNinthBuilder(BuilderFactory.getNinthBuilder(struct));
            
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
    public ChordEngineImpl setDurations(DurationRequest request) {
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
    public ChordEngineImpl setVoicings(VoicingRequest request) {
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

}
