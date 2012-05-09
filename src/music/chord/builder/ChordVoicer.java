package music.chord.builder;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicingComparison;
import music.chord.arrangement.VoicingStrategy;

public class ChordVoicer {
	private VoicingStrategy strategy;
	
	public ChordVoicer(VoicingStrategy strategy) {	    
		this.strategy = strategy;
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
			VoicedChord voicedChord = strategy.voice(previousChord, chord);
			result.add(voicedChord);
			previousChord = voicedChord;
		}
		
		
		return result;
	}
	
	public List<VoicingComparison> voicingComparisonList(
			VoicedChord previousChord, 
			VoicedChord newChord) {
		
		return strategy.voicingComparisonList(previousChord, newChord);
	}
}
