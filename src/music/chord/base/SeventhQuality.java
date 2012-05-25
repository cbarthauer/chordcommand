package music.chord.base;

import java.util.HashMap;
import java.util.Map;

public enum SeventhQuality {
    MAJOR(TriadQuality.MAJOR, Interval.MAJOR_SEVENTH),
    MINOR(TriadQuality.MINOR, Interval.MINOR_SEVENTH),
    DOMINANT(TriadQuality.MAJOR, Interval.MINOR_SEVENTH),
    DIMINISHED(TriadQuality.DIMINISHED, Interval.DIMINISHED_SEVENTH),
    HALF_DIMINISHED(TriadQuality.DIMINISHED, Interval.MINOR_SEVENTH),
    AUGMENTED(TriadQuality.AUGMENTED, Interval.MAJOR_SEVENTH),
    MINOR_MAJOR(TriadQuality.MINOR, Interval.MAJOR_SEVENTH);
    
    private TriadQuality triadQuality;
    private Interval seventhInterval;

    private SeventhQuality(TriadQuality triadQuality, Interval seventhInterval) {
        this.triadQuality = triadQuality;
        this.seventhInterval = seventhInterval;
    }

    public ChordSpec getChordSpec() {
        Map<ChordMember, IntervalDirective> dirMap = new HashMap<ChordMember, IntervalDirective>();
        dirMap.put(ChordMember.THIRD, new IntervalDirective(triadQuality.getThirdInterval()));
        dirMap.put(ChordMember.FIFTH, new IntervalDirective(triadQuality.getFifthInterval()));
        dirMap.put(ChordMember.SEVENTH, new IntervalDirective(seventhInterval));
        return new ChordSpec(dirMap);
    }
    
    public Interval getSeventhInterval() {
        return seventhInterval;
    }

    public TriadQuality getTriadQuality() {
        return triadQuality;
    }
    
}
