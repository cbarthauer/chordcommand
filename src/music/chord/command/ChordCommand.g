grammar ChordCommand;

options {
  language = Java;
  superClass = AbstractParser;
}

@header {
  package music.chord.command;

  import java.util.List;
  import music.chord.decorator.ConcreteChordBuilder;
  import music.chord.decorator.ChordMember;
  import music.chord.decorator.ChordPlayer;
  import music.chord.decorator.ChordVoicer;
  import music.chord.decorator.DerivedChordBuilder;
  import music.chord.decorator.Duration;
  import music.chord.decorator.Interval;
  import music.chord.decorator.NoteName;
  import music.chord.decorator.Quality;
  import music.chord.decorator.SeventhBuilder;
  import music.chord.decorator.SeventhVoicing;
  import music.chord.decorator.TriadBuilder;
  import music.chord.decorator.TriadVoicing;
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
  TriadBuilder triadBuilder = new TriadBuilder();
  SeventhBuilder seventhBuilder = new SeventhBuilder(triadBuilder);
  DerivedChordBuilder derivedBuilder = new DerivedChordBuilder();
  List<VoicedChord> chordList;
  ChordPlayer player;
  ChordVoicer voicer;
  
  public void setChordList(List<VoicedChord> chordList) {
    this.chordList = chordList;
  }
  
  public void setChordVoicer(ChordVoicer voicer) {
    this.voicer = voicer;
  }
    
  public void setChordPlayer(ChordPlayer player) {
    this.player = player;
  }  
}

//Miscellaneous Tokens.
INT : '0'..'9'+;

//Music Tokens
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
DOMINANT_SEVEN : 'dom7';

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
REMOVE : 'remove';
INSERT : 'insert';
ALL : 'all';
DISPLAY : 'display';
DURATION : 'duration';
BEFORE : 'before';

NOTE_LENGTH 
  : 'sixteenth'
  | 'eighth'
  | 'quarter'
  | 'half'
  | 'whole'
  ;

VOICE : 'voice';
VOICING : 'voicing';
VOICINGS : 'voicings';

ON : 'on';
PLAY : 'play';
QUIT : 'quit';
SET : 'set';

//Lists
START_LIST : '[';
END_LIST : ']';


program returns [List<Command> result]
  : command+ EOF { result = commandList; }
  ;
  
command
  : add
  | remove
  | insert
  | display
  | play
  | set
  | quit
  | voice
  ;
  
add
  : ADD currentChord=chord {
	    commandList.add(new AddChord(chordList, $currentChord.chord));
	} 
  ;

remove
  : REMOVE index=INT {
        commandList.add(new RemoveChord(chordList, Integer.parseInt($index.text)));
    }
  ;
  
insert
  : INSERT currentChord=chord BEFORE INT {
        commandList.add(
            new InsertBefore(chordList, $currentChord.chord, Integer.parseInt($INT.text))
        );
    }
  ;
  
display
  : DISPLAY {commandList.add(new Display(chordList));}
  | DISPLAY VOICINGS startIndex=INT 'to' endIndex=INT {
    commandList.add(
        new VoicingComparisonList(
            chordList, 
            Integer.parseInt($startIndex.text), 
            Integer.parseInt($endIndex.text), 
            voicer
        ));}
  ; 
  
play
  : PLAY {commandList.add(new Play(chordList, player));}
  ;

set
  : SET VOICING list=chordMemberList ON INT {
      int index = Integer.parseInt($INT.text);
      VoicedChord chord = derivedBuilder.setChord(chordList.get(index))
        .setVoicing($list.voicing)
        .buildVoicedChord();
      chordList.set(index, chord); 
  }
  | SET DURATION NOTE_LENGTH ON INT {
	  int index = Integer.parseInt($INT.text);
	  VoicedChord chord = derivedBuilder.setChord(chordList.get(index))
        .setDuration(Duration.durationFromName($NOTE_LENGTH.text))
        .buildVoicedChord();
	  chordList.set(index, chord);
  }
  ;

voice
  : VOICE ALL {commandList.add(new VoiceChordList(chordList, voicer));}
  ;
  
chordMemberList returns [Voicing voicing]
    : START_LIST { List<ChordMember> chordMemberList = new ArrayList<ChordMember>(); }
      member1=chordMember 
        {chordMemberList.add(ChordMember.memberFromName($member1.name));}
      ',' member2=chordMember 
        {chordMemberList.add(ChordMember.memberFromName($member2.name));}
      ',' member3=chordMember 
        {chordMemberList.add(ChordMember.memberFromName($member3.name));}
      ',' member4=chordMember 
        {chordMemberList.add(ChordMember.memberFromName($member4.name));}
      END_LIST {
          if(chordMemberList.contains(ChordMember.SEVENTH)) {
              voicing = new SeventhVoicing();
          }
          else {
              voicing = new TriadVoicing();
          }  
        
          for(ChordMember member : chordMemberList) {
              voicing.addChordMember(member);
          }
      }
    ;
      
quit
  : QUIT { commandList.add(new Quit()); }
  ;
  
chord returns [VoicedChord chord]
    : currentSpec=chordSpec {chord = $currentSpec.chord;}
    ;
    
chordSpec returns [VoicedChord chord]
    : NOTE_NAME QUALITY{ chord =
            triadBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.qualityFromAbbreviation($QUALITY.text))
                .buildVoicedChord();
         }
    | NOTE_NAME {chord =
            triadBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .buildVoicedChord();
         }
    | NOTE_NAME DOMINANT_SEVEN {chord =
            seventhBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .setSeventhQuality(Quality.MINOR)
                .buildVoicedChord();
         }
    | NOTE_NAME MINOR_SEVEN {chord =
            seventhBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MINOR)
                .setSeventhQuality(Quality.MINOR)
                .buildVoicedChord();
         }
    | NOTE_NAME MAJOR_SEVEN {chord =
            seventhBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.MAJOR)
                .setSeventhQuality(Quality.MAJOR)
                .buildVoicedChord();
         }
    | NOTE_NAME MINOR_SIX {chord =
            seventhBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text).up(Interval.MAJOR_SIXTH))
                .setTriadQuality(Quality.DIMINISHED)
                .setSeventhQuality(Quality.MINOR)
                .buildVoicedChord();
         }
    | NOTE_NAME DIMINISHED_SEVEN {chord =
            seventhBuilder.setRoot(NoteName.rootFromSymbol($NOTE_NAME.text))
                .setTriadQuality(Quality.DIMINISHED)
                .setSeventhQuality(Quality.DIMINISHED)
                .buildVoicedChord();
         }    
    ;
    
chordMember returns [String name]
    : ROOT {name = "ROOT";}
    | THIRD {name = "THIRD";}
    | FIFTH {name = "FIFTH";}
    | SEVENTH {name = "SEVENTH";}
    ;