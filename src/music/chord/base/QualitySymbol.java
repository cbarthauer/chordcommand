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

public enum QualitySymbol {
    MAJOR_TRIAD("M"),
    MINOR_TRIAD("m"),
    AUGMENTED_TRIAD("+"),
    DIMINISHED_TRIAD("dim"),
    SUSPENDED_TRIAD("sus"),
    DIMINISHED_SEVENTH("dim7"),
    MINOR_SEVENTH("m7"),
    MAJOR_SEVENTH("M7"),
    DOMINANT_SEVENTH("dom7"),
    HALF_DIMINISHED_SEVENTH("hdim7"),
    SUSPENDED_SEVENTH("sus7"),
//    MINOR_MAJOR_SEVENTH("mM7")
    DOMINANT_NINTH("dom9"),
    MINOR_NINTH("m9"),
    MAJOR_NINTH("M9");
    
    private final static Map<String, QualitySymbol> nameMap;
    
    static {
        nameMap = new HashMap<String, QualitySymbol>();
        
        for(QualitySymbol qs : QualitySymbol.values()) {
            nameMap.put(qs.name(), qs);
        }
    }
    
    public static QualitySymbol forName(String name) {
        return nameMap.get(name);
    }
    
    private String symbol;
    
    private QualitySymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public final String getSymbol() {
        return symbol;
    }
}
