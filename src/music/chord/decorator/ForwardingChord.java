package music.chord.decorator;

import org.apache.log4j.Logger;

public class ForwardingChord implements Chord {
	private static Logger logger;
	
	static {
		logger = Logger.getRootLogger();
	}
	
	private final Chord chord;
	
	public ForwardingChord(Chord chord) {
		this.chord = chord;
	}
	
	@Override
	public NoteName noteNameFromChordMember(ChordMember chordMember) {
		logger.debug("chord: " + chord);
		logger.debug("chordMember: " + chordMember);
		return chord.noteNameFromChordMember(chordMember);
	}
	
	@Override
	public String toString() {
		return chord.toString();
	}

}
