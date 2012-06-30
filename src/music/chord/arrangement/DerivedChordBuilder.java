package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.VoicePart;

public class DerivedChordBuilder {

    private NoteName root;
	private Duration duration;
	private Voicing voicing;
    private int octave;
    private List<VoicePart> partList;
    private Quality quality;    

	public VoicedChord buildVoicedChord() {
		return new VoicedChordImpl(root, quality, voicing, octave, duration, partList);
	}

	public DerivedChordBuilder setChord(VoicedChord chord) {
		this.root = chord.noteNameFromChordMember(ChordMember.ROOT);
		this.duration = chord.getDuration();
		this.voicing = chord.getVoicing();
		this.octave = chord.getOctave();
		this.partList = chord.getVoicePartList();
		this.quality = chord.getQuality();
		return this;
	}
	
	public DerivedChordBuilder setDuration(Duration duration) {
		this.duration = duration;
		return this;
	}
	
	public DerivedChordBuilder setOctave(int octave) {
	    this.octave = octave;
	    return this;
	}
	
	public DerivedChordBuilder setVoicePartList(List<VoicePart> partList) {
	    partList = new ArrayList<VoicePart>(partList);
	    return this;
	}
	
	public DerivedChordBuilder setVoicing(Voicing voicing) {
		this.voicing = voicing;
		return this;
	}
}
