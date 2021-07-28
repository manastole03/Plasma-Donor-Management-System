 package bloodDonorsManagementSystem;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Database {

    //this method establishe connection with database
    public static Connection ConnectDB() {
        Connection conn = null;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver loaded.");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/plasma", "root", "danceguru@39");
            System.out.println("Database Connected.");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Failed.");
            System.out.println("Database.ConnectDB():" + e);
        }

        return conn;
    }

    //this method return InputStream for image
   public InputStream getImage(int id) {
        String query = "SELECT photo FROM db_table1 WHERE id = " + id;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs;
        InputStream is = null;
        try {
            conn = ConnectDB();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            rs.next();
            is = rs.getBinaryStream("photo");
            System.out.println(query);
        } catch (SQLException ex) {
            System.out.println("Database.getImage()" + ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                    System.out.println("Databae Disconnected.");
                }
            } catch (SQLException e) {
                System.out.println("Database.viewProfile():" + e);
            }
        }
        return is;
    }

    //this method used for retrieving data to show in profile
    public Donor viewProfile(int id) {
        String query = "SELECT * FROM db_table112 WHERE id = '" + id + "'";

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Donor donor = new Donor();
        try {
            conn = ConnectDB();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            rs.next();

            donor.setName(rs.getString("name"));
            donor.setFathersName(rs.getString("father_name"));
            donor.setMothersName(rs.getString("family_name"));
            donor.setGender(rs.getString("gender"));
            donor.setAge(rs.getInt("age"));
            donor.setWeight(rs.getString("weight"));
            donor.setBloodGroup(rs.getString("blood_group"));
            donor.setMaritalStatus(rs.getString("marital_status"));
            donor.setNID(rs.getString("adhaar_number"));
           // donor.settested(rs.getDate("test_date"));
            donor.setMobileNo(rs.getString("mobile_number"));
            donor.setDivision(rs.getString("state"));
            donor.setDistrict(rs.getString("district"));
            donor.setAddress(rs.getString("address"));
            //donor.setLastDonation(rs.getDate("last_donated"));
            donor.setPicture(rs.getBinaryStream("photo"));

            System.out.println(query);
            System.out.println("Fetched Profile");
        } catch (SQLException e) {
            System.out.println("Database.viewProfile(int id):" + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                    System.out.println("Databae Disconnected.");
                }
            } catch (SQLException e) {
                System.out.println("Database.viewProfile():" + e);
            }
        }
        return donor;
    }

    //this method used for specific query in database
    public ArrayList<Donor> search(String value) {
        String query = "SELECT id, name, gender, age, weight, blood_group, mobile_number, address,state, district FROM db_table112 WHERE " + value;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Donor> donorsList = new ArrayList();
        try {
            conn = ConnectDB();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                donorsList.add(new  Donor(Integer.parseInt(rs.getObject(1).toString()), rs.getObject(2).toString(),
                        rs.getObject(3).toString(), Integer.parseInt(rs.getObject(4).toString()), rs.getObject(5).toString(), rs.getObject(6).toString(),
                        rs.getObject(7).toString(),rs.getObject(8).toString(), rs.getObject(9).toString(), rs.getObject(10).toString()));
            }
            System.out.println(query);
            System.out.println("Data Fetched");
        } catch (SQLException e) {
            System.out.println("Database.search(String value):" + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                    System.out.println("Databae Disconnected.");
                }
            } catch (SQLException e) {
                System.out.println("Database.search():" + e);
            }
        }
        return donorsList;
    }

    //this method retrive data from database and return them as ResultSet
    @SuppressWarnings("unchecked")
	public ArrayList<Donor> getDataForTable() {
        String query = "SELECT id, name, gender, age, weight, blood_group, mobile_number, address,state, district  from db_table112";

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Donor> donorsList = new ArrayList();

        try {
            conn = ConnectDB();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                donorsList.add(new Donor(Integer.parseInt(rs.getObject(1).toString()), rs.getObject(2).toString(),
                        rs.getObject(3).toString(), Integer.parseInt(rs.getObject(4).toString()), rs.getObject(5).toString(), rs.getObject(6).toString(),
                        rs.getObject(7).toString(),rs.getObject(8).toString(), rs.getObject(9).toString(), rs.getObject(10).toString()));
            }
            System.out.println(query);
            System.out.println("Data Fetchededfae");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ededeadError Occured While Fetching Data!",
                    "Error!", JOptionPane.ERROR_MESSAGE);
            System.out.println("Database.getDataForTable():" + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                    System.out.println("Databae Disconnected.");
                }
            } catch (SQLException e) {
                System.out.println("Database.getDataForTable():" + e);
            }
        }
        return donorsList;
    }

   

    //this method update data
   public void update(Donor donor) {
        int id = donor.getID();
        int age = donor.getAge();
        String name = donor.getName();
        String fname = donor.getFathersName();
        String mname = donor.getMothersName();
        String gender = donor.getGender();
        String weight = donor.getWeight();
        String bloodGroup = donor.getBloodGroup();
        String maritalStts = donor.getMaritalStatus();
        String nid = donor.getNID();
       // String disease = donor.getDiseases();
        String mobile = donor.getMobileNo();
        String division = donor.getDivision();
        String district = donor.getDistrict();
        String address = donor.getAddress();
        InputStream isImage = donor.getPicture();
        Date lstDonation = null;
        if(donor.getLastDonation() != null)
            lstDonation = new Date(donor.getLastDonation().getTime());

        String sql = "UPDATE db_table112 SET "
                + "name=?, fathers_name=?, family_name=?, gender=?, age=?, weight=?,"
                + "blood_group=?, marital_status=?, adhaar_number=?, mobile_number=?,"
                + "state=?, district=?, address=?, photo=? WHERE id=" + id;

        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = ConnectDB();
            pst = conn.prepareStatement(sql);

            System.out.println(sql);

            pst.setString(1, name);
            pst.setString(2, fname);
            pst.setString(3, mname);
            pst.setString(4, gender);
            pst.setInt(5, age);
            pst.setString(6, weight);
            pst.setString(7, bloodGroup);
            pst.setString(8, maritalStts);
            pst.setString(9, nid);
            pst.setDate(10, lstDonation);
          //  pst.setString(11, disease);
            pst.setString(12, mobile);
            pst.setString(13, division);
            pst.setString(14, district);
            pst.setString(15, address);
            pst.setBlob(16, isImage);

            pst.executeUpdate();

            System.out.println("Data Updated Successfully.");
        } catch (SQLException e) {
            System.out.println("Database.update():" + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                    System.out.println("Databae Disconnected.");
                }
            } catch (SQLException e) {
                System.out.println("Database.update():" + e);
            }
        }
    }

    //this method delete data from database
    public void delete(int id)
    {
        String del = "DELETE FROM db_table112 WHERE id = '" + id + "'";
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = ConnectDB();
            pst = conn.prepareStatement(del);
            pst.executeUpdate();

            System.out.println(id + " No Data Deleted Successfully.");
        } catch (SQLException e) {
            System.out.println("Database.delete(int id):" + e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                    System.out.println("Databae Disconnected.");
                }
            } catch (SQLException e) {
                System.out.println("Database.delete(int id):" + e);
            }
        }
    }
}
