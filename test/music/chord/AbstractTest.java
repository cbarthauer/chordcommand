package music.chord;

import music.chord.base.NoteName;
import music.chord.base.impl.NoteNameBuilderImpl;

public abstract class AbstractTest {
    static {
        NoteName.setNoteNameBuilder(new NoteNameBuilderImpl());
    }
}
