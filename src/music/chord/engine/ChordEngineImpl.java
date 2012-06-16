package music.chord.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.ChordSpec;
import music.chord.base.ChordType;
import music.chord.engine.protocol.AddChordRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.InsertChordRequest;
import music.chord.engine.protocol.RemoveChordRequest;
import music.chord.grammar.ChordListRegistry;

class ChordEngineImpl implements ChordEngine {

    private Map<ChordType, VoicedChordBuilder> builderMap;
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
            registry.add(request.getIdentifier().getName(), chord);
        }
        
        return this;
    }

    @Override
    public final List<VoicedChord> byIdentifier(Identifier id) {
        return registry.byIdentifier(id.getName());
    }

    @Override
    public final ChordEngineImpl insertChords(InsertChordRequest... requests) {
        if(requests.length == 0) {
            throw new IllegalArgumentException("Parameter 'requests' cannot be zero-length.");
        }
        
        Identifier identifier = requests[0].getIdentifier();
        LinkedList<VoicedChord> existingList = 
                new LinkedList<VoicedChord>(registry.byIdentifier(identifier.getName()));
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
        registry.put(identifier.getName(), existingList);
        
        return this;
    }

    @Override
    public final ChordEngineImpl removeChords(RemoveChordRequest request) {
        Identifier identifier = request.getIdentifier();
        List<VoicedChord> existingList = registry.byIdentifier(identifier.getName());
        List<VoicedChord> newList = new ArrayList<VoicedChord>();
        List<Integer> positions = request.getPositions();
        
        for(int i = 0; i < existingList.size(); i++) {
            if(!positions.contains(i)) {
                newList.add(existingList.get(i));
            }
        }
        
        registry.put(identifier.getName(), newList);
        return this;
    }

}
