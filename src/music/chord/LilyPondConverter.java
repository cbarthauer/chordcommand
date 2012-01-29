package music.chord;

import java.util.HashMap;
import java.util.Map;

public class LilyPondConverter {
	
	private static Map<NoteName, String> lySymbolMap;
	
	static {
		lySymbolMap = new HashMap<NoteName, String>();
		
		for(NoteName noteName : NoteName.values()) {
			String lySymbol = noteName.getSymbol()
					.replaceAll("#", "sharp");
			
			if(lySymbol.length() == 2 && lySymbol.endsWith("b")) {
				lySymbol = lySymbol.substring(0, 1) + "flat";
			}
			else if(lySymbol.length() == 3 && lySymbol.endsWith("bb")) {
				lySymbol = lySymbol.substring(0, 1) + "flatflat";
			}

			lySymbolMap.put(noteName, lySymbol);
		}
	}
	
	public Map<ChordMember, String> pitchStringMapFromChord(Chord chord) {
		Map<ChordMember, String> result = new HashMap<ChordMember, String>();
		
		NoteName noteName = chord.getRootNoteName();
		result.put(ChordMember.ROOT, lySymbolMap.get(noteName));
		
		noteName = chord.getThirdNoteName();
		result.put(ChordMember.THIRD, lySymbolMap.get(noteName));
		
		noteName = chord.getFifthNoteName();
		result.put(ChordMember.FIFTH, lySymbolMap.get(noteName));
		
		noteName = chord.getSeventhNoteName();
		result.put(ChordMember.SEVENTH, lySymbolMap.get(noteName));
		
		return result;
	}

}
