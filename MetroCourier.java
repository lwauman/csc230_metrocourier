/*
 * Author: Lucas Auman
 * Program 5 - MetroCourier
 * CSC230-02 Spring 2016
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.EmptyStackException;
import java.util.Scanner;

public class MetroCourier {
    //holds waybills
    static StackInterface<Waybill> waybillStack = new LinkedStack<>();
    //holds number of waybills
    static int waybillCount=0;
    
    public static void main(String[] args){
        //main method presents menu to the user
        Scanner scan = new Scanner(System.in); //used to read input from the console
        String input = "0"; //default value
        
        
        load();
        
        //continue to present the menu until the user enters 4
        while(!input.equals("4"))
        {
            //print the menu
            System.out.println("********************************************************");
            System.out.println("*                   Metro Courier                      *");
            System.out.println("********************************************************");
            System.out.println("*              Select an option below:                 *");
            System.out.println("* 1 - Read Waybills                                    *");
            System.out.println("* 2 - Immediate Dispatch                               *");
            System.out.println("* 3 - End of Day Dispatch                              *");
            System.out.println("* 4 - Save and Exit                                    *");
            System.out.println("********************************************************");
            System.out.print(">");
            
            //try and read menu selection
            try{
                input = scan.nextLine();
                input = input.trim();
            }
            catch(Exception ex)
            {
                //unknown exception
                System.out.println("Unknown error: " + ex);
                System.exit(1);//exit the program will error of 1
            }
            
            //process menu action and call coresponding method
            switch(input)
            {
                case "1":   readWaybills();
                            break;
                case "2":   disptach();
                            break;
                case "3":   endOfDay();
                            break;
                case "4":   save();
                            break;
                default:    System.out.println("Invalid input!");
                            break;
            }
        }
    } 

    //finds waybill files and adds to stack
    private static void readWaybills()
    {
        Scanner kb = new Scanner(System.in);
        
        System.out.print("Reading waybills...");
        
        //finding folder
        File folder = new File("new_waybills");
        
        //getting all files
        File[] listOfFiles = folder.listFiles();
        
        //creating waybills from all files, adding to stack, and deleting file
        for (File aFile : listOfFiles) {
            try{
                Scanner reader = new Scanner(aFile);
                
                String number = reader.nextLine();
                //gets rid of Waybill: 
                number = number.substring(9, number.length());
                
                String sender = reader.nextLine();
                //gets rid of Sender: 
                sender = sender.substring(8, sender.length());

                String destination = reader.nextLine();
                //gets rid of Destination: 
                destination = destination.substring(13, destination.length());
                
                //create waybill based on gathered info
                Waybill newWayBill = new Waybill(Integer.parseInt(number), destination, sender);
                
                //puts waybill into stack
                waybillStack.push(newWayBill);
                
                //increase number of waybills
                waybillCount++;
                
                //close scanner so file can be deleted
                reader.close();
                
                //delete file
                aFile.delete();
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
                System.out.println("Unable to locate new_waybills folder.");
            }
        }
        System.out.println("done!");
        System.out.println(listOfFiles.length+" waybills read, "+waybillCount+" total waybills ready.");
        System.out.print("Press any key and enter to continue.");
        kb.next();
    }

    //method will dispatch the newest waybill by writing to console and poping waybill
    private static void disptach()
    {
        System.out.println("Dispatching 1 waybill...");
        try{
            //getting top-most waybill
            Waybill onTop= waybillStack.peek();
            
            //writing to console
            System.out.println("Waybill #: "+onTop.getNumber());
            System.out.println("Waybill Address: "+onTop.getDestination());
            
            //removing from stack
            waybillStack.pop();
        }
        catch(EmptyStackException e){
            System.out.println("No waybills to disptach.");
        }
        
        Scanner kb = new Scanner(System.in);
        System.out.print("Press any key and enter to continue.");
        kb.next();
    }

    private static void endOfDay()
    {
        int numberWritten=0;
        //method will queue the waybills to the output directory
        try{
            PrintWriter pw = new PrintWriter("waybill_queue.txt");
            while(!waybillStack.isEmpty()){
                Waybill topOfStack = waybillStack.peek();
                pw.println("Waybill: "+topOfStack.getNumber());
                pw.println("Sender: "+topOfStack.getSender());
                pw.println("Destination: "+topOfStack.getDestination());
                pw.println();
                numberWritten++;
                waybillStack.pop();
            }
            pw.close();
        }catch(FileNotFoundException e){
            System.out.println("Unable to find waybill_queue.txt");
        }
        System.out.println(numberWritten+" outstanding waybills have been queued and sent.");
        
        Scanner kb = new Scanner(System.in);
        System.out.print("Press any key and enter to continue.");
        kb.next();
    }

    //method will save any waybills in memory to saved_waybills, then exit
    private static void save() 
    {
        try{
            PrintWriter pw = new PrintWriter("saved_waybills.obj");
            Waybill[] waybillArray = new Waybill[waybillCount];
            /*
              put all waybills in the newly created waybillArray starting from
              the last postion to the beginning of the array. This is done to 
              maintain proper order of the stack.
            */
            int index = waybillArray.length-1;
            for(int i=0; i<waybillArray.length; i++){
                waybillArray[index] = waybillStack.peek();
                index--;
                waybillStack.pop();
            }
            //writing to file
            for(int i=0; i<waybillArray.length; i++){
                pw.println(waybillArray[i].getNumber());
                pw.println(waybillArray[i].getSender());
                pw.println(waybillArray[i].getDestination());
            }  
            pw.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Unable to locate saved_waybills.obj");
        }
        catch(EmptyStackException e){}
        System.out.println("Saved. Goodbye!");
    }
    
    //reading from saved file
    private static void load(){
        try{
            File saved = new File("saved_waybills.obj");
            Scanner reader = new Scanner(saved);
            while(reader.hasNext()){
                String number = reader.nextLine();

                String sender = reader.nextLine();

                String destination = reader.nextLine();

                //creating waybill with gathered info
                Waybill newWayBill = new Waybill(Integer.parseInt(number), destination, sender);
                waybillStack.push(newWayBill);
                waybillCount++;
            }
        }
        catch(FileNotFoundException e){
            //It is fine if no file is found
        }
    }
}
