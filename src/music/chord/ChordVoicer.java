package music.chord;

import java.util.Collections;
import java.util.List;

public class ChordVoicer {
	private static final int octaveShift = 48;
	private List<Voicing> triadVoicingList;
	private List<NoteBean> previousNoteBeanList;
	private List<Voicing> seventhVoicingList;
	
	public ChordVoicer(List<Voicing> triadVoicingList, List<Voicing> seventhVoicingList) {
		this.triadVoicingList = triadVoicingList;
		this.seventhVoicingList = seventhVoicingList;
		Collections.shuffle(triadVoicingList);
		Collections.shuffle(seventhVoicingList);
	}
	
	public List<Chord> voice(List<Chord> chordList) {
		for(Chord chord : chordList) {
			voiceClosest(chord);
		}
		
		return chordList;
	}

	private Chord voiceClosest(Chord chord) {
		List<Voicing> voicingList = voicingListFromChord(chord);
		List<NoteBean> result = null;
		Voicing selectedVoicing = null;
		
		if(previousNoteBeanList == null) {
			selectedVoicing = voicingList.get(0);
			result = noteBeanListFromChord(chord, selectedVoicing);
		}
		else {
			for(Voicing currentVoicing : voicingList) {
				List<NoteBean> currentList = noteBeanListFromChord(chord, currentVoicing);
				
				if(
					result == null 
					|| NoteBean.difference(currentList, previousNoteBeanList) < NoteBean.difference(result, previousNoteBeanList)
				) {
					result = currentList;
					selectedVoicing = currentVoicing;
				}
			}
		}
		
		previousNoteBeanList = result;
		chord.setNoteBeanList(result);
		System.out.println("Selected " + result);
		
		return chord;
	}

	private List<NoteBean> noteBeanListFromChord(Chord chord, Voicing voicing) {
		List<NoteBean> noteBeanList = voicing.voice(chord);
		noteBeanList = shiftUp(noteBeanList, octaveShift);		
		return noteBeanList;
	}


	private List<NoteBean> shiftUp(List<NoteBean> noteBeanList, int numberHalfSteps) {
		for(NoteBean noteBean : noteBeanList) {
			noteBean.setMidiNumber(noteBean.getMidiNumber() + numberHalfSteps);
		}
		
		return noteBeanList;
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
