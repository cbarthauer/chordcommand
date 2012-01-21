package music.chord;

public enum Quality {
	MAJOR("M"),
	MINOR("m");
	
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
