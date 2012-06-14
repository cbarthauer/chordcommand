package music.chord.command;

import java.util.List;

import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.VoicedChord;
import music.chord.grammar.ChordListRegistry;

public class VoiceChordList implements Command {

	private String identifier;
    private ChordVoicer voicer;
    private ChordListRegistry reg;

    public VoiceChordList(String identifier, ChordVoicer voicer, ChordListRegistry reg) {
		this.identifier = identifier;
		this.voicer = voicer;
		this.reg = reg;
	}
	
	@Override
	public void execute() {
		List<VoicedChord> chordList = voicer.voice(reg.byIdentifier(identifier));
		reg.put(identifier, chordList);
	}

}
