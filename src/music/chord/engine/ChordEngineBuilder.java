package music.chord.engine;

import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.VoicedChordBuilder;

public final class ChordEngineBuilder {
    private ChordEngineBuilder() {
        //Hide utility class constructor.
    }

    public static ChordEngine build(
            VoicedChordBuilder triadBuilder,
            VoicedChordBuilder seventhBuilder, 
            VoicedChordBuilder ninthBuilder,
            ChordVoicer voicer,
            ChordDefinitionStructure struct) {
        return new ChordEngineImpl(triadBuilder, seventhBuilder, ninthBuilder, voicer, struct);
    }
}
