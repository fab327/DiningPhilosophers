package app.Model;

import app.Controller;
import app.Utilities.Timer;
import app.Utilities.Utils;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
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

    private TextArea loggingConsole;

    private Timer timer;
    private ObservableList<Timer> timers;

    public Philosopher(Chopstick leftChopstick, Chopstick rightChopstick, int id, String name, ImageView headView,
                       Image thinkingImg, Image hungryImg, Image eatingImg, TextArea loggingConsole, Timer timer, ObservableList<Timer> timers) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.id = id;
        this.name = name;
        this.headView = headView;
        this.thinkingImg = thinkingImg;
        this.hungryImg = hungryImg;
        this.eatingImg = eatingImg;
        this.loggingConsole = loggingConsole;

        state = State.THINKING;

        this.timer = timer;
        this.timers = timers;
    }

    @Override
    public void run() {
        while (Controller.RUNNING) {
            think();
            hungryWaitingToEat();
            if (Controller.ALLOW_DEADLOCK)
                eatWithDeadlock();
            else
                eat();

            if (timer.getEatingCounter() != 0) {
                timers.get(id).setAverageEatingTime((double) timer.getEatingTime() / timer.getEatingCounter() / 1000);
            }
            if (timer.getHungryCounter() != 0) {
                timers.get(id).setAverageHungryTime((double) (timer.getHungryTime() / timer.getHungryCounter()) / 1000);
            }
            if (timer.getThinkingCounter() != 0) {
                timers.get(id).setAverageThinkingTime((double) (timer.getThinkingTime() / timer.getThinkingCounter()) / 1000);
            }
        }
        Platform.runLater(() -> loggingConsole.appendText(name + " stopped \n"));
        System.out.println(name + " stopped");
    }

    private void think() {
        try {
            long startTime = System.currentTimeMillis();
            if (state == State.THINKING) {
                System.out.println(name + " is thinking...");
                Platform.runLater(() -> {
                    loggingConsole.appendText(name + " is thinking... \n");
//                    headView.setImage(thinkingImg);
                });

                Thread.sleep(Utils.randomIntThink());
                state = State.HUNGRY;
            }

            timer.addThinkingTime(System.currentTimeMillis() - startTime);
            timer.setThinkingCounter(timer.getThinkingCounter() + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void hungryWaitingToEat() {
        try {
            long startTime = System.currentTimeMillis();
            if (state == State.HUNGRY) {
                System.out.println(name + " is hungry...");
                Platform.runLater(() -> {
                    loggingConsole.appendText(name + " is hungry... \n");
                    headView.setImage(hungryImg);
                });

                Thread.sleep(Utils.randomIntHungry());
            }

            timer.setHungryCounter(timer.getHungryCounter() + 1);
            timer.addHungryTime(System.currentTimeMillis() - startTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eatWithDeadlock() {
        if (leftChopstick.pick(this))         //left chopstick is available
        {
            if (rightChopstick.pick(this)) {
                try {
                    //Eat
                    System.out.println(name + " is eating...");
                    long startTime = System.currentTimeMillis();
                    state = State.EATING;

                    Platform.runLater(() -> {
                        loggingConsole.appendText(name + " is eating... \n");
                        headView.setImage(eatingImg);
                    });

                    Thread.sleep(Utils.randomIntEat());

                    timer.addEatingTime(System.currentTimeMillis() - startTime);
                    timer.setEatingCounter(timer.getEatingCounter() + 1);

                    //Go back to Thinking state
                    rightChopstick.drop(this);
                    leftChopstick.drop(this);
                    state = State.THINKING;
                    Platform.runLater(() -> headView.setImage(thinkingImg));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
                        long startTime = System.currentTimeMillis();
                        state = State.EATING;

                        Platform.runLater(() -> {
                            loggingConsole.appendText(name + " is eating... \n");
                            headView.setImage(eatingImg);
                        });

                        Thread.sleep(Utils.randomIntEat());

                        timer.addEatingTime(System.currentTimeMillis() - startTime);
                        timer.setEatingCounter(timer.getEatingCounter() + 1);

                        //Go back to Thinking state
                        state = State.THINKING;
                        Platform.runLater(() -> headView.setImage(thinkingImg));
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
