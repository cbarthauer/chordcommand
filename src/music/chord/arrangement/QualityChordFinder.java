package music.chord.arrangement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.base.ChordMember;
import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;

public final class QualityChordFinder implements ChordFinder {

    private List<VoicedChord> chordList;
    private Map<ChordType, VoicedChordBuilder> builderMap;
    private List<Quality> qualities;
    
    public QualityChordFinder(
            VoicedChordBuilder triadBuilder,
            VoicedChordBuilder seventhBuilder,
            VoicedChordBuilder ninthBuilder,            
            List<Quality> qualities) {
        
        builderMap = new HashMap<ChordType, VoicedChordBuilder>();
        builderMap.put(ChordType.TRIAD, triadBuilder);
        builderMap.put(ChordType.SEVENTH, seventhBuilder);
        builderMap.put(ChordType.NINTH, ninthBuilder);
        
        this.qualities = new ArrayList<Quality>(qualities);
        chordList = initChordList();
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

    private List<VoicedChord> initChordList() {   
        List<VoicedChord> result = new ArrayList<VoicedChord>();
        
        for(NoteName noteName : NoteName.all()) {
            for(Quality quality : qualities) {
                VoicedChordBuilder builder = builderMap.get(quality.getType());
                result.add(
                    builder.setRoot(noteName)
                        .setQuality(quality)
                        .buildVoicedChord());
            }
        }
        
        return result;
    }

}
