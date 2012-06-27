package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import music.chord.base.ChordMember;
import music.chord.base.ChordPair;
import music.chord.base.ChordSpec;
import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.QualityEnum;

public class ChordFinderImpl implements ChordFinder {
    private List<VoicedChord> chordList;

    public ChordFinderImpl(
            VoicedChordBuilder triadBuilder,
            VoicedChordBuilder seventhBuilder,
            VoicedChordBuilder ninthBuilder,
            ChordDefinitionStructure struct) {
        
        chordList = new ArrayList<VoicedChord>();
        
        for(NoteName name : NoteName.all()) {            
            addChords(
                struct.qualityMapFromType(ChordType.forName("Triad")),
                name, 
                triadBuilder);
            addChords(
                struct.qualityMapFromType(ChordType.forName("Seventh")),
                name,  
                seventhBuilder);
            addChords(
                struct.qualityMapFromType(ChordType.forName("Ninth")),
                name,  
                ninthBuilder);
        }
    }

    /* (non-Javadoc)
     * @see music.chord.arrangement.ChordFinder#find(music.chord.base.NoteName, music.chord.base.ChordMember)
     */
    @Override
    public List<VoicedChord> find(NoteName note, ChordMember member) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        for(VoicedChord chord : chordList) {
            if(note.equals(chord.noteNameFromChordMember(member))) {
                result.add(chord);
            }
        }
        
        return result;
    }

    /* (non-Javadoc)
     * @see music.chord.arrangement.ChordFinder#find(java.util.List)
     */
    @Override
    public List<VoicedChord> find(List<NoteName> noteList) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        for(VoicedChord chord : chordList) {
            if(chord.containsNoteNames(noteList)) {
                result.add(chord);
            }
        }
        
        return result;
    }
    
    private void addChords(
            Map<QualityEnum, ChordSpec> qualityMap,
            NoteName name,
            VoicedChordBuilder builder) {
        
        for(QualityEnum quality : qualityMap.keySet()) {
            chordList.add(
                builder.setPair(new ChordPair(name, quality))
                    .buildVoicedChord());
        }
    }
}
