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

public final class ChordVoicerImpl implements ChordVoicer {

    private VoicingStrategy strategy;
    private QualityRegistry qualities;

    public ChordVoicerImpl(
            VoicingStrategy strategy,
            QualityRegistry qualities) {
        
        this.strategy = strategy;
        this.qualities = qualities;
    } 
    
    @Override
    public final List<VoicedChord> voice(List<VoicedChord> chordList) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        if(chordList.size() == 1) {
            result.add(chordList.get(0));
        }
        else {
            result = voice(chordList.get(0), chordList.subList(1, chordList.size()));
        }
        
        return result;
    }
    

    @Override
    public final List<VoicedChord> voice(VoicedChord startingChord, List<VoicedChord> chordList) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        result.add(startingChord);
        
        VoicedChord previousChord = startingChord;
        
        for(VoicedChord chord : chordList) {
            VoicedChord voicedChord = strategy.voice(
                    previousChord, 
                    chord, 
                    qualities.getCongruentVoicings(chord.getVoicing()));
            result.add(voicedChord);
            previousChord = voicedChord;
        }
        
        
        return result;
    }

    @Override
    public final List<VoicingComparison> voicingComparisonList(
            VoicedChord previousChord, 
            VoicedChord newChord) {

        List<Voicing> voicingList = qualities.forQuality(
                newChord.getQuality());
        
        return strategy.voicingComparisonList(
                previousChord, 
                newChord, 
                voicingList);
    }

}
