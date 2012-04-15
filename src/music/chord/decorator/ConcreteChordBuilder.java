package music.chord.decorator;

import org.apache.log4j.Logger;

public class ConcreteChordBuilder implements ChordBuilder {
	private static Logger logger;
	
	static {
		logger = Logger.getRootLogger();
	}
	
	private NoteName root;
	private Quality triadQuality;
	private Quality seventhQuality;
	private Duration duration;
	private Voicing triadVoicing;
	private VoicedChord voicedChord;
	private Voicing seventhVoicing;
	
	public ConcreteChordBuilder() {
		reset();
	}
	
	public VoicedChord build() {
		VoicedChord resultChord = null;
		
		if(voicedChord == null) {
			logger.debug("voicedChord: " + voicedChord);
			
			Chord chord = new Triad(root, triadQuality);
			logger.debug("chord: " + chord);
			resultChord = new ConcreteChord(chord, triadVoicing, duration);
			
			if(seventhQuality != null) {
				chord = new SeventhChord((Triad) chord, seventhQuality);
				logger.debug("chord: " + chord);
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
		logger.trace("Reset complete.");
		logger.debug("resultChord: " + resultChord);
		logger.debug("noteBeanList: " + resultChord.getNoteBeanList());
		
		return resultChord;
	}
	
	public ConcreteChordBuilder setChord(VoicedChord voicedChord) {
		this.voicedChord = voicedChord;
		return this;
	}
	
	public ConcreteChordBuilder setDuration(Duration duration) {
		this.duration = duration;
		return this;
	}

	public ConcreteChordBuilder setRoot(NoteName root) {
		this.root = root;
		return this;
	}
	
	public ConcreteChordBuilder setSeventhQuality(Quality seventhQuality) {
		this.seventhQuality = seventhQuality;
		return this;
	}
	
	public ConcreteChordBuilder setTriadQuality(Quality triadQuality) {
		this.triadQuality = triadQuality;
		return this;
	}
	
	public ConcreteChordBuilder setVoicing(Voicing voicing) {
		if(voicing.hasSeventh()) {
			seventhVoicing = voicing;
		}
		else {
			triadVoicing = voicing;
		}
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