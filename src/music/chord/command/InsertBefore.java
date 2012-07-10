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
package music.chord.command;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.engine.ChordEngine;
import music.chord.engine.protocol.RequestBuilder;

public final class InsertBefore implements Command {

    private RequestBuilder builder;
    private Integer position;
    private List<VoicedChord> chordList;
    private ChordEngine engine;

    public InsertBefore(
	        RequestBuilder builder,
	        Integer position,
	        List<VoicedChord> chordList,
	        ChordEngine engine) {
        this.builder = builder;
        this.position = position;
        this.chordList = new ArrayList<VoicedChord>(chordList);
	    this.engine = engine;
	}
	
	@Override
	public final void execute() {
	    engine.insertChords(builder.insertRequest(position, chordList));
	}

}
