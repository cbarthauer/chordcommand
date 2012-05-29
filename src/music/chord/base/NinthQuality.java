package music.chord.base;

import java.util.HashMap;
import java.util.Map;

public enum NinthQuality {
    MAJOR(SeventhQuality.MAJOR, Interval.MAJOR_NINTH),
    MINOR(SeventhQuality.MINOR, Interval.MAJOR_NINTH),
    DOMINANT(SeventhQuality.DOMINANT, Interval.MAJOR_NINTH);
    
    private SeventhQuality seventhQuality;
    private Interval ninthInterval;

    private NinthQuality(SeventhQuality seventhQuality, Interval ninthInterval) {
        this.seventhQuality = seventhQuality;
        this.ninthInterval = ninthInterval;
    }
    
    public ChordSpec getChordSpec() {
        Map<ChordMember, IntervalDirective> dirMap = new HashMap<ChordMember, IntervalDirective>();
        dirMap.put(
                ChordMember.THIRD, 
                new IntervalDirective(seventhQuality.getTriadQuality().getThirdInterval()));
        dirMap.put(
                ChordMember.FIFTH, 
                new IntervalDirective(seventhQuality.getTriadQuality().getFifthInterval()));
        dirMap.put(
                ChordMember.SEVENTH, 
                new IntervalDirective(seventhQuality.getSeventhInterval()));
        dirMap.put(
                ChordMember.NINTH, 
                new IntervalDirective(ninthInterval));
        return new ChordSpec(dirMap);
    }

    public Interval getNinthInterval() {
        return ninthInterval;
    }

    public SeventhQuality getSeventhQuality() {
        return seventhQuality;
    }
}
