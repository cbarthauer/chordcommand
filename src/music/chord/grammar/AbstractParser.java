package music.chord.grammar;

import java.util.List;

import music.chord.decorator.ChordPlayer;
import music.chord.decorator.ChordVoicer;
import music.chord.decorator.VoicedChord;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

public abstract class AbstractParser extends Parser {
    public AbstractParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    
    public AbstractParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }
    
    public abstract void setChordList(List<VoicedChord> chordList);
    
    public abstract void setChordVoicer(ChordVoicer voicer);
    
    public abstract void setChordPlayer(ChordPlayer player);
}