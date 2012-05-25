grammar ChordCommand;

options {
  language = Java;
  superClass = AbstractParser;
}

@header {
  package music.chord.grammar;

  import java.util.List;
  
  import music.chord.arrangement.ChordPlayer;
  import music.chord.arrangement.SeventhVoicing;
  import music.chord.arrangement.TriadVoicing;
  import music.chord.arrangement.VoicedChord;
  import music.chord.arrangement.Voicing;
  import music.chord.arrangement.DerivedChordBuilder;
  import music.chord.arrangement.SeventhBuilder;
  import music.chord.arrangement.TriadBuilder;
  import music.chord.arrangement.ChordVoicer;
  import music.chord.arrangement.VoicePartPlayer;
  
  import music.chord.base.ChordMember;
  import music.chord.base.Duration;
  import music.chord.base.Interval;
  import music.chord.base.NoteName;
  import music.chord.base.QualitySymbol;
  import music.chord.base.SeventhQuality;
  import music.chord.base.TriadQuality;
  import music.chord.base.VoicePart;
  
  import music.chord.command.AddChord;
  import music.chord.command.Command;
  import music.chord.command.Display;
  import music.chord.command.InsertBefore;
  import music.chord.command.Play;
  import music.chord.command.PlayVoicePart;
  import music.chord.command.Quit;
  import music.chord.command.RemoveChord;
  import music.chord.command.VoiceChordList;
  import music.chord.command.VoicingComparisonList;
  
  import music.chord.display.VerboseFormatter;
}

@lexer::header {
  package music.chord.grammar;
}

@members {
  List<Command> commandList = new ArrayList<Command>();
  TriadBuilder triadBuilder;  
  SeventhBuilder seventhBuilder;
  DerivedChordBuilder derivedBuilder = new DerivedChordBuilder();
  List<VoicedChord> chordList;
  ChordPlayer player;
  ChordVoicer voicer;
  VoicePartPlayer voicePartPlayer;
  
  public void setChordList(List<VoicedChord> chordList) {
    this.chordList = chordList;
  }
  
  public void setChordVoicer(ChordVoicer voicer) {
    this.voicer = voicer;
  }
    
  public void setChordPlayer(ChordPlayer player) {
    this.player = player;
  }  
  
  public void setSeventhBuilder(SeventhBuilder seventhBuilder) {
    this.seventhBuilder = seventhBuilder;
  }
  
  public void setTriadBuilder(TriadBuilder triadBuilder) {
    this.triadBuilder = triadBuilder;
  }
  
  public void setVoicePartPlayer(VoicePartPlayer voicePartPlayer) {
    this.voicePartPlayer = voicePartPlayer;
  }
}

//Miscellaneous Tokens.
INT : '0'..'9'+;

//Whitespace
WS  
    : ( ' '
    | '\t'
    | '\r'
    | '\n'
    )+ {$channel=HIDDEN;}
    ;

//Music Tokens
NOTE_NAME
    :   'A'..'G' ACCIDENTAL?
    ;
    
ACCIDENTAL
    :   'b' | '#' | 'n'
    ;

QUALITY : '+' | 'M' | 'm' | 'dim';

//Chord qualities.
MINOR_SIX : 'm6';
DIMINISHED_SEVEN : 'dim7';
MINOR_SEVEN : 'm7';
MAJOR_SEVEN : 'M7';
DOMINANT_SEVEN : 'dom7';

//Chord member tokens.
ROOT : 'root';
THIRD : 'third';
FIFTH : 'fifth';
SEVENTH : 'seventh';

//Chord types.
TRIADS : 'triads';
SEVENTHS : 'sevenths';

//Voice parts.
BASS : 'bass';
BARITONE : 'baritone';
LEAD : 'lead';
TENOR : 'tenor';

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
  : DISPLAY {commandList.add(new Display(chordList, new VerboseFormatter()));}
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
  | PLAY voicePart {
      commandList.add(
          new PlayVoicePart(
              chordList, 
              VoicePart.voicePartFromName($voicePart.name), 
              voicePartPlayer));
  }
  ;

set
  : SET VOICING list=chordMemberList ON index=INT {
      VoicedChord chord = derivedBuilder.setChord(chordList.get(Integer.parseInt($index.text)))
        .setVoicing($list.voicing)
        .buildVoicedChord();
      chordList.set(Integer.parseInt($index.text), chord); 
  }
  | SET DURATION NOTE_LENGTH ON index=INT {
	  VoicedChord chord = derivedBuilder.setChord(chordList.get(Integer.parseInt($index.text)))
        .setDuration(Duration.durationFromName($NOTE_LENGTH.text))
        .buildVoicedChord();
	  chordList.set(Integer.parseInt($index.text), chord);
  }
  | SET 'octave' octave=INT ON index=INT {
      VoicedChord chord = derivedBuilder.setChord(chordList.get(Integer.parseInt($index.text)))
        .setOctave(Integer.parseInt($octave.text))
        .buildVoicedChord();
      chordList.set(Integer.parseInt($index.text), chord);
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
          voicing = voicingFromChordMemberList(chordMemberList);
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
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setTriadQuality(TriadQuality.qualityFromSymbol($QUALITY.text))
                .setSymbol($NOTE_NAME.text + $QUALITY.text)
                .buildVoicedChord();
         }
    | NOTE_NAME {chord =
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setTriadQuality(TriadQuality.MAJOR)
                .setSymbol($NOTE_NAME.text)
                .buildVoicedChord();
         }
    | NOTE_NAME DOMINANT_SEVEN {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setSeventhQuality(SeventhQuality.DOMINANT)
                .setSymbol($NOTE_NAME.text + $DOMINANT_SEVEN.text)
                .buildVoicedChord();
         }
    | NOTE_NAME MINOR_SEVEN {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setSeventhQuality(SeventhQuality.MINOR)
                .setSymbol($NOTE_NAME.text + $MINOR_SEVEN.text)
                .buildVoicedChord();
         }
    | NOTE_NAME MAJOR_SEVEN {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setSeventhQuality(SeventhQuality.MAJOR)
                .setSymbol($NOTE_NAME.text + $MAJOR_SEVEN.text)
                .buildVoicedChord();
         }
    | NOTE_NAME MINOR_SIX {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text).up(Interval.MAJOR_SIXTH))
                .setSeventhQuality(SeventhQuality.HALF_DIMINISHED)
                .setSymbol($NOTE_NAME.text + $MINOR_SIX.text)
                .buildVoicedChord();
         }
    | NOTE_NAME DIMINISHED_SEVEN {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setSeventhQuality(SeventhQuality.DIMINISHED)
                .setSymbol($NOTE_NAME.text + $DIMINISHED_SEVEN.text)
                .buildVoicedChord();
         }    
    ;
    
chordMember returns [String name]
    : ROOT {name = "ROOT";}
    | THIRD {name = "THIRD";}
    | FIFTH {name = "FIFTH";}
    | SEVENTH {name = "SEVENTH";}
    ;
    
voicePart returns [String name]
    : BASS {name = "BASS";}
    | BARITONE {name = "BARITONE";}
    | LEAD {name = "LEAD";}
    | TENOR {name = "TENOR";}
    ;