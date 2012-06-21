package music.chord.engine.protocol;

final class LoadRequestImpl implements LoadRequest {

    private String fileName;
    private Identifier identifier;

    LoadRequestImpl(String fileName, Identifier identifier) {
        this.fileName = fileName;
        this.identifier = identifier;
    }

    public final String getFileName() {
        return fileName;
    }

    public final Identifier getIdentifier() {
        return identifier;
    }

}
