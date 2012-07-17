package music.chord.arrangement;

import java.util.List;

import music.chord.arrangement.impl.MelodyBuilderImpl;
import music.chord.arrangement.impl.MelodyPlayerImpl;
import music.chord.base.Direction;
import music.chord.base.Duration;
import music.chord.base.Interval;
import music.chord.base.NoteName;
import music.chord.base.impl.NoteNameBuilderImpl;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MelodyPlayerTest {

    @BeforeClass
    public static void setUpClass() {
        NoteName.setNoteNameBuilder(new NoteNameBuilderImpl());
    }

    private List<Note> noteList1;
    private List<Note> noteList2;
    private Melody melody1;
    private MelodyPlayer player;
    private Melody melody2;
    
    @Before
    public void setUp() throws Exception {
        NoteListBuilder nBuilder = new NoteListBuilder();
        noteList1 = 
            nBuilder.add(NoteName.forSymbol("C"), 60, Duration.QUARTER)
                .add(NoteName.forSymbol("D"), 62, Duration.QUARTER)
                .add(NoteName.forSymbol("C"), 60, Duration.HALF)
                .build();
        
        noteList2 = 
            nBuilder.add(NoteName.forSymbol("E"), 64, Duration.QUARTER)
                .add(NoteName.forSymbol("F"), 65, Duration.HALF)
                .add(NoteName.forSymbol("E"), 64, Duration.QUARTER)
                .build();
        
        MelodyBuilder builder = new MelodyBuilderImpl(new NoteListBuilder());
        melody1 =
            builder.start(NoteName.forSymbol("F#"), 5)
                .add(Duration.EIGHTH)
                .add(Direction.DOWN, Interval.MAJOR_SECOND)
                .add(Duration.EIGHTH)
                .add(Direction.DOWN, Interval.MAJOR_SECOND)
                .add(Duration.EIGHTH)
                .add(Direction.UP, Interval.MAJOR_SECOND)
                .add(Duration.EIGHTH)
                .add(Direction.UP, Interval.MAJOR_SECOND)
                .add(Duration.EIGHTH)
                .add(Direction.REPEAT, Interval.PERFECT_UNISON)
                .add(Duration.EIGHTH)
                .add(Direction.REPEAT, Interval.PERFECT_UNISON)
                .add(Duration.QUARTER)
                .build();
        
        melody2 =
            builder.start(NoteName.forSymbol("D"), 4)
                .add(Duration.EIGHTH)
                .add(Direction.DOWN, Interval.MINOR_SECOND)
                .add(Duration.EIGHTH)
                .add(Direction.DOWN, Interval.MAJOR_SECOND)
                .add(Duration.EIGHTH)
                .add(Direction.UP, Interval.MAJOR_SECOND)
                .add(Duration.EIGHTH)
                .add(Direction.UP, Interval.MINOR_SECOND)
                .add(Duration.EIGHTH)
                .add(Direction.REPEAT, Interval.PERFECT_UNISON)
                .add(Duration.EIGHTH)
                .add(Direction.REPEAT, Interval.PERFECT_UNISON)
                .add(Duration.QUARTER)
                .build();
        
        player = new MelodyPlayerImpl();
    }

    @Test
    public void playMary() {
        player.add(melody1)
            .add(melody2)
            .play();
    }
    
    @Test
    public void playSimultaneous() {
        player.add(noteList1)
            .add(noteList2)
            .play();
    }

}
