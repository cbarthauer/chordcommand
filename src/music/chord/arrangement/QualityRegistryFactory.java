/*******************************************************************************
 * Copyright (c) 2012 Chris Barthauer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Chris Barthauer - initial API and implementation
 ******************************************************************************/
package music.chord.arrangement;

import java.io.IOException;

import music.chord.grammar.ChordQualityDefinitionLexer;
import music.chord.grammar.ChordQualityDefinitionParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public final class QualityRegistryFactory {    
    public static QualityRegistry getInstance(String fileName) {
        QualityRegistry registry = null;
        
        try {
            CharStream charStream = new ANTLRFileStream(fileName);
            ChordQualityDefinitionLexer lexer = new ChordQualityDefinitionLexer(charStream);
            TokenStream tokenStream = new CommonTokenStream(lexer);
            ChordQualityDefinitionParser parser = new ChordQualityDefinitionParser(tokenStream);
            registry = parser.program();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        } catch (RecognitionException e) {
            throw new RuntimeException(e);
        }
        
        return registry;
    }
    
    private QualityRegistryFactory() {
        //Hide utility class constructor.
    }
}
