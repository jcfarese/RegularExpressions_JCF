/**
 * Description of this class: The class provides functionality to process a log file, extract relevant information, and print results based on specified flags.
 * @author Jim Farese
 * @version 1.0
 * Assignment 4
 * CS322 - Compiler Construction
 * Spring 2024
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileProcessor {
    
    private static Map<String, Integer> uniqueIP;
    private static Map<String, Integer> uniqueUsername;
    private static int linesParsed;


    /**
     * Constructs a LogFileProcessor object and initializes data structures.
     */
    public LogFileProcessor(){

        uniqueIP = new HashMap<>();
        uniqueUsername = new HashMap<>();
        linesParsed = 0;

    }


    /**
     * Getter
     * 
     * @return The size of the unique IP addresses map.
     */
    private static int getUniqueIPSize(){

        return uniqueIP.size();

    }


    /**
     * Getter
     * 
     * @return The size of the unique usernames map.
     */
    private static int getUniqueUsernameSize(){

        return uniqueUsername.size();

    }


    /**
     * Processes the specified log file and extracts unique IP addresses and usernames.
     * 
     * @param file The path to the log file.
     * @param printFlag An integer indicating the print mode desired.
     * @throws FileNotFoundException If the log file is not found.
     * @throws IOException If an I/O error occurs while reading the log file.
     */
    private void processLogFile(String file, int printFlag) throws FileNotFoundException, IOException{

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                processLine(line);
                linesParsed++;

            }

            printResults(printFlag, linesParsed);

        }catch(IOException e){

            e.printStackTrace();

        }

    }


    /**
     * Processes line by line from the log file to extract IP addresses and usernames.
     * 
     * @param line A single line from the log file.
     */
    private static void processLine(String line){

        Pattern ipPattern = Pattern.compile("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");
        Matcher ipMathcer = ipPattern.matcher(line);
        while(ipMathcer.find()){
            String ipAddress = ipMathcer.group();
            uniqueIP.put(ipAddress, uniqueIP.getOrDefault(ipAddress, 0) + 1);
        }

        Pattern usernamePattern = Pattern.compile("user (\\w+)");
        Matcher usernameMathcer = usernamePattern.matcher(line);
        while(usernameMathcer.find()){
            String username = usernameMathcer.group(1);
            uniqueUsername.put(username, uniqueUsername.getOrDefault(username, 0) + 1);
        }

    }


    /**
     * Prints the unique IP addresses and their counts.
     */
    private static void printUniqueIP(){

        System.out.println("IP Address: ");
        for(Map.Entry<String, Integer> entry : uniqueIP.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }


    /**
     * Prints the unique usernames and their counts.
     */
    private static void printUniqueUsername(){

        System.out.println("Username: ");
        for(Map.Entry<String, Integer> entry : uniqueUsername.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }


    /**
     * Prints the results based on the desired print format.
     * 
     * @param printFlag An integer indicating the print flag.
     * @param linesParsed The total number of lines parsed from the log file.
     */
    private static void printResults(int printFlag, int linesParsed){

        switch(printFlag){

            case 0:
                System.out.println("Total number of lines parsed: " + linesParsed);
                System.out.println("There are " + getUniqueIPSize() + " unique address in the log.");
                System.out.println("There are " + getUniqueUsernameSize() + " unique users in the log.");
                break;

            case 1:
                printUniqueIP();
                System.out.println("Total number of lines parsed: " + linesParsed);
                System.out.println("There are " + getUniqueIPSize() + " unique address in the log.");
                System.out.println("There are " + getUniqueUsernameSize() + " unique users in the log.");
                break;

            case 2:
                printUniqueUsername();
                System.out.println("Total number of lines parsed: " + linesParsed);
                System.out.println("There are " + getUniqueIPSize() + " unique address in the log.");
                System.out.println("There are " + getUniqueUsernameSize() + " unique users in the log.");
                break;

            default:
                System.out.println("Total number of lines parsed: " + linesParsed);
                System.out.println("There are " + getUniqueIPSize() + " unique address in the log.");
                System.out.println("There are " + getUniqueUsernameSize() + " unique users in the log.");
                break;
            

        
        }

    }


    /**
     * Main method of the program. Parses command-line arguments and processes the log file.
     * 
     * @param args Command-line arguments. Expected format: 'java LogFileProcessor log_file print_flag'.
     * @throws FileNotFoundException If the log file is not found.
     * @throws IOException If an I/O error occurs while reading the log file.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{

        if(args.length != 2) {
            System.out.println("Error processing arguments.  Please use format as 'java LogFileProcessor log_file print_flag' ");

        }

        System.out.println("Process...");
        String file = args[0];
        int printFlag = Integer.parseInt(args[1]);    
        LogFileProcessor processor = new LogFileProcessor();
        processor.processLogFile(file, printFlag);
    }

}
