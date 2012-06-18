package music.chord.engine.protocol;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.Duration;

class DurationRequestImpl implements DurationRequest {
    
    private Identifier id;
    private List<Integer> positions;
    private Duration duration;

    DurationRequestImpl(Identifier id, List<Integer> positions, Duration duration) {
        this.id = id;
        this.positions = positions;
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }

    public Identifier getIdentifier() {
        return id;
    }

    public List<Integer> getPositions() {
        return new ArrayList<Integer>(positions);
    }
}
