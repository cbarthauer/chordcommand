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
package music.chord.grammar;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import music.chord.AbstractTest;
import music.chord.TestHelper;
import music.chord.arrangement.VoicedChord;
import music.chord.engine.protocol.Identifier;

import org.junit.Before;
import org.junit.Test;

public class ChordListRegistryTest extends AbstractTest {

    private ChordListRegistry reg;
    private Identifier c1;
    private TestHelper helper;
    
    @Before
    public void setUp() throws Exception {
        reg = new ChordListRegistry();
        c1 = new Identifier("c1");
        helper = new TestHelper();
    }

    @Test
    public void add() {
        VoicedChord chord1 = helper.getChord("C", "MAJOR_TRIAD");
        reg.add(c1, chord1);
        reg.add(c1, chord1);
        
        List<VoicedChord> chordList = reg.byIdentifier(c1);
        assertEquals(2, chordList.size());
    }
    
    @Test
    public void byIdentifier() {
        VoicedChord chord1 = helper.getChord("C", "MAJOR_TRIAD");
            
        List<VoicedChord> chordList = new ArrayList<VoicedChord>();
        chordList.add(chord1);
        
        reg.put(c1, chordList);
        
        List<VoicedChord> retrievedList = reg.byIdentifier(c1);
        assertEquals(1, retrievedList.size());
        assertEquals("CM", retrievedList.get(0).getSymbol());
        
        retrievedList = reg.byIdentifier(
                new Identifier("does not exist"));
        assertEquals(0, retrievedList.size());
    }

    @Test
    public void getChord() {
        VoicedChord chord1 = helper.getChord("C", "MAJOR_TRIAD");
        reg.add(c1, chord1);
        
        VoicedChord fromReg = reg.getChord(c1, 0);
        assertEquals(chord1.getSymbol(), fromReg.getSymbol());
    }
    
    @Test
    public void set() {
        VoicedChord chord1 = helper.getChord("C", "MAJOR_TRIAD");
        reg.add(c1, chord1);
        
        VoicedChord chord2 = helper.getChord("D", "DIMINISHED_SEVENTH");
        reg.set(c1, 0, chord2);
        
        assertEquals(chord2.getSymbol(), reg.byIdentifier(c1).get(0).getSymbol());
    }    
}
