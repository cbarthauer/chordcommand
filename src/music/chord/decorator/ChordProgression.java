package music.chord.decorator;

import java.util.List;

public class ChordProgression {
	public static ChordProgression getInstance() {
		return new ChordProgression();
	}
	
	private List<Chord> chordList;
	private VoicingManager voicingManager;

	public List<Chord> getChordList() {
		return chordList;
	}
	
	public VoicingManager getVoicingManager() {
		return voicingManager;
	}
	
	public ChordProgression setChordList(List<Chord> chordList) {
		this.chordList = chordList;
		return this;
	}

	public ChordProgression setVoicingManager(VoicingManager voicingManager) {
		this.voicingManager = voicingManager;
		return this;
	}
	
	
}
