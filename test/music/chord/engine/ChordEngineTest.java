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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import music.chord.TestHelper;
import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordFinder;
import music.chord.arrangement.ChordFinderImpl;
import music.chord.arrangement.ChordMemberFilter;
import music.chord.arrangement.ChordVoicerFactory;
import music.chord.arrangement.EqualsFilter;
import music.chord.arrangement.QualityRegistry;
import music.chord.arrangement.QualityRegistryFactory;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.arrangement.Voicing;
import music.chord.arrangement.VoicingComparison;
import music.chord.arrangement.VoicingFactory;
import music.chord.base.ChordMember;
import music.chord.base.Constants;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.engine.protocol.ChordRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.RequestBuilder;

import org.junit.Before;
import org.junit.Test;

public class ChordEngineTest {
        
    private ChordEngine engine;
    private Identifier id;
    private RequestBuilder builder;
    private TestHelper helper;
    private ChordRequest request;
    private QualityRegistry qualities;
    
    @Before
    public void setUp() throws Exception {
        qualities = QualityRegistryFactory.getInstance(
                Constants.getChordDefinitions());
        
        VoicedChordBuilder triadBuilder = BuilderFactory.getTriadBuilder(
                NoteName.forSymbol("C"),
                qualities.forName("MAJOR_TRIAD"));
        VoicedChordBuilder seventhBuilder = BuilderFactory.getSeventhBuilder(
                NoteName.forSymbol("C"),
                qualities.forName("DOMINANT_SEVENTH"));
        VoicedChordBuilder ninthBuilder = BuilderFactory.getNinthBuilder(
                NoteName.forSymbol("C"),
                qualities.forName("DOMINANT_NINTH"));
        
        ChordFinder finder = new ChordFinderImpl(
                triadBuilder,
                seventhBuilder,
                ninthBuilder,
                qualities.all());
        
        engine = ChordEngineBuilder.build(
                triadBuilder,
                seventhBuilder,
                ninthBuilder,
                ChordVoicerFactory.getInstance(qualities),
                finder,
                qualities);
        
        id = new Identifier("default");
        builder = new RequestBuilder(id);
        helper = new TestHelper();
        
        request = builder.chordRequest(
            helper.getChord("C", "MAJOR_TRIAD"),
            helper.getChord("D", "MINOR_TRIAD"),
            helper.getChord("G", "DOMINANT_SEVENTH"),
            helper.getChord("C", "MAJOR_TRIAD"));
    }
    
    @Test
    public void addChords() {      
        engine.createChordList(request);
        List<VoicedChord> chordList = engine.byIdentifier(id);
        assertEquals(4, chordList.size());
    }
    
    @Test
    public void byIdentifier() {
        engine.createChordList(request);
        List<Integer> indexes = new ArrayList<Integer>();
        indexes.add(1);
        indexes.add(3);
        List<VoicedChord> chordList = engine.byIdentifier(id, indexes);
        assertEquals(2, chordList.size());
        assertEquals(
                NoteName.forSymbol("D"), 
                chordList.get(0).noteNameFromChordMember(ChordMember.ROOT));
        assertEquals(
                NoteName.forSymbol("C"), 
                chordList.get(1).noteNameFromChordMember(ChordMember.ROOT));
    }
    
    @Test
    public void createChord() {
        VoicedChord chord = engine.createChord(
            NoteName.forSymbol("C"), 
            qualities.forName("MAJOR_TRIAD"));
        
        assertEquals(NoteName.forSymbol("C"), chord.noteNameFromChordMember(ChordMember.ROOT));
        assertEquals(qualities.forName("MAJOR_TRIAD"), chord.getQuality());
    }
    
    @Test
    public void chordsContaining() {
        List<NoteName> noteNameList = new ArrayList<NoteName>();
        noteNameList.add(NoteName.forSymbol("C"));
        noteNameList.add(NoteName.forSymbol("E"));
        noteNameList.add(NoteName.forSymbol("G"));
        noteNameList.add(NoteName.forSymbol("Bb"));
        
        List<VoicedChord> chords = engine.chordsContaining(noteNameList);
        assertEquals(1, chords.size());
        assertTrue(chords.get(0).containsNoteNames(noteNameList));
    }
    
    @Test
    public void chordsByFilter() {
        ChordMemberFilter filter = new EqualsFilter(ChordMember.SEVENTH, NoteName.forSymbol("C"));
        List<VoicedChord> chordList = engine.chordsByFilter(filter);
        assertTrue(chordList.size() > 0);
    }
    
    @Test
    public void compareVoicings() {
        VoicedChord first = engine.createChord(
                NoteName.forSymbol("C"), 
                qualities.forName("MAJOR_TRIAD"));
        VoicedChord second = engine.createChord(
                NoteName.forSymbol("F"), 
                qualities.forName("MAJOR_TRIAD"));
        List<VoicingComparison> comparisonList = engine.compareVoicings(first, second);
        assertTrue(comparisonList.size() > 0);
    }
    
    @Test
    public void insertChords() {
        engine.createChordList(request)
            .insertChords(
                builder.insertRequest(
                    2, 
                    helper.getChord("Eb", "MINOR_SEVENTH"),
                    helper.getChord("F#", "MINOR_SEVENTH")));
        
        assertEquals("Dm", engine.byIdentifier(id).get(1).getSymbol());
        assertEquals("Ebm7", engine.byIdentifier(id).get(2).getSymbol());
        assertEquals("F#m7", engine.byIdentifier(id).get(3).getSymbol());
        assertEquals("Gdom7", engine.byIdentifier(id).get(4).getSymbol());
    }

    @Test
    public void load() {
        List<VoicedChord> chordList = engine.load("examples/out.txt");
        assertTrue(!chordList.isEmpty());
    }
    
    @Test
    public void removeChords() {
        engine.createChordList(request)
            .removeChords(builder.removeRequest(0, 3));
        
        assertEquals("Dm", engine.byIdentifier(id).get(0).getSymbol());
        assertEquals("Gdom7", engine.byIdentifier(id).get(1).getSymbol());
    }

    @Test
    public void setVoicings() {
        Voicing voicing = VoicingFactory.instanceFromChordMemberList(
            ChordMember.THIRD, 
            ChordMember.FIFTH, 
            ChordMember.ROOT, 
            ChordMember.FIFTH);
        builder.setVoicing(voicing);
        
        engine.createChordList(request)
            .setVoicings(builder.voicingRequest(0, 2));
        
        List<VoicedChord> chordList = engine.byIdentifier(id);
        
        assertEquals(chordList.get(0).getVoicing(), voicing);
        assertNotSame(chordList.get(1).getVoicing(), voicing);
        assertEquals(chordList.get(2).getVoicing(), voicing);
        assertNotSame(chordList.get(3).getVoicing(), voicing);
    }
    
    @Test
    public void setDurations() {
        builder.setDuration(Duration.HALF);
        engine.createChordList(request)
            .setDurations(builder.durationRequest(1, 3));
        List<VoicedChord> chordList = engine.byIdentifier(id);
        
        assertEquals(Duration.QUARTER, chordList.get(0).getDuration());
        assertEquals(Duration.HALF, chordList.get(1).getDuration());
        assertEquals(Duration.QUARTER, chordList.get(2).getDuration());
        assertEquals(Duration.HALF, chordList.get(3).getDuration());
    }
    
    @Test
    public void setOctaves() {
        builder.setOctave(3);
        engine.createChordList(request)
            .setOctaves(builder.octaveRequest(0, 3));
        List<VoicedChord> chordList = engine.byIdentifier(id);
        
        assertEquals(3, chordList.get(0).getOctave());
        assertEquals(4, chordList.get(1).getOctave());
        assertEquals(4, chordList.get(2).getOctave());
        assertEquals(3, chordList.get(3).getOctave());
    }
    
    @Test
    public void voiceAll() {
        List<VoicedChord> chordList = engine.createChordList(request)
            .voiceAll(engine.byIdentifier(id));
        assertNotSame(chordList.get(2).getVoicing(), chordList.get(3).getVoicing());
    }
}
