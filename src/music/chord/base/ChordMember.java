package music.chord.base;

public enum ChordMember {
	ROOT, THIRD, FIFTH, SEVENTH, NINTH;
	
	public static ChordMember memberFromName(String name) {
		ChordMember result = null;
		
		for(ChordMember member : ChordMember.values()) {
			if(member.name().equalsIgnoreCase(name)) {
				result = member;
				break;
			}
		}
		
		return result;
	}
}
