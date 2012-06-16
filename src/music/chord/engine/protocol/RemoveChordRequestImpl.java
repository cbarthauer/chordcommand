package music.chord.engine.protocol;

import java.util.List;

final class RemoveChordRequestImpl implements RemoveChordRequest {

    private Identifier identifier;
    private List<Integer> positions;

    RemoveChordRequestImpl(Identifier identifier, List<Integer> positions) {
        this.identifier = identifier;
        this.positions = positions;
    }

    @Override
    public final Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public final List<Integer> getPositions() {
        return positions;
    }

}
