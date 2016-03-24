package com.diogonunes.darerer;

import com.diogonunes.darerer.helpers.UniqueRandom;

public class StringRoulette {
    private String[] _possibleOutcomes;
    private UniqueRandom _randGenerator;

    public StringRoulette(String[] possibleOutcomes) {
        setPossibleOutcomes(possibleOutcomes);
        _randGenerator = new UniqueRandom(0, possibleOutcomes.length - 1);
    }

    /**
     * Specifies the possible outcomes of a roll.
     *
     * @param possibleOutcomes Array of possible outcomes.
     */
    public void setPossibleOutcomes(String[] possibleOutcomes) {
        _possibleOutcomes = possibleOutcomes;
    }

    /**
     * Returns a random element from the possible outcomes.
     *
     * @return String.
     */
    public String roll() {
        int index = (int) _randGenerator.getUniqueRandom();
        return _possibleOutcomes[index];
    }
}
