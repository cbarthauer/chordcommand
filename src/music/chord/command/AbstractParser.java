package music.chord.command;

import java.util.List;

import music.chord.decorator.Chord;
import music.chord.decorator.ChordPlayer;
import music.chord.decorator.ChordVoicer;

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
    
    public abstract void setChordList(List<Chord> chordList);
    
    public abstract void setChordVoicer(ChordVoicer voicer);
    
    public abstract void setChordPlayer(ChordPlayer player);
}