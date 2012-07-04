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

public final class IntervallicStructureBuilder {

    private Map<ChordMember, IntervalDirective> dirMap;
    
    public IntervallicStructureBuilder() {
        dirMap = new HashMap<ChordMember, IntervalDirective>();
    }
    
    public final IntervallicStructureBuilder add(
            ChordMember member, 
            IntervalDirective directive) {
        
        dirMap.put(member, directive);
        return this;
    }

    public final IntervallicStructure build() {
        IntervallicStructure struct = new IntervallicStructure(dirMap);
        dirMap = new HashMap<ChordMember, IntervalDirective>();
        return struct;
    }

}
