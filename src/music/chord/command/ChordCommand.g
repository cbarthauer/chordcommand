grammar ChordCommand;

options {
  language = Java;
  superClass = AbstractParser;
}

@header {
  package music.chord.command;

  import java.util.List;
  import music.chord.decorator.Chord;
  import music.chord.decorator.ChordBuilder;
  import music.chord.decorator.Interval;
  import music.chord.decorator.NoteName;
  import music.chord.decorator.Quality;
}

@lexer::header {
  package music.chord.command;
}

@members {
  ChordBuilder chordBuilder = new ChordBuilder();
  List<Chord> chordList;

  public void setChordList(List<Chord> chordList) {
    this.chordList = chordList;
  }
}

NOTE_NAME
    :   'A'..'G' ACCIDENTAL?
    ;
    
ACCIDENTAL
    :   'b' | '#' | 'n'
    ;

QUALITY : '+' | 'M' | 'm' | 'dim';

MINOR_SIX : 'm6';
DIMINISHED_SEVEN : 'dim7';
MINOR_SEVEN : 'm7';
MAJOR_SEVEN : 'M7';
SEVEN : '7';

//Whitespace
WS  
    : ( ' '
    | '\t'
    | '\r'
    | '\n'
    )+ {$channel=HIDDEN;}
    ;

//Chord member tokens.
ROOT : 'root';
THIRD : 'third';
FIFTH : 'fifth';
SEVENTH : 'seventh';

//Chord types.
TRIADS : 'triads';
SEVENTHS : 'sevenths';

//Commands.
ADD : 'add';

program returns [List<Chord> result]
  : command+ EOF { result = chordList; }
  ;
  
command
  : add
  ;
  
add
  : ADD currentChord=chord {chordList.add($currentChord.chord);} 
  ;
  
chord returns [Chord chord]
    : currentSpec=chordSpec {chord = $currentSpec.chord;}
    ;
    
chordSpec returns [Chord chord]
    : NOTE_NAME QUALITY{ chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.qualityFromAbbreviation($QUALITY.text))
                .build();
         }
    | NOTE_NAME {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .build();
         }
    | NOTE_NAME SEVEN {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .setSeventhQuality(Quality.MINOR)
                .build();
         }
    | NOTE_NAME MINOR_SEVEN {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MINOR)
                .setSeventhQuality(Quality.MINOR)
                .build();
         }
    | NOTE_NAME MAJOR_SEVEN {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .setSeventhQuality(Quality.MAJOR)
                .build();
         }
    | NOTE_NAME MINOR_SIX {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text).up(Interval.MAJOR_SIXTH))
                .setTriadQuality(Quality.DIMINISHED)
                .setSeventhQuality(Quality.MINOR)
                .build();
         }
    | NOTE_NAME DIMINISHED_SEVEN {chord =
            chordBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.DIMINISHED)
                .setSeventhQuality(Quality.DIMINISHED)
                .build();
         }    
    ;
    
chordMember : ROOT
    | THIRD
    | FIFTH
    | SEVENTH
    ;