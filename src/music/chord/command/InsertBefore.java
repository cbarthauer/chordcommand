package music.chord.command;

import java.util.LinkedList;
import java.util.List;

import music.chord.arrangement.VoicedChord;

public class InsertBefore implements Command {

    private List<VoicedChord> chordsForInsert;
    private int insertionIndex;
    private List<VoicedChord> existingList;
	
	public InsertBefore(
	        List<VoicedChord> chordsForInsert, 
	        int insertionIndex, 
	        List<VoicedChord> existingList) {
	    this.chordsForInsert = chordsForInsert;
	    this.insertionIndex = insertionIndex;
	    this.existingList = existingList;
	}
	
	@Override
	public void execute() {
	    LinkedList<VoicedChord> tmpList = new LinkedList<VoicedChord>(existingList);
		tmpList.addAll(insertionIndex, chordsForInsert);
		existingList.clear();
		existingList.addAll(tmpList);
	}

}
