package music.chord.command;

import java.util.List;

import music.chord.decorator.VoicedChord;

public class RemoveChord implements Command {

	private int index;
	private List<VoicedChord> chordList;
	
	public RemoveChord(List<VoicedChord> chordList, int index) {
		this.index = index;
		this.chordList = chordList;
	}
	
	@Override
	public void execute() {
		chordList.remove(index);
	}

}
