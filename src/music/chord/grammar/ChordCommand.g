grammar ChordCommand;

options {
  language = Java;
}

@header {
  package music.chord.grammar;

  import java.util.ArrayList;
  import java.util.List;
  
  import music.chord.arrangement.ChordFinder;
  import music.chord.arrangement.ChordPlayer;
  import music.chord.arrangement.VoicedChord;
  import music.chord.arrangement.Voicing;
  import music.chord.arrangement.ChordVoicer;
  import music.chord.arrangement.VoicingFactory;
  import music.chord.arrangement.VoicePartPlayer;
  
  import music.chord.base.ChordMember;
  import music.chord.base.ChordPair;
  import music.chord.base.Duration;
  import music.chord.base.NoteName;
  import music.chord.base.QualityEnum;
  import music.chord.base.VoicePart;
  
  import music.chord.command.AddChords;
  import music.chord.command.Command;
  import music.chord.command.Display;
  import music.chord.command.FindChordsByChordMember;
  import music.chord.command.FindChordsContainingNoteNames;
  import music.chord.command.InsertBefore;
  import music.chord.command.WriteLilyPondFile;
  import music.chord.command.Load;
  import music.chord.command.Play;
  import music.chord.command.PlayVoicePart;
  import music.chord.command.Quit;
  import music.chord.command.RemoveChord;
  import music.chord.command.Save;
  import music.chord.command.VoiceChordList;
  import music.chord.command.VoicingComparisonList;
  
  import music.chord.display.VerboseFormatter;
  
  import music.chord.engine.ChordEngine;
  import music.chord.engine.protocol.ChordRequest;
  import music.chord.engine.protocol.Identifier;
  import music.chord.engine.protocol.InsertChordRequest;
  import music.chord.engine.protocol.LoadRequest;
  import music.chord.engine.protocol.RemoveChordRequest;
  import music.chord.engine.protocol.RequestBuilder;
  import music.chord.engine.protocol.VoiceAllRequest;
}

@lexer::header {
  package music.chord.grammar;
}

@members {
  ChordEngine engine;
  List<Command> commandList = new ArrayList<Command>();
  ChordPlayer player;
  ChordVoicer voicer;
  VoicePartPlayer voicePartPlayer;
  ChordFinder chordFinder;
  
  public void setChordFinder(ChordFinder chordFinder) {
    this.chordFinder = chordFinder;
  }
  
  public void setChordEngine(ChordEngine engine) {
    this.engine = engine;
  }
  
  public void setChordVoicer(ChordVoicer voicer) {
    this.voicer = voicer;
  }
    
  public void setChordPlayer(ChordPlayer player) {
    this.player = player;
  }
  
  public void setVoicePartPlayer(VoicePartPlayer voicePartPlayer) {
    this.voicePartPlayer = voicePartPlayer;
  }
  
  private Voicing voicingFromChordMemberList(List<ChordMember> chordMemberList) {
      return VoicingFactory.instanceFromChordMemberList(chordMemberList);
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
FOR : 'for';

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
  : ADD chords=chordList TO IDENTIFIER {      
      RequestBuilder reqBuilder = new RequestBuilder(new Identifier($IDENTIFIER.text));
      ChordRequest request = reqBuilder.chordRequest($chords.value);
      commandList.add(new AddChords(engine, request));
  }	
  ;

display
  : DISPLAY IDENTIFIER {
    commandList.add(
        new Display(
            engine.byIdentifier(new Identifier($IDENTIFIER.text)), 
            new VerboseFormatter()));
  }
  | DISPLAY VOICINGS FOR IDENTIFIER START_LIST startIndex=INT TO endIndex=INT END_LIST {
    commandList.add(
        new VoicingComparisonList(
            engine.byIdentifier(new Identifier($IDENTIFIER.text)), 
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
  | FIND CHORDS CONTAINING noteNameList {
      commandList.add(
          new FindChordsContainingNoteNames(
              $noteNameList.value, 
              chordFinder
          ));
  }
  ;

noteNameList returns [List<NoteName> value]
@init{value = new ArrayList<NoteName>();}
  : first=NOTE_NAME 
      {value.add(NoteName.forSymbol($first.text));} 
    (',' subsequent=NOTE_NAME 
      {value.add(NoteName.forSymbol($subsequent.text));})*
  ;
  
insert
  : INSERT chords=chordList BEFORE IDENTIFIER START_LIST INT END_LIST {
      RequestBuilder reqBuilder = new RequestBuilder(new Identifier($IDENTIFIER.text));
      InsertChordRequest request = reqBuilder.insertRequest(
          Integer.parseInt($INT.text),
          $chords.value);  
      commandList.add(new InsertBefore(engine, request));
  }
  ;

load
  : LOAD fileName=STRING AS IDENTIFIER {
      RequestBuilder reqBuilder = new RequestBuilder(new Identifier($IDENTIFIER.text));
      LoadRequest request = reqBuilder.loadRequest($fileName.text.replaceAll("\"", "")); 
      commandList.add(new Load(engine, request));
  }
  ;

play
  : PLAY IDENTIFIER {commandList.add(
      new Play(
          engine.byIdentifier(new Identifier($IDENTIFIER.text)), 
          player));}
  | PLAY IDENTIFIER voicePart {
      commandList.add(
          new PlayVoicePart(
              engine.byIdentifier(new Identifier($IDENTIFIER.text)), 
              $voicePart.value, 
              voicePartPlayer));
  }
  ;

quit
  : QUIT { commandList.add(new Quit()); }
  ;
            
remove
  : REMOVE IDENTIFIER START_LIST range END_LIST {      
      RequestBuilder builder = new RequestBuilder(new Identifier($IDENTIFIER.text));  
      RemoveChordRequest request = builder.removeRequest($range.value);
      commandList.add(new RemoveChord(engine, request));
  }
  ;
  
save
  : SAVE IDENTIFIER AS fileName=STRING {
      String outFile = $fileName.text.replaceAll("\"", "");
      
      if(outFile.endsWith(".ly")) {
          commandList.add(
              new WriteLilyPondFile(
                  engine.byIdentifier(new Identifier($IDENTIFIER.text)),
                  outFile));
      }
      else {
	      commandList.add(
	          new Save(
	              engine.byIdentifier(new Identifier($IDENTIFIER.text)), 
	              outFile));
	  }
  }
  ;

set
  : SET VOICING list=chordMemberList ON IDENTIFIER START_LIST range END_LIST {
      RequestBuilder builder = new RequestBuilder(new Identifier($IDENTIFIER.text));
      builder.setVoicing($list.voicing);
      engine.setVoicings(builder.voicingRequest($range.value));
  }
  | SET DURATION NOTE_LENGTH ON IDENTIFIER START_LIST range END_LIST {
      RequestBuilder builder = new RequestBuilder(new Identifier($IDENTIFIER.text));
      builder.setDuration(Duration.durationFromName($NOTE_LENGTH.text));
      engine.setDurations(builder.durationRequest($range.value));
  }
  | SET 'octave' octave=INT ON IDENTIFIER START_LIST range END_LIST {
      RequestBuilder builder = new RequestBuilder(new Identifier($IDENTIFIER.text));
      builder.setOctave(Integer.parseInt($octave.text));
      engine.setOctaves(builder.octaveRequest($range.value));
  }
  ;

voice
  : VOICE ALL IDENTIFIER {
      RequestBuilder builder = new RequestBuilder(new Identifier($IDENTIFIER.text));
      VoiceAllRequest request = builder.voiceAllRequest();
      commandList.add(new VoiceChordList(engine, request));
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
  
chordList returns [List<VoicedChord> value]
@init {value = new ArrayList<VoicedChord>();}
  : first=chord { value.add($first.value); } 
    (',' subsequent=chord {value.add($subsequent.value);})*
  | IDENTIFIER START_LIST range END_LIST {
      List<VoicedChord> existingList = engine.byIdentifier(
          new Identifier($IDENTIFIER.text));
      for(Integer i : $range.value) {
          value.add(existingList.get(i));
      }
  }
  ;

chord returns [VoicedChord value]
    : currentPair=chordSpec {value = engine.createChord($currentPair.value);}
    ;
    
range returns [List<Integer> value]
@init {$value = new ArrayList<Integer>();}
  : rangeAtom[$value] (',' rangeAtom[$value])*
  ;

rangeAtom[List<Integer> value]
  : INT {value.add(Integer.parseInt($INT.text));}
  | firstInt=INT '-' lastInt=INT {
      int begin = Integer.parseInt($firstInt.text);
      int end = Integer.parseInt($lastInt.text);
      
      for(int i = begin; i <= end; i++) {
        value.add(i);
      }
    }
  ;
        
chordSpec returns [ChordPair value]
    : NOTE_NAME tquality=triadQuality { value = 
        new ChordPair(NoteName.forSymbol($NOTE_NAME.text), $tquality.value);
    }
    | NOTE_NAME { value = 
        new ChordPair(NoteName.forSymbol($NOTE_NAME.text), QualityEnum.MAJOR_TRIAD);
    }
    | NOTE_NAME squality=seventhQuality { value = 
        new ChordPair(NoteName.forSymbol($NOTE_NAME.text), $squality.value);
    }
    | NOTE_NAME nquality=ninthQuality { value = 
        new ChordPair(NoteName.forSymbol($NOTE_NAME.text), $nquality.value);
    }
    ;

chordMember returns [ChordMember value]
    : ROOT {$value = ChordMember.ROOT;}
    | THIRD {$value = ChordMember.THIRD;}
    | FIFTH {$value = ChordMember.FIFTH;}
    | SEVENTH {$value = ChordMember.SEVENTH;}
    | NINTH {$value = ChordMember.NINTH;}
    ;
    
triadQuality returns [QualityEnum value]
    : MAJOR { $value = QualityEnum.MAJOR_TRIAD; }
    | MINOR { $value = QualityEnum.MINOR_TRIAD; }
    | AUGMENTED { $value = QualityEnum.AUGMENTED_TRIAD; }
    | DIMINISHED { $value = QualityEnum.DIMINISHED_TRIAD; }
    | SUSPENDED { $value = QualityEnum.SUSPENDED_TRIAD; }
    ;
    
seventhQuality returns [QualityEnum value]
    : DOMINANT_SEVEN { $value = QualityEnum.DOMINANT_SEVENTH; }
    | MINOR_SEVEN { $value = QualityEnum.MINOR_SEVENTH; }
    | MAJOR_SEVEN { $value = QualityEnum.MAJOR_SEVENTH; }
    | DIMINISHED_SEVEN { $value = QualityEnum.DIMINISHED_SEVENTH; }
    | HALF_DIMINISHED_SEVEN { $value = QualityEnum.HALF_DIMINISHED_SEVENTH; }
    | SUSPENDED_SEVEN { $value = QualityEnum.SUSPENDED_SEVENTH; }
    ;
    
ninthQuality returns [QualityEnum value]
    : DOMINANT_NINE { $value = QualityEnum.DOMINANT_NINTH; }
    | MINOR_NINE { $value = QualityEnum.MINOR_NINTH; }
    | MAJOR_NINE { $value = QualityEnum.MAJOR_NINTH; }
    ;
    
voicePart returns [VoicePart value]
    : BASS {$value = VoicePart.BASS;}
    | BARITONE {$value = VoicePart.BARITONE;}
    | LEAD {$value = VoicePart.LEAD;}
    | TENOR {$value = VoicePart.TENOR;}
    ;