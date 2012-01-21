package music.chord;

import java.util.HashMap;
import java.util.Map;

public class VoicePartMap {
	private Map<VoicePart, Integer> map;
	
	public VoicePartMap() {
		map = new HashMap<VoicePart, Integer>();
	}
	
	public Map<VoicePart, Integer> getMap() {
		return map;
	}

	public void put(VoicePart part, Integer midiNumber) {
		map.put(part, midiNumber);
	}
	
	/**
	 * @param otherMap
	 * @return The total of the half-step differences between associated voice parts.
	 */
	public int difference(VoicePartMap otherMap) {
		int result = 0;
		
		for(VoicePart part : map.keySet()) {
			result = result + Math.abs(map.get(part) - otherMap.map.get(part));
		}
		
		return result;
	}
}
