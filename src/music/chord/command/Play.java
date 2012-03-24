package music.chord.command;

import java.util.List;

import music.chord.decorator.Chord;
import music.chord.decorator.ChordPlayer;
import music.chord.decorator.ChordVoicer;
import music.chord.decorator.VoicedChord;

public class Play implements Command {

	private List<Chord> chordList;
	private ChordVoicer voicer;
	private ChordPlayer player;
	
	public Play(List<Chord> chordList, ChordVoicer voicer, ChordPlayer player) {
		this.chordList = chordList;
		this.voicer = voicer;
		this.player = player;
	}
	
	@Override
	public void execute() {
		List<VoicedChord> voicedChordList = voicer.voice(chordList);
		player.play(voicedChordList);
	}

}
