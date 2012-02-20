package music.chord.decorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum NoteName {
//	               symbol     chromaticIndex diatonicIndex
	C             ("c",       0,             0),
//	C_NATURAL     ("cn",      0,             0),
	C_SHARP       ("c#",      1,             0),
	C_DOUBLE_SHARP("c##",     2,             0),
	C_FLAT        ("cb",      11,            0),
	C_DOUBLE_FLAT ("cbb",     10,            0),
	
	D             ("d",       2,             1),
//	D_NATURAL     ("dn",      2,             1),
	D_SHARP       ("d#",      3,             1),
	D_DOUBLE_SHARP("d##",     4,             1),
	D_FLAT        ("db",      1,             1),
	D_DOUBLE_FLAT ("dbb",     0,             1),
	
	E             ("e",       4,             2),
//	E_NATURAL     ("en",      4,             2),
	E_SHARP       ("e#",      5,             2),
	E_DOUBLE_SHARP("e##",     6,             2),
	E_FLAT        ("eb",      3,             2),
	E_DOUBLE_FLAT ("ebb",     2,             2),
	
	F             ("f",       5,             3),
//	F_NATURAL     ("fn",      5,             3),
	F_SHARP       ("f#",      6,             3),
	F_DOUBLE_SHARP("f##",     7,             3),
	F_FLAT        ("fb",      4,             3),
	F_DOUBLE_FLAT ("fbb",     3,             3),
	
	G             ("g",       7,             4),
//	G_NATURAL     ("gn",      7,             4),
	G_SHARP       ("g#",      8,             4),
	G_DOUBLE_SHARP("g##",     9,             4),
	G_FLAT        ("gb",      6,             4),
	G_DOUBLE_FLAT ("gbb",     5,             4),
	
	A             ("a",       9,             5),
//	A_NATURAL     ("an",      9,             5),
	A_SHARP       ("a#",     10,             5),
	A_DOUBLE_SHARP("a##",    11,             5),
	A_FLAT        ("ab",      8,             5),
	A_DOUBLE_FLAT ("abb",     7,             5),
	
	B             ("b",      11,             6),
//	B_NATURAL     ("bn",     11,             6),
	B_SHARP       ("b#",      0,             6),
	B_DOUBLE_SHARP("b##",     1,             6),
	B_FLAT        ("bb",     10,             6),
	B_DOUBLE_FLAT ("bbb",     9,             6);
	
	public static NoteName rootFromSymbol(String symbol) {
		for(NoteName root : NoteName.values()) {
			if(root.getSymbol().equalsIgnoreCase(symbol)) {
				return root;
			}
		}
		
		throw new IllegalArgumentException("No root exists for symbol: " + symbol);
	}
	
	private String symbol;
	private int chromaticIndex;
	private int diatonicIndex;

	private NoteName(String symbol, int chromaticIndex, int diatonicIndex) {
		this.symbol = symbol;
		this.chromaticIndex = chromaticIndex;
		this.diatonicIndex = diatonicIndex;
	}
	
	private int addChromatic(int chromaticIndex, int halfSteps) {
		int result = chromaticIndex + halfSteps;
		
		if(result > 11) {
			result = result % 12; 
		}
		
		return result;
	}

	public int getChromaticIndex() {
		return chromaticIndex;
	}

	public int getDiatonicIndex() {
		return diatonicIndex;
	}

	public String getSymbol() {
		return symbol;
	}

	private NoteName nextDiatonic() {
		int diatonicIndex = this.diatonicIndex == 6 ? 0 : this.diatonicIndex + 1;
		NoteName result = null;
		
		for(NoteName noteName : NoteName.values()) {
			if(noteName.diatonicIndex == diatonicIndex) {
				result = noteName;
				break;
			}
		}
		
		return result;
	}

	private NoteName toChromaticIndex(int chromaticIndex) {
		Map<Integer, NoteName> noteNameMap = new HashMap<Integer, NoteName>();
		
		for(NoteName noteName : NoteName.values()) {
			if(noteName.diatonicIndex == diatonicIndex) {
				noteNameMap.put(noteName.chromaticIndex, noteName);
			}
		}
		
		return noteNameMap.get(chromaticIndex);
	}

	public NoteName up(Interval interval) {
		return upDiatonicBy(interval.getDiatonicSteps())
			.toChromaticIndex(addChromatic(chromaticIndex, interval.getHalfSteps()));
	}

	private NoteName upDiatonicBy(int diatonicSteps) {
		NoteName result = this;
		
		for(int i = 0; i < diatonicSteps; i++) {
			result = result.nextDiatonic();
		}
		
		return result;
	}
}
