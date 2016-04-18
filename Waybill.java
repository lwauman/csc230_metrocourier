/*
 * Author: Lucas Auman
 * Program 5 - MetroCourier
 * CSC230-02 Spring 2016
 */

public class Waybill{
    //variables to hold info
    private int waybillNumber;
    private String toAddress, fromAddress;
    
    //constructor. sets all values to 0 or null
    public Waybill(){
        waybillNumber = 0;
        toAddress = null;
        fromAddress = null;
    }
    
    //constructor that sets values to passed arguments
    public Waybill(int number, String reciever, String sender){
        waybillNumber = number;
        toAddress = reciever;
        fromAddress = sender;
    }
    
    //return waybillNumber
    public int getNumber(){
        return waybillNumber;
    }
    
    //return toAddress
    public String getDestination(){
        return toAddress;
    }
    
    //return fromAddress
    public String getSender(){
        return fromAddress;
    }
    
    //display waybill as string
    @Override
    public String toString(){
        return "Waybill: "+getNumber()+
                "\nSender: "+getSender()+
                "\nDestination: "+getDestination();
    }
}