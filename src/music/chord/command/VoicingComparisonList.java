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

import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicingComparison;

public class VoicingComparisonList implements Command {
	
	private List<VoicedChord> chordList;
	private int startIndex;
	private int endIndex;
	private ChordVoicer voicer;	
	
	public VoicingComparisonList(
			List<VoicedChord> chordList, 
			int startIndex, 
			int endIndex, 
			ChordVoicer voicer) {
		
		this.chordList = chordList;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.voicer = voicer;
	}
	
	@Override
	public void execute() {
		List<VoicingComparison> resultList = voicer.voicingComparisonList(
			chordList.get(startIndex), 
			chordList.get(endIndex)
		);
		
		for(VoicingComparison comparison : resultList) {
			System.out.println(comparison);
		}
	}

}
