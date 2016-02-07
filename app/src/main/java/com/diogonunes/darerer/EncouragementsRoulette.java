package com.diogonunes.darerer;

public class EncouragementsRoulette {

    private static String[] _encouragements;

    public EncouragementsRoulette(String[] encouragements) {
        _encouragements = encouragements;
    }

    /**
     * Returns a random encouragement.
     * @return Text description of encouragement.
     */
    public String getEncouragement() {
        int index = Utils.getRandomInteger(0, _encouragements.length - 1);
        return _encouragements[index];
    }
}
