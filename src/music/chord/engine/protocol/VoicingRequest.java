package music.chord.engine.protocol;

import java.util.List;

import music.chord.arrangement.Voicing;

public interface VoicingRequest {
    public Identifier getIdentifier();
    public List<Integer> getPositions();
    public Voicing getVoicing();
}
