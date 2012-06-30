package music.chord.base;

import java.util.List;

import music.chord.arrangement.Voicing;

public interface QualityRegistry {
    public List<Quality> all();
    public List<Quality> all(ChordType type);
    public boolean contains(Quality quality);
    public Quality forName(String name);
    public List<Voicing> forQuality(Quality quality);
    public List<Voicing> getCongruentVoicings(Voicing voicing);
}