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
import java.util.Collections;
import java.util.List;

public class ClosestVoicingStrategy implements VoicingStrategy {

    private DerivedChordBuilder chordBuilder;


    public ClosestVoicingStrategy(DerivedChordBuilder chordBuilder) {
        this.chordBuilder = chordBuilder;
    }
    
    
    @Override
    public VoicedChord voice(
            VoicedChord previousChord, 
            VoicedChord nextChord, 
            List<Voicing> voicingList) {
        
        VoicedChord result = null;
        
        for(Voicing currentVoicing : voicingList) {
            VoicedChord currentVoicedChord = chordBuilder.setChord(nextChord)
                .setVoicing(currentVoicing)
                .buildVoicedChord();
            
            if(
                result == null 
                || currentVoicedChord.difference(previousChord) 
                    < result.difference(previousChord)
            ) {
                result = currentVoicedChord;
            }
        }
        
        return result;
    }
    
    
    public List<VoicingComparison> voicingComparisonList(
            VoicedChord previousChord, 
            VoicedChord newChord,
            List<Voicing> voicingList) {
        
        List<VoicingComparison> result = new ArrayList<VoicingComparison>();
        
        for(Voicing voicing : voicingList) {
            VoicedChord chord = chordBuilder.setChord(newChord)
                .setVoicing(voicing)
                .buildVoicedChord();
            Integer difference = chord.difference(previousChord);           
            result.add(new VoicingComparison(difference, voicing));
        }
        
        Collections.sort(result);
        
        return result;
    }
}
