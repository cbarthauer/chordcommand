package music.chord.command;

import java.util.List;

import music.chord.arrangement.VoicedChord;

public class InsertBefore implements Command {

	private List<VoicedChord> chordList;
	private VoicedChord chord;
	private int index;
	
	public InsertBefore(List<VoicedChord> chordList, VoicedChord chord, int index) {
		this.chordList = chordList;
		this.chord = chord;
		this.index = index;
	}
	
	@Override
	public void execute() {
		chordList.add(null);
		
		for(int i = chordList.size() - 1; i > index ; i--) {
			chordList.set(i, chordList.get(i - 1));
		}
		
		chordList.set(index, chord);
	}

}
