package music.chord.decorator;

import java.util.List;

import music.chord.decorator.NoteBean;


public class VoicedChord extends ForwardingChord {
	private Voicing voicing;

	public VoicedChord(Chord chord, Voicing voicing) {
		super(chord);
		this.voicing = voicing;
	}

	public List<NoteBean> getNoteBeanList() {
		return voicing.voice(this);
	}
	
	public Voicing getVoicing() {
		return voicing;
	}	
}
