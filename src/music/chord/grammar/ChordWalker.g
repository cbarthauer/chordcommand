tree grammar ChordWalker;

options {
  language = Java;
  tokenVocab = Chord;
  ASTLabelType = CommonTree;
  superClass = AbstractTreeParser;
}

@header {
  package music.chord.grammar;
  
  import java.util.ArrayList;
  import java.util.List;
  
  import music.chord.arrangement.ChordPlayer;
  import music.chord.arrangement.VoicedChord;
  import music.chord.arrangement.VoicingManager;
  import music.chord.arrangement.SeventhVoicing;
  import music.chord.arrangement.TriadVoicing;
  import music.chord.arrangement.Voicing;
  import music.chord.arrangement.DerivedChordBuilder;
  import music.chord.arrangement.ChordProgression;
  import music.chord.arrangement.ChordVoicer;
  import music.chord.arrangement.VoicedChordBuilder;
  
  import music.chord.base.ChordMember;
  import music.chord.base.Interval;
  import music.chord.base.NoteName;
  import music.chord.base.SeventhQuality;
  import music.chord.base.TriadQuality;
}

@members {
  List<VoicedChord> chords = new ArrayList<VoicedChord>();
  VoicingManager voicingManager = new VoicingManager();
  DerivedChordBuilder chordBuilder = new DerivedChordBuilder();
  VoicedChordBuilder triadBuilder;
  VoicedChordBuilder seventhBuilder; 
  
  public void setSeventhBuilder(VoicedChordBuilder seventhBuilder) {
    this.seventhBuilder = seventhBuilder;
  }
  
  public void setTriadBuilder(VoicedChordBuilder triadBuilder) {
    this.triadBuilder = triadBuilder;
  }
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
          voicing = voicingFromChordMemberList(chordMemberList);
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
                .setChordSpec(TriadQuality.qualityFromSymbol($QUALITY.text).getChordSpec())
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME) {chord =
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(TriadQuality.MAJOR.getChordSpec())
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(SeventhQuality.DOMINANT.getChordSpec())
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME MINOR_SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(SeventhQuality.MINOR.getChordSpec())
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME MAJOR_SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(SeventhQuality.MAJOR.getChordSpec())
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME MINOR_SIX) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text).up(Interval.MAJOR_SIXTH))
                .setChordSpec(SeventhQuality.HALF_DIMINISHED.getChordSpec())
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME DIMINISHED_SEVEN) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(SeventhQuality.DIMINISHED.getChordSpec())
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
    
