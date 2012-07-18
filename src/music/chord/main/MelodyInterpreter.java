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
package music.chord.main;

import java.io.IOException;
import java.util.Scanner;

import music.chord.base.NoteName;
import music.chord.base.impl.NoteNameBuilderImpl;
import music.chord.grammar.MelodyLexer;
import music.chord.grammar.MelodyParser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public class MelodyInterpreter {
    /**
     * @param args
     * @throws IOException 
     * @throws RecognitionException 
     */
    public static void main(String[] args) throws IOException, RecognitionException {
        Scanner scanner = new Scanner(System.in);
        String line = "";        
        NoteName.setNoteNameBuilder(new NoteNameBuilderImpl());      
        
        while(true) {
            line = scanner.nextLine();
            CharStream charStream = new ANTLRStringStream(line);
            MelodyLexer lexer = new MelodyLexer(charStream);
            TokenStream tokenStream = new CommonTokenStream(lexer);
            MelodyParser parser = new MelodyParser(tokenStream);
            parser.play();
        }
    }
    
}
