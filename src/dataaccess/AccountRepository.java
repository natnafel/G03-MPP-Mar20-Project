package dataaccess;

import business.Address;
import business.Auth;
import business.LibraryMember;
import business.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by natnafel on 3/5/20.
 */
public class AccountRepository {

    public User getUser(String username, String password) {
        Connection connection = null;
        try {
            connection = DBConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT r.code from user u JOIN user_role ur " +
                    "ON u.id = ur.user_id JOIN role r ON r.id = ur.role_id where u.username = ? and u.password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            List<Auth> auths = new ArrayList<>();
            while(rs.next()){
                for(Auth a: Auth.values()){
                    if(a.name().equals(rs.getString("code"))){
                        auths.add(a);
                    }
                }
            }
            if(auths.size() == 0)
                return null;
            return new User(username, password, auths.toArray(new Auth[auths.size()]));

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public LibraryMember findMemberByMemberId(String memberId){
        Connection connection = null;
        try{
            connection = DBConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select *, m.id mId from LibraryMember m " +
                    "join address a on m.address_id = a.id where memberId = ?");
            preparedStatement.setString(1, memberId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                Address address = new Address(rs.getString("street"), rs.getString("city"),
                        rs.getString("state"), rs.getString("zip"));
                return new LibraryMember(rs.getInt("mId"), memberId, rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("telephone"), address);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean createMember(LibraryMember member){
        Connection connection = null;
        try{
            connection = DBConnectionHelper.getConnection();
            Address address = member.getAddress();
            PreparedStatement addressStatement = connection.prepareStatement("INSERT INTO address (street, city, state, zip) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            addressStatement.setString(1, address.getStreet());
            addressStatement.setString(2, address.getCity());
            addressStatement.setString(3, address.getState());
            addressStatement.setString(4, address.getZip());

            addressStatement.executeUpdate();

            Connection helper = DBConnectionHelper.getConnection();
            Statement stmt = helper.createStatement();
            ResultSet addressSet =  stmt.executeQuery("select id from address ORDER by id DESC limit 1");

            if(addressSet.next()){
                int addressId = addressSet.getInt(1);
                Connection addConn = DBConnectionHelper.getConnection();
                PreparedStatement memberStatement = addConn.prepareStatement("INSERT INTO LibraryMember (memberId, firstName, lastName, telephone, address_id) VALUES (?, ?, ?, ?, ?)");
                memberStatement.setString(1, member.getMemberId());
                memberStatement.setString(2, member.getFirstName());
                memberStatement.setString(3, member.getLastName());
                memberStatement.setString(4, member.getTelephone());
                memberStatement.setInt(5, addressId);
                helper.close();
                memberStatement.executeUpdate();
                addConn.close();
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<LibraryMember> getAllLibraryMembers(){
        List<LibraryMember> libraryMemberList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select memberId from LibraryMember");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                libraryMemberList.add(findMemberByMemberId(rs.getString("memberId")));
            }

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return libraryMemberList;
    }
}
