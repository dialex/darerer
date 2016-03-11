package com.diogonunes.darerer;

import android.view.View;
import android.widget.LinearLayout;

import com.diogonunes.darerer.settings.Constants;

import java.util.Random;

public class Utils {
    private static Random _randGenerator = new Random();

    /**
     * Returns a random number between two numbers.
     *
     * @param min Minimum expected number.
     * @param max Maximum expected number.
     * @return An integer between [min,max], inclusive.
     */
    public static int getRandomInteger(int min, int max) {
        int range = (max - min) + 1;
        return _randGenerator.nextInt(range) + min;
    }

    /**
     * Returns True or False, randomly.
     *
     * @return A boolean.
     */
    public static boolean getRandomBool() {
        int number = getRandomInteger(0, 1);
        return (number == 1);
    }

    /**
     * Returns True or False, randomly but slightly biased.
     *
     * @param truePercentage The probability of returning true (0-100).
     * @return A boolean.
     */
    public static boolean getRandomBool(int truePercentage) {
        if (truePercentage > 100) truePercentage = 100;
        if (truePercentage <= 0) return false;

        int number = getRandomInteger(1, 100);
        return (number <= truePercentage);
    }

    /**
     * Formats a text to be shared on social networks.
     *
     * @param text
     * @return
     */
    public static String formatForSharing(String text) {
        if ((text == null) || (text.isEmpty()))
            return text;
        else
            return String.format("\"%s\" #ChallengeAccepted #DARERER @ %s", text, "", Constants.APP_STORE_URL);
    }

    /**
     * @param layout
     * @return True if layout is not null and visible. False otherwise.
     */
    public static boolean isLayoutVisible(LinearLayout layout) {
        return (layout != null) && (layout.getVisibility() == View.VISIBLE);
    }
}