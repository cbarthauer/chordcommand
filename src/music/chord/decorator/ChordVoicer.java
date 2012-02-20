package music.chord.decorator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import music.chord.decorator.NoteBean;

public class ChordVoicer {
	private List<Voicing> triadVoicingList;
	private List<NoteBean> previousNoteBeanList;
	private List<Voicing> seventhVoicingList;
	
	public ChordVoicer(List<Voicing> triadVoicingList, List<Voicing> seventhVoicingList) {
		this.triadVoicingList = triadVoicingList;
		this.seventhVoicingList = seventhVoicingList;
		Collections.shuffle(triadVoicingList);
		Collections.shuffle(seventhVoicingList);
	}
	
	public List<VoicedChord> voice(List<Chord> chordList) {
		List<VoicedChord> result = new ArrayList<VoicedChord>();
		
		for(Chord chord : chordList) {
			result.add(voiceClosest(chord));
		}
		
		return result;
	}

	private VoicedChord voiceClosest(Chord chord) {
		List<Voicing> voicingList = voicingListFromChord(chord);
		List<NoteBean> result = null;
		Voicing selectedVoicing = null;
		
		if(previousNoteBeanList == null) {
			selectedVoicing = voicingList.get(0);
			result = selectedVoicing.voice(chord);
		}
		else {
			for(Voicing currentVoicing : voicingList) {
				List<NoteBean> currentList = currentVoicing.voice(chord);
				
				if(
					result == null 
					|| NoteBean.difference(currentList, previousNoteBeanList) 
						< NoteBean.difference(result, previousNoteBeanList)
				) {
					result = currentList;
					selectedVoicing = currentVoicing;
				}
			}
		}
		
		previousNoteBeanList = result;
		VoicedChord voicedChord = new VoicedChord(chord, selectedVoicing);
		System.out.println("Selected " + result);
		
		return voicedChord;
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
