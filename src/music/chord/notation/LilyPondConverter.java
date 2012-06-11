package music.chord.notation;

import java.util.HashMap;
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
		
		if(midiNumber < 12 || midiNumber >= 96) {
		    throw new IllegalArgumentException(
		            "MIDI number " + midiNumber + " is out of range: 12-95.");
		}
	
		int lyOctave = 0;
		
		if(12 <= midiNumber && midiNumber < 24) {
		    lyOctave = 1;
		}
		else if(24 <= midiNumber && midiNumber < 36) {
		    lyOctave = 2;
		}
		else if(36 <= midiNumber && midiNumber < 48) {
		    lyOctave = 3;
		}
		else if(48 <= midiNumber && midiNumber < 60) {
		    lyOctave = 4;
		}
		else if(60 <= midiNumber && midiNumber < 72) {
		    lyOctave = 5;
		}
		else if(72 <= midiNumber && midiNumber < 84) {
		    lyOctave = 6;
		}
		else if(84 <= midiNumber && midiNumber < 96) {
		    lyOctave = 7;
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
