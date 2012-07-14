package music.chord.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import music.chord.AbstractTest;

import org.junit.Test;

public class NoteNameTest extends AbstractTest {
    
    @Test
    public void all() {
        List<NoteName> noteList = NoteName.all();
        assertTrue(noteList.size() > 0);
    }

    @Test
    public void forSymbol() {
        NoteName note = NoteName.forSymbol("C");
        assertEquals("C", note.getSymbol());
    }

}
