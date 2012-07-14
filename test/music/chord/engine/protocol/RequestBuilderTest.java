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
package music.chord.engine.protocol;

import static org.junit.Assert.assertEquals;
import music.chord.AbstractTest;
import music.chord.TestHelper;

import org.junit.Before;
import org.junit.Test;

public class RequestBuilderTest extends AbstractTest {

    private Identifier id;
    private RequestBuilder reqBuilder;
    private TestHelper helper;
    
    @Before
    public void setUp() throws Exception {
        id = new Identifier("c1");
        reqBuilder = new RequestBuilder(id);
        helper = new TestHelper();
    }

    @Test
    public void chordRequest() {
        ChordRequest request = 
            reqBuilder.chordRequest(
                helper.getChord("C", "MINOR_TRIAD"),
                helper.getChord("A", "MINOR_TRIAD"),
                helper.getChord("B", "MINOR_TRIAD"));
        assertEquals(3, request.getChordList().size());
    }
    
    @Test
    public void insertRequest() {
        InsertChordRequest request = reqBuilder.insertRequest(
            1,
            helper.getChord("C", "MINOR_TRIAD"),
            helper.getChord("A", "MINOR_TRIAD"),
            helper.getChord("B", "MINOR_TRIAD"));
        assertEquals(3, request.getChordList().size());
        assertEquals(1, request.getPosition());
    }

}
