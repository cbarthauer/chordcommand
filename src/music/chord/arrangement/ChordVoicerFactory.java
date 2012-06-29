package music.chord.arrangement;

import java.io.IOException;

import music.chord.base.QualityRegistry;

import org.antlr.runtime.RecognitionException;

public final class ChordVoicerFactory {
	public static ChordVoicer getInstance(ChordDefinitionStructure struct) 
	        throws IOException, RecognitionException {
		
	    ChordVoicer voicer = new ChordVoicerImpl(
		    new ClosestVoicingStrategy(new DerivedChordBuilder()),
		    struct
		);		
		
		return voicer;
	}
	
	public static ChordVoicer getInstance(QualityRegistry qualities) {
        return new QualityChordVoicer(
                new ClosestVoicingStrategy(new DerivedChordBuilder()),
                qualities);
    }

    private ChordVoicerFactory() {
	    //Hide utility class constructor.
	}
}
