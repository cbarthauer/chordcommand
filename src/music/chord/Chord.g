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

QUALITY : ('M' | 'm');
//MAJOR : 'M';
//    
//MINOR : 'm';
    	
COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        )+ {$channel=HIDDEN;}
    ;

START_BLOCK : '{' ;
END_BLOCK : '}' ;

compilationUnit : 'sequence'^ START_BLOCK! chord (','! chord)* END_BLOCK! EOF!;

chord : NOTE_NAME^ QUALITY;
