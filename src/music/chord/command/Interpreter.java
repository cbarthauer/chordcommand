package music.chord.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import music.chord.decorator.ChordPlayer;
import music.chord.decorator.ChordVoicer;
import music.chord.decorator.ChordVoicerFactory;
import music.chord.decorator.VoicedChord;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public class Interpreter {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws RecognitionException 
	 */
	public static void main(String[] args) throws IOException, RecognitionException {
		Scanner scanner = new Scanner(System.in);
		String line = "";
		ChordVoicer voicer = ChordVoicerFactory.getInstance(
			"D:\\musicspace\\chordgrammar\\examples\\voicings.txt");
		List<VoicedChord> chordList = new ArrayList<VoicedChord>();
		
		while(true) {
			line = scanner.nextLine();
			CharStream charStream = new ANTLRStringStream(line);
			ChordCommandLexer lexer = new ChordCommandLexer(charStream);
			TokenStream tokenStream = new CommonTokenStream(lexer);
			ChordCommandParser parser = new ChordCommandParser(tokenStream);
			parser.setChordList(chordList);
			parser.setChordVoicer(voicer);
			parser.setChordPlayer(new ChordPlayer());
			
			List<Command> commandList = parser.program();
			
			for(Command command : commandList) {
				command.execute();
			}
		}
	}
}
