package music.chord.engine;

import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.engine.protocol.AddChordRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.InsertChordRequest;
import music.chord.engine.protocol.RemoveChordRequest;

public interface ChordEngine {
    public ChordEngine addChords(AddChordRequest... request);
    public ChordEngine insertChords(InsertChordRequest... request);
    public ChordEngine removeChords(RemoveChordRequest request);
    
    public List<VoicedChord> byIdentifier(Identifier id);
}
