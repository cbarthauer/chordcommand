package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import music.chord.base.ChordMember;
import music.chord.base.ChordSpec;
import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.engine.protocol.ChordPair;

public class ChordFinder {
    private List<VoicedChord> chordList;

    public ChordFinder(ChordDefinitionStructure struct) {
        VoicedChordBuilder triadBuilder = BuilderFactory.getTriadBuilder(struct);
        VoicedChordBuilder seventhBuilder = BuilderFactory.getSeventhBuilder(struct);
        VoicedChordBuilder ninthBuilder = BuilderFactory.getNinthBuilder(struct);
        
        chordList = new ArrayList<VoicedChord>();
        
        for(NoteName name : NoteName.all()) {
            addChords(struct, name, "Triad", triadBuilder);
            addChords(struct, name, "Seventh", seventhBuilder);
            addChords(struct, name, "Ninth", ninthBuilder);
        }
    }

    public List<VoicedChord> find(NoteName note, ChordMember member) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        for(VoicedChord chord : chordList) {
            if(note.equals(chord.noteNameFromChordMember(member))) {
                result.add(chord);
            }
        }
        
        return result;
    }

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
            ChordDefinitionStructure struct,
            NoteName name,
            String typeName,
            VoicedChordBuilder builder) {

        Map<Quality, ChordSpec> qualityMap = struct.qualityMapFromType(ChordType.forName(typeName));
        
        for(Quality quality : qualityMap.keySet()) {
            chordList.add(
                builder.setPair(new ChordPair(name, quality))
                    .buildVoicedChord());
        }
    }
}
