package music.chord.base;

public enum Direction {
    DOWN(-1), 
    UP(1), 
    REPEAT(1);

    private int sign;

    private Direction(int sign) {
        this.sign = sign;
    }
    
    public int sign() {
        return sign;
    }

}
