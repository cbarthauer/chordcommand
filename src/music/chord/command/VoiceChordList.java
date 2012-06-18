package music.chord.command;

import music.chord.engine.ChordEngine;
import music.chord.engine.protocol.VoiceAllRequest;

public class VoiceChordList implements Command {

    private ChordEngine engine;
    private VoiceAllRequest request;

    public VoiceChordList(ChordEngine engine, VoiceAllRequest request) {
		this.engine = engine;
		this.request = request;
	}
	
	@Override
	public void execute() {
		engine.voiceAll(request);
	}

}
