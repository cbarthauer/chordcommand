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

import music.chord.base.Interval;
import music.chord.base.NoteName;

public class IntervalDirective {

    private Interval interval;
    
    public IntervalDirective(Interval interval) {
        this.interval = interval;
    }
    
    public NoteName calculate(NoteName root) {
        NoteName noteName = root.up(interval);
        return noteName;
    }
    
    public String toString() {
        return "IntervalDirective.toString(): Up " + interval;
    }

}
