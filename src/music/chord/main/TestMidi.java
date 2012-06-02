package music.chord.main;

import java.io.IOException;
import java.util.List;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.ChordPlayer;
import music.chord.arrangement.ChordProgression;
import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.ChordVoicerFactory;
import music.chord.arrangement.VoicedChord;
import music.chord.grammar.ChordDefinitionStructureFactory;
import music.chord.grammar.ChordLexer;
import music.chord.grammar.ChordParser;
import music.chord.grammar.ChordParser.compilationUnit_return;
import music.chord.grammar.ChordWalker;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class TestMidi {

	/**
	 * @param args
	 * @throws RecognitionException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws RecognitionException, IOException {
//		CharStream charStream = new ANTLRFileStream(
//			"D:\\musicspace\\chordgrammar\\examples\\explicit_voicing.txt");
//		CharStream charStream = new ANTLRFileStream(
//				"D:\\musicspace\\chordgrammar\\examples\\test.txt");
		CharStream charStream = new ANTLRFileStream(
				"D:\\musicspace\\chordgrammar\\examples\\irish_lullaby.txt");
		ChordLexer lexer = new ChordLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		ChordParser parser = new ChordParser(tokenStream);
		compilationUnit_return compilationUnit = parser.compilationUnit();
		System.out.println(((CommonTree) compilationUnit.getTree()).toStringTree());

	    ChordDefinitionStructure struct = ChordDefinitionStructureFactory.getInstance(
	        "D:\\musicspace\\chordgrammar\\definitions\\chords.txt");
	      
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(compilationUnit.getTree());
		ChordWalker walker = new ChordWalker(nodeStream);
		walker.setChordDefinitionStructure(struct);
		walker.setTriadBuilder(BuilderFactory.getTriadBuilder(struct));
		walker.setSeventhBuilder(BuilderFactory.getSeventhBuilder(struct));
		walker.setNinthBuilder(BuilderFactory.getNinthBuilder(struct));
		
		ChordProgression progression = walker.compilationUnit();
		List<VoicedChord> chordList = progression.getChordList();
		

		ChordVoicer voicer = ChordVoicerFactory.getInstance(struct);
		
		List<VoicedChord> voicedChordList = voicer.voice(chordList);
		ChordPlayer player = new ChordPlayer();
		player.play(voicedChordList);
	}

}
