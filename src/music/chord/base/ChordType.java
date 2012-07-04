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

public enum ChordType {
    TRIAD, SEVENTH, NINTH;
    
    private static Map<String, ChordType> typeMap;
    
    static {
        typeMap = new HashMap<String, ChordType>();
        
        for(ChordType type : ChordType.values()) {
            typeMap.put(type.name(), type);
        }
    }
    
    public static ChordType forName(String name) {
        return typeMap.get(name.toUpperCase());
    }
}
