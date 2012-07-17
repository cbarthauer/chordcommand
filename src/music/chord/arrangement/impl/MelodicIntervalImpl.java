package music.chord.arrangement.impl;

import music.chord.arrangement.MelodicInterval;
import music.chord.base.Direction;
import music.chord.base.Interval;
import music.chord.base.NoteName;

public final class MelodicIntervalImpl implements MelodicInterval {

    private Direction direction;
    private Interval interval;

    public MelodicIntervalImpl(Direction direction, Interval interval) {
        this.direction = direction;
        this.interval = interval;
    }

    @Override
    public final Integer chromaticHalfSteps() {
        return interval.getHalfSteps() * direction.sign();
    }

    @Override
    public NoteName forNoteName(NoteName noteName) {
        switch(direction) {
        case UP:
            return noteName.up(interval);
        case DOWN:
            return noteName.down(interval);
        case REPEAT:
            return noteName;
        default:
            throw new IllegalStateException("Unknown direction: " + direction);
        }
    }

}
