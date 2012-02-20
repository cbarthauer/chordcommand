tree grammar ChordWalker;

options {
  language = Java;
  tokenVocab = Chord;
  ASTLabelType = CommonTree;
}

@header {
  package music.chord.decorator;
  
  import java.util.ArrayList;
  import java.util.List;
}

@members {
  List<Chord> chords = new ArrayList<Chord>();
  VoicingManager voicingManager = new VoicingManager();
  VoicingType currentVoicingType = null;
  Voicing currentVoicing = null;
}


compilationUnit returns [ChordProgression result]
    : ^(UNIT voicingDef+ progressionDef*) {
        result = ChordProgression.getInstance()
            .setChordList(chords)
            .setVoicingManager(voicingManager);
    }
    ;
    
voicingDef 
    : ^('voicings' voicingTypeList+)
    ;
    
voicingTypeList 
    : ^(TRIADS { currentVoicingType = VoicingType.TRIAD; } chordMemberList+)
    | ^(SEVENTHS { currentVoicingType = VoicingType.SEVENTH; } chordMemberList+)
    ;
    
chordMemberList 
    : ^(VOICING { currentVoicing = Voicing.getInstance(); } 
            (member=chordMember {currentVoicing.addChordMember(ChordMember.memberFromName($member.name));} )+ 
       ) {         
            switch(currentVoicingType) {
                case TRIAD:
                    voicingManager.addTriadVoicing(currentVoicing);
                    break;
                case SEVENTH:
                    voicingManager.addSeventhVoicing(currentVoicing);
                    break;
                default:
                    throw new RuntimeException("Illegal VoicingType: " + currentVoicingType);
            }
        }
    ;
    
progressionDef 
    : ^('progression' chord+)
    ;

chord 
    : ^(CHORD NOTE_NAME QUALITY){chords.add(
            new Triad(
                NoteName.rootFromSymbol($NOTE_NAME.text), 
                Quality.qualityFromAbbreviation($QUALITY.text)
            )
         );}
    | ^(CHORD NOTE_NAME){chords.add(
            new Triad(
                NoteName.rootFromSymbol($NOTE_NAME.text), 
                Quality.MAJOR
            )
         );}
    | ^(CHORD NOTE_NAME SEVEN) {chords.add(
            new SeventhChord(
                new Triad(NoteName.rootFromSymbol($NOTE_NAME.text), Quality.MAJOR),
                Quality.MINOR
            )
         );}
    | ^(CHORD NOTE_NAME MINOR_SEVEN) {chords.add(
            new SeventhChord(
                new Triad(NoteName.rootFromSymbol($NOTE_NAME.text), Quality.MINOR),
                Quality.MINOR
            )
         );}
    | ^(CHORD NOTE_NAME MINOR_SIX) {chords.add(
            new SeventhChord(
                new Triad(NoteName.rootFromSymbol($NOTE_NAME.text).up(Interval.MAJOR_SIXTH), Quality.DIMINISHED),
                Quality.MINOR
            )
         );}
    | ^(CHORD NOTE_NAME DIMINISHED_SEVEN) {chords.add(
            new SeventhChord(
                new Triad(NoteName.rootFromSymbol($NOTE_NAME.text), Quality.DIMINISHED),
                Quality.DIMINISHED
            )
         );}
    ;
    
chordMember returns [String name] 
    : ROOT {name = "ROOT";}
    | THIRD {name = "THIRD";}
    | FIFTH {name = "FIFTH";}
    | SEVENTH {name = "SEVENTH";}
    ;
    
