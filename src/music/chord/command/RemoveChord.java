package music.chord.command;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.VoicedChord;

public class RemoveChord implements Command {

    private List<VoicedChord> chordList;
	private List<Integer> indexList;
	
	public RemoveChord(List<Integer> indexList, List<VoicedChord> chordList) {
	    this.chordList = chordList;
	    this.indexList = indexList;
	}
	
	@Override
	public void execute() {
	    List<VoicedChord> tmpList = new ArrayList<VoicedChord>(chordList);
		chordList.clear();
		
		for(int i = 0; i < tmpList.size(); i++) {
		    if(!indexList.contains(i)) {
		        chordList.add(tmpList.get(i));
		    }
		}
	}

}
