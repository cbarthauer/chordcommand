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
package music.chord.command;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.display.ChordListFormatter;
import music.chord.display.FileFormatter;

public class Save implements Command {

    private List<VoicedChord> chordList;
    private String fileName;

    public Save(List<VoicedChord> chordList, String fileName) {
        this.chordList = chordList;
        this.fileName = fileName;
    }
    
    @Override
    public void execute() {
        try {
            Path file = FileSystems.getDefault().getPath(fileName);
            ChordListFormatter formatter = new FileFormatter();
            Files.write(file, formatter.format(chordList).getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Could not write to file: " + fileName, e);
        }
    }
}
