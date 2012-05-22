package music.chord.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import music.chord.arrangement.ChordPlayer;
import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.ChordVoicerFactory;
import music.chord.arrangement.SeventhBuilder;
import music.chord.arrangement.TriadBuilder;
import music.chord.arrangement.VoicePartPlayer;
import music.chord.arrangement.VoicedChord;
import music.chord.command.Command;
import music.chord.grammar.ChordCommandLexer;
import music.chord.grammar.ChordCommandParser;

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
		TriadBuilder triadBuilder = BuilderFactory.getTriadBuilder();
		SeventhBuilder seventhBuilder = BuilderFactory.getSeventhBuilder(triadBuilder);
		
		while(true) {
			line = scanner.nextLine();
			CharStream charStream = new ANTLRStringStream(line);
			ChordCommandLexer lexer = new ChordCommandLexer(charStream);
			TokenStream tokenStream = new CommonTokenStream(lexer);
			ChordCommandParser parser = new ChordCommandParser(tokenStream);
			parser.setChordList(chordList);
			parser.setChordVoicer(voicer);
			parser.setChordPlayer(new ChordPlayer());
			parser.setTriadBuilder(triadBuilder);
			parser.setSeventhBuilder(seventhBuilder);
			parser.setVoicePartPlayer(new VoicePartPlayer());
			
			List<Command> commandList = commandListFromParser(parser);
			
			for(Command command : commandList) {
				command.execute();
			}
		}
	}

    private static List<Command> commandListFromParser(ChordCommandParser parser) 
            throws RecognitionException {
        
        List<Command> result = parser.program();
        
        if(result == null) {
            return new ArrayList<Command>();
        }
        else {
            return result;
        }
    }


}
