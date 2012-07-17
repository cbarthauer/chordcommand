package music.chord.arrangement.impl;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.MelodicInterval;
import music.chord.arrangement.Melody;
import music.chord.arrangement.Note;
import music.chord.arrangement.NoteListBuilder;
import music.chord.base.Duration;
import music.chord.base.NoteName;

public class MelodyImpl implements Melody {

    private NoteName startingNoteName;
    private int startingOctave;
    private List<Duration> durationList;
    private List<MelodicInterval> intervalList;
    private NoteListBuilder builder;

    public MelodyImpl(
            NoteName startingNoteName, 
            int startingOctave,
            List<Duration> durationList, 
            List<MelodicInterval> intervalList,
            NoteListBuilder builder) {

        this.startingNoteName = startingNoteName;
        this.startingOctave = startingOctave;
        this.durationList = new ArrayList<Duration>(durationList);
        this.intervalList = new ArrayList<MelodicInterval>(intervalList);
        this.builder = builder;
        
    }

    @Override
    public List<Note> getNoteList() {
        builder.add(startingNoteName, startingNoteName.getChromaticIndex(), durationList.get(0));
        builder.shiftUp(startingOctave);
        
        for(int i = 0; i < intervalList.size(); i++) {
            MelodicInterval interval = intervalList.get(i);
            Duration duration = durationList.get(i + 1);
            builder.add(
                    builder.nextNoteName(interval), 
                    builder.nextMidiNumber(interval), 
                    duration);
        }
        
        return builder.build();
    }

}
