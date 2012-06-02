package music.chord.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NoteName {
	private static Map<String, NoteNameEnum> symbolMap;
	
	static {
		symbolMap = new HashMap<String, NoteNameEnum>();
		
		for(NoteNameEnum noteName : NoteNameEnum.values()) {
			symbolMap.put(noteName.getSymbol(), noteName);
		}
	}
	
	public static List<NoteName> all() {
	    List<NoteName> noteList = new ArrayList<NoteName>();
	    
	    for(NoteNameEnum noteEnum : NoteNameEnum.values()) {
	        noteList.add(new NoteName(noteEnum));
	    }
	    
	    return noteList;
	}
	
	public static NoteName forSymbol(String symbol) {
		return new NoteName(symbolMap.get(symbol));
	}

	private NoteNameEnum noteEnum;
	private NoteNameCalculator calculator;
	
	private NoteName(NoteNameEnum noteNameEnum) {
		this.noteEnum = noteNameEnum;
		this.calculator = new NoteNameCalculator(noteEnum);
	}
	
	@Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NoteName other = (NoteName) obj;
        if (noteEnum != other.noteEnum)
            return false;
        return true;
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
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((noteEnum == null) ? 0 : noteEnum.hashCode());
        return result;
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
