/**
 * Description of this class:  The class reads files with word counts in the format "regex|count" and adds the counts for each regex pattern.
 * It then prints the total counts for each regex pattern.
 * @author Jim Farese
 * @version 1.0
 * Assignment 
 * CS322 - Compiler Construction
 * Spring 2024
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.LinkedHashMap;


public class WordCounter {

    /**
     * Processes word count files and adds the counts based on regular expressions.
     * 
     * @param file The file containing word counts in the format "regex|count".
     * @param regExCounts A map to store counts for each regex pattern.
     * @throws FileNotFoundException If the specified file is not found.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    private static void processWCFiles(File file, Map<String, Integer> regExCounts) throws FileNotFoundException, IOException{
        
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                int splitRegEx = line.lastIndexOf("|");
                String regEx = line.substring(0, splitRegEx);
                int count = Integer.parseInt(line.substring(splitRegEx+1));
                regExCounts.put(regEx, regExCounts.getOrDefault(regEx, 0) + count);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }


    /**
     * Main method of the WordCounter program.
     * 
     * Reads word count files in the current directory, adds the counts based on regular expressions,
     * and prints the total counts for each regex pattern.
     * 
     * @param args Command-line arguments.
     * @throws FileNotFoundException If a word count file is not found.
     * @throws IOException If an I/O error occurs while reading a word count file.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        Map<String, Integer> regExCounts = new LinkedHashMap<>();
        File dir = new File(".");
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile() && file.getName().endsWith("_wc.txt")){
                processWCFiles(file, regExCounts);
            }
        }

        for(Map.Entry<String, Integer> entry : regExCounts.entrySet()){
            System.out.println(entry.getKey() + "|" + entry.getValue());
        }
        
    }
    
}