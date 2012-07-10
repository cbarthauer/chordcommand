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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;

public final class ChordFinderImpl implements ChordFinder {

    private List<VoicedChord> chordList;
    private Map<ChordType, VoicedChordBuilder> builderMap;
    private List<Quality> qualities;
    
    public ChordFinderImpl(
            VoicedChordBuilder triadBuilder,
            VoicedChordBuilder seventhBuilder,
            VoicedChordBuilder ninthBuilder,            
            List<Quality> qualities) {
        
        builderMap = new HashMap<ChordType, VoicedChordBuilder>();
        builderMap.put(ChordType.TRIAD, triadBuilder);
        builderMap.put(ChordType.SEVENTH, seventhBuilder);
        builderMap.put(ChordType.NINTH, ninthBuilder);
        
        this.qualities = new ArrayList<Quality>(qualities);
        chordList = initChordList();
    }
    
    @Override
    public final List<VoicedChord> find(ChordMemberFilter filter) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        for(VoicedChord chord : chordList) {
            if(filter.filter(chord)) {
                result.add(chord);
            }
        }
        
        return result;
    }

    @Override
    public final List<VoicedChord> find(List<NoteName> noteNameList) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        for(VoicedChord chord : chordList) {
            if(chord.containsNoteNames(noteNameList)) {
                result.add(chord);
            }
        }
        
        return result;
    }

    private List<VoicedChord> initChordList() {   
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        for(NoteName noteName : NoteName.all()) {
            for(Quality quality : qualities) {
                VoicedChordBuilder builder = builderMap.get(quality.getType());
                result.add(
                    builder.setRoot(noteName)
                        .setQuality(quality)
                        .buildVoicedChord());
            }
        }
        
        return result;
    }

}
