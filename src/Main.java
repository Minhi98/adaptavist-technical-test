import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Path argument needs to be provided! \"java executable.java [path of inputted file]\"");
            System.exit(1);
        }
        WordsCounter WordCounter = new WordsCounter(args[0]);
        WordCounter.printWords();
    }

    public static class WordsCounter {

        final String pattern = "[!?,. ]";
        public String corpus = "";
        HashMap<String, Long> countMap = new HashMap<>();
        LinkedHashMap<String, Long> sortedCounts = new LinkedHashMap<>();

        /**
         * @param path readable text filepath (assumes that initialisation is with an intent to process the file)
         */
        public WordsCounter (String path) {
            processNewFile(path);
        }

        /**
         * In case of a new filepath being passed, internal variables should be reset
         */
        private void resetInternalVariables() {
            corpus = "";
            countMap = new HashMap<>();
            sortedCounts = new LinkedHashMap<>();
        }

        /**
         * Given a path it reads the file and assigns as a single-line lowercase string (this.corpus)
         * @param path readable text filepath
         */
        private void readFile(String path) {
            try {
                File input_file = new File(path);
                Scanner input_scanner = new Scanner(input_file);

                while (input_scanner.hasNextLine()) {
                    String data  = input_scanner.nextLine();
                    corpus = corpus.concat(" " + data);
                }

                corpus = corpus.toLowerCase();
                input_scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("The file was not found.");
                System.exit(1);
            }
        }

        /**
         * Takes the existing corpus, creates the set of all words as keys and counts occurrences of that key word.
         */
        private void words() {
            List<String> wordList = new ArrayList<String>(Arrays.asList(this.corpus.split(this.pattern)));
            wordList.removeAll(Arrays.asList("", null));

            Set<String> wordSet = new HashSet<>(wordList);

            for (String word : wordSet) {
                long occurrences = Pattern.compile("\\b" + Pattern.quote(word) + "\\b")
                        .matcher(corpus)
                        .results()
                        .count();
                countMap.put(word, occurrences);
            }
            System.out.println(countMap);
        }

        /**
         * Produces the final map of word occurrences as a LinkedHashMap in descending order
         */
        private void sortMap() {
            ArrayList<Long> occurrences = new ArrayList<>();
            for (Map.Entry<String, Long> entry : countMap.entrySet()) {
                occurrences.add(entry.getValue());
            }
            Collections.sort(occurrences, Collections.reverseOrder());

            for (long num : occurrences) {
                for (Entry<String, Long> entry : countMap.entrySet()) {
                    if (entry.getValue().equals(num)) {
                        sortedCounts.put(entry.getKey(), num);
                    }
                }
            }
        }

        /**
         * Outputs sortedCounts for printing in the below format:
         * foo: 24
         * bar: 17
         * ...
         */
        public void printWords() {
            for (Entry<String, Long> entry : sortedCounts.entrySet()) {
                System.out.printf("%s: %d%n", entry.getKey(), entry.getValue());
            }
        }

        public void processNewFile(String path) {
            resetInternalVariables();
            readFile(path);
            words();
            sortMap();
        }

    }

}