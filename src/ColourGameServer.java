import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * ColourGameServer
 * server for the game to connect to for level uploading
 */
class ColourGameServer {
  
  private ServerSocket serverSock;// server socket for connection
  private static Boolean running = true; // controls if the server is accepting clients
  private HashMap<String, File> fileMap = new HashMap<>();
  private Set<String> fileSet = fileMap.keySet();
  /**
   * Main
   * @param args parameters from command line
   */
  public static void main(String[] args) {
    new ColourGameServer().go(); // start the server
  }

  /**
   * go 
   * Starts the server
   */
  public void go() {
    System.out.println("Waiting for a client connection..");
    Socket client = null;// hold the client connection
    try {
      serverSock = new ServerSocket(5000); // assigns an port to the server
      while (running) {  // this loops to accept multiple clients
        client = serverSock.accept(); // wait for connection
        System.out.println("Client connected");
        Thread t = new Thread(new ConnectionHandler(client)); // create a thread for the new client and pass in the socket
        t.start(); // start the new thread
      }
    } catch (Exception e) {
      System.out.println("Error accepting connection");
      e.printStackTrace();
      // close all and quit
      try {
        client.close();
      } catch (Exception e1) {
        System.out.println("Failed to close socket");
      }
      System.exit(-1);
    }
  }

  /**
   * ConnectionHandler
   * class made as custom thread to handle connections and updates to and from clientside
   */
  class ConnectionHandler implements Runnable {
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket client; // keeps track of the client socket
    private boolean running;

    /*
     * ConnectionHandler Constructor
     * constructor for thread
     * @param s the messagehandler socket belonging to this client connection
     * @param updateSocket the clientupdater socket belonging to this client connection
     */
    ConnectionHandler(Socket s) {
      this.client = s; // constructor assigns client to this
      //this.updateSocket = updateSocket;
      try { // Creates streams
        InputStream inputStream = s.getInputStream();
        input = new ObjectInputStream(inputStream);
        OutputStream outputStream = s.getOutputStream();
        output = new ObjectOutputStream(outputStream);
      } catch (IOException e) {
        e.printStackTrace();
      }
      running = true;
    } // end of constructor

    /*
     * run
     * executed on start of thread
     */
    public void run() {

      // Get a message from the client
      while (running) { // loop until a message is received
        try {
          Object o = input.readObject();
          if(o.equals("/UPLOAD!")) {
            Random random = new Random();
            String tempKey = "";
            do {
              for(int i = 0; i < 5; i++) {
                tempKey = tempKey + (char)(random.nextInt(25) + 65);
              }
            } while(fileSet.contains(tempKey));
            File tempMap = new File("../serverMaps/gameMap_" + tempKey + ".txt");
            if(!tempMap.exists()) {
              tempMap.createNewFile();
            }
            byte[] fileBytes = (byte[])input.readObject();
            Files.write(tempMap.toPath(), fileBytes);
            fileMap.put(tempKey, tempMap);
            output.writeObject(tempKey);
          }
          else if(o.equals("/DOWNLOAD!")) {
            String code = (String)input.readObject();
            if(fileSet.contains(code)) {
              output.writeObject(true);
              File sentFile = fileMap.get(code);
              byte[] fileBytes = Files.readAllBytes(sentFile.toPath());
              output.writeObject(fileBytes);
            }
            else {
              output.writeObject(false);
            }
          }
          
        } catch(IOException e) {
          System.out.println("IOException occured");
          e.printStackTrace();
        } catch(ClassNotFoundException e2) {
          System.out.println("Class not found");
        }
        running = false;
      }

      // close the socket and other items
      try {
        input.close();
        output.close();
        client.close();
      } catch (IOException e) {
        System.out.println("Failed to close socket");
      }
    } // end of run()
  } // end of inner class


} // end of Class