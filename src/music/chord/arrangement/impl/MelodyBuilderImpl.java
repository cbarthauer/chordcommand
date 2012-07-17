package music.chord.arrangement.impl;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.MelodicInterval;
import music.chord.arrangement.Melody;
import music.chord.arrangement.MelodyBuilder;
import music.chord.arrangement.NoteListBuilder;
import music.chord.base.Direction;
import music.chord.base.Duration;
import music.chord.base.Interval;
import music.chord.base.NoteName;

public final class MelodyBuilderImpl implements MelodyBuilder {

    private NoteName startingNoteName;
    private int startingOctave;
    private List<Duration> durationList;
    private List<MelodicInterval> intervalList;
    private NoteListBuilder builder;

    public MelodyBuilderImpl(NoteListBuilder builder) {
        durationList = new ArrayList<Duration>();
        intervalList = new ArrayList<MelodicInterval>();
        this.builder = builder;
    }
    
    @Override
    public final MelodyBuilder add(Direction direction, Interval interval) {
        intervalList.add(new MelodicIntervalImpl(direction, interval));
        return this;
    }

    @Override
    public final MelodyBuilder add(Duration duration) {
        durationList.add(duration);
        return this;
    }

    @Override
    public final Melody build() {
        Melody result = new MelodyImpl(
                startingNoteName, 
                startingOctave, 
                durationList, 
                intervalList,
                builder);
        
        durationList = new ArrayList<Duration>();
        intervalList = new ArrayList<MelodicInterval>();      
        
        return result;
    }

    @Override
    public final MelodyBuilder start(NoteName noteName, int octave) {
        this.startingNoteName = noteName;
        this.startingOctave = octave;
        return this;
    }

}
