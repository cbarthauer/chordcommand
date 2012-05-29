package music.chord.decorator;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.ChordPlayer;
import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.ClosestVoicingStrategy;
import music.chord.arrangement.DerivedChordBuilder;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.arrangement.Voicing;
import music.chord.arrangement.VoicingImpl;
import music.chord.arrangement.VoicingManager;
import music.chord.arrangement.VoicingValidator;
import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.SeventhQuality;
import music.chord.base.VoicePart;

import org.junit.Test;

public class ChordPlayerTest {
    private final static int OCTAVE = 4;
	@Test
	public void testPlay() {
	    VoicedChordBuilder seventhBuilder = new VoicedChordBuilder(
	           SeventhQuality.DOMINANT.getChordSpec(), 
		       new VoicingImpl(VoicingValidator.SEVENTH_VALIDATOR),
		       OCTAVE,
		       Duration.QUARTER,
		       VoicePart.barbershopDefault());
		List<VoicedChord> chordList = new ArrayList<VoicedChord>();
		VoicedChord chord1 = seventhBuilder.setRoot(NoteName.forSymbol("Ab"))
		    .setChordSpec(SeventhQuality.DOMINANT.getChordSpec())
			.buildVoicedChord();
		chordList.add(chord1);
		
		Voicing voicing1 = new VoicingImpl(VoicingValidator.SEVENTH_VALIDATOR);
		voicing1.addChordMember(ChordMember.FIFTH);
		voicing1.addChordMember(ChordMember.ROOT);
		voicing1.addChordMember(ChordMember.THIRD);
		voicing1.addChordMember(ChordMember.SEVENTH);	
		
		VoicingManager voicingManager = new VoicingManager();
		voicingManager.addSeventhVoicing(voicing1);
		
        ChordVoicer voicer = new ChordVoicer(
            new ClosestVoicingStrategy(new DerivedChordBuilder()), 
            voicingManager
        );
		List<VoicedChord> voicedChordList = voicer.voice(chordList);
		
		ChordPlayer player = new ChordPlayer();
		player.play(voicedChordList);
	}

}
