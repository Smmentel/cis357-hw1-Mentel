//Homework 1: Sales Register Program
//Course: CIS357
//Due date: 7-5-2022
//Name: Sarah Mentel
//GitHub: cis357-hw1-Mentel
//Insructor: Il-Hyung Cho
//Program Description: Cash register emulation, able to read item data from a file and conduct transactions for the user

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

/** main method where item transactions occur*/
public class CashRegister {

    public static void main(String[] args) throws IOException{

        //Create file instance
        File file = new File("/Users/sarahmentel/Desktop/Items.txt");

        //Create scanner for the file
        Scanner fileInput = new Scanner(file);

        //Read item data from the file
        Item[] items = new Item[10];

        //Create history array to hold past transactions
        ItemHistory [] history = new ItemHistory[10];

        int historyIndex = 0;

        //counter variable for items array
        int count = 0;

        int totalSale = 0;


        //read data from file
        while(fileInput.hasNext()) {

            int code = fileInput.nextInt();

            fileInput.nextLine();

            String name = fileInput.nextLine();

            double unitPrice = fileInput.nextDouble();

            items[count++] = new Item(code, name, unitPrice);

        }


        //Begin sales by prompting user
        System.out.print("Beginning a new sale? (Y/N) ");

        //create scanner and variable to read and hold user input
        Scanner input = new Scanner(System.in);

        String userInput = input.next();

        //repeat process until user is finished
        while(userInput.equalsIgnoreCase("y")) {

            System.out.println("-------------");

            //method to compute sale with user
            newSale(items, history, historyIndex, totalSale);

            //formatting and prompting user
            System.out.println();

            System.out.println("-------------");

            System.out.print("Beginning a new sale? (Y/N) ");

            userInput = input.next();
        }

        //determine if the user is finished
        if (userInput.equalsIgnoreCase("n")){

            System.out.println("The total sale for the day is $ " + totalSale);
        }
    }

    /** A method to perform a sale with the user */
    public static void newSale(Item[] items, ItemHistory[] history, int historyIndex, int totalSale) {

        Scanner input = new Scanner(System.in);

        //determine item user wants to purchase
        System.out.print("Enter item code: ");

        int itemIndex = 0;

        //ensure user input is integer
        try { itemIndex = (input.nextInt() - 1); }

        catch (InputMismatchException x) {

            System.out.println("Invalid item code, please enter an integer between 1 and 10.");

            System.out.println();

        }

        // continue until the user enters -1, which denotes end of transactions
        while(itemIndex != -2) {

            //ensure that the user input is a valid integer
            if(itemIndex >= 0 && itemIndex < 10) {

                String itemName = items[itemIndex].name;

                System.out.print("Item name:       " + itemName);

                System.out.println();


                //prompt user for quantity of items
                System.out.print("Enter quantity:  ");

                int quantity = 0;

                //ensure it is valid input
                try {quantity = input.nextInt(); }

                catch(InputMismatchException invalidQuantity) {

                    System.out.println("Invalid item quantity, please enter an integer.");

                }

                //determine total cost of items
                double itemTotal = items[itemIndex].unitPrice * quantity;

                //hold value of total sale
                totalSale += itemTotal;

                //store value of purchased item in history
                history[historyIndex] = new ItemHistory(items[itemIndex], quantity);

                historyIndex++;

                System.out.println("Item total:     $" + itemTotal);

                System.out.println();

            }
            //if input is not in valid product range, inform user and allow them to try again
            else {

                System.out.println("Invalid item code, please enter an integer number between 1 - 10.");

                System.out.println();

                newSale(items, history, historyIndex, totalSale);
            }

            System.out.println();

            System.out.print("Enter item code: ");

            //ensure item code is valid input
            try { itemIndex = (input.nextInt() - 1);}

            catch (InputMismatchException x) {

                System.out.println("Invalid item code, please enter an integer between 1 and 10.");

                System.out.println();
            }
        }

        displayTransactionTotal(history, historyIndex);

    }

    public static void displayTransactionTotal(ItemHistory[] history, int historyIndex) {

        //formatting
        System.out.println("--------------");

        System.out.println("Item list: ");

        double subtotal = 0;

        int count = 0;

        for(int i = 0; i <  historyIndex; i++) {

            System.out.println(history[i].getQuantity() + " " + history[i].getName()

                    + " $" + (history[i].getQuantity() * history[i].getPrice())) ;

            subtotal += (history[i].getQuantity() * history[i].getPrice());
        }

        System.out.println("Subtotal: $ " + subtotal);

        double total = ((int)((subtotal + (subtotal * 0.006)) * 100))/ 100.0;

        System.out.println("Total with Tax (%6): $" + total);

        System.out.println();

        System.out.print("Tendered Amount: ");

        Scanner input = new Scanner(System.in);

        double usersMoney = input.nextDouble();

        System.out.println("Change: $" + ((int)((usersMoney - total) * 100)) / 100.0);

    }
}

