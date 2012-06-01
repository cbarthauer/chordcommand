package music.chord.arrangement;

import java.io.IOException;

import music.chord.base.ChordDefinitionStructure;

import org.antlr.runtime.RecognitionException;

public final class ChordVoicerFactory {
	public static ChordVoicer getInstance(ChordDefinitionStructure struct) 
	        throws IOException, RecognitionException {
		
	    ChordVoicer voicer = new ChordVoicer(
		    new ClosestVoicingStrategy(new DerivedChordBuilder()),
		    struct
		);		
		
		return voicer;
	}
	
	private ChordVoicerFactory() {
	    //Hide utility class constructor.
	}
}
