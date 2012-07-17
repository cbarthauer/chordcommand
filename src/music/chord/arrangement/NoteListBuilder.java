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

import java.util.ArrayList;
import java.util.List;

import music.chord.base.Duration;
import music.chord.base.NoteName;

public final class NoteListBuilder {
    private List<Note> list;

    public NoteListBuilder() {
        this.list = new ArrayList<Note>();
    }

    public final List<Note> build() {
        List<Note> result = new ArrayList<Note>(list);
        list = new ArrayList<Note>();
        return result;
    }

    public final NoteListBuilder shiftUp(int octaveshift) {
        for(Note note : list) {
            note.setOctave(octaveshift);
        }
        
        return this;
    }

    public final NoteListBuilder add(
            NoteName noteName, 
            int midiNumber, 
            Duration duration) {
        
        list.add(new Note(noteName, midiNumber, duration));
        return this;
    }

    public final NoteName nextNoteName(MelodicInterval interval) {
        return interval.forNoteName(
            list.get(list.size() - 1)
                .getNoteName());
    }

    public final int nextMidiNumber(MelodicInterval interval) {
        return list
                .get(list.size() - 1)
                .getMidiNumber() 
                + interval.chromaticHalfSteps();
    }
}
