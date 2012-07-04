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

import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.VoicePart;

public interface VoicedChord {
    public boolean containsNoteNames(List<NoteName> noteNameList);
    public int difference(VoicedChord chord);
	public Duration getDuration();
	public List<Integer> getMidiNumberList();
	public List<Note> getNoteList();
	public int getOctave();
	public Quality getQuality();
	public String getSymbol();
	public int getTicks(int ppq);
	public List<VoicePart> getVoicePartList();
	public Voicing getVoicing();
	public NoteName noteNameFromChordMember(ChordMember chordMember);
	public Note noteFromVoicePart(VoicePart part);
}
