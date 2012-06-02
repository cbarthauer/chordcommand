package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import music.chord.base.ChordMember;
import music.chord.base.ChordSpec;
import music.chord.base.NoteName;
import music.chord.base.Quality;

public class ChordFinder {
    private List<VoicedChord> chordList;

    public ChordFinder(ChordDefinitionStructure struct) {
        VoicedChordBuilder triadBuilder = BuilderFactory.getTriadBuilder();
        VoicedChordBuilder seventhBuilder = BuilderFactory.getSeventhBuilder();
        VoicedChordBuilder ninthBuilder = BuilderFactory.getNinthBuilder();
        
        chordList = new ArrayList<VoicedChord>();
        
        for(NoteName name : NoteName.all()) {
            addChords(struct, name, "Triad", triadBuilder);
            addChords(struct, name, "Seventh", seventhBuilder);
            addChords(struct, name, "Ninth", ninthBuilder);
        }
    }
    
    public List<VoicedChord> find(NoteName note) {
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        for(VoicedChord chord : chordList) {
            for(ChordMember member : ChordMember.values()) {
                if(note.equals(chord.noteNameFromChordMember(member))) {
                    result.add(chord);
                }
            }
        }
        
        return result;
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

    private void addChords(
            ChordDefinitionStructure struct,
            NoteName name,
            String type,
            VoicedChordBuilder builder) {

        Map<Quality, ChordSpec> qualityMap = struct.qualityMapFromType(type);
        
        for(Quality quality : qualityMap.keySet()) {
            chordList.add(
                builder.setRoot(name)
                    .setChordSpec(qualityMap.get(quality))
                    .setSymbol(quality + " " + type)
                    .buildVoicedChord());
        }
    }
}
