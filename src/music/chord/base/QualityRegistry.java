package music.chord.base;

import java.util.List;

public interface QualityRegistry {
    public List<Quality> all();
    public List<Quality> all(ChordType type);
    public boolean contains(Quality quality);
    public Quality forName(String name);
}