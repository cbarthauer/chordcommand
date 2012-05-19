package music.chord.grammar;

import java.util.List;

import music.chord.arrangement.ChordPlayer;
import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.SeventhBuilder;
import music.chord.arrangement.TriadBuilder;
import music.chord.arrangement.VoicePartPlayer;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.base.ChordMember;

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
    
    public abstract void setVoicePartPlayer(VoicePartPlayer voicePartPlayer);
    
    final Voicing voicingFromChordMemberList(List<ChordMember> chordMemberList) {
        return VoicingFactory.instanceFromChordMemberList(chordMemberList);
    }
}
