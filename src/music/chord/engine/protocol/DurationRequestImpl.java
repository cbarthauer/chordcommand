package music.chord.engine.protocol;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.Duration;

final class DurationRequestImpl implements DurationRequest {
    
    private Identifier id;
    private List<Integer> positions;
    private Duration duration;

    DurationRequestImpl(Identifier id, List<Integer> positions, Duration duration) {
        this.id = id;
        this.positions = positions;
        this.duration = duration;
    }

    public final Duration getDuration() {
        return duration;
    }

    public final Identifier getIdentifier() {
        return id;
    }

    public final List<Integer> getPositions() {
        return new ArrayList<Integer>(positions);
    }
}
