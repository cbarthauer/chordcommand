package music.chord.base;

public enum QualitySymbol {
	AUGMENTED("+"),
	MAJOR("M"),
	MINOR("m"),
	DIMINISHED("dim"), 
	
	//Placeholder for chords with missing members (i.e. triads don't have sevenths, etc.).
	NONE("");
	
	public static QualitySymbol qualityFromSymbol(String symbol) {
	    QualitySymbol result = QualitySymbol.MAJOR;
		
		for(QualitySymbol quality : QualitySymbol.values()) {
			if(quality.symbol.equals(symbol)) {
				result = quality;
				break;
			}
		}
		
		return result;
	}
	
	private String symbol;
	
	private QualitySymbol(String symbol) {
		this.symbol = symbol;
	}
}
