package music.chord.decorator;

public class ChordBuilder {
	private NoteName root;
	private Quality triadQuality;
	private Quality seventhQuality;
	private Duration duration;
	private Voicing triadVoicing;
	private VoicedChord voicedChord;
	private Voicing seventhVoicing;
	
	public ChordBuilder() {
		reset();
	}
	
	public VoicedChord build() {
		VoicedChord resultChord = null;
		
		if(voicedChord == null) {
			System.out.println("ChordBuilder.build() - voicedChord: " + voicedChord);
			
			Chord chord = new Triad(root, triadQuality);
			System.out.println("ChordBuilder.build() - chord: " + chord);
			resultChord = new ConcreteChord(chord, triadVoicing, duration);
			
			if(seventhQuality != null) {
				chord = new SeventhChord((Triad) chord, seventhQuality);
				System.out.println("ChordBuilder.build() - chord: " + chord);
				resultChord = new ConcreteChord(chord, seventhVoicing, duration);
			}
		}
		else {
			if(voicedChord.noteNameFromChordMember(ChordMember.SEVENTH) == null) {
				resultChord = new ConcreteChord(voicedChord, triadVoicing, duration);
			}
			else {
				resultChord = new ConcreteChord(voicedChord, seventhVoicing, duration);
			}
		}
		
		reset();
		System.out.println("ChordBuilder.build() - Reset complete.");
		System.out.println("ChordBuilder.build() - resultChord: " + resultChord);
		System.out.println("ChordBuilder.build() - noteBeanList: " + resultChord.getNoteBeanList());
		
		return resultChord;
	}
	
	public ChordBuilder setChord(VoicedChord voicedChord) {
		this.voicedChord = voicedChord;
		return this;
	}
	
	public ChordBuilder setDuration(Duration duration) {
		this.duration = duration;
		return this;
	}

	public ChordBuilder setRoot(NoteName root) {
		this.root = root;
		return this;
	}
	
	public ChordBuilder setSeventhQuality(Quality seventhQuality) {
		this.seventhQuality = seventhQuality;
		return this;
	}
	
	public ChordBuilder setTriadQuality(Quality triadQuality) {
		this.triadQuality = triadQuality;
		return this;
	}
	
	public ChordBuilder setVoicing(Voicing voicing) {
		this.triadVoicing = voicing;
		return this;
	}

	private void reset() {
		root = NoteName.C;
		triadQuality = Quality.MAJOR;
		seventhQuality = null;
		duration = Duration.QUARTER;
		
		triadVoicing = Voicing.getInstance();
		triadVoicing.addChordMember(ChordMember.ROOT);
		triadVoicing.addChordMember(ChordMember.THIRD);
		triadVoicing.addChordMember(ChordMember.FIFTH);
		triadVoicing.addChordMember(ChordMember.ROOT);
		
		seventhVoicing = Voicing.getInstance();
		seventhVoicing.addChordMember(ChordMember.ROOT);
		seventhVoicing.addChordMember(ChordMember.THIRD);
		seventhVoicing.addChordMember(ChordMember.FIFTH);
		seventhVoicing.addChordMember(ChordMember.SEVENTH);
		
		voicedChord = null;
	}
}
