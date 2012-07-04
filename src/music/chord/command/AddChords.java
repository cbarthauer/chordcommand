package music.chord.command;

import music.chord.engine.ChordEngine;
import music.chord.engine.protocol.ChordRequest;

public class AddChords implements Command {

	private ChordRequest request;
	private ChordEngine engine;

	public AddChords(ChordEngine engine, ChordRequest request) {
		this.request = request;
		this.engine = engine;
	}
	
	@Override
	public void execute() {
	    engine.addChords(request);
	}

}
