package info.hccis.cis2232example;

import info.hccis.dao.PlayerDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bjmaclean
 * @modified 20161003
 * @author Kyle Creamer
 * @purpose This class will allow the addition, edit, and display of players to
 * and from a database specified in the createDatabase.sql file.
 */
public class MainClass {

    public static String MENU = "\nOptions:\nE) Edit (or Add) camper\nS) Show campers\nX) Exit";

    public static void main(String[] args) throws IOException {


        String option = "";
        do {
            System.out.println(MENU);
            option = FileUtility.getInput().nextLine().toUpperCase();

            switch (option) {

                case "E":
                    System.out.println("Enter email address or enter 0 to add a new player");
                    String emailAddress = FileUtility.getInput().nextLine();
                    boolean isValid = isValidNumber(emailAddress);
                    Player thePlayer = null;
                    boolean displayTotals = false;
            {
                try {
                    if (isValid) {
                        thePlayer = new Player(true);
                    }
                    else if(!(PlayerDAO.checkEmail(emailAddress))){
                        thePlayer = new Player(true, emailAddress);
                    }
                        else {
                        for (Player player : PlayerDAO.selectAll()) {
                            if (player.getEmailAddress().toLowerCase().equals(emailAddress.toLowerCase())) {
                                thePlayer = player;
                                break;
                            }
                        }
                        displayTotals = true;
                        thePlayer.edit();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

                    try {
                        PlayerDAO.update(thePlayer);
                        if (displayTotals == true){
                            double totalPaid = 0;
                            System.out.println("\nThe updated amount paid for all players is: ");
                            for (Player player : PlayerDAO.selectAll()){
                            System.out.println("Player Name: " + player.getName() + "\nPaid Amount: " + player.getAmountPaid());
                            totalPaid += player.getAmountPaid();
                            
                        }
                            System.out.println("\nThe total amount paid is: " + totalPaid);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    }


                    break;
                case "S":
                    System.out.println("Here are the players");
                    for (Player players : loadPlayersFromDatabase()) {
                        System.out.println(players);
                    }
                    break;
                case "X":
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;

            }
        } while (!option.equalsIgnoreCase("x"));
    }

    /**
     * Load all the campers from the database and set the campers into the
     * arraylist.
     *
     * @return 
     * @since 20160930
     * @author BJ
     * @author Kyle Creamer
     * @modified 20161003
     */
    public static ArrayList<Player> loadPlayersFromDatabase() {


        return PlayerDAO.selectAll();
    }
    
    public static boolean isValidNumber(String emailAddress){
        
        try{
       int isNumber =  Integer.parseInt(emailAddress);
            return isNumber == 0;
            }
        catch(Exception ex){
            return false;
        }
    }

}
