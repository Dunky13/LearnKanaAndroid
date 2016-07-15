package online.learnkana.android.learnkanaonline.mechanics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Marc on 7/14/2016.
 */
public class Statics {
    public static double rand(double min, double max)
    {
        return Math.floor(Math.random() * (max - min + 1))+ min;
    }
    public static double round(double value)
    {
        return Statics.round(value, 1.0);
    }
    public static double round(double value, double step)
    {
        return Statics.round(value, step, 0);
    }
    public static double round(double value, double step, int direction)
    {
        double inverseStep = 1.0 / step;
        double round = 0;
        if(direction == 1)
        {
            round = Math.ceil(value * inverseStep) / inverseStep;
        }
        else if(direction == -1)
        {
            round = Math.floor(value * inverseStep) / inverseStep;
        }
        else
        {
            round = Math.round(value * inverseStep) / inverseStep;
        }
        return round;
    }

    public static List<Double> getChanceList(List<JBlock> jBlockArray)
    {
        List<Double> tmpArray = new ArrayList<Double>(jBlockArray.size());
        for(int i = 0; i < jBlockArray.size(); i++)
        {
            tmpArray.add(jBlockArray.get(i).getChance());
        }
        return tmpArray;
    }

    public static List<String> shuffle(List<String> answers)
    {
        long seed = System.nanoTime();
        Collections.shuffle(answers, new Random(seed));
        return answers;
    }
}
