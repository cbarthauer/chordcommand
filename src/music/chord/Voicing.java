package music.chord;

import java.util.ArrayList;
import java.util.List;

public class Voicing {
	public static Voicing getInstance() {
		return new Voicing();
	}
	
	private List<ChordMember> chordMemberList;
	
	private Voicing() {
		chordMemberList = new ArrayList<ChordMember>();
	}
	
	public void addChordMember(ChordMember member) {
		chordMemberList.add(member);
	}
	
	public String toString() {
		return chordMemberList.toString();
	}
}
