package music.chord.engine;

import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.DerivedChordBuilder;
import music.chord.arrangement.QualityRegistry;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.grammar.ChordListRegistry;

public final class ChordEngineBuilder {
    private ChordEngineBuilder() {
        //Hide utility class constructor.
    }

    public static ChordEngine build(
            VoicedChordBuilder triadBuilder,
            VoicedChordBuilder seventhBuilder, 
            VoicedChordBuilder ninthBuilder,
            ChordVoicer voicer,
            QualityRegistry qualities) {
        
        return new ChordEngineImpl(
                triadBuilder, 
                seventhBuilder, 
                ninthBuilder,
                new DerivedChordBuilder(),
                new ChordListRegistry(),
                voicer,
                qualities);
    }
}
