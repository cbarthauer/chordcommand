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
package music.chord.notation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.Note;
import music.chord.arrangement.VoicedChord;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.VoicePart;

public class LilyPondConverter {
	
	private static Map<NoteName, String> lySymbolMap;
	private static Map<Integer, String> lySuffixMap;
	private static Map<Duration, String> lyDurationMap;
	private static Map<Integer, Integer> lySuffixRange;
	
	static {
		lySymbolMap = new HashMap<NoteName, String>();
		
		for(NoteName noteName : NoteName.all()) {
			String lySymbol = noteName.getSymbol()
					.replaceAll("#", "sharp");
			
			if(lySymbol.length() == 2 && lySymbol.endsWith("b")) {
				lySymbol = lySymbol.substring(0, 1) + "flat";
			}
			else if(lySymbol.length() == 3 && lySymbol.endsWith("bb")) {
				lySymbol = lySymbol.substring(0, 1) + "flatflat";
			}

			lySymbolMap.put(noteName, lySymbol.toLowerCase());
		}
		
		lySuffixMap = new HashMap<Integer, String>();
		lySuffixMap.put(1, ",,,");
		lySuffixMap.put(2, ",,");
		lySuffixMap.put(3, ",");
		lySuffixMap.put(4, "");
		lySuffixMap.put(5, "'");
		lySuffixMap.put(6, "''");
		lySuffixMap.put(7, "'''");
		
		lyDurationMap = new HashMap<Duration, String>();
		lyDurationMap.put(Duration.WHOLE, "1");
		lyDurationMap.put(Duration.HALF, "2");
		lyDurationMap.put(Duration.QUARTER, "4");
		lyDurationMap.put(Duration.EIGHTH, "8");
		lyDurationMap.put(Duration.SIXTEENTH, "16");
		
		lySuffixRange = new LinkedHashMap<Integer, Integer>();
		lySuffixRange.put(24, 1);
		lySuffixRange.put(36, 2);
		lySuffixRange.put(48, 3);
		lySuffixRange.put(60, 4);
		lySuffixRange.put(72, 5);
		lySuffixRange.put(84, 6);
		lySuffixRange.put(96, 7);
	}
	

	public String stringFromChordList(List<VoicedChord> chordList, VoicePart voicePart) {
		String result = "";
		
		for(VoicedChord chord : chordList) {
			Note currentNote = chord.noteFromVoicePart(voicePart);
			result = result + lyStringFromNote(currentNote) + " ";
		}
		
		return result.trim();
	}


	private String lyDurationFromNote(Note currentNote) {
        return lyDurationMap.get(currentNote.getDuration());
    }


	private String lyStringFromNote(Note currentNote) {
		String result = lySymbolMap.get(currentNote.getNoteName());
		result = result + lySuffixFromNote(currentNote);
		result = result + lyDurationFromNote(currentNote);
		return result;
	}


    private String lySuffixFromNote(Note currentNote) {
		int midiNumber = currentNote.getMidiNumber();
		
		if(midiNumber < 12 || 95 < midiNumber) {
		    throw new IllegalArgumentException(
		            "MIDI number " + midiNumber 
		            + " for note " + currentNote 
		            + " is out of range: 12-95.");
		}
	
		int lyOctave = lySuffixRange.size();
		
		for(Integer rangeLimit : lySuffixRange.keySet()) {
		    if(midiNumber < rangeLimit) {
		        lyOctave = lySuffixRange.get(rangeLimit);
		        break;
		    }
		}
		
		String symbol = currentNote.getNoteName().getSymbol();
		
		if("Cb".equals(symbol) 
		        || "Cbb".equals(symbol)) {
		    lyOctave += 1;
		}
		
        if("B#".equals(symbol) 
                || "B##".equals(symbol)) {
            lyOctave -= 1;
        }		
		
		return lySuffixMap.get(lyOctave);
	}
}
