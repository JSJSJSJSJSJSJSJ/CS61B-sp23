package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    public List<String> keepOnlyWordsThatMatchPattern(String pattern) {
        List<String> onlyWordsThatMatchPattern = new ArrayList<>();
        for (String word : words) {
            if (word.length() == pattern.length()) {
                int i = 0;
                for ( ; i < pattern.length(); i++) {
                    if (pattern.charAt(i) != '-') {
                        // break完全结束循环
                        if (word.charAt(i) != pattern.charAt(i)) break;
                    }
                }
                if (i == pattern.length()) onlyWordsThatMatchPattern.add(word);
            }
        }
        return onlyWordsThatMatchPattern;
    }

    public Map<Character, Integer> getFreqMapThatMatchesPattern(String pattern) {
        List<String> strings = keepOnlyWordsThatMatchPattern(pattern);
        Map<Character, Integer> freqMapThatMatcherPattern = new TreeMap<>();
        for (String s : strings) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (freqMapThatMatcherPattern.get(c) == null) {
                    freqMapThatMatcherPattern.put(c, 1);
                } else {
                    freqMapThatMatcherPattern.replace(c, freqMapThatMatcherPattern.get(c), freqMapThatMatcherPattern.get(c) + 1);
                }
            }
        }
        return freqMapThatMatcherPattern;
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        TreeMap<Character, Integer> freqMapThatMatchesPattern = (TreeMap<Character, Integer>) getFreqMapThatMatchesPattern(pattern);
        if (freqMapThatMatchesPattern.size() == 0) return '?';
        for (Character guess : guesses) {
            freqMapThatMatchesPattern.remove(guess);
        }
        char maxChar = '?';
        int max = 0;
        Set<Character> characters = freqMapThatMatchesPattern.keySet();
        for (Character character : characters) {
            Integer cur = freqMapThatMatchesPattern.get(character);
            if (cur > max || (cur == max && character < maxChar)) {
                max = freqMapThatMatchesPattern.get(character);
                maxChar = character;
            }
        }
        return maxChar;
    }

    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("/data/example.txt");
        System.out.println(palfg.getFreqMapThatMatchesPattern("-e--"));
        System.out.println(palfg.keepOnlyWordsThatMatchPattern("-e--"));
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}