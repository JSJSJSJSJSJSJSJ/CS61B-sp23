package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        Map<Character, Integer> map = new TreeMap<>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (map.get(c) == null) {
                    map.put(c, 1);
                } else {
                    map.replace(c, map.get(c), map.get(c) + 1);
                }
            }
        }
        return map;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        TreeMap<Character, Integer> map = (TreeMap<Character, Integer>) getFrequencyMap();
        if (map.size() == 0) return '?';
        for (Character guess : guesses) {
            map.remove(guess);
        }
        char maxChar = '?';
        int max = 0;
        Set<Character> characters = map.keySet();
        for (Character character : characters) {
            Integer cur = map.get(character);
            if (cur > max || (cur == max && character < maxChar)) {
                max = map.get(character);
                maxChar = character;
            }
        }
        return maxChar;
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("/data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());
        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
