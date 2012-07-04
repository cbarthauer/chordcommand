package music.chord.display;

import java.util.List;

import music.chord.arrangement.VoicedChord;

public class FileFormatter implements ChordListFormatter {

    @Override
    public String format(List<VoicedChord> chordList) {
        StringBuilder builder = new StringBuilder("");
        
        builder.append("progression: {\n");
        
        for(VoicedChord chord : chordList) {
            builder.append("  ");
            builder.append(stringFromChord(chord));
            builder.append(",\n");
        }
        
        builder = removeLastComma(builder);
        
        builder.append("}\n");
        
        return builder.toString();
    }

    private StringBuilder removeLastComma(StringBuilder builder) {
        builder.replace(builder.length() - 2, builder.length(), "");
        builder.append("\n");
        return builder;
    }

    private String stringFromChord(VoicedChord chord) {
        StringBuilder builder = new StringBuilder("");
        builder.append(chord.getSymbol());
        builder.append(": {\n");
        builder.append("    voicing: ");
        builder.append(chord.getVoicing().toString());
        builder.append(",\n");
        builder.append("    octave: ");
        builder.append(chord.getOctave());
        builder.append(",\n");
        builder.append("    duration: ");
        builder.append(chord.getDuration().name());
        builder.append("\n  }");
        return builder.toString();
    }

}
