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

import music.chord.arrangement.ChordFinder;
import music.chord.arrangement.VoicedChord;
import music.chord.base.NoteName;

public class FindChordsContainingNoteNames implements Command {

    private List<NoteName> noteNameList;
    private ChordFinder finder;

    public FindChordsContainingNoteNames(List<NoteName> noteNameList, ChordFinder finder) {
        this.noteNameList = noteNameList;
        this.finder = finder;
    }
    
    @Override
    public void execute() {
        List<VoicedChord> chordList = finder.find(noteNameList);
        
        for(VoicedChord chord : chordList) {
            System.out.println(chord.getSymbol());
        }
    }

}
