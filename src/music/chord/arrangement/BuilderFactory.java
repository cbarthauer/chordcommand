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

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.VoicePart;

public class BuilderFactory {

    private static final int defaultOctaveShift = 4;
    
    public static VoicedChordBuilder getNinthBuilder(
            NoteName defaultRoot, 
            Quality defaultQuality) {
        
        Voicing ninthVoicing = new VoicingImpl(VoicingValidator.NINTH_VALIDATOR);
        ninthVoicing.addChordMember(ChordMember.ROOT);
        ninthVoicing.addChordMember(ChordMember.SEVENTH);
        ninthVoicing.addChordMember(ChordMember.NINTH);
        ninthVoicing.addChordMember(ChordMember.THIRD);
        
        VoicedChordBuilder ninthBuilder = new VoicedChordBuilderImpl(
            new VoicedChordConfig(
                defaultRoot,
                defaultQuality,
                ninthVoicing,
                defaultOctaveShift,
                Duration.QUARTER,
                VoicePart.barbershopDefault()));
        
        return ninthBuilder;
    }

    public static VoicedChordBuilder getSeventhBuilder(
            NoteName defaultRoot, 
            Quality defaultQuality) {
        
        Voicing seventhVoicing = new VoicingImpl(VoicingValidator.SEVENTH_VALIDATOR);
        seventhVoicing.addChordMember(ChordMember.ROOT);
        seventhVoicing.addChordMember(ChordMember.FIFTH);
        seventhVoicing.addChordMember(ChordMember.SEVENTH);
        seventhVoicing.addChordMember(ChordMember.THIRD);
        
        VoicedChordBuilder builder = new VoicedChordBuilderImpl(
            new VoicedChordConfig(
                defaultRoot,
                defaultQuality,
                seventhVoicing, 
                defaultOctaveShift, 
                Duration.QUARTER,
                VoicePart.barbershopDefault()));
        
        return builder;
    }

    public static VoicedChordBuilder getTriadBuilder(
            NoteName defaultRoot, 
            Quality defaultQuality) {
        
        Voicing triadVoicing = new VoicingImpl(VoicingValidator.TRIAD_VALIDATOR);
        triadVoicing.addChordMember(ChordMember.ROOT);
        triadVoicing.addChordMember(ChordMember.FIFTH);
        triadVoicing.addChordMember(ChordMember.ROOT);
        triadVoicing.addChordMember(ChordMember.THIRD);
        
        VoicedChordBuilder triadBuilder = new VoicedChordBuilderImpl(
            new VoicedChordConfig(
                defaultRoot,
                defaultQuality,
                triadVoicing,
                defaultOctaveShift, 
                Duration.QUARTER, 
                VoicePart.barbershopDefault()));
      
        return triadBuilder;
    }

    private BuilderFactory() {
        //Hide constructor for static factory.
    }
}
