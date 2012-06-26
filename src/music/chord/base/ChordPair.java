package music.chord.base;


public final class ChordPair {
    
    private NoteName root;
    private QualityEnum quality;

    public ChordPair(NoteName root, QualityEnum quality) {
        this.root = root;
        this.quality = quality;
    }
    
    public final QualityEnum getQualityEnum() {
        return quality;
    }

    public ChordType getType() {
        return quality.getType();
    }

    public NoteName getRoot() {
        return root;
    }
}
