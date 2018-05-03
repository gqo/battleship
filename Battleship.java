// Graeme Ferguson, Andrea Vasquez, Reece Rodriguez | Battleship | 05/03/18

import java.io.*;
import java.net.*;
import java.util.*;

public class Battleship {

    public static char[][] board=new char[10][10];
            
            
    public static void printBoard(){
    
        System.out.print("  ");//beginning 2 spaces
        for(int i = 0; i < board.length; ++i){
            System.out.print(" " + (char)(i + 'A') +"  ");//print letters seperately.
        }

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                board[i][j] = '-';  
            }
        }
        for(int i = 0; i < board.length; i++){
            System.out.println();
            for(int j = 0; j < board.length; j++){
                if(j == 0){
                    System.out.print(i);
                    System.out.print("| ");
                }
                System.out.print(board[i][j] + " | ");  
            }
        }
        System.out.println("");
    }   


    static int port = 5190;
    public static void main(String[] args) {
        ServerSocket server = null;
        int uid = 0; // User ID
        try {
            server = new ServerSocket(port);
            // Handles only n-set connections, consider HashMap?
            ArrayList<Connection> connections = new ArrayList<Connection>(2);
            Connection conn = null;
            while(true) {
                System.out.println("Waiting for a connection on port: "+port);
                Socket client = server.accept();
                if(connections.size() != 2) {
                    // Prints user, uid, and user's internet address to server
                    System.out.println("Player "+(++uid)+" @ "
                        +client.getInetAddress().toString()+" connected.");
                    conn = new Connection(client,uid,connections);
                    connections.add(conn);
                    conn.start();
                }
                else {
                    System.out.println("Denyed additional connection to server.");
                    client.close();
                }
            }
        } catch (IOException ex) {
            System.out.println("Error occured in socket creation.");
        }
    }
}

// Class for connections made to the server
class Connection extends Thread {
    Socket client;
    int uid;
    ArrayList<Connection> connections = new ArrayList<Connection>(20);
    PrintStream sout;
    String u_name = "";

    Connection(Socket new_client, int new_uid, ArrayList<Connection> new_conns) {
        client = new_client;
        uid = new_uid;
        connections = new_conns;
        try {
            sout = new PrintStream(client.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error occured in PrintStream creation.");
        }
    }
    
    public void run() {
        try {
            Scanner sin = new Scanner(client.getInputStream());
            String line = "";
            String message = "";

            while(!line.equals("/quit")) { // User can quit by sending "/quit" on the server
                line = sin.nextLine();
                message = "Player"+uid+" said: "+line;
                this.send(message); // Send message to user sending message
                for(int i = 0; i < connections.size(); i++) { // Sends message to all users
                    if(i != uid-1) { connections.get(i).send(message); }
                }
                // Prints user sent messages to server
                System.out.println("Player "+uid+" said: "+line);
            }
            // Prints to server when user disconnects
            System.out.println("User "+uid+" @ "+client.getInetAddress().toString()+" disconnected.");
            sin.close();
            client.close();
        } catch (IOException ex) {
            System.out.println("Error occured in scanner creation.");
        }
    }

    // Allows for easy access to sout member variable in Connection
    public void send(String message) { sout.println(message); }

    // Returns string of Connection's internet address
    public String getInetAddress() { return client.getInetAddress().toString(); }
}

 public class board{
     public String[][] multi = new String[10][10];
     int lengthOfShip = 4;

     void setArrayBoard(){

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                multi[i][j] = "-";
            }
        }
         multi[3][5] = "1";
         multi[3][6] = "1";
         multi[3][7] = "1";
         multi[3][8] = "1";
         
     }

    //  boolean checkForHit(String coord){
    //     int row = Interger.parseInt(coord.charAt(0));
    //     int columnAscii = (int) coord.charAt(1);
    //     int column = columnAscii - 64;

    //     if (multi[row-1][column -1 ] == "2"){
            
    //     }
        
    //  }
 }