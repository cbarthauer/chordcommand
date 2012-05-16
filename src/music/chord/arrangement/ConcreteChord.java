package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.Duration;
import music.chord.decorator.Chord;
import music.chord.decorator.ForwardingChord;


public class ConcreteChord extends ForwardingChord implements VoicedChord {	
	private List<Note> noteList;
	private Voicing voicing;
	private Duration duration;

	public ConcreteChord(Chord chord, Voicing voicing, Duration duration) {
		super(chord);
		this.voicing = voicing;
		noteList = voicing.voice(chord);
		this.duration = duration;
	}

	public final int difference(VoicedChord chord) {
		List<Note> list1 = this.noteList; 
		List<Note> list2 = chord.getNoteBeanList();
		
		if(list1.size() != list2.size()) { 
			throw new IllegalArgumentException(
					"Difference can only be computed on lists of the same size."); 
		}
		
		int result = 0;
		
		for(int i = 0; i < list1.size(); i++) {
			result = result + Math.abs(list1.get(i).getMidiNumber() - list2.get(i).getMidiNumber());
		}
		
		return result;
	}

	public final Duration getDuration() {
		return duration;
	}

	public final List<Integer> getMidiNumberList() {
		List<Integer> midiNumberList = new ArrayList<Integer>();
		
		for(Note note : noteList) {
			midiNumberList.add(note.getMidiNumber());
		}
		
		return midiNumberList;
	}
	
	@Override
	public final List<Note> getNoteBeanList() {
		return noteList;
	}
	
	public final int getTicks(int ppq) {
	    float conversionFactor = duration.getPpqConversionFactor();
		return Math.round(ppq * conversionFactor);
	}

	public final Voicing getVoicing() {
		return voicing;
	}
}
