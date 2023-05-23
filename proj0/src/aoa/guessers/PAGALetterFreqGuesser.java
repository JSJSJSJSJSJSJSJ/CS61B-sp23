package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    public List<String> keepOnlyWordsThatMatchPatternAndGuesses(String pattern, List<Character> guesses) {
        List<String> onlyWordsThatMatchPatternAndGuesses = new ArrayList<>();
        for (String word : words) {
            // 判断word的length和pattern的length是否相等
            if (word.length() == pattern.length()) {
                // pattern匹配
                int i = 0;
                for ( ; i < pattern.length(); i++) {
                    if (pattern.charAt(i) != '-') {
                        // break完全结束循环
                        if (word.charAt(i) != pattern.charAt(i)) break;
                    }
                }
                // 如果pattern不匹配，进行下个word
                if (i != pattern.length()) continue;
                int j = 0;
                for ( ; j < guesses.size(); j++) {
                    Character c = guesses.get(j);
                    StringBuffer g = new StringBuffer().append(c);
                    // pattern里不包含当前guess才需要考虑
                    if (!pattern.contains(g))
                        if (word.contains(g))
                            break;
                }
                if (j == guesses.size()) onlyWordsThatMatchPatternAndGuesses.add(word);
            }
        }
        return onlyWordsThatMatchPatternAndGuesses;
    }

    public Map<Character, Integer> getFreqMapThatMatchesPatternAndGuesses(String pattern, List<Character> guesses) {
        List<String> strings = keepOnlyWordsThatMatchPatternAndGuesses(pattern, guesses);
        Map<Character, Integer> freqMapThatMatcherPatternAndGuesser = new TreeMap<>();
        for (String s : strings) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (freqMapThatMatcherPatternAndGuesser.get(c) == null) {
                    freqMapThatMatcherPatternAndGuesser.put(c, 1);
                } else {
                    freqMapThatMatcherPatternAndGuesser.replace(c, freqMapThatMatcherPatternAndGuesser.get(c), freqMapThatMatcherPatternAndGuesser.get(c) + 1);
                }
            }
        }
        return freqMapThatMatcherPatternAndGuesser;
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        TreeMap<Character, Integer> freqMapThatMatchesPatternAndGuesses = (TreeMap<Character, Integer>) getFreqMapThatMatchesPatternAndGuesses(pattern, guesses);
        if (freqMapThatMatchesPatternAndGuesses.size() == 0) return '?';
        for (Character guess : guesses) {
            freqMapThatMatchesPatternAndGuesses.remove(guess);
        }
        char maxChar = '?';
        int max = 0;
        Set<Character> characters = freqMapThatMatchesPatternAndGuesses.keySet();
        for (Character character : characters) {
            Integer cur = freqMapThatMatchesPatternAndGuesses.get(character);
            if (cur > max || (cur == max && character < maxChar)) {
                max = freqMapThatMatchesPatternAndGuesses.get(character);
                maxChar = character;
            }
        }
        return maxChar;
    }
}
