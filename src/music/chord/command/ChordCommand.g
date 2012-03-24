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
  import music.chord.decorator.ChordPlayer;
  import music.chord.decorator.ChordVoicer;
  import music.chord.decorator.Interval;
  import music.chord.decorator.NoteName;
  import music.chord.decorator.Quality;
  import music.chord.command.Command;
  import music.chord.command.AddChord;
}

@lexer::header {
  package music.chord.command;
}

@members {
  List<Command> commandList = new ArrayList<Command>();
  ChordBuilder chordBuilder = new ChordBuilder();
  List<Chord> chordList;
  ChordPlayer player;
  ChordVoicer voicer;

  public void setChordList(List<Chord> chordList) {
    this.chordList = chordList;
  }
  
  public void setChordVoicer(ChordVoicer voicer) {
    this.voicer = voicer;
  }
    
  public void setChordPlayer(ChordPlayer player) {
    this.player = player;
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
PLAY : 'play';
QUIT : 'quit';

program returns [List<Command> result]
  : command+ EOF { result = commandList; }
  ;
  
command
  : add
  | play
  | quit
  ;
  
add
  : ADD currentChord=chord {commandList.add(new AddChord(chordList, $currentChord.chord));} 
  ;

play
  : PLAY {commandList.add(new Play(chordList, voicer, player));}
  ;
  
quit
  : QUIT { commandList.add(new Quit()); }
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