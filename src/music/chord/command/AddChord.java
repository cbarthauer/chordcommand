package music.chord.command;

import music.chord.arrangement.VoicedChord;
import music.chord.grammar.ChordListRegistry;

public class AddChord implements Command {

	private String identifier;
	private VoicedChord chord;
	private ChordListRegistry reg;

	public AddChord(String identifier, VoicedChord chord, ChordListRegistry reg) {
		this.identifier = identifier;
		this.chord = chord;
		this.reg = reg;
	}
	
	@Override
	public void execute() {
		reg.add(identifier, chord);
	}

}
