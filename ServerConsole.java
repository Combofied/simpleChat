
import java.io.*;
import java.util.Scanner;

import client.ChatClient;
import common.ChatIF;

public class ServerConsole implements ChatIF {
	
	final public static int DEFAULT_PORT = 5555;

	
	EchoServer server;
	
	Scanner fromConsole;

	
	public ServerConsole(int port) 
	  {
	    try 
	    {
	      server = new EchoServer(port, this);
	      //i had connectionEstablished here
	      
	    } 
	    catch(IOException exception) 
	    {
	      System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
	      System.exit(1);
	    }
	    
	    // Create scanner object to read from console
	    fromConsole = new Scanner(System.in); 
	  }
	 public void accept() 
	  {
	    try
	    {

	      String message;

	      while (true) 
	      {
	        message = fromConsole.nextLine();
	        server.sendToAllClients(message);
	        server.echoMessage(message);
	        String[] messagestring = message.split(" ");
	        if(messagestring[0].equals("#quit")) {
	        	server.close();
	        	System.exit(0);
	        }
	        else if(messagestring[0].equals("#stop")) {
	        	server.stopListening();
	        }
	        else if(messagestring[0].equals("#close")) {
	        	server.close();
	        }
	        else if(messagestring[0].equals("#setport")) {
	        	String  result = messagestring[1].replaceAll("[<\\>]","");
	        	int result2 = Integer.parseInt(result);
	        	server.setPort(result2);
	        }
	        else if(messagestring[0].equals("#start")) {
	        	server.listen();
	        }
	        else if(messagestring[0].equals("#getport")) {
	        	System.out.println(server.getPort());
	        }
	        }
	      } 
	    catch (Exception ex) 
	    {
	      System.out.println
	       ("Unexpected error while reading from console!");
	    }
	  }
	  
	
	
	@Override
	public void display(String message) {
		 System.out.println("SERVER MSG> " + message);
	}
	
	public static void main(String[] args) 
	  {
	    String host = "";
	    String stringport = "";
	    int port;
		try
	    {
	      host = args[0];
	      stringport = args[1];
	      port = Integer.parseInt(stringport);
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	      port = DEFAULT_PORT;
	    }
	    ServerConsole chat= new ServerConsole(port);
	    chat.accept();  //Wait for console data
	  }
}
