package music.chord.command;

import java.io.IOException;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.grammar.ChordLexer;
import music.chord.grammar.ChordListRegistry;
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

    private String identifier;
    private ChordDefinitionStructure struct;
    private String fileName;    
    private ChordListRegistry reg;

    public Load(
            String identifier, 
            ChordDefinitionStructure struct, 
            String fileName, 
            ChordListRegistry reg) {
        
        this.identifier = identifier;
        this.struct = struct;
        this.fileName = fileName;
        this.reg = reg;
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
            
            reg.put(identifier, walker.compilationUnit());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RecognitionException e) {
            throw new RuntimeException(e);
        }
    }

}
