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

import music.chord.arrangement.ChordPlayer;
import music.chord.arrangement.VoicedChord;

import org.apache.log4j.Logger;

public class Play implements Command {
	private static Logger logger;
	
	static {
		logger = Logger.getRootLogger();
	}
	
	private List<VoicedChord> chordList;
	private ChordPlayer player;
	
	public Play(List<VoicedChord> chordList, ChordPlayer player) {
		this.chordList = chordList;
		this.player = player;
	}
	
	@Override
	public void execute() {
		logger.debug("chordList: " + chordList);
		player.play(chordList);
	}

}
