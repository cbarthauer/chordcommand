package music.chord.decorator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ChordPlayerTest {
	@Test
	public void testPlay() {
		List<Chord> chordList = new ArrayList<Chord>();
		Chord chord1 = new SeventhChord(new Triad(NoteName.A_FLAT, Quality.MAJOR), Quality.MINOR);
		chordList.add(chord1);
		
		Chord chord2 = new Triad(NoteName.D_FLAT, Quality.MAJOR);
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
