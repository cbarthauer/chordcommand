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

final class QualityImpl implements Quality {

    private String name;
    private QualitySymbol symbol;
    private ChordType type;
    private IntervallicStructure structure;

    QualityImpl(
            String name, 
            QualitySymbol symbol, 
            ChordType type, 
            IntervallicStructure structure) {
        
        this.name = name;
        this.symbol = symbol;
        this.type = type;
        this.structure = structure;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QualityImpl other = (QualityImpl) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public final String getName() {
        return name;
    }

    public final NoteName getNoteName(NoteName root, ChordMember chordMember) {
        if(ChordMember.ROOT.equals(chordMember)) {
            return root;
        }
        else {
            return structure.getNoteName(root, chordMember);
        }
    }

    public final String getSymbol() {
        return symbol.getSymbol();
    }

    public final ChordType getType() {
        return type;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    
    public final String toString() {
        return getSymbol();
    }

}
