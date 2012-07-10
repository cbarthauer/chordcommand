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

import music.chord.engine.ChordEngine;
import music.chord.engine.protocol.RequestBuilder;

public class RemoveChord implements Command {

    private RequestBuilder builder;
    private List<Integer> range;
    private ChordEngine engine;

    public RemoveChord(RequestBuilder builder, List<Integer> range, ChordEngine engine) {
        this.builder = builder;
        this.range = new ArrayList<Integer>(range);
	    this.engine = engine;
	}
	
	@Override
	public void execute() {
	    engine.removeChords(builder.removeRequest(range));
	}

}
