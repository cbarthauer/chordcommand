grammar ChordCommand;

options {
  language = Java;
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
  import music.chord.base.ChordType;
  import music.chord.base.Duration;
  import music.chord.base.Interval;
  import music.chord.base.NoteName;
  import music.chord.base.Quality;
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
  import music.chord.engine.protocol.AddChordRequest;
  import music.chord.engine.protocol.Identifier;
  import music.chord.engine.protocol.RequestBuilder;
}

@lexer::header {
  package music.chord.grammar;
}

@members {
  ChordEngine engine;
  List<Command> commandList = new ArrayList<Command>();
  VoicedChordBuilder triadBuilder;  
  VoicedChordBuilder seventhBuilder;
  VoicedChordBuilder ninthBuilder;
  DerivedChordBuilder derivedBuilder = new DerivedChordBuilder();
  ChordListRegistry reg;
  ChordPlayer player;
  ChordVoicer voicer;
  VoicePartPlayer voicePartPlayer;
  ChordDefinitionStructure struct;
  ChordFinder chordFinder;
  
  public void setChordDefinitionStructure(ChordDefinitionStructure struct) {
    this.struct = struct;
    this.chordFinder = new ChordFinder(struct);
  }
  
  public void setChordEngine(ChordEngine engine) {
    this.engine = engine;
    this.reg = engine.getRegistry();
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
  : ADD newList=chordList TO IDENTIFIER {      
      RequestBuilder reqBuilder = new RequestBuilder(new Identifier($IDENTIFIER.text));
      AddChordRequest[] requests = reqBuilder.addRequests($newList.value);
      commandList.add(new AddChords(engine, requests));
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
  : INSERT newList=chordList BEFORE IDENTIFIER START_LIST INT END_LIST {
        RequestBuilder reqBuilder = new RequestBuilder(
            new Identifier($IDENTIFIER.text), 
            Integer.parseInt($INT.text));
            
        commandList.add(
            new InsertBefore(
                engine,
                reqBuilder.insertRequests($newList.value)));
    }
  ;

load
  : LOAD fileName=STRING AS IDENTIFIER {
      RequestBuilder reqBuilder = new RequestBuilder(new Identifier($IDENTIFIER.text));
      commandList.add(
          new Load(
              engine, 
              reqBuilder.loadRequest($fileName.text.replaceAll("\"", ""))));
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
      commandList.add(new RemoveChord(engine, builder.removeRequest($range.value)));
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
      for(Integer i : $range.value) {
	      VoicedChord chord = 
	          derivedBuilder.setChord(reg.getChord(new Identifier($IDENTIFIER.text), i))
	              .setVoicing($list.voicing)
	              .buildVoicedChord();
	      reg.set(new Identifier($IDENTIFIER.text), i, chord);
      } 
  }
  | SET DURATION NOTE_LENGTH ON IDENTIFIER START_LIST range END_LIST {
      for(Integer i : $range.value) {
		  VoicedChord chord = 
		      derivedBuilder.setChord(reg.getChord(new Identifier($IDENTIFIER.text), i))
	              .setDuration(Duration.durationFromName($NOTE_LENGTH.text))
	              .buildVoicedChord();
		  reg.set(new Identifier($IDENTIFIER.text), i, chord);
      }
  }
  | SET 'octave' octave=INT ON IDENTIFIER START_LIST range END_LIST {
      for(Integer i : $range.value) {
          VoicedChord chord = 
              derivedBuilder.setChord(reg.getChord(new Identifier($IDENTIFIER.text), i))
                  .setOctave(Integer.parseInt($octave.text))
                  .buildVoicedChord();
          reg.set(new Identifier($IDENTIFIER.text), i, chord);
      }
  }
  ;

voice
  : VOICE ALL IDENTIFIER {
      commandList.add(new VoiceChordList(new Identifier($IDENTIFIER.text), voicer, reg));
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
@init {value = new ArrayList<VoicedChord>();}
  : first=chord {
        value.add($first.chord);
      } 
    (',' subsequent=chord {value.add($subsequent.chord);})*
  | IDENTIFIER START_LIST range END_LIST {
        List<VoicedChord> existingList = engine.byIdentifier(new Identifier($IDENTIFIER.text));
        for(Integer i : $range.value) {
          value.add(existingList.get(i));
        }
      }
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
        
chordSpec returns [VoicedChord chord]
    : NOTE_NAME tquality=triadQuality { chord =
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec(ChordType.TRIAD, $tquality.value))
                .setQuality($tquality.value)
                .buildVoicedChord();
         }
    | NOTE_NAME {chord =
            triadBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec(ChordType.TRIAD, Quality.MAJOR_TRIAD))
                .setQuality(Quality.MAJOR_TRIAD)
                .buildVoicedChord();
         }
    | NOTE_NAME squality=seventhQuality {chord =
            seventhBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec(ChordType.SEVENTH, $squality.value))
                .setQuality($squality.value)
                .buildVoicedChord();
         }
    | NOTE_NAME nquality=ninthQuality {chord =
            ninthBuilder.setRoot(NoteName.forSymbol($NOTE_NAME.text))
                .setChordSpec(struct.getChordSpec(ChordType.NINTH, $nquality.value))
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