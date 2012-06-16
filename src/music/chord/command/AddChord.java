package music.chord.command;

import music.chord.arrangement.VoicedChord;
import music.chord.engine.protocol.Identifier;
import music.chord.grammar.ChordListRegistry;

public class AddChord implements Command {

	private Identifier identifier;
	private VoicedChord chord;
	private ChordListRegistry reg;

	public AddChord(Identifier identifier, VoicedChord chord, ChordListRegistry reg) {
		this.identifier = identifier;
		this.chord = chord;
		this.reg = reg;
	}
	
	@Override
	public void execute() {
		reg.add(identifier, chord);
	}

}
