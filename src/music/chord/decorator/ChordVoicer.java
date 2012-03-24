package music.chord.decorator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChordVoicer {
	private List<Voicing> triadVoicingList;
	private List<Voicing> seventhVoicingList;
	private VoicedChord previousVoicedChord;
	
	public ChordVoicer(List<Voicing> triadVoicingList, List<Voicing> seventhVoicingList) {
		this.triadVoicingList = triadVoicingList;
		this.seventhVoicingList = seventhVoicingList;
		
		Collections.shuffle(triadVoicingList);
		Collections.shuffle(seventhVoicingList);
	}
	
	public List<VoicedChord> voice(List<Chord> chordList) {
		List<VoicedChord> result = new ArrayList<VoicedChord>();
		
		for(Chord chord : chordList) {
			VoicedChord voicedChord = null;
			
			if(chord instanceof VoicedChord) {
//				System.out.println("ChordVoicer.voice() - Found explicitly voiced chord: " + chord);
				voicedChord = (VoicedChord) chord;
			}
			else {
				voicedChord = voiceClosest(chord);
			}
			
			result.add(voicedChord);
			previousVoicedChord = voicedChord;
		}
		
		return result;
	}

	private VoicedChord voiceClosest(Chord chord) {
		List<Voicing> voicingList = voicingListFromChord(chord);
		Voicing selectedVoicing = null;
		VoicedChord result = null;
		
		if(previousVoicedChord == null) {
			selectedVoicing = voicingList.get(0);
			result = new VoicedChord(chord, selectedVoicing);
		}
		else {
			for(Voicing currentVoicing : voicingList) {
				VoicedChord currentVoicedChord = new VoicedChord(chord, currentVoicing);
				
				if(
					result == null 
					|| currentVoicedChord.difference(previousVoicedChord) 
						< result.difference(previousVoicedChord)
				) {
					result = currentVoicedChord;
				}
			}
		}
		
		return result;
	}


	private List<Voicing> voicingListFromChord(Chord chord) {
		if(chord instanceof SeventhChord) {
			return seventhVoicingList;
		}
		else {
			return triadVoicingList;
		}
	}
}
