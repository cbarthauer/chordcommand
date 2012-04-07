package music.chord.decorator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ChordPlayerTest {
	@Test
	public void testPlay() {
		ChordBuilder builder = new ChordBuilder();
		List<VoicedChord> chordList = new ArrayList<VoicedChord>();
		VoicedChord chord1 = builder.setRoot(NoteName.A_FLAT)
			.setTriadQuality(Quality.MAJOR)
			.setSeventhQuality(Quality.MINOR)
			.build();
		chordList.add(chord1);
		
		VoicedChord chord2 = builder.setRoot(NoteName.D_FLAT)
			.setTriadQuality(Quality.MAJOR)
			.build();
		chordList.add(chord2);
		
		Voicing voicing1 = Voicing.getInstance();
		voicing1.addChordMember(ChordMember.FIFTH);
		voicing1.addChordMember(ChordMember.ROOT);
		voicing1.addChordMember(ChordMember.THIRD);
		voicing1.addChordMember(ChordMember.SEVENTH);		
		
		Voicing voicing2 = Voicing.getInstance();
		voicing2.addChordMember(ChordMember.ROOT);
		voicing2.addChordMember(ChordMember.FIFTH);
		voicing2.addChordMember(ChordMember.ROOT);
		voicing2.addChordMember(ChordMember.THIRD);
		
		VoicingManager voicingManager = new VoicingManager();
		voicingManager.addSeventhVoicing(voicing1);
		voicingManager.addTriadVoicing(voicing2);
		
		ChordVoicer voicer = new ChordVoicer(
				voicingManager.getTriadVoicingList(), voicingManager.getSeventhVoicingList());
		List<VoicedChord> voicedChordList = voicer.voice(chordList);
		
		ChordPlayer player = new ChordPlayer();
		player.play(voicedChordList);
	}

}
