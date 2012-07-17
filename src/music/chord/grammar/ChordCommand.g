/*******************************************************************************
 * Copyright (c) 2012 Chris Barthauer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Chris Barthauer - initial API and implementation
 ******************************************************************************/
grammar ChordCommand;

options {
  language = Java;
}

@header {
  package music.chord.grammar;

  import java.util.ArrayList;
  import java.util.List;
  
  import music.chord.arrangement.ChordMemberFilter;
  import music.chord.arrangement.ChordPlayer;
  import music.chord.arrangement.EqualsFilter;  
  import music.chord.arrangement.QualityRegistry;
  import music.chord.arrangement.VoicedChord;
  import music.chord.arrangement.VoicePartPlayer;
  import music.chord.arrangement.Voicing;
  import music.chord.arrangement.VoicingFactory;
  
  import music.chord.base.ChordMember;
  import music.chord.base.Duration;
  import music.chord.base.NoteName;
  import music.chord.base.Quality;
  import music.chord.base.VoicePart;
  
  import music.chord.command.Command;
  import music.chord.command.CreateChordList;
  import music.chord.command.Display;
  import music.chord.command.InsertBefore;
  import music.chord.command.WriteLilyPondFile;
  import music.chord.command.Play;
  import music.chord.command.PlayVoicePart;
  import music.chord.command.Quit;
  import music.chord.command.RemoveChord;
  import music.chord.command.Save;
  import music.chord.command.VoicingComparisonList;
  
  import music.chord.display.VerboseFormatter;
  
  import music.chord.engine.ChordEngine;
  import music.chord.engine.protocol.ChordRequest;
  import music.chord.engine.protocol.CreateChordRequest;
  import music.chord.engine.protocol.Identifier;
  import music.chord.engine.protocol.InsertChordRequest;
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
  VoicePartPlayer voicePartPlayer;
  QualityRegistry qualities;
  
  public void setChordEngine(ChordEngine engine) {
    this.engine = engine;
  }
    
  public void setChordPlayer(ChordPlayer player) {
    this.player = player;
  }
  
  public void setQualityRegistry(QualityRegistry qualities) {
    this.qualities = qualities;
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
CREATE : 'create';
REMOVE : 'remove';
INSERT : 'insert';
ALL : 'all';
DISPLAY : 'display';
DURATION : 'duration';
BEFORE : 'before';
FIND : 'find';
CHORDS : 'chords';
CHORDS_BY_FILTER : 'chordsByFilter';
CHORDS_CONTAINING : 'chordsContaining';
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

STRING : '"' ('a'..'z' | 'A'..'Z' | '0'..'9' | '\\' | '.' | ':' | '_')+ '"';

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
START_ARGS : '(';
END_ARGS : ')';

program returns [List<Command> result]
  : command+ EOF { result = commandList; }
  ;
  
command
  : chordsByFilter
  | chordsContaining
  | create
  | display
  | insert
  | load
  | play
  | quit
  | remove
  | save
  | set
  | voice
  ;

chordsByFilter returns [List<VoicedChord> value]
  : CHORDS_BY_FILTER START_ARGS filter END_ARGS {
      value = engine.chordsByFilter($filter.value);
  }
  ;

filter returns [ChordMemberFilter value]
  : chordMember '=' NOTE_NAME {
      value = new EqualsFilter($chordMember.value, NoteName.forSymbol($NOTE_NAME.text));
  }
  ;
  
chordsContaining returns [List<VoicedChord> value]
  : CHORDS_CONTAINING START_ARGS noteNameList END_ARGS {
      value = engine.chordsContaining($noteNameList.value);
  }
  ;

create 
  : CREATE START_ARGS IDENTIFIER ',' chords=chordList END_ARGS  {
      commandList.add(
          new CreateChordList(
             new RequestBuilder(new Identifier($IDENTIFIER.text)), 
             $chords.value, 
             engine));
  } 
  ;
    
display
  : DISPLAY START_ARGS chordList END_ARGS {
    commandList.add(
        new Display(
            $chordList.value, 
            new VerboseFormatter()));
  }
  | DISPLAY VOICINGS FOR IDENTIFIER START_LIST startIndex=INT TO endIndex=INT END_LIST {
    commandList.add(
        new VoicingComparisonList(
            engine.compareVoicings(
                engine.byIdentifier(
                    new Identifier($IDENTIFIER.text), 
                    Integer.parseInt($startIndex.text)),
                engine.byIdentifier(
                    new Identifier($IDENTIFIER.text), 
                    Integer.parseInt($endIndex.text)))));}
  ;

noteNameList returns [List<NoteName> value]
@init{value = new ArrayList<NoteName>();}
  : first=NOTE_NAME 
      {value.add(NoteName.forSymbol($first.text));} 
    (',' subsequent=NOTE_NAME 
      {value.add(NoteName.forSymbol($subsequent.text));})*
  ;
  
insert
  : INSERT START_ARGS IDENTIFIER START_LIST INT END_LIST ',' chords=chordList END_ARGS  { 
      commandList.add(
          new InsertBefore(
              new RequestBuilder(new Identifier($IDENTIFIER.text)), 
              Integer.parseInt($INT.text), 
              $chords.value,
              engine));
  }
  ;

load returns [List<VoicedChord> value]
  : LOAD START_ARGS fileName=STRING END_ARGS {
      value = engine.load($fileName.text.replaceAll("\"", ""));
  }
  ;

play
  : PLAY START_ARGS chordList END_ARGS {
      commandList.add(
          new Play(
              $chordList.value, 
              player));}
  | PLAY START_ARGS voicePart ',' chordList END_ARGS  {
      commandList.add(
          new PlayVoicePart(
              $chordList.value, 
              $voicePart.value, 
              voicePartPlayer));
  }
  ;

quit
  : QUIT { commandList.add(new Quit()); }
  ;
            
remove
  : REMOVE START_ARGS IDENTIFIER START_LIST range END_LIST END_ARGS {
      commandList.add(
          new RemoveChord(
              new RequestBuilder(new Identifier($IDENTIFIER.text)), 
              $range.value, 
              engine));
  }
  ;
  
save
  : SAVE START_ARGS fileName=STRING ',' chordList END_ARGS {
      String outFile = $fileName.text.replaceAll("\"", "");
      
      if(outFile.endsWith(".ly")) {
          commandList.add(
              new WriteLilyPondFile(
                  $chordList.value,
                  outFile));
      }
      else {
	      commandList.add(
	          new Save(
	              $chordList.value, 
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
      builder.setDuration(Duration.forName($NOTE_LENGTH.text));
      engine.setDurations(builder.durationRequest($range.value));
  }
  | SET 'octave' octave=INT ON IDENTIFIER START_LIST range END_LIST {
      RequestBuilder builder = new RequestBuilder(new Identifier($IDENTIFIER.text));
      builder.setOctave(Integer.parseInt($octave.text));
      engine.setOctaves(builder.octaveRequest($range.value));
  }
  ;

voice returns [List<VoicedChord> value]
  : VOICE START_ARGS chordList END_ARGS {
      value = engine.voiceAll($chordList.value);
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
  : first=chordAtom { value.addAll($first.value); } 
    (',' subsequent=chordAtom {value.addAll($subsequent.value);})*
  ;
  
chordAtom returns [List<VoicedChord> value]
@init {value = new ArrayList<VoicedChord>();}
  : chord {
      value.add($chord.value);
  }
  | chordsByFilter {
      value.addAll($chordsByFilter.value);
  }
  | chordsContaining {
      value.addAll($chordsContaining.value);
  }
  | IDENTIFIER {
      value.addAll(
          engine.byIdentifier(
              new Identifier($IDENTIFIER.text)));
  }
  | IDENTIFIER START_LIST range END_LIST {
      value.addAll(
          engine.byIdentifier(
              new Identifier($IDENTIFIER.text), 
              $range.value));
  }
  | load {
      value.addAll($load.value);
  }
  | voice {
      value.addAll($voice.value);
  }
  ;
  
chord returns [VoicedChord value]
    : spec=chordSpec {
        value = engine.createChord(
            $spec.root,
            $spec.quality);
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
        
chordSpec returns [NoteName root, Quality quality]
    : NOTE_NAME tquality=triadQuality { 
        $root = NoteName.forSymbol($NOTE_NAME.text);
        $quality = $tquality.value;
    }
    | NOTE_NAME {         
        $root = NoteName.forSymbol($NOTE_NAME.text);
        $quality = qualities.forName("MAJOR_TRIAD");
    }
    | NOTE_NAME squality=seventhQuality { 
        $root = NoteName.forSymbol($NOTE_NAME.text);
        $quality = $squality.value;
    }
    | NOTE_NAME nquality=ninthQuality { 
        $root = NoteName.forSymbol($NOTE_NAME.text);
        $quality = $nquality.value;
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
    : MAJOR { $value = qualities.forName("MAJOR_TRIAD"); }
    | MINOR { $value = qualities.forName("MINOR_TRIAD"); }
    | AUGMENTED { $value = qualities.forName("AUGMENTED_TRIAD"); }
    | DIMINISHED { $value = qualities.forName("DIMINISHED_TRIAD"); }
    | SUSPENDED { $value = qualities.forName("SUSPENDED_TRIAD"); }
    ;
    
seventhQuality returns [Quality value]
    : DOMINANT_SEVEN { $value = qualities.forName("DOMINANT_SEVENTH"); }
    | MINOR_SEVEN { $value = qualities.forName("MINOR_SEVENTH"); }
    | MAJOR_SEVEN { $value = qualities.forName("MAJOR_SEVENTH"); }
    | DIMINISHED_SEVEN { $value = qualities.forName("DIMINISHED_SEVENTH"); }
    | HALF_DIMINISHED_SEVEN { $value = qualities.forName("HALF_DIMINISHED_SEVENTH"); }
    | SUSPENDED_SEVEN { $value = qualities.forName("SUSPENDED_SEVENTH"); }
    ;
    
ninthQuality returns [Quality value]
    : DOMINANT_NINE { $value = qualities.forName("DOMINANT_NINTH"); }
    | MINOR_NINE { $value = qualities.forName("MINOR_NINTH"); }
    | MAJOR_NINE { $value = qualities.forName("MAJOR_NINTH"); }
    ;
    
voicePart returns [VoicePart value]
    : BASS {$value = VoicePart.BASS;}
    | BARITONE {$value = VoicePart.BARITONE;}
    | LEAD {$value = VoicePart.LEAD;}
    | TENOR {$value = VoicePart.TENOR;}
    ;
