package music.chord;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class LilyPondConverterTest {

	@Test
	public void test() {
		Chord chord = new Chord(
			NoteName.A,
			Quality.MAJOR,
			Quality.MINOR
		);
		
		LilyPondConverter ly = new LilyPondConverter();
		Map<ChordMember, String> lyPitchStrings = ly.pitchStringMapFromChord(chord);
		
		String third = lyPitchStrings.get(ChordMember.THIRD);
		assertTrue("Third of chord should be csharp.  Found " + third, "csharp".equals(third));
		
		chord = new Chord(NoteName.F_FLAT, Quality.MAJOR, Quality.MINOR);
		lyPitchStrings = ly.pitchStringMapFromChord(chord);
		String seventh = lyPitchStrings.get(ChordMember.SEVENTH);
		assertTrue("Seventh of chord should be eflatflat.  Found " + seventh, "eflatflat".equals(seventh));
	}
 
}
