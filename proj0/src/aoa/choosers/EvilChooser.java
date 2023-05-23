package aoa.choosers;

import aoa.utils.FileUtils;
import edu.princeton.cs.algs4.StdRandom;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

public class EvilChooser implements Chooser {
    private String pattern;
    private List<String> wordPool;

    public EvilChooser(int wordLength, String dictionaryFile) {
        if (wordLength <= 0) throw new IllegalArgumentException("WordLength should not be less than one");
        wordPool = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (wordPool.size() == 0) throw new IllegalStateException("There are no words found of wordLength.");
        pattern = "-".repeat(wordLength);
    }

    public String getPattern(String word, char letter) {
        // 先判断word是否属于当前pattern
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (c != '-') {
                if (c != word.charAt(i))
                    return "";
            }
        }
        // 如果匹配，则返回word和letter的pattern
        StringBuilder currentPatternBuilder = new StringBuilder();
        currentPatternBuilder.append(pattern);
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == letter) currentPatternBuilder.replace(i, i+1, String.valueOf(letter));
        }
        return currentPatternBuilder.toString();
    }

    @Override
    public int makeGuess(char letter) {
        Map<String, ArrayList<String>> optionsMap = new TreeMap<>();
        for (String word : wordPool) {
            String currentPattern = getPattern(word, letter);
            if (currentPattern.length() == 0) continue;
            ArrayList<String> currentPatternWordList = optionsMap.get(currentPattern);
            if (currentPatternWordList == null) currentPatternWordList = new ArrayList<>();
            currentPatternWordList.add(word);
            optionsMap.put(currentPattern, currentPatternWordList);
        }
        Set<String> patterns = optionsMap.keySet();
        String maxPattern = "";
        int maxSize = 0;
        for (String s : patterns) {
            int size = optionsMap.get(s).size();
            if (maxSize < size) {
                maxPattern = s;
                maxSize = size;
            }
        }
        pattern = maxPattern;
        wordPool = optionsMap.get(maxPattern);
        int occurrences = 0;
        for (int i = 0; i < pattern.length(); i++)
            if (letter == pattern.charAt(i))
                occurrences++;
        return occurrences;

    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        int randomlyChosenWordNumber = StdRandom.uniform(wordPool.size());
        return wordPool.get(randomlyChosenWordNumber);
    }

    public static void main(String[] args) {
        String EXAMPLE_FILE = "/data/example.txt";
        EvilChooser ec = new EvilChooser(4, EXAMPLE_FILE);

        int first = ec.makeGuess('e');
        assertThat(ec.getPattern()).isEqualTo("----");
        assertThat(first).isEqualTo(0);

        int second = ec.makeGuess('o');
        System.out.println(second);
        assertThat(ec.getPattern()).isEqualTo("-oo-");
        assertThat(second).isEqualTo(2);
//
//        int third = ec.makeGuess('t');
//        assertThat(ec.getPattern()).isEqualTo("-oo-");
//        assertThat(third).isEqualTo(0);
//
//        int fourth = ec.makeGuess('d');
//        assertThat(ec.getPattern()).isEqualTo("-oo-");
//        assertThat(fourth).isEqualTo(0);
//
//        int last = ec.makeGuess('c');
//        assertThat(ec.getPattern()).isEqualTo("coo-");
//        assertThat(last).isEqualTo(1);
    }

}
