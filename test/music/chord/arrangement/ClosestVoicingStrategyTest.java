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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Constants;
import music.chord.base.NoteName;

import org.junit.Before;
import org.junit.Test;

public class ClosestVoicingStrategyTest {

    private VoicingStrategy strategy;
    private QualityRegistry qualities;
    private VoicedChord previousChord;
    private VoicedChord newChord;
    private List<Voicing> voicingList;
    private ArrayList<ChordMember> memberList;

    @Before
    public void setUp() throws Exception {
        strategy = new ClosestVoicingStrategy(new DerivedChordBuilder());
        qualities = QualityRegistryFactory.getInstance(Constants.getChordDefinitions());
        VoicedChordBuilder builder = BuilderFactory.getTriadBuilder(
                NoteName.forSymbol("C"), 
                qualities.forName("MAJOR_TRIAD"));
        
        previousChord = builder.buildVoicedChord();
        newChord = builder.buildVoicedChord();
        
        memberList = new ArrayList<ChordMember>();
        memberList.add(ChordMember.ROOT);
        memberList.add(ChordMember.FIFTH);
        memberList.add(ChordMember.ROOT);
        memberList.add(ChordMember.THIRD);
        
        voicingList = new ArrayList<Voicing>();
        voicingList.add(
                VoicingFactory.instanceFromChordMemberList(
                        memberList));
        voicingList.add(
                VoicingFactory.instanceFromChordMemberList(
                        ChordMember.ROOT, 
                        ChordMember.THIRD, 
                        ChordMember.FIFTH, 
                        ChordMember.ROOT));        
    }

    @Test
    public void voice() {
        VoicedChord chord = strategy.voice(
                previousChord, 
                newChord, 
                voicingList);
        assertEquals(memberList, chord.getVoicing().getChordMembers());        
    }
    
    @Test
    public void voicingComparisonList() {
        List<VoicingComparison> compList = strategy.voicingComparisonList(
                previousChord, 
                newChord, 
                voicingList);
        assertEquals(2, compList.size());
    }

}
