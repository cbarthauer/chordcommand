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
package music.chord.arrangement;

public class VoicingComparison implements Comparable<VoicingComparison> {
	private Integer difference;
	private Voicing voicing;
	
	public VoicingComparison(int difference, Voicing voicing) {
		this.difference = difference;
		this.voicing = voicing;
	}

	@Override
	public int compareTo(VoicingComparison o) {
		return difference.compareTo(o.difference);
	}

	public Integer getDifference() {
		return difference;
	}

	public Voicing getVoicing() {
		return voicing;
	}	
	
	public String toString() {
		return difference + ": " + voicing;
	}
}
