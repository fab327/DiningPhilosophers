package app.Utilities;

import java.util.Random;

/**
 * Created by fab on 4/17/2016
 */
public class Utils {

    private static int THINKING_BOUNDARY = 15;
    private static int HUNGRY_BOUNDARY = 7;
    private static int EATING_BOUNDARY = 15;

    public static void setThinkingBoundary(int thinkingBoundary) {
        THINKING_BOUNDARY = thinkingBoundary;
    }

    public static void setHungryBoundary(int hungryBoundary) {
        HUNGRY_BOUNDARY = hungryBoundary;
    }

    public static void setEatingBoundary(int eatingBoundary) {
        EATING_BOUNDARY = eatingBoundary;
    }

    public static int randomIntThink(){
        int randTime = new Random().nextInt(THINKING_BOUNDARY);
        return (randTime+1)*1000; //1 to THINKING_BOUNDARY sec
    }

    public static int randomIntHungry() {
        int randTime = new Random().nextInt(HUNGRY_BOUNDARY);
        return (randTime+1)*1000; //1 to HUNGRY_BOUNDARY sec
    }

    public static int randomIntEat(){
        int randTime = new Random().nextInt(EATING_BOUNDARY);
        return (randTime+1)*1000; //1 to EATING_BOUNDARY sec
    }

}
