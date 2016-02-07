package com.diogonunes.darerer;

import java.util.Random;

public class Utils {

    /**
     * Returns a random number between two numbers.
     * @param min Minimum expected number.
     * @param max Maximum expected number.
     * @return An integer between [min,max], inclusive.
     */
    public static int getRandomIndex(int min, int max) {
        int range = (max - min) + 1;
        return new Random().nextInt(range) + min;
    }
}
