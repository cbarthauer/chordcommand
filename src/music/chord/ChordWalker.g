tree grammar ChordWalker;

options {
  language = Java;
  tokenVocab = Chord;
  ASTLabelType = CommonTree;
}

@header {
  package music.chord;
  
  import java.util.ArrayList;
  import java.util.List;
}

@members {
  List<Chord> chords = new ArrayList<Chord>();
  VoicingManager voicingManager = new VoicingManager();
  VoicingType currentVoicingType = null;
  Voicing currentVoicing = null;
}


compilationUnit returns [ChordGrammarFile result]
    : ^(UNIT voicingDef+ progressionDef*) {
        result = ChordGrammarFile.getInstance()
            .setChordList(chords)
            .setVoicingManager(voicingManager);
    }
    ;
    
voicingDef 
    : ^('voicings' voicingTypeList+)
    ;
    
voicingTypeList 
    : ^(TRIADS { currentVoicingType = VoicingType.TRIAD; } chordMemberList+)
    ;
    
chordMemberList 
    : ^(VOICING { currentVoicing = Voicing.getInstance(); } 
            (member=chordMember {currentVoicing.addChordMember(ChordMember.memberFromName($member.name));} )+ 
       ) {         
            switch(currentVoicingType) {
                case TRIAD:
                    voicingManager.addTriadVoicing(currentVoicing);
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
            new Chord(
                NoteName.rootFromSymbol($NOTE_NAME.text), 
                Quality.qualityFromAbbreviation($QUALITY.text)
            )
         );}
    ;
    
chordMember returns [String name] 
    : ROOT {name = "ROOT";}
    | THIRD {name = "THIRD";}
    | FIFTH {name = "FIFTH";}
    ;
    
