package music.chord.base;


public final class ChordPair {
    
    private NoteName root;
    private Quality quality;

    public ChordPair(NoteName root, Quality quality) {
        this.root = root;
        this.quality = quality;
    }
    
    public final Quality getQuality() {
        return quality;
    }

    public ChordType getType() {
        return quality.getType();
    }

    public NoteName getRoot() {
        return root;
    }
}
