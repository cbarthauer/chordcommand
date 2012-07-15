package music.chord.base;

import java.util.List;

public interface NoteNameBuilder {
    List<NoteName> all();
    NoteName forSymbol(String symbol);
}
