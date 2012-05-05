package music.chord.decorator;

import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicingManager;

public class ChordProgression {
	public static ChordProgression getInstance() {
		return new ChordProgression();
	}
	
	private List<VoicedChord> chordList;
	private VoicingManager voicingManager;

	public List<VoicedChord> getChordList() {
		return chordList;
	}
	
	public VoicingManager getVoicingManager() {
		return voicingManager;
	}
	
	public ChordProgression setChordList(List<VoicedChord> chordList) {
		this.chordList = chordList;
		return this;
	}

	public ChordProgression setVoicingManager(VoicingManager voicingManager) {
		this.voicingManager = voicingManager;
		return this;
	}
	
	
}
