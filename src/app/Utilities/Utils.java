package app.Utilities;

import java.util.Random;

/**
 * Created by fab on 4/17/2016
 */
public class Utils {

    private static int EATING_THINKING_BOUNDARY = 15;
    private static int HUNGRY_BOUNDARY = 7;

    public static int RandIntEatAndThink(){
        int randTime = new Random().nextInt(EATING_THINKING_BOUNDARY);
        return (randTime+1)*1000; //1-15 sec
    }

    public static int RandIntHungry() {
        int randTime = new Random().nextInt(HUNGRY_BOUNDARY);
        return (randTime+1)*1000; //1-7 sec
    }

}
