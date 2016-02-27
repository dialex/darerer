package com.diogonunes.darerer.unused;

public class DiceBox {
    private static final String LOG_TAG = DiceBox.class.getSimpleName();
    private String _title;
    private String _description;
    private int _score;
    private String _category;

    public DiceBox(String title, String description, String category) {
        setTitle(title);
        setDescription(description);
        setCategory(category);
        resetScore();
    }

    // Getters and Setters

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public int getScore() {
        return _score;
    }

    public void incScore(int value) {
        this._score += value;
    }

    public void decScore(int value) {
        this._score -= value;
    }

    public void resetScore() {
        this._score = 0;
    }

    public String getCategory() {
        return _category;
    }

    public void setCategory(String category) {
        this._category = category;
    }
}
