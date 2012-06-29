package music.chord.arrangement;

import java.util.List;

public interface ChordVoicer {
    public List<VoicedChord> voice(List<VoicedChord> chordList);
    
    public List<VoicedChord> voice(
            VoicedChord startingChord,
            List<VoicedChord> chordList);
    
    public List<VoicingComparison> voicingComparisonList(
            VoicedChord previousChord, 
            VoicedChord newChord);

}