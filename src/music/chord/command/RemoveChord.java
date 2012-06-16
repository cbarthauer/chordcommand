package music.chord.command;

import music.chord.engine.ChordEngine;
import music.chord.engine.protocol.RemoveChordRequest;

public class RemoveChord implements Command {
	
    private ChordEngine engine;
    private RemoveChordRequest request;

    public RemoveChord(ChordEngine engine, RemoveChordRequest request) {
	    this.engine = engine;
	    this.request = request;
	}
	
	@Override
	public void execute() {
	    engine.removeChords(request);
	}

}
