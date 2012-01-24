grammar Chord;

options {
  language = Java;
  output = AST;
  ASTLabelType=CommonTree;
}

@header {
  package music.chord;
}

@lexer::header {
  package music.chord;
}

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
SEVEN : '7';

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;
    
//Whitespace
WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        )+ {$channel=HIDDEN;}
    ;

//Blocks
START_BLOCK : '{' ;
END_BLOCK : '}' ;

//Lists
START_LIST : '[';
END_LIST : ']';

//Chord member tokens.
ROOT : 'root';
THIRD : 'third';
FIFTH : 'fifth';
SEVENTH : 'seventh';

//Chord types.
TRIADS : 'triads';
SEVENTHS : 'sevenths';

UNIT : 'unit';
VOICING : 'voicing';
CHORD : 'chord';

compilationUnit 
    : voicingDef+ progressionDef* EOF -> ^(UNIT voicingDef+ progressionDef*)
    ;
    
voicingDef :
    'voicings' START_BLOCK
      voicingTypeList
      (',' voicingTypeList)*
    END_BLOCK
    -> ^('voicings' voicingTypeList+)
    ;
    
voicingTypeList 
    : TRIADS ':' START_LIST
        chordMemberList
        (',' chordMemberList)*
      END_LIST
      -> ^(TRIADS chordMemberList+)
    | SEVENTHS ':' START_LIST
        chordMemberList
        (',' chordMemberList)*    
      END_LIST
      -> ^(SEVENTHS chordMemberList+)
    ;
    
chordMemberList
    : START_BLOCK chordMember (',' chordMember)* END_BLOCK
    -> ^(VOICING chordMember+)
    ;
    
progressionDef :
    'progression' START_BLOCK 
        chord (',' chord)* 
    END_BLOCK 
    -> ^('progression' chord+)
    ;

chord 
    : NOTE_NAME QUALITY
        -> ^(CHORD NOTE_NAME QUALITY)
    | NOTE_NAME
        -> ^(CHORD NOTE_NAME)
    | NOTE_NAME SEVEN
        -> ^(CHORD NOTE_NAME SEVEN)
    | NOTE_NAME MINOR_SEVEN
        -> ^(CHORD NOTE_NAME MINOR_SEVEN)
    | NOTE_NAME MINOR_SIX
        -> ^(CHORD NOTE_NAME MINOR_SIX)
    | NOTE_NAME DIMINISHED_SEVEN
        -> ^(CHORD NOTE_NAME DIMINISHED_SEVEN)    
    ;

chordMember : ROOT
    | THIRD
    | FIFTH
    | SEVENTH
    ;