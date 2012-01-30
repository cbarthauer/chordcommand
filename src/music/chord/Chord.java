package music.chord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chord {
	private NoteName rootName;
	private Quality triadQuality;
	private Quality seventhQuality;
	private Interval thirdInterval;
	private Interval fifthInterval;
	private Interval seventhInterval;
	private List<Integer> midiNumberList;
	private List<NoteBean> noteList;
	
	public Chord(NoteName rootName, Quality triadQuality) {
		this.rootName = rootName;
		this.triadQuality = triadQuality;
		
		switch(triadQuality) {
		case AUGMENTED:
			this.thirdInterval = Interval.MAJOR_THIRD;
			this.fifthInterval = Interval.AUGMENTED_FIFTH;
			break;			
		case MAJOR:
			this.thirdInterval = Interval.MAJOR_THIRD;
			this.fifthInterval = Interval.PERFECT_FIFTH;
			break;
		case MINOR:
			this.thirdInterval = Interval.MINOR_THIRD;
			this.fifthInterval = Interval.PERFECT_FIFTH;
			break;
		case DIMINISHED:
			this.thirdInterval = Interval.MINOR_THIRD;
			this.fifthInterval = Interval.DIMINISHED_FIFTH;
			break;
		default:
			throw new RuntimeException("Unrecognized triad quality: " + triadQuality);
		}
		
		this.seventhQuality = Quality.NONE;
	}

	public Chord(NoteName rootName, Quality triadQuality, Quality seventhQuality) {
		this(rootName, triadQuality);
		this.seventhQuality = seventhQuality;
		
		switch(seventhQuality) {
		case MAJOR:
			seventhInterval = Interval.MAJOR_SEVENTH;
			break;
		case MINOR:
			seventhInterval = Interval.MINOR_SEVENTH;
			break;
		case DIMINISHED:
			seventhInterval = Interval.DIMINISHED_SEVENTH;
			break;
		default:
			throw new RuntimeException("Unrecognized seventh quality: " + seventhQuality);
		}
	}
	
	public int chromaticIndexFromChordMember(ChordMember chordMember) {
		int result = 0;
		
		switch(chordMember) {
		case ROOT:
			result = getRootNoteName().getChromaticIndex();
			break;
		case THIRD:
			result = getThirdNoteName().getChromaticIndex();
			break;
		case FIFTH:
			result = getFifthNoteName().getChromaticIndex();
			break;
		case SEVENTH:
			result = getSeventhNoteName().getChromaticIndex();
			break;
		default:
			throw new RuntimeException("Unknown chord member: " + chordMember);
		}
		
		return result;
	}
	
	public NoteName getFifthNoteName() {
		return rootName.up(fifthInterval);
	}
	
	public List<Integer> getMidiNumberList() {
		List<Integer> result = new ArrayList<Integer>();
		for(NoteBean note : noteList) {
			result.add(note.getMidiNumber());
		}
		return result;
	}

	public NoteName getRootNoteName() {
		return rootName;
	}

	public NoteName getSeventhNoteName() {
		if(seventhInterval == null) throw new RuntimeException("Triads don't have sevenths!");
		
		return rootName.up(seventhInterval);
	}

	public NoteName getThirdNoteName() {
		return rootName.up(thirdInterval);		
	}
	
	public boolean isSeventh() {
		return seventhInterval != null;
	}

	public NoteBean noteFromVoicePart(VoicePart voicePart) {
		NoteBean result = null;
		
		switch(voicePart) {
		case BASS:
			result = noteList.get(0);
			break;
		case BARITONE:
			result = noteList.get(1);
			break;
		case LEAD:
			result = noteList.get(2);
			break;
		case TENOR:
			result = noteList.get(3);
			break;
		default:
			throw new RuntimeException("Unknown voice part: " + voicePart);
		}
		
		return result;
	}

	public NoteName noteNameFromChordMember(ChordMember chordMember) {
		NoteName result = null;
		
		switch(chordMember) {
		case ROOT:
			result = this.getRootNoteName();
			break;
		
		case THIRD:
			result = this.getThirdNoteName();
			break;
			
		case FIFTH:
			result = this.getFifthNoteName();
			break;
			
		case SEVENTH:
			result = this.getSeventhNoteName();
			break;
			
		default:
			throw new RuntimeException("Unrecognized chord member: " + chordMember);
		}
		
		return result;
	}

	public void setMidiNumberList(List<Integer> midiNumberList) {
		this.midiNumberList = midiNumberList;
	}

	public void setNoteBeanList(List<NoteBean> noteList) {
		this.noteList = noteList;
		Collections.sort(this.noteList);
	}

	public String toString() {
		return "chord(" + rootName.toString() + " " + triadQuality.toString() + "): {\n" + 
	           "  root: " + rootName.toString() + ",\n" + 
			   "  third: " + getThirdNoteName().toString() + "\n" +
	           "  fifth: " + getFifthNoteName().toString() + "\n" +
	           "}\n";
	}
}
