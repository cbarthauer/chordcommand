package music.chord.command;

import java.util.List;

import music.chord.decorator.Chord;

public class Display implements Command {

	private List<Chord> chordList;
	
	public Display(List<Chord> chordList) {
		this.chordList = chordList;
	}
	
	@Override
	public void execute() {
		for(int i = 0; i < chordList.size(); i++) {
			System.out.println(i + ": " + chordList.get(i));
		}
	}

}
