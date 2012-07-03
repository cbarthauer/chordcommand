package music.chord.engine;

import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.engine.protocol.ChordRequest;
import music.chord.engine.protocol.CreateChordRequest;
import music.chord.engine.protocol.DurationRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.InsertChordRequest;
import music.chord.engine.protocol.OctaveRequest;
import music.chord.engine.protocol.RemoveChordRequest;
import music.chord.engine.protocol.VoicingRequest;

public interface ChordEngine {
    public ChordEngine addChords(ChordRequest request);
    public VoicedChord createChord(CreateChordRequest request);
    public ChordEngine insertChords(InsertChordRequest request);
    public ChordEngine removeChords(RemoveChordRequest request);
    
    public List<VoicedChord> byIdentifier(Identifier id);
    public List<VoicedChord> load(String fileName);
    public ChordEngine setVoicings(VoicingRequest request);
    public ChordEngine setDurations(DurationRequest request);
    public ChordEngine setOctaves(OctaveRequest request);
    public List<VoicedChord> voiceAll(List<VoicedChord> chordList);
}
