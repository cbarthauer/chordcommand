package music.chord.decorator;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class ChordPlayer {
	private static final int PPQ_VALUE = 4;
	private static final int DEFAULT_CHANNEL = 0;
	
	public void play(List<VoicedChord> chordList) {
		try {
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			
			Sequence sequence = new Sequence(Sequence.PPQ, PPQ_VALUE);
			Track track = sequence.createTrack();
			
			int startTick = 0;
			
			for(VoicedChord chord : chordList) {
				track = addChordToTrack(chord, track, startTick);
				startTick = startTick + chord.getTicks(PPQ_VALUE);
			}
			
			sequencer.setSequence(sequence);
			sequencer.setTempoInBPM(60);
			sequencer.start();
			
			while(sequencer.isRunning()) {
				Thread.sleep(250);
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

	
	private Track addChordToTrack(VoicedChord chord, Track track, int startTick) 
			throws InvalidMidiDataException {
		
		List<Integer> midiNumberList = chord.getMidiNumberList();
		
		for(Integer number : midiNumberList) {
			ShortMessage noteOnMessage = new ShortMessage();
			noteOnMessage.setMessage(ShortMessage.NOTE_ON, DEFAULT_CHANNEL, number, 120);
			MidiEvent noteOnEvent = new MidiEvent(noteOnMessage, startTick);
			track.add(noteOnEvent);
			
			ShortMessage noteOffMessage = new ShortMessage();
			noteOffMessage.setMessage(ShortMessage.NOTE_OFF, DEFAULT_CHANNEL, number, 120);
			MidiEvent noteOffEvent = new MidiEvent(noteOffMessage, startTick + chord.getTicks(PPQ_VALUE));
			track.add(noteOffEvent);
		}
		
		return track;
	}
}
