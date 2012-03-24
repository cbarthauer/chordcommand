package music.chord.command;

import java.util.List;

import music.chord.decorator.Chord;

public class AddChord implements Command {

	private List<Chord> chordList;
	private Chord chord;

	public AddChord(List<Chord> chordList, Chord chord) {
		this.chordList = chordList;
		this.chord = chord;
	}
	
	@Override
	public void execute() {
		chordList.add(chord);
	}

}
