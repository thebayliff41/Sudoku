
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

/**
 * Represents a counting timer that moves up from 0 to monitor the time
 * spent on the current puzzle
 *
 * @author Bailey Nelson
 * @author baileyd.nelson@gmail.com
 */
public class CountDownTimer extends AnimationTimer {

    private long pause;
    private long difference;
    private long finalTime;
    private boolean running;
    private long start;
    private long lastResult;
    private final Label text;

    /**
     * Constructor
     * @param text the label that corresponds to the timer
     */
    public CountDownTimer(Label text) {
        this.start();
        this.text = text;
        running = true;
    }//constructor

    @Override
    public void start() {
        start = System.currentTimeMillis();
        lastResult = 0;
        difference = 0;
        super.start();
    }//start

    @Override
    public void stop() {
        finalTime = (System.currentTimeMillis() - start)/1000 - difference;
        System.out.println(finalTime);
        running = false;
        super.stop();
    }//stop

    @Override
    public void handle(long time) {
        long now = System.currentTimeMillis();
        long second = ((now - start)/1000) - difference;
        if (second == lastResult) return;
        text.setText(getAsText(second));
        lastResult = second;
    }//handle

    /**
     * Acts as a pause on the timer
     */
    public void pause() { pause = System.currentTimeMillis(); }//pause

    /**
     * Resumes the timer
     */
    public void resume() { difference += (System.currentTimeMillis() - pause)/1000; }//resume

    /**
     * Converts the time given into a readable conter in MM:SS format or HH:MM:SS format
     * 
     * @param second the number of seconds on the timer
     * @return A string in readable format
     */
    private String getAsText(long second) {
       return (second/3600 > 0) 
           ? String.format("%02d:%02d:%02d", second/3600, (second % 3600)/60, second % 60) 
           : String.format("%02d:%02d", (second % 3600)/60, second %60);
    }//getAsText

    /**
     * Gets the end time from the timer
     *
     * @throws TimerNotStoppedException
     * @return the final time (in seconds) of the timer
     */
    public long getFinal() throws TimerNotStoppedException {
        if (running) throw new TimerNotStoppedException("The Timer is still running");

        return finalTime;
    }//getFinal

    /**
     * New exception to be thrown if the timer hasn't stopped
     * @author Bailey Nelson
     * @author baileyd.nelson@gmail.com
     */
    private class TimerNotStoppedException extends Exception {
        /**
         * Constructor
         */
        public TimerNotStoppedException(String message) {
            super(message);
        }//constructor
    }//class

}//countDownTimerClass
