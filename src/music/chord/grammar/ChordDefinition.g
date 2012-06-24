grammar ChordDefinition;

options {
  language = Java;
}

@header {
    package music.chord.grammar;
    
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    
    import music.chord.arrangement.ChordDefinitionStructure;
    import music.chord.arrangement.Voicing;
    import music.chord.arrangement.VoicingFactory;
    
    import music.chord.base.ChordMember;
    import music.chord.base.ChordType;
    import music.chord.base.Interval;
    import music.chord.base.IntervalDirective;
    import music.chord.base.QualityEnum;
}

@lexer::header {
    package music.chord.grammar;
}

@members {
    ChordDefinitionStructure struct = new ChordDefinitionStructure();
    ChordType currentType;
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
NINTH: 'ninth';

DIRECTION_UP: 'up';
TO: 'to';

MINOR_THIRD: 'm3';
MAJOR_THIRD: 'M3';
PERFECT_FOURTH: 'P4';
DIMINISHED_FIFTH: 'dim5';
PERFECT_FIFTH: 'P5';
AUGMENTED_FIFTH: '+5';
DIMINISHED_SEVENTH: 'dim7';
MINOR_SEVENTH: 'm7';
MAJOR_SEVENTH: 'M7';
MAJOR_NINTH: 'M9';

START_LIST: '[';
END_LIST: ']';

VOICINGS: 'Voicings';

NAME: ('a'..'z' | 'A'..'Z' | '_')+ 
  ;
    
program returns [ChordDefinitionStructure chordDefinitionStructure]
  : definition+ EOF { chordDefinitionStructure = struct; }
  ;

definition:
  DEFINE name1=NAME { currentType = ChordType.forName($name1.text); }
  BEGIN
    chordStructure+
    voicingList
  END NAME
  ;
  

chordStructure: 
  QUALITY ':' NAME
  INTERVALLIC STRUCTURE ':'
    START_LIST 
      { Map<ChordMember, IntervalDirective> dirMap 
          = new HashMap<ChordMember, IntervalDirective>(); }
      ROOT ','
      dir1=intervalDirective {dirMap.putAll($dir1.value);}
      (',' dir2=intervalDirective {dirMap.putAll($dir2.value);})*
    END_LIST
    { struct.addQuality(QualityEnum.forName($NAME.text + "_" + currentType), dirMap); }
  ;
  
intervalDirective returns [Map<ChordMember, IntervalDirective> value]
  : DIRECTION_UP currentInterval=interval TO currentMember=chordMember { 
      value = new HashMap<ChordMember, IntervalDirective>(); 
      value.put($currentMember.value, new IntervalDirective($currentInterval.value)); }
  ;

voicingList: 
  VOICINGS ':'
  START_LIST
    voicing1=voicing { struct.addVoicing(currentType, $voicing1.value); }
    (',' voicing2=voicing { struct.addVoicing(currentType, $voicing2.value); })*
  END_LIST
  ;
  
voicing returns [Voicing value]
  : START_LIST { List<ChordMember> memberList = new ArrayList<ChordMember>(); }
      member1=chordMember { memberList.add($member1.value); } 
      (',' member2=chordMember { memberList.add($member2.value); })+ 
  END_LIST { value = VoicingFactory.instanceFromChordMemberList(memberList); }
  ;


chordMember returns [ChordMember value]
  : ROOT { value = ChordMember.ROOT; }
  | THIRD { value = ChordMember.THIRD; }
  | FIFTH { value = ChordMember.FIFTH; }
  | SEVENTH { value = ChordMember.SEVENTH; }
  | NINTH { value = ChordMember.NINTH; }
  ;
  
interval returns [Interval value]
  : MINOR_THIRD { value = Interval.MINOR_THIRD; }
  | MAJOR_THIRD { value = Interval.MAJOR_THIRD; }
  | PERFECT_FOURTH { value = Interval.PERFECT_FOURTH; }
  | DIMINISHED_FIFTH { value = Interval.DIMINISHED_FIFTH; }
  | PERFECT_FIFTH { value = Interval.PERFECT_FIFTH; }
  | AUGMENTED_FIFTH { value = Interval.AUGMENTED_FIFTH; }
  | DIMINISHED_SEVENTH { value = Interval.DIMINISHED_SEVENTH; }
  | MINOR_SEVENTH { value = Interval.MINOR_SEVENTH; }
  | MAJOR_SEVENTH { value = Interval.MAJOR_SEVENTH; }
  | MAJOR_NINTH { value = Interval.MAJOR_NINTH; }
  ;
