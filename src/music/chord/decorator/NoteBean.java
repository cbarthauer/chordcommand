package music.chord.decorator;

import java.util.List;

public class NoteBean implements Comparable<NoteBean> {
	public static int difference(List<NoteBean> list1, List<NoteBean> list2) {
		if(list1.size() != list2.size()) { throw new IllegalArgumentException("Difference can only be computed on lists of the same size."); }
		
		int result = 0;
		
		for(int i = 0; i < list1.size(); i++) {
			result = result + Math.abs(list1.get(i).getMidiNumber() - list2.get(i).getMidiNumber());
		}
		
		System.err.println(list1 + " - " + list2 + " = " + result);
		
		return result;
	}
	
	
	private NoteName noteName;
	private Integer midiNumber;

	public NoteBean(NoteName noteName, Integer midiNumber) {
		this.noteName = noteName;
		this.midiNumber = midiNumber;
	}

	@Override
	public int compareTo(NoteBean otherNote) {
		return midiNumber.compareTo(otherNote.midiNumber);
	}

	public Integer getMidiNumber() {
		return midiNumber;
	}

	public NoteName getNoteName() {
		return noteName;
	}

	public void setMidiNumber(int midiNumber) {
		this.midiNumber = midiNumber;
	}
	
	public String toString() {
		return noteName + "(" + midiNumber + ")";
	}
}
