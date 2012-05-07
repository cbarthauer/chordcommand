package music.chord.command;

import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicingComparison;
import music.chord.builder.ChordVoicer;

public class VoicingComparisonList implements Command {
	
	private List<VoicedChord> chordList;
	private int startIndex;
	private int endIndex;
	private ChordVoicer voicer;	
	
	public VoicingComparisonList(
			List<VoicedChord> chordList, 
			int startIndex, 
			int endIndex, 
			ChordVoicer voicer) {
		
		this.chordList = chordList;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.voicer = voicer;
	}
	
	@Override
	public void execute() {
		List<VoicingComparison> resultList = voicer.voicingComparisonList(
			chordList.get(startIndex), 
			chordList.get(endIndex)
		);
		
		for(VoicingComparison comparison : resultList) {
			System.out.println(comparison);
		}
	}

}
