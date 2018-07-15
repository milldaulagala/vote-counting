import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class - Reads candidate names and count each candidate preference
 */
public class ReadFileToListSample {

    private static HashMap<Character, String> candidateNames = new HashMap<>();
    private static HashMap<Character, ArrayList> candidateVotes = new HashMap<>();
    private static int numberOfCandidates = 0;
    private static int counter = 1;

    /**
     * This method initiates the voting system
     *
     * @param args main methods argument
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        readCandidateName("/Users/macos/stv/Ballet/src/candidates.txt");
        printCandidates();
        arrangeVoteStructure();
        outputVotes();
        System.out.println("tally");
    }

    /**
     * This method reads candidate names from the file and create maps & lists
     *
     * @param path candidate name text file location
     * @throws IOException
     */
    private static void readCandidateName(String path) throws IOException {

        File file = new File(path);

        List<String> contents = FileUtils.readLines(file);
        char alphabet;

        for (alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            // Iterate the result to print each line of the file.
            for (String line : contents) {
                candidateNames.put(alphabet, line);
                candidateVotes.put(alphabet, new ArrayList());
                numberOfCandidates++;
                alphabet++;

            }
            break;
        }
    }

    /**
     * This method add vote preference for each candidate
     */
    private static void arrangeVoteStructure() {

        System.out.println("Please enter the vote preference as a sequence: > ");
        Scanner in = new Scanner(System.in);

        String voteKey = in.nextLine();

        // split by vote key letter
        String votes[] = voteKey.split(" ");


        for (int x = 0; x < numberOfCandidates; x++) {
            for (int y = x; y < votes.length; y++) {
                candidateVotes.get(votes[y].charAt(0)).add(counter);
                counter++;
                break;
            }
        }
    }

    private static void outputVotes() {
        Iterator<Map.Entry<Character, ArrayList>> it = candidateVotes.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry<Character, ArrayList> next = it.next();
            System.out.println(next.getKey() +"->"+
                    Integer.parseInt(next.getValue().toString().replace("[","").replace("]","")));
        }
    }//get key

    private static void printCandidates(){
        Iterator<Map.Entry<Character, String>> it = candidateNames.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry<Character, String> next = it.next();
            System.out.println(next.getKey() +"."+" "+ next.getValue());
        }

    }
}