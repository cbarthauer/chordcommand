package music.chord.arrangement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.decorator.DerivedChordBuilder;

public class ChordVoicer {
	private List<Voicing> triadVoicingList;
	private List<Voicing> seventhVoicingList;
	private DerivedChordBuilder chordBuilder;
	
	public ChordVoicer(
			List<Voicing> triadVoicingList, 
			List<Voicing> seventhVoicingList, 
			DerivedChordBuilder chordBuilder) {
		
		this.triadVoicingList = triadVoicingList;
		this.seventhVoicingList = seventhVoicingList;
		this.chordBuilder = chordBuilder;
	}
	
	public List<VoicedChord> voice(List<VoicedChord> chordList) {
		List<VoicedChord> result = new ArrayList<VoicedChord>();
		
		if(chordList.size() == 1) {
			result.add(chordList.get(0));
		}
		else {
			result = voice(chordList.get(0), chordList.subList(1, chordList.size()));
		}
		
		return result;
	}
	
	public List<VoicedChord> voice(VoicedChord startingChord, List<VoicedChord> chordList) {
		List<VoicedChord> result = new ArrayList<VoicedChord>();
		result.add(startingChord);
		
		VoicedChord previousChord = startingChord;
		
		for(VoicedChord chord : chordList) {
			VoicedChord voicedChord = voiceClosest(previousChord, chord);
			result.add(voicedChord);
			previousChord = voicedChord;
		}
		
		
		return result;
	}

	public Map<Integer, List<VoicedChord>> voicedChordDifferenceMap(
			VoicedChord previousChord, 
			VoicedChord newChord) {
		
		List<Voicing> voicingList = voicingListFromVoicing(newChord.getVoicing());
		Map<Integer, List<VoicedChord>> diffMap = new HashMap<Integer, List<VoicedChord>>();
		
		for(Voicing voicing : voicingList) {
			VoicedChord chord = chordBuilder.setChord(newChord)
				.setVoicing(voicing)
				.buildVoicedChord();
			Integer difference = chord.difference(previousChord);
			
			List<VoicedChord> chordList = 
				diffMap.get(difference) == null 
				? new ArrayList<VoicedChord>() 
				: diffMap.get(difference);
			
			chordList.add(chord);
			
			diffMap.put(difference, chordList);
		}
		
		return diffMap;
	}
	
	public List<VoicingComparison> voicingComparisonList(
			VoicedChord previousChord, 
			VoicedChord newChord) {
		
		List<Voicing> voicingList = voicingListFromVoicing(newChord.getVoicing());
		List<VoicingComparison> result = new ArrayList<VoicingComparison>();
		
		for(Voicing voicing : voicingList) {
			VoicedChord chord = chordBuilder.setChord(newChord)
				.setVoicing(voicing)
				.buildVoicedChord();
			Integer difference = chord.difference(previousChord);			
			result.add(new VoicingComparison(difference, voicing));
		}
		
		Collections.sort(result);
		
		return result;
	}
	
	private VoicedChord voiceClosest(VoicedChord previousChord, VoicedChord newChord) {
		List<Voicing> voicingList = voicingListFromVoicing(newChord.getVoicing());
		VoicedChord result = null;
		
		for(Voicing currentVoicing : voicingList) {
			VoicedChord currentVoicedChord = chordBuilder.setChord(newChord)
				.setVoicing(currentVoicing)
				.buildVoicedChord();
			
			if(
				result == null 
				|| currentVoicedChord.difference(previousChord) 
					< result.difference(previousChord)
			) {
				result = currentVoicedChord;
			}
		}
		
		return result;
	}


	private List<Voicing> voicingListFromVoicing(Voicing voicing) {
		if(voicing instanceof SeventhVoicing) {
			return seventhVoicingList;
		}
		else {
			return triadVoicingList;
		}
	}
}
