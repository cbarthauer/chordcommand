package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

public class VoicingManager {
	private List<Voicing> triadVoicingList;
	private List<Voicing> seventhVoicingList;
	
	public VoicingManager() {
		triadVoicingList = new ArrayList<Voicing>();
		seventhVoicingList = new ArrayList<Voicing>();
	}

	public void addSeventhVoicing(Voicing voicing) {
		seventhVoicingList.add(voicing);
	}
	
	public void addTriadVoicing(Voicing voicing) {
		triadVoicingList.add(voicing);
	}
	
	public List<Voicing> getSeventhVoicingList() {
		return seventhVoicingList;
	}
	
	public List<Voicing> getTriadVoicingList() {
		return triadVoicingList;
	}

    public String toString() {
		return "voicingManager {\n" +
				"    triadVoicingList: " + triadVoicingList + ",\n" +
				"    seventhVoicingList: " + seventhVoicingList + ",\n" +
			"}\n";
	}
    
	public List<Voicing> voicingListFromVoicing(Voicing voicing) {
        if(voicing instanceof SeventhVoicing) {
            return seventhVoicingList;
        }
        else {
            return triadVoicingList;
        }
    }
}
