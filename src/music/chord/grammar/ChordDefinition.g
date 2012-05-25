grammar ChordDefinition;

options {
  language = Java;
}

@header {
    package music.chord.grammar;
}

@lexer::header {
    package music.chord.grammar;
}

//Whitespace
WS  
  : ( ' '
  | '\t'
  | '\r'
  | '\n'
  )+ {$channel=HIDDEN;}
  ;

DEFINE: 'Define';

BEGIN: 'Begin';
END: 'End';

QUALITY: 'Quality';

INTERVALLIC: 'Intervallic';
STRUCTURE: 'Structure';

ROOT: 'root';
THIRD: 'third';
FIFTH: 'fifth';
SEVENTH: 'seventh';

DIRECTION_UP: 'up';
TO: 'to';

MAJOR_THIRD: 'M3';
PERFECT_FIFTH: 'P5';
MINOR_THIRD: 'm3';
AUGMENTED_FIFTH: '+5';
DIMINISHED_FIFTH: 'dim5';
MINOR_SEVENTH: 'm7';

START_LIST: '[';
END_LIST: ']';

VOICINGS: 'Voicings';

NAME: ('a'..'z'|'A'..'Z')+ 
  ;
    
program: definition+ EOF
  ;

definition:
  DEFINE NAME
  BEGIN
    chordStructure+
    voicingList
  END NAME
  ;
  

chordStructure: 
  QUALITY ':' NAME
  INTERVALLIC STRUCTURE ':'
    START_LIST
      ROOT ','
      intervalDirective
      (',' intervalDirective)*
    END_LIST
  ;
  
intervalDirective: DIRECTION_UP interval TO chordMember
  ;

voicingList: 
  VOICINGS ':'
  START_LIST
    voicing
    (',' voicing)*
  END_LIST
  ;
  
voicing
  : START_LIST chordMember (',' chordMember)+ END_LIST
  ;


chordMember
  : ROOT 
  | THIRD 
  | FIFTH 
  | SEVENTH
  ;
  
interval
  : MAJOR_THIRD 
  | PERFECT_FIFTH 
  | MINOR_THIRD 
  | AUGMENTED_FIFTH 
  | DIMINISHED_FIFTH 
  | MINOR_SEVENTH
  ;
