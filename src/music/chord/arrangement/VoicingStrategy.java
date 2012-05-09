package music.chord.arrangement;

import java.util.List;

public interface VoicingStrategy {
    public VoicedChord voice(VoicedChord previousChord, VoicedChord nextChord, List<Voicing> voicingList);
    public List<VoicingComparison> voicingComparisonList(
            VoicedChord previousChord, 
            VoicedChord newChord, 
            List<Voicing> voicingList);
}
