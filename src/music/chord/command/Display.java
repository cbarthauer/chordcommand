package music.chord.command;

import java.util.List;

import music.chord.decorator.VoicedChord;

public class Display implements Command {

	private List<VoicedChord> chordList;
	
	public Display(List<VoicedChord> chordList) {
		this.chordList = chordList;
	}
	
	@Override
	public void execute() {
		for(int i = 0; i < chordList.size(); i++) {
			VoicedChord chord = chordList.get(i);
			System.out.println(i + ": " + chord + " " + chord.getVoicing());
		}
	}

}
