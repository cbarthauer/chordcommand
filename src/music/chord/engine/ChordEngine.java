package music.chord.engine;

import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.engine.protocol.AddChordRequest;
import music.chord.engine.protocol.DurationRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.InsertChordRequest;
import music.chord.engine.protocol.LoadRequest;
import music.chord.engine.protocol.OctaveRequest;
import music.chord.engine.protocol.RemoveChordRequest;
import music.chord.engine.protocol.VoiceAllRequest;
import music.chord.engine.protocol.VoicingRequest;
import music.chord.grammar.ChordListRegistry;

public interface ChordEngine {
    public ChordEngine addChords(AddChordRequest... request);
    public ChordEngine insertChords(InsertChordRequest... request);
    public ChordEngine removeChords(RemoveChordRequest request);
    
    public List<VoicedChord> byIdentifier(Identifier id);
    public ChordEngine load(LoadRequest request);
    public ChordEngine setVoicings(VoicingRequest request);
    public ChordEngine setDurations(DurationRequest request);
    public ChordEngine setOctaves(OctaveRequest request);
    public ChordEngine voiceAll(VoiceAllRequest request);
    
    public ChordListRegistry getRegistry();
}
