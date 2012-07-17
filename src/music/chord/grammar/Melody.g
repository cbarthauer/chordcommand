grammar Melody;

options {
  language = Java;
}

@header {
  package music.chord.grammar;
  
  import music.chord.base.Duration;
  import music.chord.base.Interval;
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
QUARTER: 'q';
HALF: 'h';
WHOLE: 'w';

//Intervals
PERFECT_UNISON: 'PU';
MINOR_SECOND: 'm2';
MAJOR_SECOND: 'M2';

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
    
direction
  : DIRECTION_UP
  | DIRECTION_DOWN
  ;
  
duration returns [Duration duration]
  : SIXTEENTH {duration = Duration.forName($SIXTEENTH.text);}
  | EIGHTH {duration = Duration.forName($EIGHTH.text);}
  | QUARTER {duration = Duration.forName($QUARTER.text);}
  | HALF {duration = Duration.forName($HALF.text);}
  | WHOLE {duration = Duration.forName($WHOLE.text);}
  ;

interval returns [Interval interval]
  : PERFECT_UNISON {interval = Interval.forName($PERFECT_UNISON.text);}
  | MINOR_SECOND {interval = Interval.forName($MINOR_SECOND.text);}
  | MAJOR_SECOND {interval = Interval.forName($MAJOR_SECOND.text);}
  ;

melodicInterval
  : direction interval
  | PERFECT_UNISON
  ;

melody
  : NOTE_NAME INT ':' motif 
  ;

motif
  : OPEN_BRACE
    duration melodicInterval duration (melodicInterval duration)*
  CLOSE_BRACE
  ;
  
play
  : PLAY START_ARGS melody END_ARGS
  ;