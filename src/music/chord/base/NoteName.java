package music.chord.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NoteName {
	private static class MapOperation {
        static boolean equals(NoteName noteName, Object obj) {
            if (noteName == obj)
                return true;
            if (obj == null)
                return false;
            if (noteName.getClass() != obj.getClass())
                return false;
            NoteName other = (NoteName) obj;
            if (noteName.noteEnum != other.noteEnum)
                return false;
            return true;           
        }
        
        static int hashCode(NoteName noteName) {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((noteName.noteEnum == null) 
                            ? 0 
                            : noteName.noteEnum.hashCode());
            return result;
        }
    }
	
	private static class NoteNameList {
	    private static List<NoteName> noteList = new ArrayList<NoteName>();
	    
	    static {
	        for(NoteNameEnum noteEnum : NoteNameEnum.values()) {
	            noteList.add(new NoteName(noteEnum));
	        }
	    }
	    
	    static List<NoteName> asList() {
	        return new ArrayList<NoteName>(noteList);
	    }
	}
	
	private static class SymbolMap {
	    private static Map<String, NoteNameEnum> symbolMap;
	    
	    static {
	        symbolMap = new HashMap<String, NoteNameEnum>();
	        
	        for(NoteNameEnum noteName : NoteNameEnum.values()) {
	            symbolMap.put(noteName.getSymbol(), noteName);
	        }
	    }    
	    
	    static NoteNameEnum get(String symbol) {
	        return symbolMap.get(symbol);
	    }
	}
	
	public static List<NoteName> all() {
	    return NoteNameList.asList();
	}

	public static NoteName forSymbol(String symbol) {
		return new NoteName(SymbolMap.get(symbol));
	}
	
	private NoteNameEnum noteEnum;
	private NoteNameCalculator calculator;
	
	private NoteName(NoteNameEnum noteNameEnum) {
		this.noteEnum = noteNameEnum;
		this.calculator = new NoteNameCalculator(noteEnum);
	}	
	
	@Override
    public final boolean equals(Object obj) {
	    return MapOperation.equals(this, obj);
    }

	public final int getChromaticIndex() {
		return noteEnum.getChromaticIndex();
	}

	public final int getDiatonicIndex() {
		return noteEnum.getDiatonicIndex();
	}

	public final String getSymbol() {
		return noteEnum.getSymbol();
	}
	
	@Override
    public final int hashCode() {
	    return MapOperation.hashCode(this);
    }

    public final String name() {
		return noteEnum.name();
	}

    public final String toString() {
	    return noteEnum.getSymbol();
	}
    
    public final NoteName up(Interval interval) {
	    NoteNameEnum noteEnum = calculator.upChromaticBy(interval.getHalfSteps())
            .upDiatonicBy(interval.getDiatonicSteps())
            .result();
	    NoteName noteName = new NoteName(noteEnum);
		return noteName;
	}
}
