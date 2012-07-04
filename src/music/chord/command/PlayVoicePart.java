package music.chord.command;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.Note;
import music.chord.arrangement.VoicePartPlayer;
import music.chord.arrangement.VoicedChord;
import music.chord.base.VoicePart;

public class PlayVoicePart implements Command {

    private List<VoicedChord> chordList;
    private VoicePart part;
    private VoicePartPlayer player;
    
    public PlayVoicePart(List<VoicedChord> chordList, VoicePart part, VoicePartPlayer player) {
        this.chordList = new ArrayList<VoicedChord>(chordList);
        this.part = part;
        this.player = player;
    }
    
    @Override
    public void execute() {
        List<Note> noteList = new ArrayList<Note>();
        
        for(VoicedChord chord : chordList) {
            noteList.add(chord.noteFromVoicePart(part));
        }
        
        player.play(noteList);
    }

}
