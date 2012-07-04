package music.chord.engine;

import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.base.NoteName;
import music.chord.engine.protocol.ChordRequest;
import music.chord.engine.protocol.CreateChordRequest;
import music.chord.engine.protocol.DurationRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.InsertChordRequest;
import music.chord.engine.protocol.OctaveRequest;
import music.chord.engine.protocol.RemoveChordRequest;
import music.chord.engine.protocol.VoicingRequest;
import music.chord.engine.protocol.filter.ChordMemberFilter;

public interface ChordEngine {
    public ChordEngine addChords(ChordRequest request);
    public List<VoicedChord> byIdentifier(Identifier id);
    public List<VoicedChord> chordsByFilter(ChordMemberFilter filter);
    public List<VoicedChord> chordsContaining(List<NoteName> noteNameList);
    
    public VoicedChord createChord(CreateChordRequest request);
    public ChordEngine insertChords(InsertChordRequest request);
    public List<VoicedChord> load(String fileName);
    public ChordEngine removeChords(RemoveChordRequest request);
    public ChordEngine setDurations(DurationRequest request);
    public ChordEngine setOctaves(OctaveRequest request);
    public ChordEngine setVoicings(VoicingRequest request);
    public List<VoicedChord> voiceAll(List<VoicedChord> chordList);
}
