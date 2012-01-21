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
}

sequence returns [List<Chord> result]
    :   ^('sequence' chordList) {result = chords;}
    ;

chordList
    :   (^(NOTE_NAME QUALITY){chords.add(
            new Chord(
                NoteName.rootFromSymbol($NOTE_NAME.text), 
                Quality.qualityFromAbbreviation($QUALITY.text)
            )
         );})+ 
    ;