package music.chord.grammar;

import java.util.List;

import music.chord.arrangement.ChordPlayer;
import music.chord.arrangement.SeventhVoicing;
import music.chord.arrangement.TriadVoicing;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.base.ChordMember;
import music.chord.builder.ChordVoicer;
import music.chord.builder.SeventhBuilder;
import music.chord.builder.TriadBuilder;

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
    
    public abstract void setSeventhBuilder(SeventhBuilder seventhBuilder);
    
    public abstract void setTriadBuilder(TriadBuilder triadBuilder);
    
    final Voicing voicingFromChordMemberList(List<ChordMember> chordMemberList) {
        return VoicingFactory.instanceFromChordMemberList(chordMemberList);
    }
}
