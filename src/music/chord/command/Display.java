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

import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.display.ChordListFormatter;

public class Display implements Command {

	private List<VoicedChord> chordList;
	private ChordListFormatter formatter;
	
	public Display(List<VoicedChord> chordList, ChordListFormatter formatter) {
		this.chordList = chordList;
		this.formatter = formatter;
	}
	
	@Override
	public void execute() {
	    System.out.print(formatter.format(chordList));
	}

}
