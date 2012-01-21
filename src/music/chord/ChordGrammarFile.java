package music.chord;

import java.util.List;

public class ChordGrammarFile {
	public static ChordGrammarFile getInstance() {
		return new ChordGrammarFile();
	}
	
	private List<Chord> chordList;
	private VoicingManager voicingManager;

	public List<Chord> getChordList() {
		return chordList;
	}
	
	public VoicingManager getVoicingManager() {
		return voicingManager;
	}
	
	public ChordGrammarFile setChordList(List<Chord> chordList) {
		this.chordList = chordList;
		return this;
	}

	public ChordGrammarFile setVoicingManager(VoicingManager voicingManager) {
		this.voicingManager = voicingManager;
		return this;
	}
	
	
}
