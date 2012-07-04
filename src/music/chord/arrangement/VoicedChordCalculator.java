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
package music.chord.arrangement;

import java.util.List;

final class VoicedChordCalculator {
    private VoicedChordCalculator() {
        //Hide default constructor in utility class.
    }
    
    static final int difference(VoicedChord chord1, VoicedChord chord2) {
        List<Note> list1 = chord1.getNoteList(); 
        List<Note> list2 = chord2.getNoteList();
        
        if(list1.size() != list2.size()) { 
            throw new IllegalArgumentException(
                    "Difference can only be computed on lists of the same size."); 
        }
        
        int result = 0;
        
        for(int i = 0; i < list1.size(); i++) {
            result = result + Math.abs(list1.get(i).getMidiNumber() - list2.get(i).getMidiNumber());
        }
        
        return result;
    }
}
