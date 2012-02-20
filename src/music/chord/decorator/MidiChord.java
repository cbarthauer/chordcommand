package music.chord.decorator;

import java.util.ArrayList;
import java.util.List;

import music.chord.decorator.NoteBean;

public class MidiChord extends ForwardingChord {
	
	private VoicedChord chord;

	public MidiChord(VoicedChord chord) {
		super(chord);
		this.chord = chord;
	}
	
	public List<Integer> getMidiNumberList() {
		List<Integer> midiNumberList = new ArrayList<Integer>();
		
		List<NoteBean> noteList = chord.getNoteBeanList();
		
		for(NoteBean note : noteList) {
			midiNumberList.add(note.getMidiNumber());
		}
		
		return midiNumberList;
	}
}
