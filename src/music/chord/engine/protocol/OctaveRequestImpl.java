package music.chord.engine.protocol;

import java.util.ArrayList;
import java.util.List;

class OctaveRequestImpl implements OctaveRequest {

    private Identifier id;
    private ArrayList<Integer> positions;
    private int octave;

    OctaveRequestImpl(Identifier id, List<Integer> positions, int octave) {
        this.id = id;
        this.positions = new ArrayList<Integer>(positions);
        this.octave = octave;
    }
    
    @Override
    public Identifier getIdentifier() {
        return id;
    }

    @Override
    public int getOctave() {
        return octave;
    }

    @Override
    public List<Integer> getPositions() {
        return new ArrayList<Integer>(positions);
    }

}
