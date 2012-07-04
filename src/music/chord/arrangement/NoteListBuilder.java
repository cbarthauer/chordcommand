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

class NoteListBuilder {
    private List<Note> list;

    NoteListBuilder() {
        this.list = new ArrayList<Note>();
    }

    List<Note> getNoteList() {
        return list;
    }

    void shiftUp(int octaveshift) {
        for(Note note : list) {
            note.setOctave(octaveshift);
        }
    }

    void add(NoteName noteName, int midiNumber, Duration duration) {
        list.add(new Note(noteName, midiNumber, duration));
    }
}
