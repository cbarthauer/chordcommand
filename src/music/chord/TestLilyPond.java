package music.chord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import music.chord.ChordParser.compilationUnit_return;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class TestLilyPond {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws RecognitionException 
	 */
	public static void main(String[] args) throws IOException, RecognitionException {
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
		
		
		STGroup group = new STGroupFile("src/templates/barbershop.stg");
		ST st = group.getInstanceOf("score");
		st.add("tenorPitches", "csharp' csharp' bsharp csharp'");
		st.add("leadPitches", "eflat fflat dflat eflat");
		st.add("baritonePitches", "gsharp asharp fsharp gsharp");
		st.add("bassPitches", "csharp csharp gsharp csharp");
		System.out.print(st.render());
		PrintWriter pw = new PrintWriter("lilypond/barbershop.ly");
		pw.print(st.render());
		pw.flush();
		pw.close();
		
		Runtime.getRuntime().exec("cmd /c start setup.bat");
	}

}
