package aoa.choosers;

public interface Chooser {
    public int makeGuess(char letter);

    public String getPattern();

    public String getWord();
}
