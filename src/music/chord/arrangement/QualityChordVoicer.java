package music.chord.arrangement;

import java.util.ArrayList;
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
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        if(chordList.size() == 1) {
            result.add(chordList.get(0));
        }
        else {
            result = voice(chordList.get(0), chordList.subList(1, chordList.size()));
        }
        
        return result;
    }
    

    @Override
    public final List<VoicedChord> voice(VoicedChord startingChord, List<VoicedChord> chordList) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        result.add(startingChord);
        
        VoicedChord previousChord = startingChord;
        
        for(VoicedChord chord : chordList) {
            VoicedChord voicedChord = strategy.voice(
                    previousChord, 
                    chord, 
                    qualities.getCongruentVoicings(chord.getVoicing()));
            result.add(voicedChord);
            previousChord = voicedChord;
        }
        
        
        return result;
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
