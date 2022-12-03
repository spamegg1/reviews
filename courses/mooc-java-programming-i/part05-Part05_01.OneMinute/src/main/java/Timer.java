public class Timer {
    private ClockHand seconds;
    private ClockHand hundredths;

    public Timer() {
        seconds = new ClockHand(60);
        hundredths = new ClockHand(100);
    }

    public String toString() {
        return seconds.toString() + ":" + hundredths.toString();
    }

    public void advance() {
        hundredths.advance();
        if (hundredths.value() == 0) {
            seconds.advance();
        }
    }
}
