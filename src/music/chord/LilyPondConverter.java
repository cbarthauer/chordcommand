package music.chord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LilyPondConverter {
	
	private static Map<NoteName, String> lySymbolMap;
	
	static {
		lySymbolMap = new HashMap<NoteName, String>();
		
		for(NoteName noteName : NoteName.values()) {
			String lySymbol = noteName.getSymbol()
					.replaceAll("#", "sharp");
			
			if(lySymbol.length() == 2 && lySymbol.endsWith("b")) {
				lySymbol = lySymbol.substring(0, 1) + "flat";
			}
			else if(lySymbol.length() == 3 && lySymbol.endsWith("bb")) {
				lySymbol = lySymbol.substring(0, 1) + "flatflat";
			}

			lySymbolMap.put(noteName, lySymbol);
		}
	}
	

	public String stringFromChordList(List<Chord> chordList, VoicePart voicePart) {
		String result = "";
		
		for(Chord chord : chordList) {
			NoteBean currentNote = chord.noteFromVoicePart(voicePart);
			result = result + lyStringFromNoteBean(currentNote) + " ";
		}
		
		return result.trim();
	}


	private String lyStringFromNoteBean(NoteBean currentNote) {
		String result = lySymbolMap.get(currentNote.getNoteName());
		result = result + lySuffixFromNoteBean(currentNote);
		return result;
	}


	private String lySuffixFromNoteBean(NoteBean currentNote) {
		int midiNumber = currentNote.getMidiNumber();
		if(midiNumber < 36 || midiNumber >= 96) {throw new IllegalArgumentException("MIDI number " + midiNumber + " is out of range: 36-95.");}
	
		String result = "";
		
		if(36 <= midiNumber && midiNumber < 48) {
			result = ",";
		}
		else if(48 <= midiNumber && midiNumber < 60) {
			result = "";
		}
		else if(60 <= midiNumber && midiNumber < 72) {
			result = "'";
		}
		else if(72 <= midiNumber && midiNumber < 84) {
			result = "''";
		}
		else if(84 <= midiNumber && midiNumber < 96) {
			result = "'''";
		}
		
		return result;
	}
}
