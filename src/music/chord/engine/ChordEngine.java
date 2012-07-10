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
package music.chord.engine;

import java.util.List;

import music.chord.arrangement.ChordMemberFilter;
import music.chord.arrangement.VoicedChord;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.engine.protocol.ChordRequest;
import music.chord.engine.protocol.DurationRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.InsertChordRequest;
import music.chord.engine.protocol.OctaveRequest;
import music.chord.engine.protocol.RemoveChordRequest;
import music.chord.engine.protocol.VoicingRequest;

public interface ChordEngine {
    public ChordEngine createChordList(ChordRequest request);
    public List<VoicedChord> byIdentifier(Identifier id);
    public List<VoicedChord> byIdentifier(Identifier id, List<Integer> indexes);
    public List<VoicedChord> chordsByFilter(ChordMemberFilter filter);
    public List<VoicedChord> chordsContaining(List<NoteName> noteNameList);
    
    public VoicedChord createChord(NoteName root, Quality quality);
    public ChordEngine insertChords(InsertChordRequest request);
    public List<VoicedChord> load(String fileName);
    public ChordEngine removeChords(RemoveChordRequest request);
    public ChordEngine setDurations(DurationRequest request);
    public ChordEngine setOctaves(OctaveRequest request);
    public ChordEngine setVoicings(VoicingRequest request);
    public List<VoicedChord> voiceAll(List<VoicedChord> chordList);
}
