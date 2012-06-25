package music.chord.arrangement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.QualityEnum;
import music.chord.base.VoicePart;
import music.chord.decorator.Chord;


final class ConcreteChord implements Chord, VoicedChord {	
	private List<Note> noteList;
	private List<NoteName> noteNameList;
	private Voicing voicing;
	private Duration duration;
    private int octave;
    private Map<VoicePart, Note> voicePartMap;
    private List<VoicePart> partList;
    private QualityEnum quality;
    private Chord chord;

	ConcreteChord(
	        Chord chord, 
	        Voicing voicing, 
	        int octave, 
	        Duration duration, 
	        List<VoicePart> partList,
	        QualityEnum quality) {
	    
	    if(quality == null) throw new IllegalArgumentException("Quality can't be null.");
	    
		this.chord = chord;
		this.voicing = voicing;
		this.noteList = voicing.voice(chord, octave, duration);
		this.noteNameList = noteNames(noteList);
		this.duration = duration;
		this.octave = octave;
		this.partList = partList;
		this.voicePartMap = initVoicePartMap(partList, noteList);
		this.quality = quality;		
	}

    @Override
    public boolean containsNoteNames(List<NoteName> noteNameList) {
        return this.noteNameList.containsAll(noteNameList);
    }

    @Override
	public final int difference(VoicedChord chord) {
		return VoicedChordCalculator.difference(this, chord);
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
    public QualityEnum getQualityEnum() {
        return quality;
    }
	
	@Override
    public final String getSymbol() {
        return noteNameFromChordMember(ChordMember.ROOT) + quality.getSymbol();
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
    public final Note noteFromVoicePart(VoicePart part) {
        return voicePartMap.get(part);
    }

    @Override
    public final NoteName noteNameFromChordMember(ChordMember chordMember) {
        return chord.noteNameFromChordMember(chordMember);
    }

    @Override
    public final String toString() {
        return chord.toString();
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

    private List<NoteName> noteNames(List<Note> noteList) {
        List<NoteName> result = new ArrayList<NoteName>();
        
        for(Note note : noteList) {
            result.add(note.getNoteName());
        }
        
        return result;
    }
}
