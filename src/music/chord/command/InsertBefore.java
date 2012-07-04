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

import music.chord.engine.ChordEngine;
import music.chord.engine.protocol.InsertChordRequest;

public class InsertBefore implements Command {

	private ChordEngine engine;
    private InsertChordRequest request;

    public InsertBefore(
	        ChordEngine engine,
	        InsertChordRequest request) {
	    this.engine = engine;
	    this.request = request;
	}
	
	@Override
	public void execute() {
	    engine.insertChords(request);
	}

}
