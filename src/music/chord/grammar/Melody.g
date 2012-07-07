grammar Melody;

options {
  language = Java;
}

@header {
  package music.chord.grammar;
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
UP: '/';
DOWN: '\\';

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

//Miscellaneous
OPEN_BRACE: '{';
CLOSE_BRACE: '}';

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
  : UP
  | DOWN
  ;
  
duration
  : SIXTEENTH
  | EIGHTH
  | QUARTER
  | HALF
  | WHOLE
  ;

interval
  : PERFECT_UNISON
  | MINOR_SECOND
  | MAJOR_SECOND
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