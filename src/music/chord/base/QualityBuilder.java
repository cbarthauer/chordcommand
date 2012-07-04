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

public class QualityBuilder {
    
    private IntervallicStructure struct;
    private String name;
    private ChordType type;

    public Quality build() {
        return new QualityImpl(name, QualitySymbol.forName(name), type, struct);
    }
    
    public void setIntervallicStructure(IntervallicStructure struct) {
        this.struct = struct;
    }
    
    public void setName(String name) {
        this.name = name.toUpperCase();
    }
    
    public void setType(ChordType type) {
        this.type = type;
    }
    
    
}
