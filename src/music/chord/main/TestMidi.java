package music.chord.main;

import java.io.IOException;
import java.util.List;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordPlayer;
import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.ChordVoicerFactory;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.Constants;
import music.chord.base.NoteName;
import music.chord.base.QualityRegistry;
import music.chord.base.QualityRegistryFactory;
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
		CharStream charStream = new ANTLRFileStream(
				"D:\\musicspace\\chordgrammar\\examples\\out.txt");
//		CharStream charStream = new ANTLRFileStream(
//				"D:\\musicspace\\chordgrammar\\examples\\irish_lullaby.txt");
//        CharStream charStream = new ANTLRFileStream(
//                "D:\\musicspace\\chordgrammar\\examples\\adding_octave.txt");
	    ChordLexer lexer = new ChordLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		ChordParser parser = new ChordParser(tokenStream);
		compilationUnit_return compilationUnit = parser.compilationUnit();
		System.out.println(((CommonTree) compilationUnit.getTree()).toStringTree());

        QualityRegistry qualities = QualityRegistryFactory.getInstance(
                Constants.getChordDefinitions());
        
        VoicedChordBuilder triadBuilder = BuilderFactory.getTriadBuilder(
                NoteName.forSymbol("C"),
                qualities.forName("MAJOR_TRIAD"));
        VoicedChordBuilder seventhBuilder = BuilderFactory.getSeventhBuilder(
                NoteName.forSymbol("C"),
                qualities.forName("DOMINANT_SEVENTH"));
        VoicedChordBuilder ninthBuilder = BuilderFactory.getNinthBuilder(
                NoteName.forSymbol("C"),
                qualities.forName("DOMINANT_NINTH"));
	    
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(compilationUnit.getTree());
		ChordWalker walker = new ChordWalker(nodeStream);
		walker.setQualityRegistry(qualities);
		walker.setTriadBuilder(triadBuilder);
		walker.setSeventhBuilder(seventhBuilder);
		walker.setNinthBuilder(ninthBuilder);
		
		List<VoicedChord> chordList = walker.compilationUnit();		
		ChordVoicer voicer = ChordVoicerFactory.getInstance(qualities);
		List<VoicedChord> voicedChordList = voicer.voice(chordList);
		ChordPlayer player = new ChordPlayer();
		player.play(voicedChordList);
	}

}
