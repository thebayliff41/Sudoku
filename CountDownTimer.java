
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.util.converter.LocalTimeStringConverter;

public class CountDownTimer extends Label {

    private LocalTimeStringConverter converter = new LocalTimeStringConverter();
    private AnimationTimer timer = new AnimationTimer() {
        private long start;

        @Override
        public void start() {
            start = System.currentTimeMillis();
            super.start();
        }//start

        @Override
        public void stop() {
            long now = System.currentTimeMillis();
            System.out.println("Final time: " + (now - start));
            super.stop();
        }//stop

        @Override
        public void handle(long time) {
            long now = System.currentTimeMillis();
            long second = (now - start)/1000;
            String display = String.format("%02d:%02d", (second % 3600)/60, second % 60);
            CountDownTimer.this.setText(display);
        }//handle
    };//AnimationTimer
    
    public CountDownTimer() {
        super();
        timer.start();
    }//CountDownTimerConstructor
}//countDownTimerClass
