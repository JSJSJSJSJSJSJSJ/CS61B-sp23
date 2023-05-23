package aoa.choosers;

import aoa.utils.FileUtils;
import edu.princeton.cs.algs4.StdRandom;

import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    /**
     *
     * @param wordLength
     * The desired word length
     * @param dictionaryFile
     * A filename containing the dictionary to select a word from, with one word on each line.
     */
    public RandomChooser(int wordLength, String dictionaryFile) {
        if (wordLength <= 0) throw new IllegalArgumentException("WordLength should not be less than one");
        List<String> words = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (words.size() == 0) throw new IllegalStateException("There are no words found of wordLength.");
        int randomlyChosenWordNumber = StdRandom.uniform(words.size());
        chosenWord = words.get(randomlyChosenWordNumber);
        pattern = "-".repeat(wordLength);
    }

    @Override
    public int makeGuess(char letter) {
        int num = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(pattern);
        for (int i = 0; i < chosenWord.length(); i++) {
            if (letter == chosenWord.charAt(i)) {
                num++;
                builder.replace(i,i+1, String.valueOf(letter));
            }
        }
        pattern = builder.toString();
        return num;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }

}
