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
  
  import music.chord.arrangement.ChordDefinitionStructure;
  import music.chord.arrangement.ChordPlayer;
  import music.chord.arrangement.VoicedChord;
  import music.chord.arrangement.VoicingManager;
  import music.chord.arrangement.Voicing;
  import music.chord.arrangement.DerivedChordBuilder;
  import music.chord.arrangement.ChordProgression;
  import music.chord.arrangement.ChordVoicer;
  import music.chord.arrangement.VoicedChordBuilder;
  
  import music.chord.base.ChordMember;
  import music.chord.base.Interval;
  import music.chord.base.NoteName;
  import music.chord.base.Quality;
}

@members {
  List<VoicedChord> chords = new ArrayList<VoicedChord>();
  VoicingManager voicingManager = new VoicingManager();
  DerivedChordBuilder chordBuilder = new DerivedChordBuilder();
  VoicedChordBuilder triadBuilder;
  VoicedChordBuilder seventhBuilder; 
  VoicedChordBuilder ninthBuilder;
  ChordDefinitionStructure struct;
  
  public void setChordDefinitionStructure(ChordDefinitionStructure struct) {
    this.struct = struct;
  }
  
  public void setNinthBuilder(VoicedChordBuilder ninthBuilder) {
    this.ninthBuilder = ninthBuilder;
  }
    
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
    | ^(NINTHS (currentList=chordMemberList {voicingManager.addNinthVoicing($currentList.voicing);})+)
    ;
    
chordMemberList returns [Voicing voicing]
    : ^(VOICING { List<ChordMember> chordMemberList = new ArrayList<ChordMember>(); } 
            (member=chordMember {chordMemberList.add($member.value);} )+ 
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
    : ^(SPEC NOTE_NAME tquality=triadQuality) { chord =
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec("Triad", $tquality.value))
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME) {chord =
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec("Triad", Quality.MAJOR_TRIAD))
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME squality=seventhQuality) {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec("Seventh", $squality.value))
                .buildVoicedChord();
         }
    | ^(SPEC NOTE_NAME nquality=ninthQuality) {chord =
            ninthBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec("Ninth", $nquality.value))
                .buildVoicedChord();
         }
    ;

chordAttr returns [Voicing voicing]
    : ^(ATTR currentList=chordMemberList) {voicing = $currentList.voicing;}
    ;
    
chordMember returns [ChordMember value]
    : ROOT {$value = ChordMember.ROOT;}
    | THIRD {$value = ChordMember.THIRD;}
    | FIFTH {$value = ChordMember.FIFTH;}
    | SEVENTH {$value = ChordMember.SEVENTH;}
    | NINTH {$value = ChordMember.NINTH;}
    ;
    
triadQuality returns [Quality value]
    : MAJOR { $value = Quality.MAJOR_TRIAD; }
    | MINOR { $value = Quality.MINOR_TRIAD; }
    | AUGMENTED { $value = Quality.AUGMENTED_TRIAD; }
    | DIMINISHED { $value = Quality.DIMINISHED_TRIAD; }
    | SUSPENDED { $value = Quality.SUSPENDED_TRIAD; }
    ;
    
seventhQuality returns [Quality value]
    : DOMINANT_SEVEN { $value = Quality.DOMINANT_SEVENTH; }
    | MINOR_SEVEN { $value = Quality.MINOR_SEVENTH; }
    | MAJOR_SEVEN { $value = Quality.MAJOR_SEVENTH; }
    | DIMINISHED_SEVEN { $value = Quality.DIMINISHED_SEVENTH; }
    | HALF_DIMINISHED_SEVEN { $value = Quality.HALF_DIMINISHED_SEVENTH; }
    | SUSPENDED_SEVEN { $value = Quality.SUSPENDED_SEVENTH; }
    ;
    
ninthQuality returns [Quality value]
    : DOMINANT_NINE { $value = Quality.DOMINANT_NINTH; }
    | MINOR_NINE { $value = Quality.MINOR_NINTH; }
    | MAJOR_NINE { $value = Quality.MAJOR_NINTH; }
    ;