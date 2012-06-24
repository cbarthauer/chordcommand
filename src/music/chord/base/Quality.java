package music.chord.base;

public interface Quality {
    public boolean equals(Object obj);
    public String getName();
    public String getSymbol();
    public ChordType getType();
    public int hashCode();
}
