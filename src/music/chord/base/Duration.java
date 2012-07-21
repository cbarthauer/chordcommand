/*******************************************************************************
 * Copyright (c) 2012 Chris Barthauer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Chris Barthauer - initial API and implementation
 ******************************************************************************/
package music.chord.base;

public enum Duration {
	SIXTEENTH(.25f),
	EIGHTH(.5f),
	DOTTED_EIGHTH(.75f),
	QUARTER(1f),
	DOTTED_QUARTER(1.5f),
	HALF(2f),
	DOTTED_HALF(3f),
	WHOLE(4f);

	public static Duration forName(String name) {
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
