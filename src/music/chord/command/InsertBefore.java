package music.chord.command;

import java.util.LinkedList;
import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.grammar.ChordListRegistry;

public class InsertBefore implements Command {

    private String identifier;
    private int insertionIndex;
    private List<VoicedChord> chordsForInsert;
    private ChordListRegistry reg;
	
	public InsertBefore(
	        String identifier,
	        int insertionIndex,
	        List<VoicedChord> chordsForInsert, 
	        ChordListRegistry reg) {
	    this.identifier = identifier;
	    this.insertionIndex = insertionIndex;
	    this.chordsForInsert = chordsForInsert;
	    this.reg = reg;
	}
	
	@Override
	public void execute() {
	    LinkedList<VoicedChord> tmpList = new LinkedList<VoicedChord>(reg.byIdentifier(identifier));
		tmpList.addAll(insertionIndex, chordsForInsert);
		reg.put(identifier, tmpList);
	}

}
