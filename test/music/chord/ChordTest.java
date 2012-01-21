package music.chord;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChordTest {

	@Test
	public void testGetThirdNoteName() {
		Chord chord = new Chord(NoteName.A_FLAT, Quality.MINOR);
		NoteName result = chord.getThirdNoteName();
		assertTrue("Abm third: " + result, NoteName.C_FLAT.equals(result));
	}

	@Test
	public void testGetFifthNoteName() {
		Chord chord = new Chord(NoteName.B, Quality.MAJOR);
		NoteName result = chord.getFifthNoteName();
		assertTrue("BM fifth: " + result, NoteName.F_SHARP.equals(result));
	}
}
