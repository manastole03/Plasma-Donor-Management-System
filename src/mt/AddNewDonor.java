package bloodDonorsManagementSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

public class AddNewDonor {

    private final ImageIcon icon;

    private final JFrame frameAddNewEntry = new JFrame("Add New Donor");

    private final JPanel leftPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();

    private final JButton btnCancel, btnAdd, btnChoose;

    private final Color clrRed = new Color(195, 5, 5);
    private final Color clrBlue = new Color(15, 70, 140);
    private final Color clrYellow = new Color(240, 230, 180);
    private final Color clrCyan = new Color(130, 185, 220);

    private  JLabel lblName, lblFatherName, lblFamilyName, lblGender, lblBloodGroup, lblAge, lblWeight, lblMaritalStts, lblNID, lblStatement, lblLastDonation, lblLstDontnEx, lblPrevDisease, lblMobile, lblDistrict, lblDivision, lblAddress, lblImage;
    private  JTextField tfName, tfFatherName,  txtWeight,txtFamilyName;
    private  JTextArea taDiseas, taAddress;
    private  JComboBox cbGender, cbBloodGroup, cbMaritalStts, cbDivision, cbDistrict;
    private  JSpinner spAge;
    private  SpinnerNumberModel age;
    private  JCheckBox chbSttmnt;
    
    private  MaskFormatter mskMobile, mskNID;
    private  JFormattedTextField tfMobile, tfNID;
    JComboBox month1;
    JComboBox month;
    JComboBox date;
    JComboBox date1;
    JComboBox year;
    JComboBox year1;

    private final String divisions[] = {"Barisal", "Chittagong", "Dhaka", "Khulna", "Mymensingh", "Rajshahi", "Rangpur", "Sylhet"};

    private final String districts[][] = {{"Pirojpur", "Patuakhali", "Jhalokati", "Bhola", "Barisal", "Barguna"},
    {"Rangamati", "Noakhali", "Lakshmipur", "Khagrachhari", "Feni", "Cox's Bazar", "Comilla", "Chittagong", "Chandpur", "Brahmanbaria", "Bandarban"},
    {"Tangail", "Shariatpur", "Rajbari", "Narsingdi", "Narayanganj", "Munshiganj", "Manikganj", "Madaripur", "Kishoreganj", "Gopalganj", "Gazipur", "Faridpur", "Dhaka"},
    {"Satkhira", "Narail", "Meherpur", "Magura", "Kushtia", "Khulna", "Jhenaidah", "Jessore", "Chuadanga", "Bagerhat"},
    {"Sherpur", "Netrokona", "Mymensingh", "Jamalpur"},
    {"Sirajganj", "Rajshahi", "Pabna", "Natore", "Naogaon", "Joypurhat", "Chapai Nawabganj", "Bogra"},
    {"Thakurgaon", "Rangpur", "Panchagarh", "Nilphamari", "Lalmonirhat", "Kurigram", "Gaibandha", "Dinajpur"},
    {"Sylhet", "Sunamganj", "Moulvibazar", "Habiganj"}};

    private final String bloodGroups[] = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    private final String gender[] = {"Male", "Female","Other"};
    private final String maritalStts[] = {"Single", "Married"};
    private String imagePath;

    private Donor donor = new Donor();

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public AddNewDonor() {

        //adding image for icon
        icon = new ImageIcon(getClass().getResource("images/add_donor_icon.png"));
        frameAddNewEntry.setIconImage(icon.getImage());                         //set frame icon

        //setting up the frame
        frameAddNewEntry.setSize(600, 540);
        frameAddNewEntry.setLayout(new BorderLayout());                         //frame layout: Border Layout
        frameAddNewEntry.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);     //setting default close operation
        frameAddNewEntry.setResizable(false);                                   //disable resize button
        frameAddNewEntry.setLocationRelativeTo(null);
frameAddNewEntry.setBackground(new Color(213, 219, 219));

        Border margin = new EmptyBorder(10, 10, 10, 10);                        //create margin for titled border
        Border blueline = BorderFactory.createLineBorder(clrBlue);              //create line for titled border

        //setting up left panel        
       /* TitledBorder ttlPersonInfo = BorderFactory.createTitledBorder(blueline);//create titled border
        ttlPersonInfo.setTitle(" Personal Information ");                       //set title
        ttlPersonInfo.setTitleColor(clrBlue); */                                  //title color: Blue

       // leftPanel.setBorder(new CompoundBorder(margin, ttlPersonInfo));         //set border(outer border: margin, inner border: title)
        leftPanel.setBackground(new Color(213, 219, 219));
        leftPanel.setPreferredSize(new Dimension(300, 540));                    //setting panel size
        leftPanel.setLayout(null);

        //name
        lblName = new JLabel("Name:*");
        lblName.setBounds(30, 30, 100, 20);
        leftPanel.add(lblName);

        tfName = new JTextField();
        tfName.setBounds(30, 50, 240, 25);
        leftPanel.add(tfName);

        //fathers_name
        lblFatherName = new JLabel("Father's Name:*");
        lblFatherName.setBounds(30, 80, 150, 20);
        leftPanel.add(lblFatherName);

        tfFatherName = new JTextField();
        tfFatherName.setBounds(30, 100, 240, 25);
        leftPanel.add(tfFatherName);

        //family_name
        lblFamilyName = new JLabel("Family Name:");
        lblFamilyName.setBounds(30, 130, 150, 20);
        leftPanel.add(lblFamilyName);

        txtFamilyName = new JTextField();
        txtFamilyName.setBounds(30, 150, 240, 25);
        leftPanel.add(txtFamilyName);

        //gender
        lblGender = new JLabel("Gender:");
        lblGender.setBounds(30, 180, 80, 20);
        leftPanel.add(lblGender);

        cbGender = new JComboBox(gender);
        cbGender.setBounds(30, 200, 80, 25);
        leftPanel.add(cbGender);

        //age
        lblAge = new JLabel("Age:");
        lblAge.setBounds(120, 180, 50, 20);
        leftPanel.add(lblAge);

        age = new SpinnerNumberModel(18, 18, 65, 1);
        spAge = new JSpinner(age);
        spAge.setBounds(120, 200, 50, 25);
        leftPanel.add(spAge);
        
        //weight
        lblWeight = new JLabel("Weight (KG):*");
        lblWeight.setBounds(178, 180, 95, 20);
        leftPanel.add(lblWeight);

        txtWeight = new JTextField();
        txtWeight.setBounds(180, 200, 90, 25);
        leftPanel.add(txtWeight);

        //bloodgroup
        lblBloodGroup = new JLabel("Blood Group:");
        lblBloodGroup.setBounds(30, 230, 100, 20);
        leftPanel.add(lblBloodGroup);

        cbBloodGroup = new JComboBox(bloodGroups);
        cbBloodGroup.setBounds(30, 250, 100, 25);
        leftPanel.add(cbBloodGroup);

        //marital_status
        lblMaritalStts = new JLabel("Marital Status:");
        lblMaritalStts.setBounds(160, 230, 110, 20);
        leftPanel.add(lblMaritalStts);

        cbMaritalStts = new JComboBox(maritalStts);
        cbMaritalStts.setBounds(160, 250, 110, 25);
        leftPanel.add(cbMaritalStts);

        //adhaar_number
        lblNID = new JLabel("Adhaar Number :");
        lblNID.setBounds(30, 280, 100, 20);
        leftPanel.add(lblNID);

        mskNID = new MaskFormatter();
        try {
            mskNID.setMask("######");
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frameAddNewEntry, "Invalid Number", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        tfNID = new JFormattedTextField(mskNID);
        tfNID.setBounds(30, 300, 240, 25);
        leftPanel.add(tfNID);


        lblAddress = new JLabel("Premanaent Address:");
        lblAddress.setBounds(30, 330, 200, 20);
        leftPanel.add(lblAddress);

        taAddress = new JTextArea();
        taAddress.setLineWrap(true);

        JScrollPane scrlPaneAdrs = new JScrollPane();
        scrlPaneAdrs.setBounds(30, 350, 240, 100);
        scrlPaneAdrs.setViewportView(taAddress);
        leftPanel.add(scrlPaneAdrs);
       
      
        
        //left panel work done

        //setting up right panel
        rightPanel.setBackground(new Color(213, 219, 219));
        rightPanel.setPreferredSize(new Dimension(300, 540));                   //setting panel size
        rightPanel.setLayout(new BorderLayout());

        //we divided right panel into three part vertically
        //upper panel will contain donors picture
        //mid panel will contain contact info
        //and lower panel will contain buttons
        JPanel upperPanel = new JPanel();
        JPanel midPanel = new JPanel();
        JPanel lowerPanel = new JPanel();

        //upper panel work
        /*TitledBorder ttlChoosePic = BorderFactory.createTitledBorder(blueline); //create titled border
        ttlChoosePic.setTitle(" Choose Picture ");                              //set title
        ttlChoosePic.setTitleColor(clrBlue);    */                                //title color: Blue

        //upperPanel.setBorder(new CompoundBorder(margin, ttlChoosePic));         //set border(outer border: margin, inner border: title)
        upperPanel.setBackground(new Color(213, 219, 219));
        upperPanel.setPreferredSize(new Dimension(300, 275));                   //setting panel size
        upperPanel.setLayout(null);

        lblImage = new JLabel();
        lblImage.setBounds(60, 50, 180, 180);
        lblImage.setBorder(blueline);
        lblImage.setIcon(resizeImage(new ImageIcon(getClass().getResource("images/user.png"))));
        upperPanel.add(lblImage);

        btnChoose = new JButton("Select Picture");
        btnChoose.setBounds(80, 245, 150, 30);
        btnChoose.setBackground(clrCyan);
        btnChoose.setForeground(Color.WHITE);
        upperPanel.add(btnChoose);

        btnChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //create a file chooser to select image
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

                //filter the files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
                fileChooser.addChoosableFileFilter(filter);

                imagePath = null;
                int result = fileChooser.showSaveDialog(null);
                //if the user click on save in Jfilechooser
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath();
                    lblImage.setIcon(resizeImage(imagePath));
                }//if the user click on cancel in Jfilechooser
                else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("No File Selected");
                }
            }

        });
        //upper panel work done

        //mid panel work
      //  TitledBorder ttlContctInfo = BorderFactory.createTitledBorder(blueline);//create titled border

      //  ttlContctInfo.setTitle(" Contact Information ");                        //set title
      //  ttlContctInfo.setTitleColor(clrBlue);                                   //title color: Blue

       // midPanel.setBorder(new CompoundBorder(
              //  new EmptyBorder(0, 10, 0, 10), ttlContctInfo));                 //set border(outer border: margin, inner border: title)
        midPanel.setBackground(new Color(213, 219, 219));
        midPanel.setPreferredSize(new Dimension(300, 165));                     //setting panel size
        midPanel.setLayout(null);

        lblMobile = new JLabel("Mobile No:*");
        lblMobile.setBounds(30, 20, 100, 20);
        midPanel.add(lblMobile);

        mskMobile = new MaskFormatter();
        try {
            mskMobile.setMask("+91#########");
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frameAddNewEntry, "'Mobile No' is invalid.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        tfMobile = new JFormattedTextField(mskMobile);
        tfMobile.setBounds(30, 40, 240, 25);
        midPanel.add(tfMobile);

        lblDivision = new JLabel("Home Division:");
        lblDivision.setBounds(30, 70, 120, 20);
        midPanel.add(lblDivision);

        cbDivision = new JComboBox(divisions);
        cbDivision.setBounds(30, 90, 115, 25);
        midPanel.add(cbDivision);

        lblDistrict = new JLabel("Home District:");
        lblDistrict.setBounds(150, 70, 120, 20);
        midPanel.add(lblDistrict);

        cbDistrict = new JComboBox();
        cbDistrict.setBounds(150, 90, 120, 25);
        for (int i = 0; i < districts[0].length; ++i) {
            cbDistrict.addItem(districts[0][i]);
        }
        midPanel.add(cbDistrict);


        cbDivision.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cbDistrict.removeAllItems();
                int index = cbDivision.getSelectedIndex();
                for (int i = 0; i < districts[index].length; ++i) {
                    cbDistrict.addItem(districts[index][i]);
                }
            }
        });
        //mid panel work done

        //lower panel work
       // lowerPanel.setBackground(clrYellow);                                    //background color: Yellow
        lowerPanel.setPreferredSize(new Dimension(300, 50));                    //setting panel size
        lowerPanel.setBorder(new EmptyBorder(10, 50, 10, 50));
        lowerPanel.setBackground(new Color(213, 219, 219));
        lowerPanel.setLayout(new BorderLayout());

        btnAdd = new JButton("Add Donor");
        btnAdd.setBackground(new Color(39, 174, 96));                                          //button background color: Blue
        btnAdd.setForeground(Color.WHITE);                                      //button text color: White
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));                       //set button cursor
        btnAdd.setBounds(40, 380, 100, 20);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                insertData();
            }
        });
       // leftPanel.add(btnAdd);

        btnCancel = new JButton("Cancel");
        btnCancel.setBackground(clrRed);                                        //button background color: clrRed
        btnCancel.setForeground(Color.WHITE);                                   //button text color: White
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));   
       // btnCancel.setBackground(new Color(213, 219, 219));
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frameAddNewEntry.dispose();
            }

        });

       lowerPanel.add(btnAdd, BorderLayout.WEST);
        lowerPanel.add(btnCancel, BorderLayout.EAST);
        //lowr panel work done

        rightPanel.add(upperPanel, BorderLayout.NORTH);
        rightPanel.add(midPanel, BorderLayout.CENTER);
        rightPanel.add(lowerPanel, BorderLayout.SOUTH);
        //right panle work done

        frameAddNewEntry.add(leftPanel, BorderLayout.WEST);
        frameAddNewEntry.add(rightPanel, BorderLayout.EAST);

        //make frame visible
        frameAddNewEntry.setVisible(true);
    }

    private ImageIcon resizeImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();

        image = image.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        return imageIcon;
    }

    private ImageIcon resizeImage(ImageIcon imageIcon) {
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon retImage = new ImageIcon(image);
        return retImage;
    }

    private void insertData() {
        boolean ok = true;

        String name = tfName.getText();
        String fname = tfFatherName.getText();
        String family_name = txtFamilyName.getText();
        String gender = cbGender.getSelectedItem().toString();
        String bloodGroup = cbBloodGroup.getSelectedItem().toString();
        String weight = txtWeight.getText();
        String maritalStts = cbMaritalStts.getSelectedItem().toString();
        String nid = tfNID.getText();
        String mobile = tfMobile.getText();
        String division = cbDivision.getSelectedItem().toString();
        String district = cbDistrict.getSelectedItem().toString();
        String address = taAddress.getText();
        
        
    	
		
		/*String dt=(String) date.getSelectedItem();
		String mnth=(String) month.getSelectedItem();
		String yr=(String) year.getSelectedItem();
		
		String date=dt+"/"+mnth+"/"+yr;*/
		
		
		/*String dt1=(String) date1.getSelectedItem();
		String mnth1=(String) month1.getSelectedItem();
		String yr1=(String) year1.getSelectedItem();
		
		String date1=dt1+"/"+mnth1+"/"+yr1;*/
        
        /*String dat=(String) dcLstDonation1.getSelectedItem();
        Date test_date =  dcLstDonation1.getDate();
        String date =  dcLstDonation.getDate();*/
        int age = (int) spAge.getModel().getValue();

        InputStream isImage = null;
        if (imagePath == null) {
            isImage = getClass().getResourceAsStream("images/user.png");
        } else {
            try {
                isImage = Files.newInputStream(Paths.get(imagePath));
            } catch (IOException ex) {
                ok = false;
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frameAddNewEntry, "'Picture' is invalid.",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (!txtWeight.getText().isEmpty()) {
            try {
                Float.parseFloat(txtWeight.getText());
            } catch (NumberFormatException ex) {
                ok = false;
                txtWeight.setText("");
                JOptionPane.showMessageDialog(frameAddNewEntry, "'Weight' is invalid.",
                        "Error!", JOptionPane.ERROR_MESSAGE);
                System.out.println("AddNewDonor.tfWeight.getText(): " + ex);
            }
        }
      /*  if (chbSttmnt.isSelected() && date == null) {
            ok = false;
            JOptionPane.showMessageDialog(frameAddNewEntry, "'Last Donation Date' is invalid.",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }*/

        if (name.equals("")) {
            JOptionPane.showMessageDialog(frameAddNewEntry, "'Name' Can't Be Empty.", "Error!", JOptionPane.ERROR_MESSAGE);
        } else if (name.length() > 30) {
            JOptionPane.showMessageDialog(frameAddNewEntry, "'Name' is too long (MAX 30 character).", "Error!", JOptionPane.ERROR_MESSAGE);
        } else if (fname.equals("")) {
            JOptionPane.showMessageDialog(frameAddNewEntry, "'Father's Name' Can't Be Empty.", "Error!", JOptionPane.ERROR_MESSAGE);
        } else if (fname.length() > 30) {
            JOptionPane.showMessageDialog(frameAddNewEntry, "'Father's Name' is too long (MAX 30 character)", "Error!", JOptionPane.ERROR_MESSAGE);
        } else if (family_name.length() > 30) {
            JOptionPane.showMessageDialog(frameAddNewEntry, "'Mother's Name' is too long (MAX 30 character)", "Error!", JOptionPane.ERROR_MESSAGE);
        } else if (mobile.trim().equals("+8801")) {
            JOptionPane.showMessageDialog(frameAddNewEntry, "'Mobile No' Can't Be Empty.", "Error!", JOptionPane.ERROR_MESSAGE);
        } else if (weight.trim().equals("")) {
            JOptionPane.showMessageDialog(frameAddNewEntry, "'Weight' Can't Be Empty.", "Error!", JOptionPane.ERROR_MESSAGE);
        } else if (ok == true) {
            try {
                /*donor.setName(name);
                donor.setFathersName(fname);
                donor.setMothersName(familyname);
                donor.setAge(age);
                donor.setWeight(weight);
                donor.setGender(gender);
                donor.setBloodGroup(bloodGroup);
                donor.setLastDonation(date);
                donor.settested(test_date);
                donor.setMaritalStatus(maritalStts);
                donor.setNID(nid);
                donor.setMobileNo(mobile);
                donor.setDivision(division);
                donor.setDistrict(district);
                donor.setAddress(address);
                donor.setPicture(isImage);*/
            	Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("driver loaded.");

               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/plasma", "root", "danceguru@39");
                System.out.println("Database Connected.");
                String sql = "INSERT INTO db_table112 (id, name,fathers_name,family_name, gender,age,weight, blood_group,marital_status,adhaar_number, mobile_number, state, district,address,photo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                
                PreparedStatement pst = conn.prepareStatement(sql);

                int id = 0;
				pst.setInt(1, id);
                pst.setString(2, name);
                pst.setString(3, fname);
                pst.setString(4, family_name);
                pst.setString(5, gender);
                pst.setInt(6, age);
                pst.setString(7, weight);
                pst.setString(8, bloodGroup);
                pst.setString(9, maritalStts);
                pst.setString(10, nid);
               // pst.setString(11, date);
                //pst.setString(11, date1);
                pst.setString(11, mobile);
                pst.setString(12, division);
                pst.setString(13, district);
                pst.setString(14, address);
                pst.setBlob(15, isImage);

                pst.executeUpdate();

                System.out.println("Data Inserted Successfully.");
                
                int choice = JOptionPane.showConfirmDialog(frameAddNewEntry,
                        "Data Added Successfully.\nDo you want to add another?", "Messege.",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                //if user choose NO then dispose this window
                //otherwise clear all the field                        
                if (choice == JOptionPane.NO_OPTION) {
                    frameAddNewEntry.dispose();
                } else {
                    tfName.setText("");
                    tfFatherName.setText("");
                    txtFamilyName.setText("");
                    txtWeight.setText("");
                    tfNID.setText("");
                    taDiseas.setText("");
                    tfMobile.setText("");
                    taAddress.setText("");
                    chbSttmnt.setSelected(false);
                    //dcLstDonation.setDate(null);
                    //dcLstDonation.setEnabled(false);
                    cbGender.setSelectedIndex(0);
                    cbBloodGroup.setSelectedIndex(0);
                    cbMaritalStts.setSelectedIndex(0);
                    cbDivision.setSelectedIndex(0);
                    cbDistrict.setSelectedIndex(0);
                    spAge.setValue(18);
                    lblImage.setIcon(resizeImage(new ImageIcon(getClass().getResource("images/user.png"))));
                }
            } catch (Exception ex) {
                System.out.println("AddNewEntry.getData():");
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewDonor frame = new AddNewDonor();
					( frame).setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}


}
