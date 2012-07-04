package music.chord.command;

import music.chord.engine.ChordEngine;
import music.chord.engine.protocol.InsertChordRequest;

public class InsertBefore implements Command {

	private ChordEngine engine;
    private InsertChordRequest request;

    public InsertBefore(
	        ChordEngine engine,
	        InsertChordRequest request) {
	    this.engine = engine;
	    this.request = request;
	}
	
	@Override
	public void execute() {
	    engine.insertChords(request);
	}

}
