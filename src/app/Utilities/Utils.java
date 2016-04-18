package app.Utilities;

import java.util.Random;

/**
 * Created by fab on 4/17/2016
 */
public class Utils {

    private static int THINKING_BOUNDARY = 15;
    private static int HUNGRY_BOUNDARY = 7;
    private static int EATING_BOUNDARY = 15;

    public static int RandIntThink(){
        int randTime = new Random().nextInt(THINKING_BOUNDARY);
        return (randTime+1)*1000; //1-15 sec
    }

    public static int RandIntHungry() {
        int randTime = new Random().nextInt(HUNGRY_BOUNDARY);
        return (randTime+1)*1000; //1-7 sec
    }

    public static int RandIntEat(){
        int randTime = new Random().nextInt(EATING_BOUNDARY);
        return (randTime+1)*1000; //1-15 sec
    }

}
