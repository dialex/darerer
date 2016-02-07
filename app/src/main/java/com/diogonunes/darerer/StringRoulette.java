package com.diogonunes.darerer;

public class StringRoulette {
    private String[] _possibleOutcomes;

    public StringRoulette(String[] possibleOutcomes) {
        setPossibleOutcomes(possibleOutcomes);
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
        int index = Utils.getRandomInteger(0, _possibleOutcomes.length - 1);
        return _possibleOutcomes[index];
    }
}
