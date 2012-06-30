package music.chord.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordFinder;
import music.chord.arrangement.ChordFinderImpl;
import music.chord.arrangement.ChordPlayer;
import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.ChordVoicerFactory;
import music.chord.arrangement.VoicePartPlayer;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.Constants;
import music.chord.base.NoteName;
import music.chord.base.QualityRegistry;
import music.chord.base.QualityRegistryFactory;
import music.chord.command.Command;
import music.chord.engine.ChordEngine;
import music.chord.engine.ChordEngineBuilder;
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
		
		ChordVoicer voicer = ChordVoicerFactory.getInstance(qualities);
		
		ChordEngine engine = ChordEngineBuilder.build(
		        triadBuilder, seventhBuilder, ninthBuilder, voicer, qualities);
		
		ChordFinder finder = new ChordFinderImpl(
                triadBuilder,
                seventhBuilder,
                ninthBuilder,
                qualities.all());		
		
		while(true) {
			line = scanner.nextLine();
			CharStream charStream = new ANTLRStringStream(line);
			ChordCommandLexer lexer = new ChordCommandLexer(charStream);
			TokenStream tokenStream = new CommonTokenStream(lexer);
			ChordCommandParser parser = new ChordCommandParser(tokenStream);
			parser.setChordEngine(engine);
			parser.setChordFinder(finder);
			parser.setChordVoicer(voicer);
			parser.setChordPlayer(new ChordPlayer());
			parser.setQualityRegistry(qualities);
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
