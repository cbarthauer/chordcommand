package music.chord.arrangement;


public final class ChordVoicerFactory {
	public static ChordVoicer getInstance(QualityRegistry qualities) {
        return new ChordVoicerImpl(
                new ClosestVoicingStrategy(new DerivedChordBuilder()),
                qualities);
    }

    private ChordVoicerFactory() {
	    //Hide utility class constructor.
	}
}
