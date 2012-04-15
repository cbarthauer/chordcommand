package music.chord.decorator;

public class SeventhBuilder implements ChordBuilder {
	private Quality seventhQuality;
	private Duration duration;
	private Voicing voicing;
	private TriadBuilder triadBuilder;
	
	public SeventhBuilder() {
		voicing = SeventhVoicing.getInstance();
		voicing.addChordMember(ChordMember.ROOT);
		voicing.addChordMember(ChordMember.FIFTH);
		voicing.addChordMember(ChordMember.SEVENTH);
		voicing.addChordMember(ChordMember.THIRD);
		
		duration = Duration.QUARTER;
		
		triadBuilder = new TriadBuilder();
	}
	
	@Override
	public VoicedChord buildVoicedChord() {
		VoicedChord result = new ConcreteChord(buildSeventhChord(), voicing, duration);
		reset();
		return result;
	}
	
	SeventhChord buildSeventhChord() {
		return new SeventhChord(triadBuilder.buildTriad(), seventhQuality);
	}
	
	public SeventhBuilder setDuration(Duration duration) {
		triadBuilder.setDuration(duration);
		return this;
	}

	public SeventhBuilder setRoot(NoteName root) {
		triadBuilder.setRoot(root);
		return this;
	}

	public SeventhBuilder setSeventhQuality(Quality seventhQuality) {
		this.seventhQuality = seventhQuality;
		return this;
	}
	
	public SeventhBuilder setTriadQuality(Quality triadQuality) {
		triadBuilder.setTriadQuality(triadQuality);
		return this;
	}
	
	public SeventhBuilder setVoicing(Voicing voicing) {
		this.voicing = voicing;
		return this;
	}
	
	private void reset() {
		seventhQuality = null;
		duration = null;
		voicing = null;
	}
}
