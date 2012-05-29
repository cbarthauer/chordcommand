package music.chord.base;

import java.util.HashMap;
import java.util.Map;

public final class NoteName {
	private static Map<String, NoteNameEnum> symbolMap;
	
	static {
		symbolMap = new HashMap<String, NoteNameEnum>();
		
		for(NoteNameEnum noteName : NoteNameEnum.values()) {
			symbolMap.put(noteName.getSymbol(), noteName);
		}
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
	
	public final NoteName up(Interval interval) {
	    NoteName noteName = new NoteName(
            calculator.upChromaticBy(interval.getHalfSteps())
                .upDiatonicBy(interval.getDiatonicSteps())
                .result());
		return noteName;
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

	public final String name() {
		return noteEnum.name();
	}
	
	public final String toString() {
	    return noteEnum.getSymbol();
	}
}
