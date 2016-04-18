package app.Model;

import app.Controller;
import app.Utilities.Utils;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by fab on 4/16/2016
 */
public class Philosopher implements Runnable {

    private Chopstick leftChopstick;
    private Chopstick rightChopstick;
    private int id;
    private String name;
    private State state;

    private ImageView headView;
    private Image thinkingImg;
    private Image hungryImg;
    private Image eatingImg;

    public Philosopher(Chopstick leftChopstick, Chopstick rightChopstick, int id, String name, ImageView headView,
                       Image thinkingImg, Image hungryImg, Image eatingImg) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.id = id;
        this.name = name;
        this.headView = headView;
        this.thinkingImg = thinkingImg;
        this.hungryImg = hungryImg;
        this.eatingImg = eatingImg;

        state = State.THINKING;
    }

    @Override
    public void run() {
        while (Controller.running) {
            think();
            hungryWaitingToEat();
            eat();
        }
        System.out.println(name + " stopped");
    }

    private void think() {
        try {
            if (state == State.THINKING) {
                System.out.println(name + " is thinking...");
                Platform.runLater(() -> headView.setImage(thinkingImg));

                Thread.sleep(Utils.RandIntThink());
                state = State.HUNGRY;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void hungryWaitingToEat() {
        try {
            if (state == State.HUNGRY) {
                System.out.println(name + " is hungry...");
                Platform.runLater(() -> headView.setImage(hungryImg));

                Thread.sleep(Utils.RandIntHungry());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        if (leftChopstick.pick(this))            //left chopstick is available
        {
            try {
                if (rightChopstick.pick(this)) { //right chopstick is available
                    try {
                        //Eat
                        System.out.println(name + " is eating...");
                        state = State.EATING;

                        Platform.runLater(() -> headView.setImage(eatingImg));

                        Thread.sleep(Utils.RandIntEat());

                        //Go back to Thinking state
                        Platform.runLater(() -> headView.setImage(thinkingImg));
                        state = State.THINKING;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        rightChopstick.drop(this);
                    }
                }
            } finally {
                leftChopstick.drop(this);
            }
        }
    }

    @Override
    public String toString() {
        return "Philosopher_" + id + "_" + name;
    }
}
