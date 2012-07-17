package music.chord.arrangement.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import music.chord.arrangement.Melody;
import music.chord.arrangement.MelodyPlayer;
import music.chord.arrangement.Note;

public final class MelodyPlayerImpl implements MelodyPlayer {
    private static final class SequenceBuilder {
        private Sequence sequence;

        SequenceBuilder() {
            try {
                sequence = new Sequence(Sequence.PPQ, PPQ_VALUE);
            } catch (InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
        }
        
        private Track addNoteToTrack(Note note, Track track, int startTick) 
                throws InvalidMidiDataException {
            
            int midiNumber = note.getMidiNumber();        
            track.add(noteEvent(ShortMessage.NOTE_ON, midiNumber, startTick));
            track.add(
                    noteEvent(
                            ShortMessage.NOTE_OFF, 
                            midiNumber, 
                            endTick(startTick, note.getTicks(PPQ_VALUE))));

            return track;
        }
        
        private int endTick(int startTick, int totalTicks) {
            return startTick + totalTicks;
        }
        
        private MidiEvent noteEvent(int midiMessage, int midiNumber, int tick) 
                throws InvalidMidiDataException {
            ShortMessage message = new ShortMessage();
            message.setMessage(midiMessage, DEFAULT_CHANNEL, midiNumber, DEFAULT_VELOCITY);
            MidiEvent noteEvent = new MidiEvent(message, tick);
            return noteEvent;
        }


        final SequenceBuilder add(List<Note> noteList) throws InvalidMidiDataException {
            Track track = sequence.createTrack();
            
            int startTick = 0;
            
            for(Note note : noteList) {
                track = addNoteToTrack(note, track, startTick);
                startTick = startTick + note.getTicks(PPQ_VALUE);
            }
            
            return this;
        }


        final Sequence build() {
            Sequence result = sequence;
            
            try {
                sequence = new Sequence(Sequence.PPQ, PPQ_VALUE);
            } catch (InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
            
            return result;
        }
    }
    private static final int BPM = 60;
    private static final int PPQ_VALUE = 4;
    private static final int DEFAULT_CHANNEL = 0;
    private static final int DEFAULT_VELOCITY = 120;
    
    private static final int SLEEP_INTERVAL = 250;

    private List<List<Note>> notes;
    
    public MelodyPlayerImpl() {
        this.notes = new ArrayList<List<Note>>();
    }

    @Override
    public final MelodyPlayer add(List<Note> noteList) {
        this.notes.add(new ArrayList<Note>(noteList));
        return this;
    }

    
    @Override
    public MelodyPlayer add(Melody melody) {
        notes.add(melody.getNoteList());
        return this;
    }


    @Override
    public final void play() {
        try {           
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            
            SequenceBuilder builder = new SequenceBuilder();
            for(List<Note> noteList : notes) {
                builder.add(noteList);
            }
            
            sequencer.setSequence(builder.build());
            sequencer.setTempoInBPM(BPM);
            sequencer.start();
            
            while(sequencer.isRunning()) {
                Thread.sleep(SLEEP_INTERVAL);
            }
            
            sequencer.close();
        } 
        catch (MidiUnavailableException e) {
            e.printStackTrace();
        } 
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
