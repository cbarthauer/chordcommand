package music.chord.arrangement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.base.Duration;
import music.chord.base.VoicePart;
import music.chord.decorator.Chord;
import music.chord.decorator.ForwardingChord;


class ConcreteChord extends ForwardingChord implements VoicedChord {	
	private List<Note> noteList;
	private Voicing voicing;
	private Duration duration;
    private int octave;
    private Map<VoicePart, Note> voicePartMap;
    private List<VoicePart> partList;

	ConcreteChord(Chord chord, Voicing voicing, int octave, Duration duration, List<VoicePart> partList) {
		super(chord);
		this.voicing = voicing;
		noteList = voicing.voice(chord, octave, duration);
		this.duration = duration;
		this.octave = octave;
		this.partList = partList;
		this.voicePartMap = initVoicePartMap(partList, noteList);
	}

	@Override
	public final int difference(VoicedChord chord) {
		List<Note> list1 = this.noteList; 
		List<Note> list2 = chord.getNoteList();
		
		if(list1.size() != list2.size()) { 
			throw new IllegalArgumentException(
					"Difference can only be computed on lists of the same size."); 
		}
		
		int result = 0;
		
		for(int i = 0; i < list1.size(); i++) {
			result = result + Math.abs(list1.get(i).getMidiNumber() - list2.get(i).getMidiNumber());
		}
		
		return result;
	}
	
	@Override
	public final Duration getDuration() {
		return duration;
	}

	@Override
	public final List<Integer> getMidiNumberList() {
		List<Integer> midiNumberList = new ArrayList<Integer>();
		
		for(Note note : noteList) {
			midiNumberList.add(note.getMidiNumber());
		}
		
		return midiNumberList;
	}

	@Override
	public final List<Note> getNoteList() {
		return new ArrayList<Note>(noteList);
	}
	
	@Override
    public final int getOctave() {
        return octave;
    }
	
	@Override
	public final int getTicks(int ppq) {
	    float conversionFactor = duration.getPpqConversionFactor();
		return Math.round(ppq * conversionFactor);
	}

	@Override
    public final List<VoicePart> getVoicePartList() {
        return new ArrayList<VoicePart>(partList);
    }

	@Override
    public final Voicing getVoicing() {
		return voicing;
	}

    @Override
    public Note noteFromVoicePart(VoicePart part) {
        return voicePartMap.get(part);
    }

    private Map<VoicePart, Note> initVoicePartMap(List<VoicePart> partList,
            List<Note> noteList) {
	    
	    if(partList.size() != noteList.size()) {
	        throw new IllegalStateException(
	                "Size of partList(" + partList.size() 
	                + ") must equal size of noteList(" 
	                + noteList.size() + ").");
	    }
	    
	    Map<VoicePart, Note> result = new HashMap<VoicePart, Note>();
	    
	    for(int i = 0; i < partList.size(); i++) {
	        result.put(partList.get(i), noteList.get(i));
	    }	    
	    
        return result;
    }
}
