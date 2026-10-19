package com.mycompany.main;

//Imports Scanner so we can get user input
import java.util.Scanner;

//Creates the Main class
public class Main {
   
    //Creates Scanner for user input
    static Scanner input = new Scanner(System.in);
    
    //Stores the user's entered details
    static String username;
    static String password;
    static String CellPhone;
    
    //Stores the successfully registered details
   
    //Stores the registered username
    static String registeredUsername;
   
    //Stores the registered password
    static String registeredPassword;
   
    //Stores the registered cellphone number
    static String registeredCellPhone;
   
    //Creates the username checking method
    public static boolean checkUserName(String username) {
        //Username must have 5 characters and underscore
        if (username.length() ==5 && username.contains("_")) {
            return true;
        }else {
        return false;  
        } 
    }
           
     // Create the Password checking method
    public static boolean checkPasswordComplexity(String password) {
        //Password must have 8 characters
        if (password.length() >=8
            //Allows password to have uppercase, lowercase, numbers, special characters
            && password.matches(".*[A-Z].*")
            && password.matches(".*[a-z].*")
            && password.matches(".*[0-9].*")
            && password.matches(".*[^a-zA-Z0-9].*")) {
        return true;
    }else {
            return false;
        }
}

    // Create the cellphone checking method
    public static boolean checkCellPhoneNumber(String cellphone) {
        //Number must start with +27 and followed with 9 digits
        if (cellphone.matches("\\+27(0-9) {9}$")) {
            return true;
        }else {
           return false;  
    } 
}    
   
    //Create the registration method
    public static void registerUser(){
        //Ask for Username
        System.out.print("Enter your username: ");
        username = input.nextLine();
        //Ask for password
        System.out.print("Enter your password: ");
        password = input.nextLine();
        //Ask for cellphone number
        System.out.print("Enter your cellPhone number (+27): ");
        CellPhone = input.nextLine();
        
        //Check username
        boolean usernameCorrect = checkUserName(username);
        
        //Check password
        boolean passwordCorrect = checkPasswordComplexity(password);
        
        //Check cellphone number
        boolean phoneCorrect = checkCellPhoneNumber(CellPhone);
        
        //Check if all registration details are correct
        if (usernameCorrect && passwordCorrect && phoneCorrect){
            //Save the valid username
            registeredUsername = username;
            //Save the  valid password
            registeredPassword = password;
            //Save the valid cellphone number
            registeredCellPhone = CellPhone;
            //Display success message
            System.out.println("User registered successfully.");
        }else {
            //Display failure message
            System.out.println("Registration failed.");    
        }
    }
   
    //Creates the login method
    public static boolean loginUser(String username, String password) {
        //Compare entered details with registered details
        if (username.equals(registeredUsername)
                && password.equals(registeredPassword)){
            return true;
        }else {
            return false; 
        }   
    }
   
    //Creates the login status method
    public static String returnLoginStatus(boolean loginSuccessful) {
        //Check if login was successful
        if (loginSuccessful){
            return "Welcome, it is great to see you again.";
        }else {
             return "Username or password incorrect, please try again.";
        }
    }
   
    //Main method where the program starts
    public static void main(String[] args) {
    //Call login method
    boolean loginSuccessful = loginUser(username, password);
    
    //Call login status
    System.out.print(returnLoginStatus(loginSuccessful));

    }
}
    