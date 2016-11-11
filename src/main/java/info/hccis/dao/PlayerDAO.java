package info.hccis.dao;


import info.hccis.cis2232example.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class will contain db functionality for working with Campers
 *
 * @author bjmaclean
 * @since 20160929
 * @modified 20161006
 * @author Kyle Creamer
 */
public class PlayerDAO {

    private final static Logger LOGGER = Logger.getLogger(PlayerDAO.class.getName());

        public static Player select(String emailAddress) {

        PreparedStatement ps = null;
        String sql = null;
        Connection conn = null;
        Player thePlayer=null;
        try {
            conn = ConnectionUtils.getConnection();

            sql = "SELECT * FROM player where emailAddress = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, emailAddress);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {

                int amountPaid = rs.getInt("amountPaid");
                String name= rs.getString("name");
                String parentName = rs.getString("parentName");
                String phoneNumber = rs.getString("phoneNumber");
                thePlayer = new Player(name, parentName, emailAddress, phoneNumber, amountPaid);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            DbUtils.close(ps, conn);
        }
        return thePlayer;
    }

    
    /**
     * Select all campers
     *
     * @author bjmaclean
     * @since 20160929
     * @modified 20161006
     * @author Kyle Creamer
     * @purpose This method will load all players in the database to an arraylist.
     * @return
     */
    public static ArrayList<Player> selectAll() {

        ArrayList<Player> players = new ArrayList();
        PreparedStatement ps = null;
        String sql = null;
        Connection conn = null;
        try {
            conn = ConnectionUtils.getConnection();

            sql = "SELECT * FROM player order by emailAddress";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                    
                int amountPaid = rs.getInt("amountPaid");
                String emailAddress = rs.getString("emailAddress");
                String name= rs.getString("name");
                String parentName = rs.getString("parentName");
                String phoneNumber = rs.getString("phoneNumber");
                
                players.add(new Player(name, parentName, emailAddress, phoneNumber, amountPaid));
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            DbUtils.close(ps, conn);
        }
        return players;
    }
    
        /**
     * @since 20161006
     * @author Kyle Creamer
     * @purpose This method check if an e-mail address already exists in the database.
     * @return boolean
     */
    
    public static synchronized boolean checkEmail(String emailAddress) throws Exception{
        
        PreparedStatement ps = null;
        String sql = null;
        Connection conn = null;
               try {
               conn = ConnectionUtils.getConnection();
           
                sql = "SELECT emailAddress from player where emailAddress= ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, emailAddress);
                ResultSet rs = ps.executeQuery();
                
                return rs.next();
                       } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
            throw e;
        } finally {
            DbUtils.close(ps, conn);
        }

        
    }

    /**
     * This method will insert or update.
     *
     * @return
     * @author BJ
     * @since 20140615
     * @modified 20161006
     * @author Kyle Creamer
     */
    public static synchronized Player update(Player player) throws Exception {
//        System.out.println("inserting player");
        PreparedStatement ps = null;
        String sql = null;
        Connection conn = null;


        /*
         * Setup the sql to update or insert the row.
         */
        try {
            conn = ConnectionUtils.getConnection();

            //Check to see if camper exists.
                if (!checkEmail(player.getEmailAddress())){

                sql = "INSERT INTO player (name,parentName,phoneNumber, emailAddress, amountPaid)"
                        + "VALUES (?,?,?,?,?)";

                ps = conn.prepareStatement(sql);
                ps.setString(1, player.getName());
                ps.setString(2, player.getParentName());
                ps.setString(3, player.getPhoneNumber());
                ps.setString(4, player.getEmailAddress());
                ps.setInt(5, player.getAmountPaid());

            } else {
                   
                sql = "UPDATE player SET name=?, parentName=?, phoneNumber=?, amountPaid=? where emailAddress = ?";

                ps = conn.prepareStatement(sql);
                ps.setString(1, player.getName());
                ps.setString(2, player.getParentName());
                ps.setString(3, player.getPhoneNumber());
                ps.setInt(4, player.getAmountPaid());
                ps.setString(5, player.getEmailAddress());

            }
            /*
             Note executeUpdate() for update vs executeQuery for read only!!
             */
            ps.executeUpdate();

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
            throw e;
        } finally {
            DbUtils.close(ps, conn);
        }
        return player;

    }

}
