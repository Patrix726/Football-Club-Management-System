package com.fifa.fcms;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

import jakarta.servlet.http.HttpServletRequest;

public class Utils {

    public static LinkedList<HashMap<String, Object>> getStaff() {
        LinkedList<HashMap<String, Object>> allStaff = new LinkedList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ALLSTAFF_VIEW");
            while (rs.next()) {
                HashMap<String, Object> staff = new HashMap<>();
                staff.put("id", rs.getInt(1));
                staff.put("fullName", rs.getString(2) + " " + rs.getString(3));
                staff.put("nationality", rs.getString(4));
                staff.put("salary", rs.getString(5));
                staff.put("currentClub", rs.getString(6));
                allStaff.addLast(staff);
            }
            con.close();
        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            allStaff.addLast(error);
        }

        return allStaff;
    }

    public static HashMap<String, Object> getPlayer(int playerId) {
        HashMap<String, Object> player = new HashMap<>();

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ALLPLAYERS_VIEW WHERE SID=" + playerId);

            rs.next();
            player.put("id", rs.getInt(1));
            player.put("fullName", rs.getString(2));
            player.put("position", rs.getString(3));
            player.put("nationality", rs.getString(4));
            player.put("goals", rs.getInt(5));
            player.put("assists", rs.getInt(6));
            player.put("jerseyNo", rs.getInt(7));
            player.put("currentClub", rs.getString(8));
            player.put("currentClubId", rs.getInt(9));
            con.close();
        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            e.printStackTrace();
            return error;
        }

        return player;
    }

    public static LinkedList<HashMap<String, Object>> getPlayers(int clubId) {
        LinkedList<HashMap<String, Object>> allPlayers = new LinkedList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");
            String procedureCall = "{call QUERY_ALLPLAYERS_IN_A_CLUB(?)}";
            CallableStatement cs = con.prepareCall(procedureCall);
            cs.setInt(1, clubId);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                HashMap<String, Object> player = new HashMap<>();
                System.out.println(rs.getString(1));
                player.put("id", rs.getInt(1));
                player.put("fullName", rs.getString(2));
                player.put("position", rs.getString(3));
                player.put("nationality", rs.getString(4));
                player.put("goals", rs.getInt(5));
                player.put("assists", rs.getInt(6));
                player.put("jerseyNo", rs.getInt(7));
                player.put("currentClubId", rs.getInt(8));
                player.put("currentClub", rs.getString(9));

                allPlayers.addLast(player);
            }
            con.close();
        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            allPlayers.addLast(error);
            e.printStackTrace();
        }

        return allPlayers;
    }

    public static HashMap<String, Object> getClub(int clubId) {
        HashMap<String, Object> club = new HashMap<>();

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ALLCLUBS_VIEW WHERE CLUB_ID=" + clubId);

            rs.next();
            club.put("id", rs.getInt(1));
            club.put("name", rs.getString(2));
            club.put("country", rs.getString(3));
            club.put("founded", rs.getDate(4));
            club.put("stadium", rs.getString(5));
            club.put("manager", rs.getString(6));
            con.close();
        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            e.printStackTrace();
            return error;
        }

        return club;
    }

    public static LinkedList<HashMap<String, Object>> getClubs() {
        LinkedList<HashMap<String, Object>> allClubs = new LinkedList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ALLCLUBS_VIEW");

            while (rs.next()) {
                HashMap<String, Object> club = new HashMap<>();
                System.out.println(rs.getString(1));
                club.put("id", rs.getInt(1));
                club.put("name", rs.getString(2));
                club.put("country", rs.getString(3));
                club.put("founded", rs.getDate(4));
                club.put("stadium", rs.getString(5));
                club.put("manager", rs.getString(6));
                allClubs.addLast(club);
            }
            con.close();
        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            allClubs.addLast(error);
            e.printStackTrace();
        }

        return allClubs;
    }

    public static boolean authenticate(HttpServletRequest request) {
        boolean output;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");
            PreparedStatement st = con.prepareStatement("SELECT * FROM USERS WHERE USERNAME=? AND PASS=?");
            st.setString(1, request.getParameter("username"));
            st.setString(2, request.getParameter("password"));
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                output = true;
            } else {
                output = false;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return output;
    }

    // public static HashMap<String, Object> getNextMatch(int clubId);

    // public static HashMap<String, Object> getTopGoalScorer( int clubId);

    // public static HashMap<String, Object> getTopAssister(int clubId);
    public static LinkedList<HashMap<String, Object>> getLeagues() {
        LinkedList<HashMap<String, Object>> Leagues = new LinkedList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM TOURNAMENT");

            while (rs.next()) {
                HashMap<String, Object> league = new HashMap<>();
                System.out.println(rs.getString(1));
                league.put("id", rs.getInt(1));
                league.put("name", rs.getString(2));
                league.put("region", rs.getString(3));
                league.put("noOfTeams", rs.getInt(4));
                Leagues.addLast(league);
            }
            con.close();
        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            Leagues.addLast(error);
            e.printStackTrace();
        }

        return Leagues;
    }

    public static LinkedList<HashMap<String, Object>> getLeagueTable(int tid) {
        LinkedList<HashMap<String, Object>> LeagueTable = new LinkedList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");
            Statement st = con.createStatement();
            ResultSet rs = st
                    .executeQuery(
                            "SELECT * FROM LEAGUE_TABLE_VIEW WHERE [TOURNAMENT ID]=" + tid + " ORDER BY PTS DESC;");

            while (rs.next()) {
                HashMap<String, Object> club = new HashMap<>();
                System.out.println(rs.getString(1));
                club.put("id", rs.getInt(2));
                club.put("name", rs.getString(3));
                club.put("played", rs.getInt(4));
                club.put("won", rs.getInt(5));
                club.put("drew", rs.getString(6));
                club.put("lost", rs.getString(7));
                club.put("points", rs.getString(8));
                LeagueTable.addLast(club);
            }
            con.close();
        } catch (Exception e) {
            HashMap<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            LeagueTable.addLast(error);
            e.printStackTrace();
        }

        return LeagueTable;
    }

    public static boolean registerPlayer(String fname, String lname, long dateOfBirth, String nationality,
            String position, float salary, int clubId, int jerseyNo, long contractEndDate) {
        boolean success = false;
        if (position == "Defence") {
            position = "DF";
        } else if (position == "Midfield") {
            position = "MD";
        } else {
            position = "FW";
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");
            String sql = "{call REGISTER_PLAYER(?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = con.prepareCall(sql);
            cs.setString(1, fname);
            cs.setString(2, lname);
            cs.setDate(3, new Date(dateOfBirth));
            cs.setString(4, nationality);
            cs.setString(5, position);
            cs.setFloat(6, salary);
            cs.setInt(7, clubId);
            cs.setInt(8, jerseyNo);
            cs.setDate(9, new Date(contractEndDate));

            int rs = cs.executeUpdate();
            success = rs > 0;
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public static boolean transferPlayer(int playerId, int clubId, int salary, int jerseyNo, long contractEndDate) {
        boolean success = false;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");
            String sql = "{call TRANSFER_PLAYER(?,?,?,?,?)}";
            CallableStatement cs = con.prepareCall(sql);
            cs.setInt(1, playerId);
            cs.setInt(2, clubId);
            cs.setInt(3, salary);
            cs.setInt(4, jerseyNo);
            cs.setDate(5, new Date(contractEndDate));

            int rs = cs.executeUpdate();
            success = rs > 0;
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public static boolean deletePlayer(int playerId) {
        boolean success = false;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://EDITH\\SQLEXPRESS;databaseName=FOOTBALLCLUBDB";
            Connection con = DriverManager.getConnection(url, "sa", "elliot@mrrobot");
            String sql = "{call REMOVE_PLAYER(?)}";
            CallableStatement cs = con.prepareCall(sql);
            cs.setInt(1, playerId);

            int rs = cs.executeUpdate();
            System.out.println(rs);
            success = rs > 0;
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

}
