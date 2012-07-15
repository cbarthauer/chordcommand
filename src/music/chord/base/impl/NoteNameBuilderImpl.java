package music.chord.base.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.base.NoteName;
import music.chord.base.NoteNameBuilder;
import music.chord.base.SymbolNotFoundException;

public class NoteNameBuilderImpl implements NoteNameBuilder {
    private static class NoteNameList {
        private static List<NoteName> noteList = new ArrayList<NoteName>();
        
        static {
            for(NoteNameEnum noteEnum : NoteNameEnum.values()) {
                noteList.add(new NoteNameImpl(noteEnum));
            }
        }
        
        static List<NoteName> asList() {
            return new ArrayList<NoteName>(noteList);
        }
    }
    
    private static class SymbolMap {
        private static Map<String, NoteNameEnum> symbolMap;
        
        static {
            symbolMap = new HashMap<String, NoteNameEnum>();
            
            for(NoteNameEnum noteName : NoteNameEnum.values()) {
                symbolMap.put(noteName.getSymbol(), noteName);
            }
        }    
        
        static NoteNameEnum get(String symbol) {
            return symbolMap.get(symbol);
        }
    }
    
    @Override
    public final List<NoteName> all() {
        return NoteNameList.asList();
    }

    @Override
    public final NoteName forSymbol(String symbol) {
        NoteNameEnum noteNameEnum = SymbolMap.get(symbol);
        
        if(noteNameEnum != null) {
            return new NoteNameImpl(noteNameEnum);
        }
        else {
            throw new SymbolNotFoundException(symbol);
        }
    }

}
