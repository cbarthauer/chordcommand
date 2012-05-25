package music.chord.decorator;

import music.chord.base.Interval;
import music.chord.base.NoteName;

public class IntervalDirective {

    private Interval interval;
    
    public IntervalDirective(Interval interval) {
        this.interval = interval;
    }
    
    public NoteName calculate(NoteName root) {
        return root.up(interval);
    }

}
