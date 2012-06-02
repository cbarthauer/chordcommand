package music.chord.grammar;

import java.util.List;

import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.ChordPlayer;
import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.VoicePartPlayer;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
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
    
    public abstract void setChordDefinitionStructure(ChordDefinitionStructure struct);
    
    public abstract void setChordList(List<VoicedChord> chordList);
    
    public abstract void setChordVoicer(ChordVoicer voicer);
    
    public abstract void setChordPlayer(ChordPlayer player);
    
    public abstract void setNinthBuilder(VoicedChordBuilder ninthBuilder);
    
    public abstract void setSeventhBuilder(VoicedChordBuilder seventhBuilder);
    
    public abstract void setTriadBuilder(VoicedChordBuilder triadBuilder);
    
    public abstract void setVoicePartPlayer(VoicePartPlayer voicePartPlayer);
    
    final Voicing voicingFromChordMemberList(List<ChordMember> chordMemberList) {
        return VoicingFactory.instanceFromChordMemberList(chordMemberList);
    }
}
