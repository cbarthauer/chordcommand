package music.chord.engine.protocol;

final class VoiceAllRequestImpl implements VoiceAllRequest {

    private Identifier id;

    VoiceAllRequestImpl(Identifier id) {
        this.id = id;
    }
    
    @Override
    public final Identifier getIdentifier() {
        return id;
    }

}
