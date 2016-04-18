/*
 * Author: Lucas Auman
 * Program 5 - MetroCourier
 * CSC230-02 Spring 2016
 */

//used in LinkedStack class
public interface StackInterface<T>{
    public void push(T anEntry);
    public T pop();
    public T peek();
    public boolean isEmpty();
    public void clear();
}