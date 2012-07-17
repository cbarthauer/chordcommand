package music.chord.arrangement;

import music.chord.base.Direction;
import music.chord.base.Duration;
import music.chord.base.Interval;
import music.chord.base.NoteName;

public interface MelodyBuilder {
    MelodyBuilder add(Duration duration);
    MelodyBuilder add(Direction direction, Interval interval);
    Melody build();
    MelodyBuilder start(NoteName startingNote, int octave);
}
