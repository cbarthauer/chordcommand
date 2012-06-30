package music.chord.base;

public class QualityBuilder {
    
    private IntervallicStructure struct;
    private String name;
    private ChordType type;

    public Quality build() {
        return new QualityImpl(name, QualitySymbol.forName(name), type, struct);
    }
    
    public void setIntervallicStructure(IntervallicStructure struct) {
        this.struct = struct;
    }
    
    public void setName(String name) {
        this.name = name.toUpperCase();
    }
    
    public void setType(ChordType type) {
        this.type = type;
    }
    
    
}
