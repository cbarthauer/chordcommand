package music.chord.engine.protocol;

import java.util.List;


public interface RemoveChordRequest {
    public Identifier getIdentifier();
    public List<Integer> getPositions();
}
