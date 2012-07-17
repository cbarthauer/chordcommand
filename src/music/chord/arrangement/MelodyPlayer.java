package music.chord.arrangement;

import java.util.List;


public interface MelodyPlayer {
    MelodyPlayer add(Melody melody);
    MelodyPlayer add(List<Note> noteList);
    void play();
}
