package music.chord.command;

import java.io.IOException;
import java.util.List;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.VoicedChord;
import music.chord.grammar.ChordLexer;
import music.chord.grammar.ChordParser;
import music.chord.grammar.ChordParser.compilationUnit_return;
import music.chord.grammar.ChordWalker;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class Load implements Command {

    private List<VoicedChord> chordList;
    private String fileName;
    private ChordDefinitionStructure struct;

    public Load(List<VoicedChord> chordList, ChordDefinitionStructure struct, String fileName) {
        this.chordList = chordList;
        this.struct = struct;
        this.fileName = fileName;
    }
    
    @Override
    public void execute() {
        try {
            CharStream charStream = new ANTLRFileStream(fileName);
            ChordLexer lexer = new ChordLexer(charStream);
            TokenStream tokenStream = new CommonTokenStream(lexer);
            ChordParser parser = new ChordParser(tokenStream);
            compilationUnit_return compilationUnit = parser.compilationUnit();
            CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(compilationUnit.getTree());
            
            ChordWalker walker = new ChordWalker(nodeStream);
            walker.setChordDefinitionStructure(struct);
            walker.setTriadBuilder(BuilderFactory.getTriadBuilder(struct));
            walker.setSeventhBuilder(BuilderFactory.getSeventhBuilder(struct));
            walker.setNinthBuilder(BuilderFactory.getNinthBuilder(struct));
            
            chordList.clear();
            chordList.addAll(walker.compilationUnit());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RecognitionException e) {
            throw new RuntimeException(e);
        }
    }

}
