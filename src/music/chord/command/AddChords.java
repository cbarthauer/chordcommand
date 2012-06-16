package music.chord.command;

import music.chord.engine.ChordEngine;
import music.chord.engine.protocol.AddChordRequest;

public class AddChords implements Command {

	private AddChordRequest[] requests;
	private ChordEngine engine;

	public AddChords(ChordEngine engine, AddChordRequest... requests) {
		this.requests = requests;
		this.engine = engine;
	}
	
	@Override
	public void execute() {
	    engine.addChords(requests);
	}

}
