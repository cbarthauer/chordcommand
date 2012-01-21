package music.chord;

import static org.junit.Assert.*;

import org.junit.Test;

public class NoteNameTest {

	@Test
	public void testUp() {
		NoteName root = NoteName.A;
		NoteName result = root.up(Interval.MAJOR_THIRD);
		assertTrue("A up M3: " + result, NoteName.C_SHARP.equals(result));
	}

}
