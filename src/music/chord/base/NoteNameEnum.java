package music.chord.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


enum NoteNameEnum {
//	               symbol     chromaticIndex diatonicIndex
	C             ("C",       1,             1),
	C_SHARP       ("C#",      2,             1),
	C_DOUBLE_SHARP("C##",     3,             1),
	C_FLAT        ("Cb",      12,            1),
	C_DOUBLE_FLAT ("Cbb",     11,            1),
	
	D             ("D",       3,             2),
	D_SHARP       ("D#",      4,             2),
	D_DOUBLE_SHARP("D##",     5,             2),
	D_FLAT        ("Db",      2,             2),
	D_DOUBLE_FLAT ("Dbb",     1,             2),
	
	E             ("E",       5,             3),
	E_SHARP       ("E#",      6,             3),
	E_DOUBLE_SHARP("E##",     7,             3),
	E_FLAT        ("Eb",      4,             3),
	E_DOUBLE_FLAT ("Ebb",     3,             3),
	
	F             ("F",       6,             4),
	F_SHARP       ("F#",      7,             4),
	F_DOUBLE_SHARP("F##",     8,             4),
	F_FLAT        ("Fb",      5,             4),
	F_DOUBLE_FLAT ("Fbb",     4,             4),
	
	G             ("G",       8,             5),
	G_SHARP       ("G#",      9,             5),
	G_DOUBLE_SHARP("G##",    10,             5),
	G_FLAT        ("Gb",      7,             5),
	G_DOUBLE_FLAT ("Gbb",     6,             5),
	
	A             ("A",      10,             6),
	A_SHARP       ("A#",     11,             6),
	A_DOUBLE_SHARP("A##",    12,             6),
	A_FLAT        ("Ab",      9,             6),
	A_DOUBLE_FLAT ("Abb",     8,             6),
	
	B             ("B",      12,             7),
	B_SHARP       ("B#",      1,             7),
	B_DOUBLE_SHARP("B##",     2,             7),
	B_FLAT        ("Bb",     11,             7),
	B_DOUBLE_FLAT ("Bbb",    10,             7);

    private static final Map<Integer, NoteNameEnum> diatonicMap;
    private static final Map<Integer, Map<Integer, NoteNameEnum>> chromaticMapByDiatonicIndex;
    private static final Logger log;
    
    static {
        diatonicMap = new HashMap<Integer, NoteNameEnum>();
        diatonicMap.put(NoteNameEnum.C.diatonicIndex, NoteNameEnum.C);
        diatonicMap.put(NoteNameEnum.D.diatonicIndex, NoteNameEnum.D);
        diatonicMap.put(NoteNameEnum.E.diatonicIndex, NoteNameEnum.E);
        diatonicMap.put(NoteNameEnum.F.diatonicIndex, NoteNameEnum.F);
        diatonicMap.put(NoteNameEnum.G.diatonicIndex, NoteNameEnum.G);
        diatonicMap.put(NoteNameEnum.A.diatonicIndex, NoteNameEnum.A);
        diatonicMap.put(NoteNameEnum.B.diatonicIndex, NoteNameEnum.B);
        
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
