tree grammar ChordWalker;

options {
  language = Java;
  tokenVocab = Chord;
  ASTLabelType = CommonTree;
}

@header {
  package music.chord.grammar;
  
  import java.util.ArrayList;
  import java.util.List;
  
  import music.chord.arrangement.VoicedChord;
  import music.chord.arrangement.VoicingManager;
  import music.chord.arrangement.SeventhVoicing;
  import music.chord.arrangement.TriadVoicing;
  import music.chord.arrangement.Voicing;
  
  import music.chord.base.ChordMember;
  import music.chord.base.Interval;
  import music.chord.base.NoteName;
  import music.chord.base.TriadQuality;

  import music.chord.builder.ChordProgression;
  import music.chord.builder.DerivedChordBuilder;
  import music.chord.builder.TriadBuilder;
  import music.chord.builder.SeventhBuilder;
}

@members {
  List<VoicedChord> chords = new ArrayList<VoicedChord>();
  VoicingManager voicingManager = new VoicingManager();
  DerivedChordBuilder chordBuilder = new DerivedChordBuilder();
  TriadBuilder triadBuilder = new TriadBuilder();
  SeventhBuilder seventhBuilder = new SeventhBuilder(triadBuilder);
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
              voicing = new SeventhVoicing();
          }
          else {
              voicing = new TriadVoicing();
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
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setTriadQuality(TriadQuality.qualityFromSymbol($QUALITY.text))
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME) {chord =
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setTriadQuality(TriadQuality.MAJOR)
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setTriadQuality(TriadQuality.MAJOR)
                .setSeventhInterval(Interval.MINOR_SEVENTH)
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME MINOR_SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setTriadQuality(TriadQuality.MINOR)
                .setSeventhInterval(Interval.MINOR_SEVENTH)
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME MAJOR_SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setTriadQuality(TriadQuality.MAJOR)
                .setSeventhInterval(Interval.MAJOR_SEVENTH)
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME MINOR_SIX) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text).up(Interval.MAJOR_SIXTH))
                .setTriadQuality(TriadQuality.DIMINISHED)
                .setSeventhInterval(Interval.MINOR_SEVENTH)
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME DIMINISHED_SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setTriadQuality(TriadQuality.DIMINISHED)
                .setSeventhInterval(Interval.DIMINISHED_SEVENTH)
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
    
