package music.chord.decorator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChordVoicer {
	private List<Voicing> triadVoicingList;
	private List<Voicing> seventhVoicingList;
	private VoicedChord previousVoicedChord;
	private ConcreteChordBuilder chordBuilder;
	
	public ChordVoicer(List<Voicing> triadVoicingList, List<Voicing> seventhVoicingList) {
		this.triadVoicingList = triadVoicingList;
		this.seventhVoicingList = seventhVoicingList;
		
		Collections.shuffle(triadVoicingList);
		Collections.shuffle(seventhVoicingList);
		
		chordBuilder = new ConcreteChordBuilder();
	}
	
	public List<VoicedChord> voice(List<VoicedChord> chordList) {
		System.out.println("ChordVoicer.voicer() - chordList: " + chordList);
		
		List<VoicedChord> result = new ArrayList<VoicedChord>();
		
		for(VoicedChord chord : chordList) {
			VoicedChord voicedChord = voiceClosest(chord);			
			result.add(voicedChord);
			previousVoicedChord = voicedChord;
		}
		
		return result;
	}

	private VoicedChord voiceClosest(VoicedChord chord) {
		List<Voicing> voicingList = voicingListFromChord(chord);
		Voicing selectedVoicing = null;
		VoicedChord result = null;
		
		if(previousVoicedChord == null) {
			selectedVoicing = voicingList.get(0);
			result = chordBuilder.setChord(chord)
				.setVoicing(selectedVoicing)
				.build();
		}
		else {
			for(Voicing currentVoicing : voicingList) {
				VoicedChord currentVoicedChord = chordBuilder.setChord(chord)
					.setVoicing(currentVoicing)
					.build();
				
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
