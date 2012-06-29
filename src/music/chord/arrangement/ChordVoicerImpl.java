package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

public final class ChordVoicerImpl implements ChordVoicer {
	private VoicingStrategy strategy;
    private ChordDefinitionStructure struct;
	
	public ChordVoicerImpl(VoicingStrategy strategy, ChordDefinitionStructure struct) {	    
		this.strategy = strategy;
		this.struct = struct;
	}
	
	/* (non-Javadoc)
     * @see music.chord.arrangement.ChordVoicer#voice(java.util.List)
     */
	@Override
    public final List<VoicedChord> voice(List<VoicedChord> chordList) {
		List<VoicedChord> result = new ArrayList<VoicedChord>();
		
		if(chordList.size() == 1) {
			result.add(chordList.get(0));
		}
		else {
			result = voice(chordList.get(0), chordList.subList(1, chordList.size()));
		}
		
		return result;
	}
	
	/* (non-Javadoc)
     * @see music.chord.arrangement.ChordVoicer#voice(music.chord.arrangement.VoicedChord, java.util.List)
     */
	@Override
    public final List<VoicedChord> voice(VoicedChord startingChord, List<VoicedChord> chordList) {
		List<VoicedChord> result = new ArrayList<VoicedChord>();
		result.add(startingChord);
		
		VoicedChord previousChord = startingChord;
		
		for(VoicedChord chord : chordList) {
			VoicedChord voicedChord = strategy.voice(
			        previousChord, 
			        chord, 
			        struct.getCongruentVoicings(chord.getVoicing()));
			result.add(voicedChord);
			previousChord = voicedChord;
		}
		
		
		return result;
	}
	
	/* (non-Javadoc)
     * @see music.chord.arrangement.ChordVoicer#voicingComparisonList(music.chord.arrangement.VoicedChord, music.chord.arrangement.VoicedChord)
     */
	@Override
    public final List<VoicingComparison> voicingComparisonList(
			VoicedChord previousChord, 
			VoicedChord newChord) {
		
		return strategy.voicingComparisonList(
	        previousChord, 
	        newChord, 
	        struct.getCongruentVoicings(newChord.getVoicing())
	    );
	}
}
