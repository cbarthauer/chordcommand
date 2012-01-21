package music.chord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChordVoicer {
	private List<VoicingStrategy> voicingList;
	private VoicePartMap previousChordMap;
	
	public ChordVoicer() {
		voicingList = new ArrayList<VoicingStrategy>();
		voicingList.add(new Voicing1());
		voicingList.add(new Voicing2());
		voicingList.add(new Voicing3());
		voicingList.add(new Voicing4());
		voicingList.add(new Voicing5());
		Collections.shuffle(voicingList);
	}
	
	public List<Integer> midiNumbersFromChord(Chord chord) {
		VoicePartMap partMap = findClosest(chord, previousChordMap);
		List<Integer> midiNumberList = new ArrayList<Integer>();
		midiNumberList.addAll(partMap.getMap().values());
		previousChordMap = partMap;
		Collections.sort(midiNumberList);
		System.out.println(midiNumberList);
		return midiNumberList;
	}

	private VoicePartMap partMapFromChord(Chord chord, VoicingStrategy voicing) {
		VoicePartMap partMap = new VoicePartMap();
		List<Integer> midiNumberList = voicing.voice(chord);
		midiNumberList = shiftUp(midiNumberList, 48);
		
		partMap.put(VoicePart.BASS, midiNumberList.get(0));
		partMap.put(VoicePart.BARITONE, midiNumberList.get(1));
		partMap.put(VoicePart.LEAD, midiNumberList.get(2));
		partMap.put(VoicePart.TENOR, midiNumberList.get(3));
		
		return partMap;
	}


	private VoicePartMap findClosest(Chord chord, VoicePartMap previousChordMap) {
		VoicePartMap result = null;
		VoicingStrategy selected = null;
		
		if(previousChordMap == null) {
			selected = voicingList.get(0);
			result = partMapFromChord(chord, selected);
		}
		else {
			for(VoicingStrategy strategy : voicingList) {
				VoicePartMap currentMap = partMapFromChord(chord, strategy);
				
				if(result == null || currentMap.difference(previousChordMap) < result.difference(previousChordMap)) {
					result = currentMap;
					selected = strategy;
				}
			}
		}
		
		System.out.print(selected.getClass() + ": ");
		
		return result;
	}


	private List<Integer> shiftUp(List<Integer> midiNumberList, int numberHalfSteps) {
		for(int i = 0; i < midiNumberList.size(); i++) {
			midiNumberList.set(i, midiNumberList.get(i) + numberHalfSteps);
		}
		
		return midiNumberList;
	}
}
