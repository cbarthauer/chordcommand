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
  import music.chord.decorator.ChordMember;
  import music.chord.decorator.ChordPlayer;
  import music.chord.decorator.ChordVoicer;
  import music.chord.decorator.Duration;
  import music.chord.decorator.Interval;
  import music.chord.decorator.NoteName;
  import music.chord.decorator.Quality;
  import music.chord.decorator.VoicedChord;
  import music.chord.decorator.Voicing;
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
  Duration defaultDuration = Duration.HALF;

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

//Keywords.
ADD : 'add';
DISPLAY : 'display';
DURATION : 'duration';

NOTE_LENGTH 
  : 'sixteenth'
  | 'eighth'
  | 'quarter'
  | 'half'
  | 'whole'
  ;

VOICING : 'voicing';

ON : 'on';
PLAY : 'play';
QUIT : 'quit';
SET : 'set';

//Lists
START_LIST : '[';
END_LIST : ']';

//Other.
INT : '0'..'9'('0'..'9')*;

program returns [List<Command> result]
  : command+ EOF { result = commandList; }
  ;
  
command
  : add
  | display
  | play
  | set
  | quit
  ;
  
add
  : ADD currentChord=chord {
	    commandList.add(new AddChord(chordList, $currentChord.chord));
	} 
  ;

display
  : DISPLAY {commandList.add(new Display(chordList));}
  ; 
  
play
  : PLAY {commandList.add(new Play(chordList, voicer, player));}
  ;

set
  : SET VOICING list=chordMemberList ON INT {
      int index = Integer.parseInt($INT.text);
      Chord chord = chordList.get(index);
      chord = new VoicedChord(chord, $list.voicing);
      chordList.set(index, chord); 
  }
  | SET DURATION NOTE_LENGTH ON INT {
	  int index = Integer.parseInt($INT.text);
	  Chord chord = chordList.get(index);
	  chord = new VoicedChord((VoicedChord) chord, Duration.durationFromName($NOTE_LENGTH.text));
	  chordList.set(index, chord);
  }
  ;

chordMemberList returns [Voicing voicing]
    : START_LIST {voicing = Voicing.getInstance();}
      member1=chordMember {voicing.addChordMember(ChordMember.memberFromName($member1.name));}
      ',' member2=chordMember {voicing.addChordMember(ChordMember.memberFromName($member2.name));}
      ',' member3=chordMember {voicing.addChordMember(ChordMember.memberFromName($member3.name));}
      ',' member4=chordMember {voicing.addChordMember(ChordMember.memberFromName($member4.name));}
      END_LIST
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
    
chordMember returns [String name]
    : ROOT {name = "ROOT";}
    | THIRD {name = "THIRD";}
    | FIFTH {name = "FIFTH";}
    | SEVENTH {name = "SEVENTH";}
    ;