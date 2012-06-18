package music.chord.engine.protocol;

import java.util.List;

public interface OctaveRequest {
    public Identifier getIdentifier();
    public int getOctave();
    public List<Integer> getPositions();
}
