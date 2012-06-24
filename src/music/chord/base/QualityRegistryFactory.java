package music.chord.base;

public final class QualityRegistryFactory {
    private static IntervallicStructureBuilder builder;
    
    static {
        builder = new IntervallicStructureBuilder();
    }
    
    public static QualityRegistry getInstance(String fileName) {
        QualityRegistry registry = new QualityRegistry();
        
        registry.addQuality(
            createQuality(
                "MAJOR_TRIAD", 
                QualitySymbol.MAJOR_TRIAD,
                ChordType.TRIAD,
                builder.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                    .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                    .build()));
        
        registry.addQuality(
            createQuality(
                "MINOR_TRIAD", 
                QualitySymbol.MINOR_TRIAD, 
                ChordType.TRIAD, 
                builder.add(ChordMember.THIRD, new IntervalDirective(Interval.MINOR_THIRD))
                    .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                    .build()));

        registry.addQuality(
            createQuality(
                "AUGMENTED_TRIAD", 
                QualitySymbol.AUGMENTED_TRIAD, 
                ChordType.TRIAD, 
                builder.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                    .add(ChordMember.FIFTH, new IntervalDirective(Interval.AUGMENTED_FIFTH))
                    .build()));
    
        registry.addQuality(
            createQuality(
                "DOMINANT_SEVENTH", 
                QualitySymbol.DOMINANT_SEVENTH,
                ChordType.SEVENTH,
                builder.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                    .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                    .add(ChordMember.SEVENTH, new IntervalDirective(Interval.MINOR_SEVENTH))
                    .build()));
        
        registry.addQuality(
            createQuality(
                "DOMINANT_NINTH", 
                QualitySymbol.DOMINANT_NINTH,
                ChordType.NINTH,
                builder.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                    .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                    .add(ChordMember.SEVENTH, new IntervalDirective(Interval.MINOR_SEVENTH))
                    .add(ChordMember.NINTH, new IntervalDirective(Interval.MAJOR_NINTH))
                    .build()));
        
        return registry;
    }
    
    public static Quality createQuality(
            String name, 
            QualitySymbol symbol, 
            ChordType type, 
            IntervallicStructure struct) {
        return new QualityImpl(name, symbol, type, struct);
    }
    
    private QualityRegistryFactory() {
        //Hide utility class constructor.
    }
}
