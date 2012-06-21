package music.chord.grammar;

import java.util.List;

import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.arrangement.Voicing;
import music.chord.arrangement.VoicingFactory;
import music.chord.base.ChordMember;

import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;

public abstract class AbstractTreeParser extends TreeParser {
    public AbstractTreeParser(TreeNodeStream input) {
        super(input);
    }
    
    public AbstractTreeParser(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public abstract void setChordDefinitionStructure(ChordDefinitionStructure struct);
    public abstract void setNinthBuilder(VoicedChordBuilder ninthBuilder);
    public abstract void setSeventhBuilder(VoicedChordBuilder seventhBuilder);
    public abstract void setTriadBuilder(VoicedChordBuilder triadBuilder);
    
    final Voicing voicingFromChordMemberList(List<ChordMember> chordMemberList) {
        return VoicingFactory.instanceFromChordMemberList(chordMemberList);
    }
}
