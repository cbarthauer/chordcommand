package music.chord.command;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import music.chord.arrangement.VoicedChord;
import music.chord.base.VoicePart;
import music.chord.notation.LilyPondConverter;

public class WriteLilyPondFile implements Command {
    
    private List<VoicedChord> chordList;
    private String fileName;

    public WriteLilyPondFile(List<VoicedChord> chordList, String fileName) {
        this.chordList = chordList;
        this.fileName = fileName;
    }
    
    @Override
    public void execute() {
        LilyPondConverter ly = new LilyPondConverter();
        STGroup group = new STGroupFile("music/chord/notation/barbershop.stg");
        ST st = group.getInstanceOf("score");
        st.add("tenorPitches", ly.stringFromChordList(chordList, VoicePart.TENOR));
        st.add("leadPitches", ly.stringFromChordList(chordList, VoicePart.LEAD));
        st.add("baritonePitches", ly.stringFromChordList(chordList, VoicePart.BARITONE));
        st.add("bassPitches", ly.stringFromChordList(chordList, VoicePart.BASS));
        
        try {
            PrintWriter pw = new PrintWriter(fileName);
            pw.print(st.render());
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
