import java.io.*;
import java.util.*;

public class TextAnalysis {
    public static void main(String[] args)
            throws FileNotFoundException {
        // Store directory paths into File variable to loop over
        File worksByAusten = new File("./data/austen/");
        File worksByBronte = new File("./data/bronte/");

        printIntro();

        // Increment values based on .txt files
        Map<String, Integer> austenWordCount = getWordCount(worksByAusten);
        Map<String, Integer> bronteWordCount = getWordCount(worksByBronte, austenWordCount);

        // Print sorted list of hashMap values
        printResults(convertHashMapToList(bronteWordCount));
    }

    private static void printIntro() {
        System.out.println("This program displays the top 10 words and");
        System.out.println("number of times they occur in books by Charlotte Bronte");
        System.out.println("but not used by Jane Austen");
        System.out.println();
    }

    // Increment values in passed Map based on inputted .txt files
    private static Map<String, Integer> getWordCount(File dir)
            throws FileNotFoundException {

        Map<String, Integer> wordCountMap = new HashMap<>();
        File[] files = dir.listFiles();

        if (files == null) {
            System.out.println("No files in directory");
        }
        else {
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

                        if (wordCountMap.containsKey(word)) {
                            int count = wordCountMap.get(word) + 1;
                            wordCountMap.put(word, count);
                        } else {
                            wordCountMap.put(word, 1);
                        }
                    }
                }
            }
        }

        return wordCountMap;
    }

    // Overload getWordCount to create map with values unique to one data set
    private static Map<String, Integer> getWordCount(File dir, Map<String, Integer> mapToCompare)
            throws FileNotFoundException {

        Map<String, Integer> wordCountMap = new HashMap<>();
        File[] files = dir.listFiles();

        if (files == null) {
            System.out.println("No files in directory");
        }
        else {
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

                        if(wordCountMap.containsKey(word)) {
                            int count = wordCountMap.get(word) + 1;
                            wordCountMap.put(word, count);
                        }
                        // create new key only if word does not exist in both maps
                        else if (!wordCountMap.containsKey(word) && !mapToCompare.containsKey(word)) {
                            wordCountMap.put(word, 1);
                        }
                    }
                }
            }
        }

        return wordCountMap;
    }

    // Convert HashMap into list of Entries to sort by value
    private static List<Map.Entry<String, Integer>> convertHashMapToList(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>(map.entrySet());

        // Implemented value comparator to sort entries based on values
        Collections.sort(listOfEntries, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return listOfEntries;
    }

    private static void printResults(List<Map.Entry<String, Integer>> list) {
        System.out.println("Top 10 Unique words in Bronte's works vs. Austen:");
        System.out.printf("%-16s%s", "\tWORD" , "OCCURRENCE\n");
        System.out.println("\t-------------------------");
        for (int i = 0; i < 10; i++) {
            String word = list.get(i).getKey();
            int count = list.get(i).getValue();
            System.out.printf("%-16s%s", "\t" + word, count + "\n");
        }
    }
}
