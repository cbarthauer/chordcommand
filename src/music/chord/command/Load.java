package music.chord.command;

import music.chord.engine.ChordEngine;
import music.chord.engine.protocol.LoadRequest;

public class Load implements Command {

    private ChordEngine engine;
    private LoadRequest request;

    public Load(ChordEngine engine, LoadRequest request) {
        this.engine = engine;
        this.request = request;
    }
    
    @Override
    public final void execute() {
        engine.load(request);
    }

}
