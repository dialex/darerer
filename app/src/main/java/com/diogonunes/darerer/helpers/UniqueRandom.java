package com.diogonunes.darerer.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UniqueRandom {
    private List<Object> _domain;
    private int _currentIndex;

    public UniqueRandom(int minValue, int maxValue) {
        if (minValue > maxValue)
            throw new IllegalArgumentException("maxValue must be greater than minValue");

        _domain = new ArrayList<>();
        for (int i = minValue; i <= maxValue; i++) {
            _domain.add(i);
        }
        shuffle();
    }

    public Object getUniqueRandom() {
        if (_currentIndex >= _domain.size())
            shuffle();
        return _domain.get(_currentIndex++);
    }

    public void shuffle() {
        Collections.shuffle(_domain, new Random());
        _currentIndex = 0;
    }
}
