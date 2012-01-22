package music.chord;

public enum Quality {
	AUGMENTED("+"),
	MAJOR("M"),
	MINOR("m"),
	DIMINISHED("dim");
	
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
