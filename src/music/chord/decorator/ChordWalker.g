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
  ChordBuilder chordBuilder = new ChordBuilder();
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
    : ^(TRIADS (currentList=chordMemberList {voicingManager.addTriadVoicing($currentList.voicing);})+)
    | ^(SEVENTHS (currentList=chordMemberList {voicingManager.addSeventhVoicing($currentList.voicing);})+)
    ;
    
chordMemberList returns [Voicing voicing]
    : ^(VOICING { voicing = Voicing.getInstance(); } 
            (member=chordMember {voicing.addChordMember(ChordMember.memberFromName($member.name));} )+ 
       )
    ;
    
progressionDef 
    : ^('progression' (currentChord=chord {chords.add($currentChord.value);} )+)
    ;

chord returns [Chord value]
    : ^(CHORD currentSpec=chordSpec) {value = $currentSpec.chord;}
    | ^(CHORD currentSpec=chordSpec currentAttr=chordAttr) {
        value = new VoicedChord($currentSpec.chord, $currentAttr.voicing);
    }
    ;
    
chordSpec returns [Chord chord]
    : ^(SPEC NOTE_NAME QUALITY){chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.qualityFromAbbreviation($QUALITY.text))
                .build();
         }
    | ^(SPEC NOTE_NAME) {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .build();
         }
    | ^(SPEC NOTE_NAME SEVEN) {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .setSeventhQuality(Quality.MINOR)
                .build();
         }
    | ^(SPEC NOTE_NAME MINOR_SEVEN) {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MINOR)
                .setSeventhQuality(Quality.MINOR)
                .build();
         }
    | ^(SPEC NOTE_NAME MAJOR_SEVEN) {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .setSeventhQuality(Quality.MAJOR)
                .build();
         }
    | ^(SPEC NOTE_NAME MINOR_SIX) {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text).up(Interval.MAJOR_SIXTH))
                .setTriadQuality(Quality.DIMINISHED)
                .setSeventhQuality(Quality.MINOR)
                .build();
         }
    | ^(SPEC NOTE_NAME DIMINISHED_SEVEN) {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.DIMINISHED)
                .setSeventhQuality(Quality.DIMINISHED)
                .build();
         }
    ;

chordAttr returns [Voicing voicing]
    : ^(ATTR currentList=chordMemberList) {voicing = $currentList.voicing;}
    ;
    
chordMember returns [String name] 
    : ROOT {name = "ROOT";}
    | THIRD {name = "THIRD";}
    | FIFTH {name = "FIFTH";}
    | SEVENTH {name = "SEVENTH";}
    ;
    
