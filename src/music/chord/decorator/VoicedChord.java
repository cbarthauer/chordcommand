package music.chord.decorator;

import java.util.ArrayList;
import java.util.List;


public class VoicedChord extends ForwardingChord {
	private List<NoteBean> noteBeanList;

	public VoicedChord(Chord chord, Voicing voicing) {
		super(chord);
		noteBeanList = voicing.voice(this);
	}

	public int difference(VoicedChord chord) {
		List<NoteBean> list1 = this.noteBeanList; 
		List<NoteBean> list2 = chord.noteBeanList;
		
		if(list1.size() != list2.size()) { 
			throw new IllegalArgumentException(
					"Difference can only be computed on lists of the same size."); 
		}
		
		int result = 0;
		
		for(int i = 0; i < list1.size(); i++) {
			result = result + Math.abs(list1.get(i).getMidiNumber() - list2.get(i).getMidiNumber());
		}
		
		System.err.println(list1 + " - " + list2 + " = " + result);
		
		return result;
	}
	
	public List<Integer> getMidiNumberList() {
		List<Integer> midiNumberList = new ArrayList<Integer>();
		
		for(NoteBean note : noteBeanList) {
			midiNumberList.add(note.getMidiNumber());
		}
		
		return midiNumberList;
	}
}
