package music.chord.command;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.grammar.ChordListRegistry;

public class RemoveChord implements Command {
	
	private String identifier;
    private List<Integer> indexList;
    private ChordListRegistry reg;

    public RemoveChord(String identifier, List<Integer> indexList, ChordListRegistry reg) {
	    this.identifier = identifier;
	    this.indexList = indexList;
	    this.reg = reg;
	}
	
	@Override
	public void execute() {
	    List<VoicedChord> existingList = reg.byIdentifier(identifier);
		List<VoicedChord> newList = new ArrayList<VoicedChord>();
		
		for(int i = 0; i < existingList.size(); i++) {
		    if(!indexList.contains(i)) {
		        newList.add(existingList.get(i));
		    }
		}
		
		reg.put(identifier, newList);
	}

}
