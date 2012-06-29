package music.chord.arrangement;

import java.util.List;

import music.chord.base.QualityRegistry;

public final class QualityChordVoicer implements ChordVoicer {

    private VoicingStrategy strategy;
    private QualityRegistry qualities;

    public QualityChordVoicer(
            VoicingStrategy strategy,
            QualityRegistry qualities) {
        
        this.strategy = strategy;
        this.qualities = qualities;
    }
    
    @Override
    public final List<VoicedChord> voice(List<VoicedChord> chordList) {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public final List<VoicedChord> voice(VoicedChord startingChord,
            List<VoicedChord> chordList) {
        throw new RuntimeException("Not implemented.");
    }

    @Override
    public final List<VoicingComparison> voicingComparisonList(
            VoicedChord previousChord, 
            VoicedChord newChord) {

        List<Voicing> voicingList = qualities.forQuality(
                newChord.getQuality());
        
        return strategy.voicingComparisonList(
                previousChord, 
                newChord, 
                voicingList);
    }

}
