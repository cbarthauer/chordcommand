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

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.VoicePart;

public final class VoicedChordImpl implements VoicedChord {

    private NoteName root;
    private Quality quality;    
    private Voicing voicing;
    private int octave;
    private Duration duration;
    private ArrayList<VoicePart> partList;
    private List<Note> noteList;
    private List<NoteName> noteNameList;
    private Map<VoicePart, Note> voicePartMap;

    VoicedChordImpl(
            NoteName root,
            Quality quality,
            Voicing voicing,
            int octave,
            Duration duration,
            List<VoicePart> partList) {
        
        this.root = root;
        this.quality = quality;
        this.voicing = voicing;
        this.octave = octave;
        this.duration = duration;
        this.partList = new ArrayList<VoicePart>(partList);
        this.noteList = voicing.voice(this, octave, duration);
        this.noteNameList = noteNames(noteList);
        this.voicePartMap = initVoicePartMap(partList, noteList);
    }
    
    @Override
    public final boolean containsNoteNames(List<NoteName> noteNameList) {
        return this.noteNameList.containsAll(noteNameList);
    }

    @Override
    public final int difference(VoicedChord chord) {
        return VoicedChordCalculator.difference(this, chord);
    }

    @Override
    public final Duration getDuration() {
        return duration;
    }

    @Override
    public final List<Integer> getMidiNumberList() {
        List<Integer> midiNumberList = new ArrayList<Integer>();
        
        for(Note note : noteList) {
            midiNumberList.add(note.getMidiNumber());
        }
        
        return midiNumberList;
    }

    @Override
    public final List<Note> getNoteList() {
        return new ArrayList<Note>(noteList);
    }

    @Override
    public final int getOctave() {
        return octave;
    }

    @Override
    public Quality getQuality() {
        return quality;
    }

    @Override
    public final String getSymbol() {
        return noteNameFromChordMember(ChordMember.ROOT) + quality.getSymbol();
    }

    @Override
    public final int getTicks(int ppq) {
        float conversionFactor = duration.getPpqConversionFactor();
        return Math.round(ppq * conversionFactor);
    }

    @Override
    public final List<VoicePart> getVoicePartList() {
        return new ArrayList<VoicePart>(partList);
    }

    @Override
    public final Voicing getVoicing() {
        return voicing;
    }

    @Override
    public final Note noteFromVoicePart(VoicePart part) {
        return voicePartMap.get(part);
    }
    
    @Override
    public final NoteName noteNameFromChordMember(ChordMember chordMember) {
        return quality.getNoteName(root, chordMember);
    }
    
    @Override
    public final String toString() {
        return getSymbol();
    }
    
    private Map<VoicePart, Note> initVoicePartMap(List<VoicePart> partList,
            List<Note> noteList) {
        
        Map<VoicePart, Note> result = new HashMap<VoicePart, Note>();
        
        for(int i = 0; i < partList.size(); i++) {
            result.put(partList.get(i), noteList.get(i));
        }       
        
        return result;
    }

    private List<NoteName> noteNames(List<Note> noteList) {
        List<NoteName> result = new ArrayList<NoteName>();
        
        for(Note note : noteList) {
            result.add(note.getNoteName());
        }
        
        return result;
    }

}
