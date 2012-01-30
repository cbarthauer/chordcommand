package music.chord;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NoteBeanTest {

	@Test
	public void testDifference() {
		List<NoteBean> list1 = new ArrayList<NoteBean>();
		list1.add(new NoteBean(NoteName.E, 52));
		list1.add(new NoteBean(NoteName.A_FLAT, 56));
		list1.add(new NoteBean(NoteName.C, 60));
		list1.add(new NoteBean(NoteName.E, 64));
		
		List<NoteBean> list2 = new ArrayList<NoteBean>();
		list2.add(new NoteBean(NoteName.D_FLAT, 49));
		list2.add(new NoteBean(NoteName.A_FLAT, 56));
		list2.add(new NoteBean(NoteName.D_FLAT, 61));
		list2.add(new NoteBean(NoteName.F, 65));
		
		assertTrue(NoteBean.difference(list1, list2) == 5);
	}

}
