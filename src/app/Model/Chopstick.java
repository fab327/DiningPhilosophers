package app.Model;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by fab on 4/16/2016
 */
public class Chopstick extends ReentrantLock {

    private int id;
    private boolean taken;

    private ImageView chopstickView;


    public Chopstick(int id) {
        this.id = id;
    }

    public void setChopstickView(ImageView chopstickView) {
        this.chopstickView = chopstickView;
    }

    public boolean isTaken() {
        return taken;
    }

    boolean pick(Philosopher philosopher) {
        if (tryLock()) {
            taken = true;
            Platform.runLater(()-> chopstickView.setVisible(false));
            System.out.println(philosopher + " picked up chopstick #" + id);
            return true;
        }
        return false;
    }

    boolean drop(Philosopher philosopher) {
        if (isHeldByCurrentThread()) {
            unlock();
            taken = false;
            Platform.runLater(()-> chopstickView.setVisible(true));
            System.out.println(philosopher + " dropped chopstick #" + id);
            return true;
        }
        return false;
    }

}
