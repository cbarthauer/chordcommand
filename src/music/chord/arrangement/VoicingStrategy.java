package music.chord.arrangement;

import java.util.List;
import java.util.Map;

public interface VoicingStrategy {
    public VoicedChord voice(VoicedChord previousChord, VoicedChord nextChord);
    public List<Voicing> voicingListFromVoicing(Voicing voicing);
    public Map<Integer, List<VoicedChord>> voicedChordDifferenceMap(
            VoicedChord previousChord, 
            VoicedChord newChord);
    public List<VoicingComparison> voicingComparisonList(
            VoicedChord previousChord, 
            VoicedChord newChord);
}
