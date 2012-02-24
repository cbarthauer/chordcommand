package music.chord.decorator;

import java.util.HashMap;
import java.util.Map;


public class BasicChord implements Chord {
	private Map<ChordMember, NoteName> chordMemberMap;
	
	public BasicChord(NoteName rootNoteName) {
		chordMemberMap = new HashMap<ChordMember, NoteName>();
		chordMemberMap.put(ChordMember.ROOT, rootNoteName);
	}
	
	@Override
	public NoteName noteNameFromChordMember(ChordMember chordMember) {
		return chordMemberMap.get(chordMember);
	}

}
