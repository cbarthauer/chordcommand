package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

public class VoicingManager {
	private List<Voicing> triadVoicingList;
	private List<Voicing> seventhVoicingList;
    private List<Voicing> ninthVoicingList;
	
	public VoicingManager() {
		triadVoicingList = new ArrayList<Voicing>();
		seventhVoicingList = new ArrayList<Voicing>();
		ninthVoicingList = new ArrayList<Voicing>();
	}

	public void addSeventhVoicing(Voicing voicing) {
		seventhVoicingList.add(voicing);
	}
	
	public void addTriadVoicing(Voicing voicing) {
		triadVoicingList.add(voicing);
	}
	
	public void addNinthVoicing(Voicing voicing) {
	    ninthVoicingList.add(voicing);
	}
	
	public List<Voicing> getSeventhVoicingList() {
		return seventhVoicingList;
	}
	
	public List<Voicing> getTriadVoicingList() {
		return triadVoicingList;
	}
	
	public List<Voicing> getNinthVoicingList() {
	    return ninthVoicingList;
	}

    public String toString() {
		return "voicingManager {\n" +
				"    triadVoicingList: " + triadVoicingList + ",\n" +
				"    seventhVoicingList: " + seventhVoicingList + ",\n" +
				"    ninthVoicingList: " + ninthVoicingList + "\n" +
			"}\n";
	}
    
	public List<Voicing> voicingListFromVoicing(Voicing voicing) {
        if(voicing instanceof SeventhVoicing) {
            return seventhVoicingList;
        }
        else if(voicing instanceof NinthVoicing) {
            return ninthVoicingList;
        }
        else {
            return triadVoicingList;
        }
    }
}
