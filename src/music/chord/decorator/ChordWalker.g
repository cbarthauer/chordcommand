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
  List<VoicedChord> chords = new ArrayList<VoicedChord>();
  VoicingManager voicingManager = new VoicingManager();
  DerivedChordBuilder chordBuilder = new DerivedChordBuilder();
  TriadBuilder triadBuilder = new TriadBuilder();
  SeventhBuilder seventhBuilder = new SeventhBuilder();
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
    : ^(VOICING { List<ChordMember> chordMemberList = new ArrayList<ChordMember>(); } 
            (member=chordMember {chordMemberList.add(ChordMember.memberFromName($member.name));} )+ 
       ) {
          if(chordMemberList.contains(ChordMember.SEVENTH)) {
              voicing = SeventhVoicing.getInstance();
          }
          else {
              voicing = TriadVoicing.getInstance();
          }  
        
          for(ChordMember currentMember : chordMemberList) {
              voicing.addChordMember(currentMember);
          }
      }
    ;
    
progressionDef 
    : ^('progression' (currentChord=chord {chords.add($currentChord.value);} )+)
    ;

chord returns [VoicedChord value]
    : ^(CHORD currentSpec=chordSpec) {value = $currentSpec.chord;}
    | ^(CHORD currentSpec=chordSpec currentAttr=chordAttr) {
        value = chordBuilder.setChord($currentSpec.chord)
            .setVoicing($currentAttr.voicing)
            .buildVoicedChord();
    }
    ;
    
chordSpec returns [VoicedChord chord]
    : ^(SPEC NOTE_NAME QUALITY){chord =
            triadBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.qualityFromAbbreviation($QUALITY.text))
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME) {chord =
            triadBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .setSeventhQuality(Quality.MINOR)
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME MINOR_SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MINOR)
                .setSeventhQuality(Quality.MINOR)
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME MAJOR_SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .setSeventhQuality(Quality.MAJOR)
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME MINOR_SIX) {chord =
            seventhBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text).up(Interval.MAJOR_SIXTH))
                .setTriadQuality(Quality.DIMINISHED)
                .setSeventhQuality(Quality.MINOR)
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME DIMINISHED_SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.DIMINISHED)
                .setSeventhQuality(Quality.DIMINISHED)
                .buildVoicedChord();
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
    
