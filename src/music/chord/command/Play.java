package music.chord.command;

import java.util.List;

import music.chord.decorator.ChordPlayer;
import music.chord.decorator.VoicedChord;

public class Play implements Command {

	private List<VoicedChord> chordList;
	private ChordPlayer player;
	
	public Play(List<VoicedChord> chordList, ChordPlayer player) {
		this.chordList = chordList;
		this.player = player;
	}
	
	@Override
	public void execute() {
		System.out.println("Play.execute() - chordList: " + chordList);
		player.play(chordList);
	}

}
