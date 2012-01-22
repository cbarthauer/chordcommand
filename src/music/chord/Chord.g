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

//Chord types.
TRIADS : 'triads';

UNIT : 'unit';
VOICING : 'voicing';
CHORD : 'chord';

compilationUnit 
    : voicingDef+ progressionDef* EOF -> ^(UNIT voicingDef+ progressionDef*)
    ;
    
voicingDef :
    'voicings' START_BLOCK
      voicingTypeList+
    END_BLOCK
    -> ^('voicings' voicingTypeList+)
    ;
    
voicingTypeList :
    TRIADS ':' START_LIST
      chordMemberList
      (',' chordMemberList)*
    END_LIST
    -> ^(TRIADS chordMemberList+)
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
    -> ^(CHORD NOTE_NAME QUALITY);

chordMember : ROOT
    | THIRD
    | FIFTH
    ;