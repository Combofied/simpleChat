// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import ocsf.server.*;

import common.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
	
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  ChatIF serverUI;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ChatIF serverUI) 
		  throws IOException 
  {
	  super(port);
	  this.serverUI = serverUI;
	    
	    try 
	    {
	      this.listen(); //Start listening for connections
	    } 
	    catch (Exception ex) 
	    {
	    	System.out.println("hello");
	    	this.listeningException(ex);
	      System.out.println("ERROR - Could not listen for clients!");
	    }
	  
  }

  
  //Instance methods ************************************************
  
  /**
   * Hook method called each time a new client connection is
   * accepted. The default implementation does nothing.
   * @param client the connection connected to the client.
   */
  protected void clientConnected(ConnectionToClient client) {
	  
  }

  /**
   * Hook method called each time a client disconnects.
   * The default implementation does nothing. The method
   * may be overridden by subclasses but should remains synchronized.
   *
   * @param client the connection with the client.
   */
  synchronized protected void clientDisconnected(
    ConnectionToClient client) {
	  
  }

  /**
   * Hook method called each time an exception is thrown in a
   * ConnectionToClient thread.
   * The method may be overridden by subclasses but should remains
   * synchronized.
   *
   * @param client the client that raised the exception.
   * @param Throwable the exception thrown.
   */
  synchronized protected void clientException(
    ConnectionToClient client, Throwable exception) {
	  
  }
  /**
   * Hook method called when the server stops accepting
   * connections because an exception has been raised.
   * The default implementation does nothing.
   * This method may be overriden by subclasses.
   *
   * @param exception the exception raised.
   */
  protected void listeningException(Throwable exception) {
	  System.out.println("client has disconnected, have a nice day!");
  }

  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	String msg1 = msg.toString();
	String login = "";
	String[] messagestring = msg1.split(" ");
	if(messagestring[0].equals("#login") && client.getInfo("loginID") == null){
		String  result = messagestring[1].replaceAll("[<\\>]","");
		client.setInfo("loginID", result);
		serverUI.display("Message received: " + msg + " from " + client);
		login = client.getInfo("loginID").toString();
	}
	login = client.getInfo("loginID").toString();
    this.sendToAllClients(login + ": " + msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    serverUI.display
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    serverUI.display
      ("Server has stopped listening for connections.");
  }
  /**
   * Hook method called when the server is clased.
   * The default implementation does nothing. This method may be
   * overriden by subclasses. When the server is closed while still
   * listening, serverStopped() will also be called.
   */
  protected void serverClosed() {
	  serverUI.display("The server has closed");
  }
  
  public void echoMessage(Object msg) {
	  serverUI.display(msg.toString());
  }
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to  on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) {
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
//End of EchoServer class
