/*******************************************************************************
 * Copyright (c) 2012 Chris Barthauer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Chris Barthauer - initial API and implementation
 ******************************************************************************/
package music.chord.arrangement;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class VoicePartPlayer {
    private static final int BPM = 60;
    private static final int PPQ_VALUE = 4;
    private static final int DEFAULT_CHANNEL = 0;
    private static final int DEFAULT_VELOCITY = 120;
    private static final int SLEEP_INTERVAL = 250;
    
    public void play(List<Note> noteList) {
        try {           
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequenceFromNoteList(noteList));
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

    
    private Track addNoteToTrack(Note note, Track track, int startTick) 
            throws InvalidMidiDataException {
        
        int midiNumber = note.getMidiNumber();        
        track.add(noteEvent(ShortMessage.NOTE_ON, midiNumber, startTick));
        track.add(noteEvent(ShortMessage.NOTE_OFF, midiNumber, endTick(startTick, note.getTicks(PPQ_VALUE))));

        return track;
    }


    private int endTick(int startTick, int totalTicks) {
        return startTick + totalTicks;
    }


    private MidiEvent noteEvent(int midiMessage, int midiNumber, int tick) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage();
        message.setMessage(midiMessage, DEFAULT_CHANNEL, midiNumber, DEFAULT_VELOCITY);
        MidiEvent noteEvent = new MidiEvent(message, tick);
        return noteEvent;
    }


    private Sequence sequenceFromNoteList(List<Note> noteList) throws InvalidMidiDataException {
        Sequence sequence = new Sequence(Sequence.PPQ, PPQ_VALUE);
        Track track = sequence.createTrack();
        
        int startTick = 0;
        
        for(Note note : noteList) {
            track = addNoteToTrack(note, track, startTick);
            startTick = startTick + note.getTicks(PPQ_VALUE);
        }
        
        return sequence;
    }
}
