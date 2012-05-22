package music.chord.command;

import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.display.ChordListFormatter;

public class Display implements Command {

	private List<VoicedChord> chordList;
	private ChordListFormatter formatter;
	
	public Display(List<VoicedChord> chordList, ChordListFormatter formatter) {
		this.chordList = chordList;
		this.formatter = formatter;
	}
	
	@Override
	public void execute() {
	    System.out.print(formatter.format(chordList));
	}

}
