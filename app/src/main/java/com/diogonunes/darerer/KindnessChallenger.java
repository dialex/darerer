package com.diogonunes.darerer;

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
        int index = Utils.getRandomIndex(0, _challenges.length-1);
        return _challenges[index];
    }
}
