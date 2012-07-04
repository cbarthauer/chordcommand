package music.chord.arrangement;

public class VoicingComparison implements Comparable<VoicingComparison> {
	private Integer difference;
	private Voicing voicing;
	
	public VoicingComparison(int difference, Voicing voicing) {
		this.difference = difference;
		this.voicing = voicing;
	}

	@Override
	public int compareTo(VoicingComparison o) {
		return difference.compareTo(o.difference);
	}

	public Integer getDifference() {
		return difference;
	}

	public Voicing getVoicing() {
		return voicing;
	}	
	
	public String toString() {
		return difference + ": " + voicing;
	}
}
