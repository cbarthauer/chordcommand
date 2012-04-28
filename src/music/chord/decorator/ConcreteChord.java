package music.chord.decorator;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.Duration;


class ConcreteChord extends ForwardingChord implements VoicedChord {	
	private List<NoteBean> noteBeanList;
	private Voicing voicing;
	private Duration duration;

	ConcreteChord(Chord chord, Voicing voicing, Duration duration) {
		super(chord);
		this.voicing = voicing;
		noteBeanList = voicing.voice(chord);
		this.duration = duration;
	}

	public final int difference(VoicedChord chord) {
		List<NoteBean> list1 = this.noteBeanList; 
		List<NoteBean> list2 = chord.getNoteBeanList();
		
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
		
		for(NoteBean note : noteBeanList) {
			midiNumberList.add(note.getMidiNumber());
		}
		
		return midiNumberList;
	}
	
	@Override
	public final List<NoteBean> getNoteBeanList() {
		return noteBeanList;
	}
	
	public final int getTicks(int ppq) {
		return Math.round(ppq * duration.getPpqConversionFactor());
	}

	public final Voicing getVoicing() {
		return voicing;
	}
}
