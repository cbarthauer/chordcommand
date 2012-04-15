package music.chord.decorator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChordVoicer {
	private List<Voicing> triadVoicingList;
	private List<Voicing> seventhVoicingList;
	private VoicedChord previousVoicedChord;
	private DerivedChordBuilder chordBuilder;
	
	public ChordVoicer(List<Voicing> triadVoicingList, List<Voicing> seventhVoicingList) {
		this.triadVoicingList = triadVoicingList;
		this.seventhVoicingList = seventhVoicingList;
		
		Collections.shuffle(triadVoicingList);
		Collections.shuffle(seventhVoicingList);
		
		chordBuilder = new DerivedChordBuilder();
	}
	
	public List<VoicedChord> voice(List<VoicedChord> chordList) {
		System.out.println("ChordVoicer.voicer() - chordList: " + chordList);
		
		List<VoicedChord> result = new ArrayList<VoicedChord>();
		
		for(VoicedChord chord : chordList) {
			VoicedChord voicedChord = voiceClosest(chord);			
			result.add(voicedChord);
			previousVoicedChord = voicedChord;
		}
		
		reset();
		
		return result;
	}

	private void reset() {
		previousVoicedChord = null;
		Collections.shuffle(triadVoicingList);
		Collections.shuffle(seventhVoicingList);
	}

	private VoicedChord voiceClosest(VoicedChord chord) {
		List<Voicing> voicingList = voicingListFromVoicing(chord.getVoicing());
		Voicing selectedVoicing = null;
		VoicedChord result = null;
		
		if(previousVoicedChord == null) {
			selectedVoicing = voicingList.get(0);
			result = chordBuilder.setChord(chord)
				.setVoicing(selectedVoicing)
				.buildVoicedChord();
		}
		else {
			for(Voicing currentVoicing : voicingList) {
				VoicedChord currentVoicedChord = chordBuilder.setChord(chord)
					.setVoicing(currentVoicing)
					.buildVoicedChord();
				
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


	private List<Voicing> voicingListFromVoicing(Voicing voicing) {
		if(voicing instanceof SeventhVoicing) {
			return seventhVoicingList;
		}
		else {
			return triadVoicingList;
		}
	}
}
