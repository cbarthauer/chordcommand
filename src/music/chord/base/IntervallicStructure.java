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
package music.chord.base;

import java.util.HashMap;
import java.util.Map;

import music.chord.base.ChordMember;
import music.chord.base.NoteName;

public class IntervallicStructure {

    private Map<ChordMember, IntervalDirective> intervalDirectiveMap;
    
    IntervallicStructure(Map<ChordMember, IntervalDirective> intervalDirectiveMap) {
        this.intervalDirectiveMap = 
                new HashMap<ChordMember, IntervalDirective>(intervalDirectiveMap);
    }
    
    public NoteName getNoteName(NoteName root, ChordMember chordMember) {
        IntervalDirective directive = intervalDirectiveMap.get(chordMember);
        NoteName result = null;
        
        if(directive != null) {
            result = directive.calculate(root);
        }
        
        return result;
    }

}
