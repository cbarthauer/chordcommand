grammar Melody;

options {
  language = Java;
}

@header {
  package music.chord.grammar;
  
  import music.chord.arrangement.MelodicInterval;
  import music.chord.arrangement.Melody;
  import music.chord.arrangement.MelodyBuilder;
  import music.chord.arrangement.MelodyPlayer;
  import music.chord.arrangement.NoteListBuilder;
  import music.chord.arrangement.impl.MelodicIntervalImpl;
  import music.chord.arrangement.impl.MelodyBuilderImpl;
  import music.chord.arrangement.impl.MelodyPlayerImpl;
  import music.chord.base.Direction;
  import music.chord.base.Duration;
  import music.chord.base.Interval;
  import music.chord.base.NoteName;
}

@lexer::header {
  package music.chord.grammar;
}

//Music Tokens
NOTE_NAME
    :   'A'..'G' ACCIDENTAL?
    ;
    
ACCIDENTAL
    :   'b' | '#' | 'bb' | '##'
    ;
    
//Directions
DIRECTION_UP: '/';
DIRECTION_DOWN: '\\';

//Durations
SIXTEENTH: 's';
EIGHTH: 'e';
DOTTED_EIGHTH: 'e.';
QUARTER: 'q';
DOTTED_QUARTER: 'q.';
HALF: 'h';
DOTTED_HALF: 'h.';
WHOLE: 'w';

//Intervals
PERFECT_UNISON: 'PU';
MINOR_SECOND: 'm2';
MAJOR_SECOND: 'M2';
MINOR_THIRD: 'm3';
MAJOR_THIRD: 'M3';
PERFECT_FOURTH: 'P4';
AUGMENTED_FOURTH: '+4';
DIMINISHED_FIFTH: 'd5';
PERFECT_FIFTH: 'P5';
MINOR_SIXTH: 'm6';
MAJOR_SIXTH: 'M6';
MINOR_SEVENTH: 'm7';
MAJOR_SEVENTH: 'M7';
PERFECT_OCTAVE: 'P8';

//Keywords
PLAY: 'play';

//Miscellaneous
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
START_ARGS: '(';
END_ARGS: ')';

INT : '0'..'9'+;

//Whitespace
WS  
    : ( ' '
    | '\t'
    | '\r'
    | '\n'
    )+ {$channel=HIDDEN;}
    ;
    
direction returns [Direction value]
  : DIRECTION_UP {value = Direction.UP;}
  | DIRECTION_DOWN {value = Direction.DOWN;}
  ;
  
duration returns [Duration value]
  : SIXTEENTH {value = Duration.SIXTEENTH;}
  | EIGHTH {value = Duration.EIGHTH;}
  | DOTTED_EIGHTH {value = Duration.DOTTED_EIGHTH;}
  | QUARTER {value = Duration.QUARTER;}
  | DOTTED_QUARTER {value = Duration.DOTTED_QUARTER;}
  | HALF {value = Duration.HALF;}
  | DOTTED_HALF {value = Duration.DOTTED_HALF;}
  | WHOLE {value = Duration.WHOLE;}
  ;

interval returns [Interval value]
  : PERFECT_UNISON {value = Interval.PERFECT_UNISON;}
  | MINOR_SECOND {value = Interval.MINOR_SECOND;}
  | MAJOR_SECOND {value = Interval.MAJOR_SECOND;}
  | MINOR_THIRD {value = Interval.MINOR_THIRD;}
  | MAJOR_THIRD {value = Interval.MAJOR_THIRD;}
  | PERFECT_FOURTH {value = Interval.PERFECT_FOURTH;}
  | AUGMENTED_FOURTH {value = Interval.AUGMENTED_FOURTH;}
  | DIMINISHED_FIFTH {value = Interval.DIMINISHED_FIFTH;}
  | PERFECT_FIFTH {value = Interval.PERFECT_FIFTH;}
  | MINOR_SIXTH {value = Interval.MINOR_SIXTH;}
  | MAJOR_SIXTH {value = Interval.MAJOR_SIXTH;}
  | MINOR_SEVENTH {value = Interval.MINOR_SEVENTH;}
  | MAJOR_SEVENTH {value = Interval.MAJOR_SEVENTH;}
  | PERFECT_OCTAVE {value = Interval.PERFECT_OCTAVE;}
  ;

melodicInterval returns [Direction direction, Interval interval]
  : d=direction i=interval {
      $direction = $d.value;
      $interval = $i.value;
  }
  | PERFECT_UNISON {
      $direction = Direction.REPEAT;
      $interval = Interval.PERFECT_UNISON;
  }
  ;

melody returns [Melody value]
@init {MelodyBuilder builder = null;}
@after {value = builder.build();}
  : NOTE_NAME INT ':' motif {
      builder = $motif.value;
      builder.start(NoteName.forSymbol($NOTE_NAME.text), Integer.parseInt($INT.text));
  }
  ;

motif returns [MelodyBuilder value]
@init {value = new MelodyBuilderImpl(new NoteListBuilder());}
  : OPEN_BRACE
    d1=duration {value.add($d1.value);} 
    m1=melodicInterval {value.add($m1.direction, $m1.interval);}
    d2=duration  {value.add($d2.value);}
    (m2=melodicInterval {value.add($m2.direction, $m2.interval);}
        d3=duration {value.add($d3.value);})*
  CLOSE_BRACE
  ;
  
play
@init {MelodyPlayer player = new MelodyPlayerImpl();}
  : PLAY START_ARGS 
      melody {player.add($melody.value).play();} 
  END_ARGS
  ;