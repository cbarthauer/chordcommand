package music.chord.base;

public enum Duration {
	SIXTEENTH(.25f),
	EIGHTH(.5f),
	QUARTER(1f),
	HALF(2f),
	WHOLE(4f);

	public static Duration durationFromName(String name) {
		Duration result = null;
		
		for(Duration duration : Duration.values()) {
			if(duration.name().equalsIgnoreCase(name)) {
				result = duration;
				break;
			}
		}
		
		return result;
	}
	
	private float conversionFactor;
	
	private Duration(float conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	
	public float getPpqConversionFactor() {
		return conversionFactor;
	}
}
