package com.diogonunes.darerer;

import java.util.Random;

/**
 * Created by Diogo on 06/02/2016.
 */
public class KindnessChallenger {

    private static String[] _challenges;

    public KindnessChallenger(String[] challenges) {
        _challenges = challenges;
    }

    /**
     * Returns a random challenge.
     * @return Text description of challenge.
     */
    public String getChallenge() {
        return _challenges[getRandomIndex(0, _challenges.length)];
    }

    /**
     * Returns a random number between two numbers.
     * @param min Minimum expected number.
     * @param max Maximum expected number.
     * @return An integer between [min,max], inclusive.
     */
    private int getRandomIndex(int min, int max) {
        int range = (max - min) + 1;
        int index = new Random().nextInt(range) + min;
        return index;

        /*
        TODO: if one day (minSDK = 21), replace with the line below
        return ThreadLocalRandom.current().nextInt(min, max + 1);  // +1 makes it inclusive
        */
    }
}
