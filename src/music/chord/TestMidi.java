package music.chord;

import java.io.IOException;
import java.util.List;

import music.chord.ChordParser.compilationUnit_return;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class TestMidi {

	/**
	 * @param args
	 * @throws RecognitionException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws RecognitionException, IOException {
		CharStream charStream = new ANTLRFileStream("D:\\musicspace\\chordgrammar\\examples\\test.txt");
		ChordLexer lexer = new ChordLexer(charStream );
		TokenStream tokenStream = new CommonTokenStream(lexer);
		ChordParser parser = new ChordParser(tokenStream);
		compilationUnit_return compilationUnit = parser.compilationUnit();
		System.out.println(compilationUnit.tree.toStringTree());
		
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(compilationUnit.tree);
		ChordWalker walker = new ChordWalker(nodeStream);
		ChordProgression progression = walker.compilationUnit();
		List<Chord> chordList = progression.getChordList();
		VoicingManager voicingManager = progression.getVoicingManager();
		
		ChordVoicer voicer = new ChordVoicer(voicingManager.getTriadVoicingList(), voicingManager.getSeventhVoicingList());
		chordList = voicer.voice(chordList);
		ChordPlayer player = new ChordPlayer();
		player.play(chordList);
	}

}