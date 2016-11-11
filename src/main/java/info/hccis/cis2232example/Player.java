package info.hccis.cis2232example;

import com.google.gson.Gson;
import info.hccis.dao.PlayerDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class will represent a player.
 *
 * @author bjmaclean
 * @since 20150915
 * @modified by Kyle Creamer
 * @since 20161005
 */
public class Player implements Serializable{

    private int amountPaid;
    private String name;
    private String parentName;
    private String emailAddress;
    private String phoneNumber;

    public Player() {
        //do nothing.
    }

    /**
     * Default constructor which will get info from user
     *
     * @since 20150917
     * @author BJ MacLean
     * @modified by Kyle Creamer
     * @since 20161005
     */
    public Player(boolean getFromUser) {
        System.out.println("What is the player's e-mail address?");
        this.emailAddress = FileUtility.getInput().nextLine();
        
        try {
            if (PlayerDAO.checkEmail(this.emailAddress)){
                System.out.println("This address exists, but we can update the information for you.");
            }
        } catch (Exception ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("What is the player's name?");
        this.name = FileUtility.getInput().nextLine();

        System.out.println("What is the player's parent's name?");
        this.parentName= FileUtility.getInput().nextLine();
        
        System.out.println("What is the player's phone number?");
        this.phoneNumber = FileUtility.getInput().nextLine();
        
        System.out.println("What is the player's amount paid?");
        this.amountPaid = FileUtility.getInput().nextInt();
        FileUtility.getInput().nextLine();
        
        

    }
    
    public Player(boolean getFromUser, String emailAddress){
        this.emailAddress = emailAddress;
                System.out.println("What is the player's name?");
        this.name = FileUtility.getInput().nextLine();

        System.out.println("What is the player's parent's name?");
        this.parentName= FileUtility.getInput().nextLine();
        
        System.out.println("What is the player's phone number?");
        this.phoneNumber = FileUtility.getInput().nextLine();
        
        System.out.println("What is the player's amount paid?");
        this.amountPaid = FileUtility.getInput().nextInt();
        FileUtility.getInput().nextLine();
    }

    /**
     * Custom constructor with all info
     *
     * @param name
     * @param parentName
     * @param emailAddress
     * @param registrationId
     * @param amountPaid
     * @param phoneNumber
     * @author BJ MacLean
     * @since 20150917
     * @modified Kyle Creamer
     * @since 20161005
     */
    public Player(String name, String parentName, String emailAddress, String phoneNumber, int amountPaid ) {
        this.name = name;
        this.parentName = parentName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.amountPaid = amountPaid;

    }

    public  void edit(){
        System.out.println("**************************************");
        System.out.println("Here's the existing info for this player.");
        Player reloadedPlayer = PlayerDAO.select(this.emailAddress);
            this.name = reloadedPlayer.getName();
            this.parentName = reloadedPlayer.getParentName();
            this.phoneNumber = reloadedPlayer.getPhoneNumber();
            this.amountPaid = reloadedPlayer.getAmountPaid();

        System.out.println(this.toString());
        System.out.println("**************************************");
        
        System.out.println("What is the player's name?");
        this.name = FileUtility.getInput().nextLine();

        System.out.println("What is the player's parent's name?");
        this.parentName= FileUtility.getInput().nextLine();
        
        System.out.println("What is the player's phone number?");
        this.phoneNumber = FileUtility.getInput().nextLine();
        
        System.out.println("What is the player's amount paid?");
        this.amountPaid = FileUtility.getInput().nextInt();
        FileUtility.getInput().nextLine();
    }
    
    

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    


    @Override
    public String toString() {
        return "Player Name=" + name + ", Parent Name=" + parentName + ", Email Address= "+ emailAddress +", Phone Number=" + phoneNumber + ", Amount Paid=" + amountPaid;
    }

}
