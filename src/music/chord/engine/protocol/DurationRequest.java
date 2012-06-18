package music.chord.engine.protocol;

import java.util.List;

import music.chord.base.Duration;

public interface DurationRequest {
    public Duration getDuration();
    public Identifier getIdentifier();
    public List<Integer> getPositions();
}
