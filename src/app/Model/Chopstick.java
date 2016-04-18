package app.Model;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by fab on 4/16/2016
 */
public class Chopstick extends ReentrantLock {

    private int id;
    private boolean taken;

    public Chopstick(int id) {
        this.id = id;
    }

    boolean pick(Philosopher philosopher) {
        if (tryLock()) {
            taken = true;
            System.out.println(philosopher + " picked up chopstick # " + id);
            return true;
        }
        return false;
    }

    boolean drop(Philosopher philosopher) {
        if (isHeldByCurrentThread()) {
            unlock();
            taken = false;
            System.out.println(philosopher + " dropped chopstick # " + id);
            return true;
        }
        return false;
    }

    public boolean isTaken() {
        return taken;
    }

}
