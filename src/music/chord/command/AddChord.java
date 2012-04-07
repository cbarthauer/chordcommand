package music.chord.command;

import java.util.List;

import music.chord.decorator.VoicedChord;

public class AddChord implements Command {

	private List<VoicedChord> chordList;
	private VoicedChord chord;

	public AddChord(List<VoicedChord> chordList, VoicedChord chord) {
		this.chordList = chordList;
		this.chord = chord;
	}
	
	@Override
	public void execute() {
		chordList.add(chord);
	}

}
