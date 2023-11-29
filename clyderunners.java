/* the program is console based app for glasgow clyde runners club. It takes an external file
with the runners time, name and extracts the information to an 2D array and display it to the user.
The user will first need to login into the app with correct password and then will be shown menu screen.
Based on the user choice the program will carry out the intended task and program will run until the user
decides to leave the app.
*/


import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException; //import java classes
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class clyderunners{

    public static File fileName;// declared global file object
    public static Scanner in = new Scanner(System.in); // create scanner object which can be used for readfile () and in the rest of the program to take user input
    public static PrintWriter out; //create global PrintWriter object to create and write to text file

    // create global arrays which can be seen and accessed by all methods
    public static String[][] result = new String[16][3]; // 2D arrays used to store data from the recorded clyde runner file
    public static int[] sortedTimeSet = new int[16]; // 1D array to store the sorted recorded time in seconds
    public static int[] unsortedTimeSet = new int[16]; // 1D array to store the unsorted recorded time in seconds and this is because so i can match the highest or smallest score with runners name.
    public static String[] firstName = new String[16];// 1D array to store the first name of finalist
    public static String[] lastName = new String[16]; // 1D array to store the last name of the finalist
   
    public static void main(String[] args) throws FileNotFoundException { // checked exceptions must be handled for reading to and writting to file
        login();
    }
    public static void login() throws FileNotFoundException {
        String password = "clyderunners"; // set the value of the password
        int fail = 3; // set value for number of fail attempts allowed

        //use a do while loop to allow user to attempt to gain access to clyde runner app

        do {
                System.out.println("Welcome to the Glasgow clyde runner app");
                System.out.println("Please enter the password to gain access to the app");
                String login = in.nextLine();
                if(login.equals(password)){
                    System.out.println("Password Validated\n");
                    menu(); // if the password is validated go to the menu screen
                }else {
                    // if password not validated take one away from the fail and repeat the loop
                    fail--;
                    System.out.println("Your password was incorrect"); // print message to the user
                    System.out.println("you have " + fail + " attempts left.");

                }

        } while(fail != 0); // set condition for the loop.

         System.out.println("Numbers of attempt exceeded. You are now locked out");
         System.exit(0); 

    }


    public static void menu() throws FileNotFoundException {

        int option;

        do {
            System.out.println("Glasgow Clyde Runner App");
            System.out.println("Please choose from the following options:");
            System.out.println("1. Read and Display File");
            System.out.println("2. Sort and Print Recorded Times");
            System.out.println("3. Find and Print Fastest Time ");
            System.out.println("4. Find and Print the Slowest Time ");
            System.out.println("5. Search for recorded time");
            System.out.println("6. Time Occurrence for recorded time");
            System.out.println("7. Exit ");
            option = in.nextInt();  //capture users menu option

            if(option == 1){
                readFile();
            }else if (option == 2){
                sort();
            }else if (option == 3){
                fastestTime();
            }else if (option == 4){
                slowestTime();
            }else if (option == 5){
                search();
            }else if (option == 6){
                occurrence();
            }
            else if (option == 7){
                in.close(); //close scanner object
                System.out.println();
                System.exit(0);
            }

        }while(option !=7);

    }
    public static void readFile() throws FileNotFoundException{
        
        String nameOfFile = "C:\\Users\\manso\\Documents\\Java\\race results-1.txt";
        fileName  = new File(nameOfFile);
        in = new Scanner(fileName);

        while (in.hasNextLine()){
            for (int i = 0; i < result.length; i++){ // loop through length of result array
                String[] line = in.nextLine().split(" "); // save each line to the string line array by splitting the text whenever their is a space
                for (int j =0; j < line.length; j++){
                    result[i][j] = line[j]; // copy all the value in String line array into 2d array called result
                    
            }
            }
        }
        System.out.println("Clyde runner result are: " + "\n" +Arrays.deepToString(result));
        in.close();//close scanner being used for file input

        in = new Scanner(System.in);//create new instance of scanner for reading in user input i.e. menu choice

        }
    
    public static void sort() throws FileNotFoundException{

        // first before we can sort the information i am going to extract the time into 1D array
        for (int i = 0; i< result.length; i++){ // loop through the result array
            int time = Integer.parseInt(result[i][2]); // convert string at column 3 into an int and store it into num variable
            firstName[i] = result[i][0]; // copy the first name to 1D array -firstName
            lastName[i] = result[i][1]; // copy the last name to 1D array - lastName
            sortedTimeSet[i] = time; // copy the value from num variable into timeSet array
            unsortedTimeSet[i] = time;
        }
    

        // now we will sort the TimeSet array from smallest time to largest using selection sort algorithms

         Arrays.sort(sortedTimeSet);
            System.out.println("the clyde runners time from shortest to the fastest: " + Arrays.toString(sortedTimeSet));

            // now we will output the result into  another txt file
            out = new PrintWriter("C://Users//manso//Documents//Java//sortedrunnersTime.txt"); 

            for (int i = 0; i < sortedTimeSet.length; i++){ // we will loop through the entire length of the array and add each number to the txt file
                out.println(sortedTimeSet[i]);
            }
            out.close(); // to ensure all the data is written to the file and their isnt any loss of data

        }
    public static void fastestTime() throws FileNotFoundException{


        String studentFName = ""; // store the students first name who has the fastest time
        String studentLName = ""; // store the student last name who has the fastest time

        int fastestTime = unsortedTimeSet[0];// 

        for (int i = 1; i< unsortedTimeSet.length; i++){// loop through the length of timeSet array to find the fastest time 
            if(unsortedTimeSet[i] > fastestTime){ 
                fastestTime = unsortedTimeSet[i]; // store the fastest time in the variable fastestTime by comparing with each finalist run time
                studentFName = firstName[i];// match the sudent with the fastest time to first name of the runner
                studentLName = lastName[i]; // match the sudent with the fastest time to last name of the runner
            }
        }
        System.out.println("the fastest time of the race is: " + studentFName + " " + studentLName +" with the time of " + fastestTime +" seconds.");

        out = new PrintWriter("C://Users//manso//Documents//Java//fastestTime.txt");// location of the output .txtfile

        out.println("Name: " + studentFName + "  " + studentLName + " with the time of " + fastestTime);// print the value of the fastest time to output file
        out.close();// close filewriter
        
    }

    public static void slowestTime() throws FileNotFoundException{

        //timeSet[] holds all the finalist run time{70,90,75,70,95,103,80,110,68,120,80,140,90,72,78,97}
        
        String studentFName = ""; 
        String studentLName = ""; 

        System.out.println(Arrays.toString(unsortedTimeSet));
        int slowest = unsortedTimeSet[0];// initally set the smallest time to an int variable as this way we can compare and keep track of the smallest value.

        for(int i = 0; i < unsortedTimeSet.length; i++){ // loop through the length of timeSet array to find the smallest value
            if(unsortedTimeSet[i] < slowest){
                slowest = unsortedTimeSet[i]; // store the slowest time by comparing it with each finalist run time.
                studentFName =firstName[i]; // match the sudent with the slowest time to first name of the runner
                studentLName =lastName[i]; // match the sudent with the slowest time to last name of the runner
            }
            }
        
        System.out.println("the slowest time of the race is: " + studentFName + " " + studentLName +" with the time of " + slowest +" seconds.");
        out = new PrintWriter("C://Users//manso//Documents//Java//slowestTime.txt");
        out.println("Name: " + studentFName + "  " + studentLName + " with the time of " + slowest);
        out.close();

    }

    public static void search() throws FileNotFoundException{
        boolean valid = false;
        int input = 0;
        do {
          try{  System.out.println("please enter the time you are looking for in in seconds i.e 23,10 : ");
                 input = in.nextInt();
                 valid = true;

            }catch(InputMismatchException e){
                System.out.println("invalid input please enter whole number only ");
                valid = false;
                in.next();// removes the mismatch token so that it doesnt go into an infinite loop.
            }
            

        }while(!valid);

        int value = linearSearch(sortedTimeSet, input);// pass in the timeSet array and user input to linearSearch method

        out = new PrintWriter("C://Users//manso//Documents//Java//searchResult.txt");

            if(value != -1){ // if the time is found then display the message and copy the input to the another file
                System.out.println("The time has been found: " + value);
                out.println(input);
                out.close();
            }
            else{
                System.out.println("The time has not been found");
                out.println("The time has not been found");
                out.close();
            }       

    }

    public static int linearSearch(int array[], int input) throws FileNotFoundException{

        for (int i = 0; i< sortedTimeSet.length; i++){ // loop through the time array length and return the result if found
            if(input == sortedTimeSet[i]){
                return input;
            }
        }
        return -1; // if no result found then return -1 
        
    }


    public static void occurrence() throws FileNotFoundException{

        System.out.println("please enter a time to see how many times it appeared in the race, i.e 20: ");
        int input = in.nextInt();
        int count = 0; // keep track of number of times the time appeared in the race

        for (int i = 0; i < sortedTimeSet.length; i++){ // loop through the entire time array to see if the search time appeared
            if (input == sortedTimeSet[i]) {

                count++; // if found then increase the counter by 1
            }
        out = new PrintWriter("C://Users//manso//Documents//Java//occurrence.txt"); // location and file name of the txt file to be saved 

        out.println("Numbers of times " + input + "appreared in the race is " + count);
        out.close();

        

        }
        System.out.println("the number of the times " + input + " appeared in the race is : " + count + " times");


    }
    
    }
