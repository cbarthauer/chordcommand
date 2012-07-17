package music.chord.arrangement;

import music.chord.base.NoteName;

public interface MelodicInterval {
    Integer chromaticHalfSteps();
    NoteName forNoteName(NoteName noteName);
}
