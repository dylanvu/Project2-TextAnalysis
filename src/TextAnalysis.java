import java.io.*;
import java.util.*;

public class TextAnalysis {
    public static void main(String[] args)
            throws FileNotFoundException {
        // store directory paths into File variable to loop over
        File worksByAusten = new File("./data/austen/");
        File worksByBronte = new File("./data/bronte/");

        printIntro();

        // Find unique words in works of both authors
        Set<String> uniqueWordsInWorksByAusten = getUniqueWords(worksByAusten);
        Set<String> uniqueWordsInWorksByBronte = getUniqueWords(worksByBronte);

        // Find unique words in Bronte's work not used in Austen's works
        uniqueWordsInWorksByBronte.removeAll(uniqueWordsInWorksByAusten);

        // Create map of word count using set values(String) as keys
        Map<String, Integer> wordCountMap = convertSetToMapWithValue0(uniqueWordsInWorksByBronte);

        // Increment values based on .txt files
        wordCountMap = getWordCount(wordCountMap, worksByBronte);

        // Print sorted list of hashMap values
        printResults(convertHashMapToList(wordCountMap));


    }

    public static void printIntro() {
        System.out.println("This program displays the top 10 words and");
        System.out.println("number of times they occur in books by Charlotte Bronte");
        System.out.println("but not used by Jane Austen");
        System.out.println();
    }

    public static Set<String> getUniqueWords(File dir)
            throws FileNotFoundException {
        Set<String> uniqueWords = new HashSet<String>();

        // Loop over all files in given directory
        for (File file : dir.listFiles()) {
            // check for .txt extension
            if (file.getName().endsWith(".txt")) {
                Scanner input = new Scanner(file);

                // parse words out of String
                input.useDelimiter("[^a-zA-Z']+");

                while (input.hasNext()) {
                    // toLowerCase to prevent duplicate keys of same word but different case
                    String word = input.next().toLowerCase();
                    uniqueWords.add(word);
                }
            }
        }

        return uniqueWords;
    }

    // Create a wordCountMap with all values = 0 for each key
    public static Map<String, Integer> convertSetToMapWithValue0(Set<String> set) {
        Map<String, Integer> map = new TreeMap<String, Integer>();

        // Set values for all new keys 0
        for (String s : set) {
            map.put(s, 0);
        }

        return map;
    }

    // Increment values in passed Map based on inputted .txt files
    public static Map<String, Integer> getWordCount(Map<String, Integer> wordCountMap, File dir)
            throws FileNotFoundException {

        // Loop over all files in given directory
        for(File file : dir.listFiles()) {
            // check for .txt extension
            if (file.getName().endsWith(".txt")) {
                Scanner input = new Scanner(file);

                // parse words out of String
                input.useDelimiter("[^a-zA-Z']+");

                while (input.hasNext()) {
                    // toLowerCase to prevent duplicate keys of same word but different case
                    String word = input.next().toLowerCase();

                    if (wordCountMap.containsKey(word)) {
                        int count = wordCountMap.get(word) + 1;
                        wordCountMap.put(word, count);
                    }
                }
            }
        }

        return wordCountMap;
    }

    // Convert HashMap into list of Entries to sort by value
    public static List<Map.Entry<String, Integer>> convertHashMapToList(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());

        // Implemented value comparator to sort entries based on values
        Collections.sort(listOfEntries, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return listOfEntries;
    }

    public static void printResults(List<Map.Entry<String, Integer>> list) {
        System.out.println("Top 10 Unique words in Bronte's works vs. Austen");
        for (int i = 0; i < 10; i++) {
            String word = list.get(i).getKey();
            int count = list.get(i).getValue();
            System.out.println(word + " appeared " + count + " times");
        }
    }
}
