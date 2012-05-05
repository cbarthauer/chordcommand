package music.chord.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


enum NoteNameEnum {
//	               symbol     chromaticIndex diatonicIndex
	C             ("c",       1,             1),
	C_SHARP       ("c#",      2,             1),
	C_DOUBLE_SHARP("c##",     3,             1),
	C_FLAT        ("cb",      12,            1),
	C_DOUBLE_FLAT ("cbb",     11,            1),
	
	D             ("d",       3,             2),
	D_SHARP       ("d#",      4,             2),
	D_DOUBLE_SHARP("d##",     5,             2),
	D_FLAT        ("db",      2,             2),
	D_DOUBLE_FLAT ("dbb",     1,             2),
	
	E             ("e",       5,             3),
	E_SHARP       ("e#",      6,             3),
	E_DOUBLE_SHARP("e##",     7,             3),
	E_FLAT        ("eb",      4,             3),
	E_DOUBLE_FLAT ("ebb",     3,             3),
	
	F             ("f",       6,             4),
	F_SHARP       ("f#",      7,             4),
	F_DOUBLE_SHARP("f##",     8,             4),
	F_FLAT        ("fb",      5,             4),
	F_DOUBLE_FLAT ("fbb",     4,             4),
	
	G             ("g",       8,             5),
	G_SHARP       ("g#",      9,             5),
	G_DOUBLE_SHARP("g##",    10,             5),
	G_FLAT        ("gb",      7,             5),
	G_DOUBLE_FLAT ("gbb",     6,             5),
	
	A             ("a",      10,             6),
	A_SHARP       ("a#",     11,             6),
	A_DOUBLE_SHARP("a##",    12,             6),
	A_FLAT        ("ab",      9,             6),
	A_DOUBLE_FLAT ("abb",     8,             6),
	
	B             ("b",      12,             7),
	B_SHARP       ("b#",      1,             7),
	B_DOUBLE_SHARP("b##",     2,             7),
	B_FLAT        ("bb",     11,             7),
	B_DOUBLE_FLAT ("bbb",    10,             7);

    private static final Map<Integer, NoteNameEnum> diatonicMap;
    private static final Map<Integer, Map<Integer, NoteNameEnum>> chromaticMapByDiatonicIndex;
    private static final Logger log;
    
    static {
        diatonicMap = new HashMap<Integer, NoteNameEnum>();
        diatonicMap.put(1, NoteNameEnum.C);
        diatonicMap.put(2, NoteNameEnum.D);
        diatonicMap.put(3, NoteNameEnum.E);
        diatonicMap.put(4, NoteNameEnum.F);
        diatonicMap.put(5, NoteNameEnum.G);
        diatonicMap.put(6, NoteNameEnum.A);
        diatonicMap.put(7, NoteNameEnum.B);
        
        chromaticMapByDiatonicIndex = new HashMap<Integer, Map<Integer, NoteNameEnum>>();
        for(NoteNameEnum note : values()) {
            Map<Integer, NoteNameEnum> chromaticMap = 
                chromaticMapByDiatonicIndex.get(note.diatonicIndex) == null
                ? new HashMap<Integer, NoteNameEnum>()
                : chromaticMapByDiatonicIndex.get(note.diatonicIndex);
            chromaticMap.put(note.chromaticIndex, note);
            chromaticMapByDiatonicIndex.put(note.diatonicIndex, chromaticMap);
        }
        
        log = Logger.getRootLogger();
        log.trace("chromaticMapByDiatonicIndex: " + chromaticMapByDiatonicIndex);
    }
    
    static NoteNameEnum byIndex(int diatonicIndex, int chromaticIndex) {
        return chromaticMapByDiatonicIndex.get(diatonicIndex)
            .get(chromaticIndex);
    }
    
    static NoteNameEnum forDiatonicIndex(int diatonicIndex) {
        return diatonicMap.get(diatonicIndex);
    }

	private String symbol;
	private int chromaticIndex;
	private int diatonicIndex;

	private NoteNameEnum(String symbol, int chromaticIndex, int diatonicIndex) {
		this.symbol = symbol;
		this.chromaticIndex = chromaticIndex;
		this.diatonicIndex = diatonicIndex;
	}
	
	int getChromaticIndex() {
		return chromaticIndex;
	}

	int getDiatonicIndex() {
		return diatonicIndex;
	}

	String getSymbol() {
		return symbol;
	}

}
