package music.chord.base;

public interface Quality {
    public boolean equals(Object obj);
    public String getName();
    public NoteName getNoteName(NoteName root, ChordMember member);
    public String getSymbol();
    public ChordType getType();
    public int hashCode();
}
