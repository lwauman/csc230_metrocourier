/*
 * Author: Lucas Auman
 * Program 5 - MetroCourier
 * CSC230-02 Spring 2016
 */

import java.util.EmptyStackException;

public class LinkedStack<T> implements StackInterface<T>{
    private Node topNode;
    
    //constructor. set topNode to null
    public LinkedStack(){
        topNode = null;
    }

    //add a node with passed argument to stack
    @Override
    public void push(T anEntry) {
        //create new node with anEntry data and set the nextNode to be the 
        //node that was already in the stack if there was one
        Node newNode = new Node(anEntry, topNode);
        topNode = newNode;
    }

    //remove top node from stack
    @Override
    public T pop() {
        T toReturn = peek();
        assert toReturn != null;
        topNode = topNode.getNextNode();
        return toReturn;
    }

    //look at top of stack
    @Override
    public T peek() {
        if(isEmpty())
            throw new EmptyStackException();
        else
            return topNode.getData();
    }

    //return if stack is empty or not
    @Override
    public boolean isEmpty() {
      return topNode == null;
    }

    //clear the stack
    @Override
    public void clear() {
       topNode = null;   
    }
    
    //inner node class
    private class Node{
        private T data;
        private Node next;
        
        public Node(T anEntry){
            this(anEntry, null);
        }
        public Node(T anEntry, Node nextNode){
            data = anEntry;
            next = nextNode;
        }
        public T getData(){
            return data;
        }
        public Node getNextNode(){
            return next;
        }
        public void setData(T anEntry){
            data=anEntry;
        }
        public void setNextNode(Node nextNode){
            next = nextNode;
        } 
    }
}