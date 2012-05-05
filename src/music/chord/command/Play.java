package music.chord.command;

import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.decorator.ChordPlayer;

import org.apache.log4j.Logger;

public class Play implements Command {
	private static Logger logger;
	
	static {
		logger = Logger.getRootLogger();
	}
	
	private List<VoicedChord> chordList;
	private ChordPlayer player;
	
	public Play(List<VoicedChord> chordList, ChordPlayer player) {
		this.chordList = chordList;
		this.player = player;
	}
	
	@Override
	public void execute() {
		logger.debug("chordList: " + chordList);
		player.play(chordList);
	}

}
