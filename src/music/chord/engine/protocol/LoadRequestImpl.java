package music.chord.engine.protocol;

public class LoadRequestImpl implements LoadRequest {

    private String fileName;
    private Identifier identifier;

    public LoadRequestImpl(String fileName, Identifier identifier) {
        this.fileName = fileName;
        this.identifier = identifier;
    }

    public String getFileName() {
        return fileName;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

}
