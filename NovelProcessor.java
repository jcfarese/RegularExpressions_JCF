/**
 * Description of this class: NovelProcessor.class processes a novel.txt againts a regex file to create a word_count.txt that gives the amount of times a specifc word appears in the novel
 * @author Jim Farese
 * @version 1.0
 * Assignment 4
 * CS322 - Compiler Construction
 * Spring 2024
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.*;

public class NovelProcessor {

    /**
     * Retrieves the novel from its file.
     * 
     * @param novelFileName The file name of the novel.
     * @return The name of the novel without the file extension, converted to lowercase.
     */
    private static String getNovelName(String novelFileName) {

        File novelFile = new File(novelFileName);
        String fileName = novelFile.getName();
        fileName = fileName.replaceAll("\\.txt$", "");
        return fileName.replaceAll("^a-zA-Z", "").toLowerCase();
    }


    /**
     * Writes the output of regEx counts to a text file.
     * 
     * @param novelName The name of the novel.
     * @param regExCount A map containing regex patterns and their corresponding counts.
     * @param regExOrder A list containing the order of regex patterns.
     * @throws IOException If an I/O error occurs while writing the output file.
     */
    private static void writeOutputFile(String novelName, Map<String, Integer> regExCount, List<String> regExOrder) throws IOException {

        String outputFile = capitalizeFirstLetter(novelName) + "_wc.txt";

        try(PrintWriter writer = new PrintWriter(new FileWriter(outputFile))){
            for(String regExString : regExOrder){
                writer.println(regExString + "|" + regExCount.get(regExString));
            }
        
        }

        System.out.println("Processing complete.  File saved to " + outputFile);
    }


    /**
     * Capitalizes the first letter of a string.
     * 
     * @param string The string to capitalize.
     * @return The string with the first letter capitalized.
     */
    private static String capitalizeFirstLetter(String string){
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }


    /**
     * Processes the novel text file based on provided regex patterns.
     * 
     * @param novelFileName The file name of the novel text file.
     * @param regExFileName The file name of the regex patterns file.
     * @throws IOException If an I/O error occurs while reading the novel or regex file.
     */
    private static void processNovel(String novelFileName, String regExFileName) throws IOException {

        String novelName = getNovelName(novelFileName);
        Map<String, Integer> regExCount = new HashMap<>();
        List<String> regExOrder = new ArrayList<>();

        try(Scanner regExScan = new Scanner(new File(regExFileName))){
            while (regExScan.hasNextLine()){
                String regExString = regExScan.nextLine().trim();
                Pattern regEx = Pattern.compile(regExString);
                int count = 0;

                try(Scanner novelScan = new Scanner(new File(novelFileName))){

                    while(novelScan.hasNextLine()){
                        String novelString = novelScan.nextLine();
                        Matcher match = regEx.matcher(novelString);

                        while(match.find()){
                            count++;
                        }
                    }
                    
                }

                regExCount.put(regExString, count);
                regExOrder.add(regExString);

            }

        }

        writeOutputFile(novelName, regExCount, regExOrder);

    }

    
    /**
     * The main method of the program.
     * 
     * @param args Command-line arguments. Expected format: 'java NovelProcessor novel_file.txt regex_file.txt'.
     */
    public static void main(String[] args){

        if (args.length !=2){
            System.out.println("Error processing arguments.  Please use format as 'java NovelProcessor novel_file.txt regex_file.txt'");
            System.exit(1);
        }

        String novelFileName = args[0];
        String regExFileName = args[1];

        System.out.println("Processing...");

        try{
            processNovel(novelFileName, regExFileName);
        } catch(IOException e){
            System.err.println("Error" + e.getMessage());
        }

    }

}