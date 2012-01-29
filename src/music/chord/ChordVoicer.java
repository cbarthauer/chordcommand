package music.chord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChordVoicer {
	private static final int octaveShift = 48;
	private List<Voicing> triadVoicingList;
	private VoicePartMap previousChordMap;
	private List<Voicing> seventhVoicingList;
	
	public ChordVoicer(List<Voicing> triadVoicingList, List<Voicing> seventhVoicingList) {
		this.triadVoicingList = triadVoicingList;
		this.seventhVoicingList = seventhVoicingList;
		Collections.shuffle(triadVoicingList);
		Collections.shuffle(seventhVoicingList);
	}
	
	public List<Chord> voice(List<Chord> chordList) {
		for(Chord chord : chordList) {
			chord.setMidiNumberList(midiNumbersFromChord(chord));
		}
		
		return chordList;
	}

	private VoicePartMap findClosest(Chord chord, VoicePartMap previousChordMap) {
		List<Voicing> voicingList = voicingListFromChord(chord);
		VoicePartMap result = null;
		Voicing selectedVoicing = null;
		
		if(previousChordMap == null) {
			selectedVoicing = voicingList.get(0);
			result = partMapFromChord(chord, selectedVoicing);
		}
		else {
			for(Voicing currentVoicing : voicingList) {
				VoicePartMap currentMap = partMapFromChord(chord, currentVoicing);
				
				if(result == null || currentMap.difference(previousChordMap) < result.difference(previousChordMap)) {
					result = currentMap;
					selectedVoicing = currentVoicing;
				}
			}
		}
		
		System.out.print("Voicing - " + selectedVoicing + ": ");
		
		return result;
	}


	private List<Integer> midiNumbersFromChord(Chord chord) {
		VoicePartMap partMap = findClosest(chord, previousChordMap);
		List<Integer> midiNumberList = new ArrayList<Integer>();
		midiNumberList.addAll(partMap.getMidiNumberMap().values());
		previousChordMap = partMap;
		Collections.sort(midiNumberList);
		System.out.println(midiNumberList);
		return midiNumberList;
	}

	private VoicePartMap partMapFromChord(Chord chord, Voicing voicing) {
		VoicePartMap partMap = new VoicePartMap();
		List<Integer> midiNumberList = voicing.voice(chord);
		midiNumberList = shiftUp(midiNumberList, octaveShift);
		
		partMap.put(VoicePart.BASS, midiNumberList.get(0));
		partMap.put(VoicePart.BARITONE, midiNumberList.get(1));
		partMap.put(VoicePart.LEAD, midiNumberList.get(2));
		partMap.put(VoicePart.TENOR, midiNumberList.get(3));
		
		return partMap;
	}


	private List<Integer> shiftUp(List<Integer> midiNumberList, int numberHalfSteps) {
		for(int i = 0; i < midiNumberList.size(); i++) {
			midiNumberList.set(i, midiNumberList.get(i) + numberHalfSteps);
		}
		
		return midiNumberList;
	}

	private List<Voicing> voicingListFromChord(Chord chord) {
		if(chord.isSeventh()) {
			return seventhVoicingList;
		}
		else {
			return triadVoicingList;
		}
	}
}
