package com.diogonunes.darerer;

import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.Random;

public class Utils {

    /**
     * Returns a random number between two numbers.
     * @param min Minimum expected number.
     * @param max Maximum expected number.
     * @return An integer between [min,max], inclusive.
     */
    public static int getRandomInteger(int min, int max) {
        int range = (max - min) + 1;
        return new Random().nextInt(range) + min;
    }

    /**
     * Returns True or False, randomly.
     * @return A boolean.
     */
    public static boolean getRandomBool() {
        int number = getRandomInteger(0,1);
        return (number == 1);
    }

    /**
     * Returns True or False, randomly but slightly biased.
     * @param truePercentage The probability of returning true (0-100).
     * @return A boolean.
     */
    public static boolean getRandomBool(int truePercentage) {
        if (truePercentage > 100) truePercentage = 100;
        if (truePercentage <= 0) return false;

        int number = getRandomInteger(1,100);
        return (number <= truePercentage);
    }

    // Display Messages

    public static void ShowSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }
}


