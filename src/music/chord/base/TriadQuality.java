package music.chord.base;

import java.util.HashMap;
import java.util.Map;

public enum TriadQuality {
    AUGMENTED(QualitySymbol.AUGMENTED, Interval.MAJOR_THIRD, Interval.AUGMENTED_FIFTH),
    DIMINISHED(QualitySymbol.DIMINISHED, Interval.MINOR_THIRD, Interval.DIMINISHED_FIFTH),
    MAJOR(QualitySymbol.MAJOR, Interval.MAJOR_THIRD, Interval.PERFECT_FIFTH),
    MINOR(QualitySymbol.MINOR, Interval.MINOR_THIRD, Interval.PERFECT_FIFTH);
    
    private static Map<QualitySymbol, TriadQuality> symbolMap;
    
    static {
        symbolMap = new HashMap<QualitySymbol, TriadQuality>();
        symbolMap.put(QualitySymbol.AUGMENTED, TriadQuality.AUGMENTED);
        symbolMap.put(QualitySymbol.DIMINISHED, TriadQuality.DIMINISHED);
        symbolMap.put(QualitySymbol.MAJOR, TriadQuality.MAJOR);
        symbolMap.put(QualitySymbol.MINOR, TriadQuality.MINOR);
    }
    
    public static TriadQuality qualityFromSymbol(String symbol) {
        return symbolMap.get(QualitySymbol.qualityFromSymbol(symbol));
    }
    
    private QualitySymbol symbol;
    private Interval thirdInterval;
    private Interval fifthInterval;

    private TriadQuality(QualitySymbol symbol, Interval thirdInterval, Interval fifthInterval) {
        this.symbol = symbol;
        this.thirdInterval = thirdInterval;
        this.fifthInterval = fifthInterval;
    }

    public ChordSpec getChordSpec() {
        Map<ChordMember, IntervalDirective> dirMap = new HashMap<ChordMember, IntervalDirective>();
        dirMap.put(ChordMember.THIRD, new IntervalDirective(thirdInterval));
        dirMap.put(ChordMember.FIFTH, new IntervalDirective(fifthInterval));
        return new ChordSpec(dirMap);
    }
    
    public Interval getFifthInterval() {
        return fifthInterval;
    }

    public QualitySymbol getSymbol() {
        return symbol;
    }

    public Interval getThirdInterval() {
        return thirdInterval;
    }
}