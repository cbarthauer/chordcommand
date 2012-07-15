package music.chord.base;

public final class SymbolNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String symbol;
    
    public SymbolNotFoundException(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public final String getMessage() {
        return "Did not find NoteName for symbol: " + symbol;
    }
    
}
