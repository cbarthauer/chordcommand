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
package music.chord.display;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.VoicedChord;

public class VerboseFormatter implements ChordListFormatter {

    @Override
    public String format(List<VoicedChord> chordList) {
        StringBuilder builder = new StringBuilder("");
        
        List<String> lineNumberList = getListWithHeader("Line");
        List<String> chordSymbolList = getListWithHeader("Chord");
        List<String> voicingList = getListWithHeader("Voicing");
        List<String> durationList = getListWithHeader("Duration");
        List<String> noteList = getListWithHeader("Notes");
        List<String> octaveList = getListWithHeader("Octave");
        int lineNumber = 0;
        
        for(VoicedChord chord : chordList) {
            lineNumberList.add(new Integer(lineNumber).toString());
            chordSymbolList.add(chordSymbolFromChord(chord));
            voicingList.add(voicingFromChord(chord));
            durationList.add(chord.getDuration().toString());
            noteList.add(chord.getNoteList().toString());
            octaveList.add(new Integer(chord.getOctave()).toString());
            lineNumber++;
        }
        
        lineNumberList = rightPad(lineNumberList);
        chordSymbolList = rightPad(chordSymbolList);
        voicingList = rightPad(voicingList);
        durationList = rightPad(durationList);
        noteList = rightPad(noteList);
        octaveList = rightPad(octaveList);
        String columnSpacer = " ";
        
        for(int i = 0; i < lineNumberList.size(); i++) {
            builder.append(lineNumberList.get(i))
                .append(columnSpacer)
                .append(chordSymbolList.get(i))
                .append(columnSpacer)
                .append(voicingList.get(i))
                .append(columnSpacer)
                .append(durationList.get(i))
                .append(columnSpacer)
                .append(noteList.get(i))
                .append(columnSpacer)
                .append(octaveList.get(i))
                .append("\n");
        }
        
        return builder.toString();
    }

    private List<String> getListWithHeader(String header) {
        List<String> result = new ArrayList<String>();
        result.add(header);
        return result;
    }

    private String chordSymbolFromChord(VoicedChord chord) {
        return chord.getSymbol();
    }

    private int maxLengthFromList(List<String> stringList) {
        int maxLength = 0;
        
        for(String entry : stringList) {
            if(entry.length() > maxLength) {
                maxLength = entry.length();
            }
        }
        
        return maxLength;
    }

    private List<String> rightPad(List<String> stringList) {
        int maxLength = maxLengthFromList(stringList);
        
        for(int i = 0; i < stringList.size(); i++) {
            String entry = stringList.get(i);
            
            while(entry.length() < maxLength) {
                entry = entry + " ";
            }
            
            stringList.set(i, entry);
        }
        
        return stringList;
    }

    private String voicingFromChord(VoicedChord chord) {
        return chord.getVoicing().toString();
    }

}
