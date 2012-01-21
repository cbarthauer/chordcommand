package music.chord;

public enum ChordMember {
	ROOT, THIRD, FIFTH;
	
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
