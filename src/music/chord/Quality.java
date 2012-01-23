package music.chord;

public enum Quality {
	AUGMENTED("+"),
	MAJOR("M"),
	MINOR("m"),
	DIMINISHED("dim"), 
	
	//Placeholder for chords with missing members (i.e. triads don't have sevenths, etc.).
	NONE("");
	
	public static Quality qualityFromAbbreviation(String abbreviation) {
		Quality result = Quality.MAJOR;
		
		for(Quality quality : Quality.values()) {
			if(quality.abbreviation.equals(abbreviation)) {
				result = quality;
				break;
			}
		}
		
		return result;
	}
	
	private String abbreviation;
	
	private Quality(String abbreviation) {
		this.abbreviation = abbreviation;
	}
}
