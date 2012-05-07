package music.chord.decorator;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.ChordPlayer;
import music.chord.arrangement.SeventhVoicing;
import music.chord.arrangement.TriadVoicing;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.arrangement.VoicingManager;
import music.chord.base.ChordMember;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.builder.ChordVoicer;
import music.chord.builder.DerivedChordBuilder;
import music.chord.builder.SeventhBuilder;
import music.chord.builder.TriadBuilder;

import org.junit.Test;

public class ChordPlayerTest {
	@Test
	public void testPlay() {
		SeventhBuilder builder = new SeventhBuilder(new TriadBuilder());
		List<VoicedChord> chordList = new ArrayList<VoicedChord>();
		VoicedChord chord1 = builder.setRoot(NoteName.forSymbol("Ab"))
			.setTriadQuality(Quality.MAJOR)
			.setSeventhQuality(Quality.MINOR)
			.buildVoicedChord();
		chordList.add(chord1);
		
		VoicedChord chord2 = builder.setRoot(NoteName.forSymbol("Db"))
			.setTriadQuality(Quality.MAJOR)
			.buildVoicedChord();
		chordList.add(chord2);
		
		Voicing voicing1 = new SeventhVoicing();
		voicing1.addChordMember(ChordMember.FIFTH);
		voicing1.addChordMember(ChordMember.ROOT);
		voicing1.addChordMember(ChordMember.THIRD);
		voicing1.addChordMember(ChordMember.SEVENTH);		
		
		Voicing voicing2 = new TriadVoicing();
		voicing2.addChordMember(ChordMember.ROOT);
		voicing2.addChordMember(ChordMember.FIFTH);
		voicing2.addChordMember(ChordMember.ROOT);
		voicing2.addChordMember(ChordMember.THIRD);
		
		VoicingManager voicingManager = new VoicingManager();
		voicingManager.addSeventhVoicing(voicing1);
		voicingManager.addTriadVoicing(voicing2);
		
		ChordVoicer voicer = new ChordVoicer(
				voicingManager.getTriadVoicingList(), 
				voicingManager.getSeventhVoicingList(),
				new DerivedChordBuilder());
		List<VoicedChord> voicedChordList = voicer.voice(chordList);
		
		ChordPlayer player = new ChordPlayer();
		player.play(voicedChordList);
	}

}
