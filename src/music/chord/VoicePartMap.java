package music.chord;

import java.util.HashMap;
import java.util.Map;

public class VoicePartMap {
	private Map<VoicePart, Integer> voicePartToMidiNumberMap;
	
	public VoicePartMap() {
		voicePartToMidiNumberMap = new HashMap<VoicePart, Integer>();
	}
	
	public Map<VoicePart, Integer> getMidiNumberMap() {
		return voicePartToMidiNumberMap;
	}

	public void put(VoicePart part, Integer midiNumber) {
		voicePartToMidiNumberMap.put(part, midiNumber);
	}
	
	/**
	 * @param otherMap
	 * @return The total of the half-step differences between associated voice parts.
	 */
	public int difference(VoicePartMap otherMap) {
		int result = 0;
		
		for(VoicePart part : voicePartToMidiNumberMap.keySet()) {
			result = result + Math.abs(voicePartToMidiNumberMap.get(part) - otherMap.voicePartToMidiNumberMap.get(part));
		}
		
		return result;
	}
}
