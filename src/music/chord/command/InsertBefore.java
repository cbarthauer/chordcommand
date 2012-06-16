package music.chord.command;

import music.chord.engine.ChordEngine;
import music.chord.engine.protocol.InsertChordRequest;

public class InsertBefore implements Command {

	private ChordEngine engine;
    private InsertChordRequest[] requests;

    public InsertBefore(
	        ChordEngine engine,
	        InsertChordRequest... requests) {
	    this.engine = engine;
	    this.requests = requests;
	}
	
	@Override
	public void execute() {
	    engine.insertChords(requests);
	}

}
