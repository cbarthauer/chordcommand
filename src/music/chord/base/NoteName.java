package music.chord.base;

import java.util.HashMap;
import java.util.Map;

public class NoteName {
	private static Map<String, NoteNameEnum> symbolMap;
	
	static {
		symbolMap = new HashMap<String, NoteNameEnum>();
		
		for(NoteNameEnum noteName : NoteNameEnum.values()) {
			symbolMap.put(noteName.getSymbol(), noteName);
		}
	}
	
	public static NoteName forSymbol(String symbol) {
		return new NoteName(symbolMap.get(symbol.toLowerCase()));
	}

	private NoteNameEnum noteEnum;
	private NoteNameCalculator calculator;
	
	private NoteName(NoteNameEnum noteNameEnum) {
		this.noteEnum = noteNameEnum;
		this.calculator = new NoteNameCalculator(noteEnum);
	}
	
	public NoteName up(Interval interval) {
		return new NoteName(
			calculator.upChromaticBy(interval.getHalfSteps())
				.upDiatonicBy(interval.getDiatonicSteps())
				.result());
	}	
	
	public int getChromaticIndex() {
		return noteEnum.getChromaticIndex();
	}

	public int getDiatonicIndex() {
		return noteEnum.getDiatonicIndex();
	}

	public String getSymbol() {
		return noteEnum.getSymbol();
	}

	public String name() {
		return noteEnum.name();
	}
}
