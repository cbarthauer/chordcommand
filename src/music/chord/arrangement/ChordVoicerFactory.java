package music.chord.arrangement;

import music.chord.base.QualityRegistry;

public final class ChordVoicerFactory {
	public static ChordVoicer getInstance(QualityRegistry qualities) {
        return new QualityChordVoicer(
                new ClosestVoicingStrategy(new DerivedChordBuilder()),
                qualities);
    }

    private ChordVoicerFactory() {
	    //Hide utility class constructor.
	}
}
