package music.chord;

import java.util.ArrayList;
import java.util.List;

public class VoicingManager {
	private List<Voicing> triadVoicingList;
	
	public VoicingManager() {
		triadVoicingList = new ArrayList<Voicing>();
	}
	
	public void addTriadVoicing(Voicing voicing) {
		triadVoicingList.add(voicing);
	}
	
	public String toString() {
		return "voicingManager {\n" +
				"    triadVoicingList: " + triadVoicingList + "\n" +
			"}\n";
	}
}
