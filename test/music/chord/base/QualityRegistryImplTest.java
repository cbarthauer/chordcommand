package music.chord.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import music.chord.arrangement.QualityRegistryImpl;

import org.junit.Before;
import org.junit.Test;

public class QualityRegistryImplTest {

    private QualityRegistryImpl registry;
    private QualityImpl majorTriad;
    private QualityImpl minorTriad;
    private QualityImpl augmentedTriad;
    private QualityImpl dominantSeventh;
    private QualityImpl dominantNinth;

    @Before
    public void setUp() throws Exception {
        registry = new QualityRegistryImpl();
        IntervallicStructureBuilder builder = new IntervallicStructureBuilder();
        
        majorTriad = new QualityImpl(
            "MAJOR_TRIAD", 
            QualitySymbol.MAJOR_TRIAD, 
            ChordType.TRIAD, 
            builder.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                .build());
        
        minorTriad = new QualityImpl(
            "MINOR_TRIAD", 
            QualitySymbol.MINOR_TRIAD, 
            ChordType.TRIAD, 
            builder.add(ChordMember.THIRD, new IntervalDirective(Interval.MINOR_THIRD))
                .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                .build());
        
        augmentedTriad = new QualityImpl(
            "AUGMENTED_TRIAD", 
            QualitySymbol.AUGMENTED_TRIAD, 
            ChordType.TRIAD, 
            builder.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                .add(ChordMember.FIFTH, new IntervalDirective(Interval.AUGMENTED_FIFTH))
                .build());
        
        dominantSeventh = new QualityImpl(
            "DOMINANT_SEVENTH", 
            QualitySymbol.DOMINANT_SEVENTH, 
            ChordType.SEVENTH, 
            builder.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                .add(ChordMember.SEVENTH, new IntervalDirective(Interval.MINOR_SEVENTH))
                .build());
        
        dominantNinth = new QualityImpl(
            "DOMINANT_NINTH", 
            QualitySymbol.DOMINANT_NINTH, 
            ChordType.NINTH, 
            builder.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                .add(ChordMember.SEVENTH, new IntervalDirective(Interval.MINOR_SEVENTH))
                .add(ChordMember.NINTH, new IntervalDirective(Interval.MAJOR_NINTH))
                .build());
    }

    @Test
    public void addQuality() {
        registry.addQuality(majorTriad);
        registry.addQuality(minorTriad);
        assertTrue(registry.contains(majorTriad));
        assertTrue(registry.contains(minorTriad));
        assertFalse(registry.contains(augmentedTriad));
    }
    
    @Test
    public void all() {
        registry.addQuality(majorTriad);
        registry.addQuality(dominantSeventh);
        registry.addQuality(dominantNinth);
        assertTrue(registry.all(ChordType.TRIAD).size() > 0);
        assertTrue(registry.all(ChordType.SEVENTH).size() > 0);
        assertTrue(registry.all(ChordType.NINTH).size() > 0);
        assertEquals(3, registry.all().size());
    }
    
    @Test
    public void get() {
        registry.addQuality(majorTriad);
        Quality quality = registry.forName("MAJOR_TRIAD");
        assertEquals(majorTriad, quality);
    }

}
