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

import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.VoicePart;

public interface VoicedChordBuilder {
    public VoicedChord buildVoicedChord();
    public VoicedChordBuilder setChord(VoicedChord chord);
    public VoicedChordBuilder setDuration(Duration duration);
    public VoicedChordBuilder setOctave(int octave);
    public VoicedChordBuilder setQuality(Quality quality);
    public VoicedChordBuilder setRoot(NoteName noteName);
    public VoicedChordBuilder setVoicePartList(List<VoicePart> partList);
    public VoicedChordBuilder setVoicing(Voicing voicing);
}
