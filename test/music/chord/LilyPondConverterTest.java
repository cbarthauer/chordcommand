package music.chord;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import music.chord.ChordParser.compilationUnit_return;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Test;

public class LilyPondConverterTest {

	@Test
	public void test() throws IOException, RecognitionException {
		CharStream charStream = new ANTLRFileStream("D:\\musicspace\\chordgrammar\\examples\\testLy.txt");
		ChordLexer lexer = new ChordLexer(charStream );
		TokenStream tokenStream = new CommonTokenStream(lexer);
		ChordParser parser = new ChordParser(tokenStream);
		compilationUnit_return compilationUnit = parser.compilationUnit();
		
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(compilationUnit.tree);
		ChordWalker walker = new ChordWalker(nodeStream);
		ChordProgression progression = walker.compilationUnit();
		List<Chord> chordList = progression.getChordList();
		VoicingManager voicingManager = progression.getVoicingManager();
		
		ChordVoicer voicer = new ChordVoicer(voicingManager.getTriadVoicingList(), voicingManager.getSeventhVoicingList());
		chordList = voicer.voice(chordList);
		
		LilyPondConverter ly = new LilyPondConverter();
		String tenor = ly.stringFromChordList(chordList, VoicePart.TENOR);
		assertTrue("Found incorrect tenor voice: " + tenor, "e' a' b' e'".equals(tenor));
		
		String lead = ly.stringFromChordList(chordList, VoicePart.LEAD);
		assertTrue("Found incorrect lead voice: " + lead, "c' f' g' c'".equals(lead));
		
		String baritone = ly.stringFromChordList(chordList, VoicePart.BARITONE);
		assertTrue("Found incorrect baritone voice: " + baritone, "g c' d' g".equals(baritone));
		
		String bass = ly.stringFromChordList(chordList, VoicePart.BASS);
		assertTrue("Found incorrect bass voice: " + bass, "c f g c".equals(bass));
	}
 
}
