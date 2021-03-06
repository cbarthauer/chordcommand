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

@Deprecated
enum QualityEnum {
    MAJOR_TRIAD("M", ChordType.TRIAD),
    MINOR_TRIAD("m", ChordType.TRIAD),
    AUGMENTED_TRIAD("+", ChordType.TRIAD),
    DIMINISHED_TRIAD("dim", ChordType.TRIAD),
    SUSPENDED_TRIAD("sus", ChordType.TRIAD),
    DIMINISHED_SEVENTH("dim7", ChordType.SEVENTH),
    MINOR_SEVENTH("m7", ChordType.SEVENTH),
    MAJOR_SEVENTH("M7", ChordType.SEVENTH),
    DOMINANT_SEVENTH("dom7", ChordType.SEVENTH),
    HALF_DIMINISHED_SEVENTH("hdim7", ChordType.SEVENTH),
    SUSPENDED_SEVENTH("sus7", ChordType.SEVENTH),
//    MINOR_MAJOR_SEVENTH("mM7", ChordType.SEVENTH)
    DOMINANT_NINTH("dom9", ChordType.NINTH),
    MINOR_NINTH("m9", ChordType.NINTH),
    MAJOR_NINTH("M9", ChordType.NINTH);
    
    private static final Map<String, QualityEnum> nameMap;
    
    static {
        nameMap = new HashMap<String, QualityEnum>();
        
        for(QualityEnum quality : QualityEnum.values()) {
            nameMap.put(quality.name(), quality);
        }
    }

    public static QualityEnum forName(String name) {
        return nameMap.get(name.toUpperCase());
    }
    
    private String symbol;
    private ChordType type;

    private QualityEnum(String symbol, ChordType type) {
        this.symbol = symbol;
        this.type = type;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public ChordType getType() {
        return type;
    }
}
