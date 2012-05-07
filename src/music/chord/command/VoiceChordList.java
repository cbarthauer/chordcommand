package music.chord.command;

import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.builder.ChordVoicer;

public class VoiceChordList implements Command {

	private List<VoicedChord> chordList;
	private ChordVoicer voicer;
	
	public VoiceChordList(List<VoicedChord> chordList, ChordVoicer voicer) {
		this.chordList = chordList;
		this.voicer = voicer;
	}
	
	@Override
	public void execute() {
		List<VoicedChord> tempList = voicer.voice(chordList);
		chordList.clear();
		chordList.addAll(tempList);
	}

}
