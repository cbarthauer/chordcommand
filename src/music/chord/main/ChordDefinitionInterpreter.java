package music.chord.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.DerivedChordBuilder;
import music.chord.arrangement.TriadVoicing;
import music.chord.base.ChordMember;
import music.chord.base.ChordSpec;
import music.chord.base.Interval;
import music.chord.base.IntervalDirective;
import music.chord.base.NoteName;
import music.chord.base.VoicePart;
import music.chord.decorator.Chord;
import music.chord.decorator.ChordImpl;
import music.chord.grammar.ChordDefinitionLexer;
import music.chord.grammar.ChordDefinitionParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public class ChordDefinitionInterpreter {

    /**
     * @param args
     * @throws IOException 
     * @throws RecognitionException 
     */
    public static void main(String[] args) throws IOException, RecognitionException {
        CharStream charStream = new ANTLRFileStream(
                "D:\\musicspace\\chordgrammar\\definitions\\chords.txt");
        ChordDefinitionLexer lexer = new ChordDefinitionLexer(charStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        ChordDefinitionParser parser = new ChordDefinitionParser(tokenStream);
        parser.program();
        System.out.println("Done.");
        
        DerivedChordBuilder builder = new DerivedChordBuilder();
//        builder.setChord(getMajorChord(NoteName.forSymbol("Bb")));
    }
    
    private static List<VoicePart> getPartList() {
        List<VoicePart> result = new ArrayList<VoicePart>();
        result.add(VoicePart.BASS);
        result.add(VoicePart.BARITONE);
        result.add(VoicePart.LEAD);
        result.add(VoicePart.TENOR);
        return result;
    }

    private static TriadVoicing getVoicing() {
        TriadVoicing voicing = new TriadVoicing();
        voicing.addChordMember(ChordMember.THIRD);
        voicing.addChordMember(ChordMember.ROOT);
        voicing.addChordMember(ChordMember.FIFTH);
        voicing.addChordMember(ChordMember.ROOT);
        return voicing;
    }

    private static Chord getMajorChord(NoteName root) {
        return new ChordImpl(root, getChordSpec());
    }
    
    private static ChordSpec getChordSpec() {
        return new ChordSpec(getIntervalDirectiveMap());
    }
    
    private static Map<ChordMember, IntervalDirective> getIntervalDirectiveMap() {
        Map<ChordMember, IntervalDirective> result = new HashMap<ChordMember, IntervalDirective>();
        
        result.put(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD));
        result.put(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH));
        
        return result;
    }

}