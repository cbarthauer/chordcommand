package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.QualityRegistry;

public final class QualityChordFinder implements ChordFinder {

    private List<VoicedChord> chordList;
    
    public QualityChordFinder(
            VoicedChordBuilder triadBuilder,
            VoicedChordBuilder seventhBuilder,
            VoicedChordBuilder ninthBuilder,            
            QualityRegistry registry) {
        
        chordList = new ArrayList<VoicedChord>();
        
        for(NoteName noteName : NoteName.all()) {
            addChords(noteName, registry.all(ChordType.TRIAD), triadBuilder);
            addChords(noteName, registry.all(ChordType.SEVENTH), seventhBuilder);
            addChords(noteName, registry.all(ChordType.NINTH), ninthBuilder);
        }
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

    @Override
    public final List<VoicedChord> find(NoteName note, ChordMember member) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        for(VoicedChord chord : chordList) {
            if(chord.noteNameFromChordMember(member).equals(note)) {
                result.add(chord);
            }
        }
        
        return result;
    }

    private void addChords(
            NoteName noteName, 
            List<Quality> qualities,
            VoicedChordBuilder builder) {
        
        for(Quality quality : qualities) {
            chordList.add(
                builder.setRoot(noteName)
                    .setQuality(quality)
                    .buildVoicedChord());
        }
    }

}
