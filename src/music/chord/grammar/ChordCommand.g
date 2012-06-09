grammar ChordCommand;

options {
  language = Java;
  superClass = AbstractParser;
}

@header {
  package music.chord.grammar;

  import java.util.ArrayList;
  import java.util.List;
  import java.util.Map;
  
  import music.chord.arrangement.ChordDefinitionStructure;
  import music.chord.arrangement.ChordFinder;
  import music.chord.arrangement.ChordPlayer;
  import music.chord.arrangement.VoicedChord;
  import music.chord.arrangement.Voicing;
  import music.chord.arrangement.DerivedChordBuilder;
  import music.chord.arrangement.ChordVoicer;
  import music.chord.arrangement.VoicePartPlayer;
  import music.chord.arrangement.VoicedChordBuilder;
  
  import music.chord.base.ChordMember;
  import music.chord.base.Duration;
  import music.chord.base.Interval;
  import music.chord.base.NoteName;
  import music.chord.base.Quality;
  import music.chord.base.VoicePart;
  
  import music.chord.command.AddChord;
  import music.chord.command.Command;
  import music.chord.command.Display;
  import music.chord.command.FindChordsByChordMember;
  import music.chord.command.FindChordsContainingNoteName;
  import music.chord.command.InsertBefore;
  import music.chord.command.Load;
  import music.chord.command.Play;
  import music.chord.command.PlayVoicePart;
  import music.chord.command.Quit;
  import music.chord.command.RemoveChord;
  import music.chord.command.Save;
  import music.chord.command.VoiceChordList;
  import music.chord.command.VoicingComparisonList;
  
  import music.chord.display.VerboseFormatter;
}

@lexer::header {
  package music.chord.grammar;
}

@members {
  final String DEFAULT = "default";
  List<Command> commandList = new ArrayList<Command>();
  VoicedChordBuilder triadBuilder;  
  VoicedChordBuilder seventhBuilder;
  VoicedChordBuilder ninthBuilder;
  DerivedChordBuilder derivedBuilder = new DerivedChordBuilder();
  Map<String, List<VoicedChord>> chordListMap;
  ChordPlayer player;
  ChordVoicer voicer;
  VoicePartPlayer voicePartPlayer;
  ChordDefinitionStructure struct;
  ChordFinder chordFinder;
  
  public void setChordDefinitionStructure(ChordDefinitionStructure struct) {
    this.struct = struct;
    this.chordFinder = new ChordFinder(struct);
  }
  
  public void setChordListMap(Map<String, List<VoicedChord>> chordListMap) {
    this.chordListMap = chordListMap;
  }
  
  public void setChordVoicer(ChordVoicer voicer) {
    this.voicer = voicer;
  }
    
  public void setChordPlayer(ChordPlayer player) {
    this.player = player;
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
  
  public void setVoicePartPlayer(VoicePartPlayer voicePartPlayer) {
    this.voicePartPlayer = voicePartPlayer;
  }
}


//Music Tokens
NOTE_NAME
    :   'A'..'G' ACCIDENTAL?
    ;
    
ACCIDENTAL
    :   'b' | '#' | 'bb' | '##'
    ;

//Chord qualities.
MAJOR : 'M';
MINOR : 'm';
AUGMENTED : '+';
DIMINISHED : 'dim';
SUSPENDED : 'sus';
MINOR_SIX : 'm6';
DIMINISHED_SEVEN : 'dim7';
MINOR_SEVEN : 'm7';
MAJOR_SEVEN : 'M7';
DOMINANT_SEVEN : 'dom7';
HALF_DIMINISHED_SEVEN : 'hdim7';
SUSPENDED_SEVEN : 'sus7';
DOMINANT_NINE : 'dom9';
MINOR_NINE : 'm9';
MAJOR_NINE : 'M9';

//Chord member tokens.
ROOT : 'root';
THIRD : 'third';
FIFTH : 'fifth';
SEVENTH : 'seventh';
NINTH : 'ninth';

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
FIND : 'find';
CHORDS : 'chords';
WHERE : 'where';
IS : 'is';
CONTAINING : 'containing';
SAVE : 'save';
AS : 'as';
LOAD : 'load';
TO : 'to';

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

//Miscellaneous Tokens.
INT : '0'..'9'+;

STRING : '"' ('a'..'z' | 'A'..'Z' | '0'..'9' | '\\' | '.' | ':')+ '"';

IDENTIFIER : 'a'..'z' ('a'..'z' | 'A'..'Z' | '0'..'9')*;

//Whitespace
WS  
    : ( ' '
    | '\t'
    | '\r'
    | '\n'
    )+ {$channel=HIDDEN;}
    ;
    
//Lists
START_LIST : '[';
END_LIST : ']';

program returns [List<Command> result]
  : command+ EOF { result = commandList; }
  ;
  
command
  : add
  | display
  | find
  | insert
  | load
  | play
  | quit
  | remove
  | save
  | set
  | voice
  ;
  
add
  : ADD currentChord=chord {
	    commandList.add(new AddChord(chordListMap.get(DEFAULT), $currentChord.chord));
	} 
  | ADD newList=chordList TO IDENTIFIER {
      List<VoicedChord> currentList = chordListMap.get($IDENTIFIER.text);
      
      if(currentList == null) {
          currentList = new ArrayList<VoicedChord>();
          chordListMap.put($IDENTIFIER.text, currentList);
      }
      
      for(VoicedChord chord : $newList.value) {
          commandList.add(new AddChord(chordListMap.get($IDENTIFIER.text), chord));
      }
  }	
  ;
  
display
  : DISPLAY {commandList.add(new Display(chordListMap.get(DEFAULT), new VerboseFormatter()));}
  | DISPLAY IDENTIFIER {
    commandList.add(new Display(chordListMap.get($IDENTIFIER.text), new VerboseFormatter()));
  }
  | DISPLAY VOICINGS startIndex=INT TO endIndex=INT {
    commandList.add(
        new VoicingComparisonList(
            chordListMap.get(DEFAULT), 
            Integer.parseInt($startIndex.text), 
            Integer.parseInt($endIndex.text), 
            voicer
        ));}
  ;

find
  : FIND CHORDS WHERE member=chordMember IS NOTE_NAME {
      commandList.add(
          new FindChordsByChordMember(
              NoteName.forSymbol($NOTE_NAME.text), 
              $member.value,
              chordFinder
          ));
  }
  | FIND CHORDS CONTAINING NOTE_NAME {
      commandList.add(
          new FindChordsContainingNoteName(
              NoteName.forSymbol($NOTE_NAME.text), 
              chordFinder
          ));
  }
  ;

insert
  : INSERT currentChord=chord BEFORE INT {
        commandList.add(
            new InsertBefore(
                chordListMap.get(DEFAULT), 
                $currentChord.chord, 
                Integer.parseInt($INT.text))
        );
    }
  ;

load
  : LOAD fileName=STRING {
      commandList.add(
        new Load(
          chordListMap.get(DEFAULT), 
          struct, 
          $fileName.text.replaceAll("\"", "")));
  }
  ;

play
  : PLAY {commandList.add(new Play(chordListMap.get(DEFAULT), player));}
  | PLAY IDENTIFIER {commandList.add(new Play(chordListMap.get($IDENTIFIER.text), player));}
  | PLAY voicePart {
      commandList.add(
          new PlayVoicePart(
              chordListMap.get(DEFAULT), 
              $voicePart.value, 
              voicePartPlayer));
  }
  ;

quit
  : QUIT { commandList.add(new Quit()); }
  ;
            
remove
  : REMOVE index=INT {
        commandList.add(new RemoveChord(chordListMap.get(DEFAULT), Integer.parseInt($index.text)));
    }
  ;
  
save
  : SAVE AS fileName=STRING {
      commandList.add(new Save(chordListMap.get(DEFAULT), $fileName.text.replaceAll("\"", "")));
  }
  ;

set
  : SET VOICING list=chordMemberList ON index=INT {
      VoicedChord chord = 
        derivedBuilder.setChord(chordListMap.get(DEFAULT).get(Integer.parseInt($index.text)))
        .setVoicing($list.voicing)
        .buildVoicedChord();
      chordListMap.get(DEFAULT).set(Integer.parseInt($index.text), chord); 
  }
  | SET DURATION NOTE_LENGTH ON index=INT {
	  VoicedChord chord = 
	    derivedBuilder.setChord(chordListMap.get(DEFAULT).get(Integer.parseInt($index.text)))
        .setDuration(Duration.durationFromName($NOTE_LENGTH.text))
        .buildVoicedChord();
	  chordListMap.get(DEFAULT).set(Integer.parseInt($index.text), chord);
  }
  | SET 'octave' octave=INT ON index=INT {
      VoicedChord chord = 
        derivedBuilder.setChord(chordListMap.get(DEFAULT).get(Integer.parseInt($index.text)))
        .setOctave(Integer.parseInt($octave.text))
        .buildVoicedChord();
      chordListMap.get(DEFAULT).set(Integer.parseInt($index.text), chord);
  }
  ;

voice
  : VOICE ALL {commandList.add(new VoiceChordList(chordListMap.get(DEFAULT), voicer));}
  | VOICE ALL IDENTIFIER {
      commandList.add(new VoiceChordList(chordListMap.get($IDENTIFIER.text), voicer));
  }
  ;
    
chordMemberList returns [Voicing voicing]
    : START_LIST { List<ChordMember> chordMemberList = new ArrayList<ChordMember>(); }
      member1=chordMember 
        {chordMemberList.add($member1.value);}
      ',' member2=chordMember 
        {chordMemberList.add($member2.value);}
      ',' member3=chordMember 
        {chordMemberList.add($member3.value);}
      ',' member4=chordMember 
        {chordMemberList.add($member4.value);}
      END_LIST {
          voicing = voicingFromChordMemberList(chordMemberList);
      }
    ;
  
chord returns [VoicedChord chord]
    : currentSpec=chordSpec {chord = $currentSpec.chord;}
    ;

chordList returns [List<VoicedChord> value]
  : first=chord {
        value = new ArrayList<VoicedChord>();
        value.add($first.chord);
      } 
    (',' subsequent=chord {value.add($subsequent.chord);})*
  ;
      
chordSpec returns [VoicedChord chord]
    : NOTE_NAME tquality=triadQuality { chord =
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec("Triad", $tquality.value))
                .setQuality($tquality.value)
                .buildVoicedChord();
         }
    | NOTE_NAME {chord =
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec("Triad", Quality.MAJOR_TRIAD))
                .setQuality(Quality.MAJOR_TRIAD)
                .buildVoicedChord();
         }
    | NOTE_NAME squality=seventhQuality {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec("Seventh", $squality.value))
                .setQuality($squality.value)
                .buildVoicedChord();
         }
    | NOTE_NAME nquality=ninthQuality {chord =
            ninthBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec("Ninth", $nquality.value))
                .setQuality($nquality.value)
                .buildVoicedChord();
         }
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
    
voicePart returns [VoicePart value]
    : BASS {$value = VoicePart.BASS;}
    | BARITONE {$value = VoicePart.BARITONE;}
    | LEAD {$value = VoicePart.LEAD;}
    | TENOR {$value = VoicePart.TENOR;}
    ;