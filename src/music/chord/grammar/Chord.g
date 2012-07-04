grammar Chord;

options {
  language = Java;
  output = AST;
  ASTLabelType=CommonTree;
}

@header {
  package music.chord.grammar;
}

@lexer::header {
  package music.chord.grammar;
}

//Miscellaneous Tokens.
INT : '0'..'9'+;

//Comments.
COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;
    
//Whitespace.
WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        )+ {$channel=HIDDEN;}
    ;

//Blocks.
START_BLOCK : '{' ;
END_BLOCK : '}' ;

//Lists.
START_LIST : '[';
END_LIST : ']';

//Music Tokens.
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
NINTHS : 'ninths';

NOTE_LENGTH 
  : 'SIXTEENTH'
  | 'EIGHTH'
  | 'QUARTER'
  | 'HALF'
  | 'WHOLE'
  ;
  
UNIT : 'unit';
VOICING : 'voicing';
OCTAVE: 'octave';
DURATION: 'duration';
CHORD : 'chord';
SPEC : 'spec';
ATTR_LIST : 'attr_list';

compilationUnit 
    : progressionDef* EOF -> ^(UNIT progressionDef*)
    ;
    
chordMemberList
    : START_LIST chordMember (',' chordMember)* END_LIST
    -> ^(VOICING chordMember+)
    ;
    
progressionDef :
    'progression' ':' START_BLOCK 
        chord (',' chord)* 
    END_BLOCK 
    -> ^('progression' chord+)
    ;

chord
    : chordSpec -> ^(CHORD chordSpec)
    | chordSpec ':' chordAttrList -> ^(CHORD chordSpec chordAttrList)
    ;
    
chordSpec 
    : NOTE_NAME triadQuality
        -> ^(SPEC NOTE_NAME triadQuality)
    | NOTE_NAME
        -> ^(SPEC NOTE_NAME)
    | NOTE_NAME seventhQuality
        -> ^(SPEC NOTE_NAME seventhQuality)
    | NOTE_NAME ninthQuality
        -> ^(SPEC NOTE_NAME ninthQuality)
    ;

chordAttrList
    : START_BLOCK
        chordAttr
        (',' chordAttr)*
    END_BLOCK
    -> ^(ATTR_LIST chordAttr+)
    ;

chordAttr
    : VOICING ':' chordMemberList -> chordMemberList
    | OCTAVE ':' INT -> ^(OCTAVE INT)
    | DURATION ':' NOTE_LENGTH -> ^(DURATION NOTE_LENGTH)
    ;
    
chordMember : ROOT
    | THIRD
    | FIFTH
    | SEVENTH
    | NINTH
    ;
    
triadQuality
    : MAJOR
    | MINOR
    | AUGMENTED
    | DIMINISHED
    | SUSPENDED
    ;
    
seventhQuality
    : DOMINANT_SEVEN
    | MINOR_SEVEN
    | MAJOR_SEVEN
    | DIMINISHED_SEVEN
    | HALF_DIMINISHED_SEVEN
    | SUSPENDED_SEVEN
    ;
    
ninthQuality
    : DOMINANT_NINE
    | MINOR_NINE
    | MAJOR_NINE
    ;