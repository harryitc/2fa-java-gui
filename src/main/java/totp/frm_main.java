package totp;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import lib_totp.code.CodeGenerator;
import lib_totp.code.CodeVerifier;
import lib_totp.code.DefaultCodeGenerator;
import lib_totp.code.DefaultCodeVerifier;
import lib_totp.code.HashingAlgorithm;
import lib_totp.exceptions.QrGenerationException;
import lib_totp.qr.QrData;
import lib_totp.qr.QrGenerator;
import lib_totp.qr.ZxingPngQrGenerator;
import lib_totp.secret.DefaultSecretGenerator;
import lib_totp.secret.SecretGenerator;
import lib_totp.time.SystemTimeProvider;
import lib_totp.time.TimeProvider;
import lib_totp.util.Utils;
import org.apache.commons.codec.binary.Base32;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author AnhEmPhanMem
 */
public class frm_main extends javax.swing.JFrame {

    Timer time, timeStamp;
    private Logger logger = Logger.getLogger(frm_main.class.getName());
    private final int TIME_STEP = 30;

    private final int MAX_WIDTH_QRCODE = 200;
    private final int MAX_HEIGHT_QRCODE = 200;

    private final String[] FIELDS_JSON = {"Username", "Hash", "Secret Key"};

    private String DEFAULT_PATH_FILE_NAME = System.getProperty("user.dir");
    private String FILENAME = "tableData.json";

    private final TimeProvider timeProvider = new SystemTimeProvider();

    private final int DELAY_PER_SECOND = 1000;

    private final int DIGIT_TOKEN = 6;

    private int indexUserLogined = -1;

    private boolean isUserLogined = false;

    private String currentUsername = "";

    private final ImageIcon imageValidStatus = new ImageIcon(new ImageIcon("src/assets/icons/valid.png").getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT));
    private final ImageIcon imageInvalidStatus = new ImageIcon(new ImageIcon("src/assets/icons/invalid.png").getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT));
    private final ImageIcon imageDeleteStatus = new ImageIcon(new ImageIcon("src/assets/icons/delete.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
    private final ImageIcon imageClearStatus = new ImageIcon(new ImageIcon("src/assets/icons/clear.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
    private final ImageIcon imageCheckStatus = new ImageIcon(new ImageIcon("src/assets/icons/check.png").getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
    private final ImageIcon imageIconQrcode = new ImageIcon(new ImageIcon("src/assets/icons/logo-big.png").getImage().getScaledInstance(40, 45, Image.SCALE_SMOOTH));
    private final DefaultTableModel tableModel;

    public frm_main() {
        initComponents();
        this.tableModel = (DefaultTableModel) this.tb_accountpool.getModel();

        this.lb_qrcode.setText("");
        this.lb_tokenStatus.setText("");
        this.txt_directory.setText(DEFAULT_PATH_FILE_NAME + FILENAME);

        this.btn_delete.setIcon(this.imageDeleteStatus);
        this.btn_clean.setIcon(imageClearStatus);
        this.btn_checkall.setIcon(imageCheckStatus);

        this.txt_directory.setText("File path");

        this.time = new Timer(DELAY_PER_SECOND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String digits = "";
                try {
                    currentTime = TIME_STEP - (timeProvider.getTime() % TIME_STEP);

                    if (tableModel.getRowCount() > 0) {
                        // Hiện thời gian hay không?
                        if (isUserLogined) {
                            lb_timer.setText(String.valueOf(currentTime) + "s");
                            digits = getDigitsFromHash(generateHash(tableModel.getValueAt(indexUserLogined, FieldTable.SECRET_KEY).toString(), timeProvider.getTime() / TIME_STEP));
                            txt_otpToken.setText(digits);
                        } else {
                        }
                        updateTimerInTable();
                        updateTokenInTable();
                    }
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        this.time.start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lb_title = new javax.swing.JLabel();
        lb_username = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        lb_password = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        btn_login = new javax.swing.JButton();
        btn_register = new javax.swing.JButton();
        lb_icon = new javax.swing.JLabel();
        lb_qrcode = new javax.swing.JLabel();
        txt_checktoken = new javax.swing.JTextField();
        lb_checkOTP = new javax.swing.JLabel();
        lb_tokenStatus = new javax.swing.JLabel();
        txt_otpToken = new javax.swing.JTextField();
        btn_copy = new javax.swing.JButton();
        lb_OTPtoken = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_accountpool = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_otpauth = new javax.swing.JTextArea();
        lb_otpauth = new javax.swing.JLabel();
        lb_timer = new javax.swing.JLabel();
        btn_checktoken = new javax.swing.JButton();
        btn_openFile = new javax.swing.JButton();
        btn_saveFile = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_directory = new javax.swing.JTextArea();
        btn_checkall = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_clean = new javax.swing.JButton();

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TOTP");
        setMinimumSize(new java.awt.Dimension(600, 600));
        setName("TOTP"); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(580, 570));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_title.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        lb_title.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_title.setText("TOTP");
        lb_title.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lb_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 8, -1, -1));

        lb_username.setText("Username:");
        getContentPane().add(lb_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 61, -1, -1));
        getContentPane().add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 83, 200, -1));

        lb_password.setText("Password:");
        getContentPane().add(lb_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 111, -1, -1));
        getContentPane().add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 133, 200, -1));

        btn_login.setText("Login");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });
        getContentPane().add(btn_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, -1, -1));

        btn_register.setText("Register");
        btn_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registerActionPerformed(evt);
            }
        });
        getContentPane().add(btn_register, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 161, -1, -1));

        lb_icon.setPreferredSize(new java.awt.Dimension(60, 60));
        getContentPane().add(lb_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 40, 40));

        lb_qrcode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_qrcode.setText("QRCode Is HERE!!!");
        lb_qrcode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lb_qrcode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lb_qrcode.setPreferredSize(new java.awt.Dimension(200, 200));
        getContentPane().add(lb_qrcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));
        getContentPane().add(txt_checktoken, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 208, 124, -1));

        lb_checkOTP.setText("Check Token TOTP");
        getContentPane().add(lb_checkOTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        lb_tokenStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_tokenStatus.setText("Token Status");
        lb_tokenStatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lb_tokenStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 40, 40));

        txt_otpToken.setFont(new java.awt.Font("Helvetica Neue", 1, 48)); // NOI18N
        txt_otpToken.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_otpToken.setText("######");
        getContentPane().add(txt_otpToken, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 270, 190, 78));

        btn_copy.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        btn_copy.setText("Copy");
        btn_copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_copyActionPerformed(evt);
            }
        });
        getContentPane().add(btn_copy, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 70, 20));

        lb_OTPtoken.setText("OTP Token");
        getContentPane().add(lb_OTPtoken, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, -1, -1));

        tb_accountpool.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Hash", "Secret Key", "Token", "S", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_accountpool.setCellSelectionEnabled(true);
        tb_accountpool.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tb_accountpool.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_accountpoolMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_accountpool);
        tb_accountpool.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tb_accountpool.getColumnModel().getColumnCount() > 0) {
            tb_accountpool.getColumnModel().getColumn(0).setMinWidth(75);
            tb_accountpool.getColumnModel().getColumn(0).setPreferredWidth(75);
            tb_accountpool.getColumnModel().getColumn(0).setMaxWidth(200);
            tb_accountpool.getColumnModel().getColumn(1).setMinWidth(75);
            tb_accountpool.getColumnModel().getColumn(1).setPreferredWidth(75);
            tb_accountpool.getColumnModel().getColumn(1).setMaxWidth(200);
            tb_accountpool.getColumnModel().getColumn(3).setMinWidth(60);
            tb_accountpool.getColumnModel().getColumn(3).setPreferredWidth(60);
            tb_accountpool.getColumnModel().getColumn(3).setMaxWidth(60);
            tb_accountpool.getColumnModel().getColumn(4).setMinWidth(32);
            tb_accountpool.getColumnModel().getColumn(4).setPreferredWidth(32);
            tb_accountpool.getColumnModel().getColumn(4).setMaxWidth(32);
            tb_accountpool.getColumnModel().getColumn(5).setMinWidth(25);
            tb_accountpool.getColumnModel().getColumn(5).setPreferredWidth(25);
            tb_accountpool.getColumnModel().getColumn(5).setMaxWidth(25);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 355, 520, 120));

        txt_otpauth.setColumns(20);
        txt_otpauth.setLineWrap(true);
        txt_otpauth.setRows(5);
        jScrollPane2.setViewportView(txt_otpauth);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 271, 300, 78));

        lb_otpauth.setText("OTPAuth");
        getContentPane().add(lb_otpauth, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        lb_timer.setText("XX");
        getContentPane().add(lb_timer, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, -1, -1));

        btn_checktoken.setText("Check");
        btn_checktoken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_checktokenActionPerformed(evt);
            }
        });
        getContentPane().add(btn_checktoken, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 208, -1, -1));

        btn_openFile.setText("Open");
        btn_openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_openFileActionPerformed(evt);
            }
        });
        getContentPane().add(btn_openFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 480, 70, -1));

        btn_saveFile.setText("Save");
        btn_saveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveFileActionPerformed(evt);
            }
        });
        getContentPane().add(btn_saveFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 510, 70, -1));

        txt_directory.setEditable(false);
        txt_directory.setColumns(20);
        txt_directory.setLineWrap(true);
        txt_directory.setRows(5);
        txt_directory.setMaximumSize(new java.awt.Dimension(200, 30));
        txt_directory.setMinimumSize(new java.awt.Dimension(100, 20));
        jScrollPane3.setViewportView(txt_directory);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 440, 60));

        btn_checkall.setToolTipText("CheckAll");
        btn_checkall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_checkallActionPerformed(evt);
            }
        });
        getContentPane().add(btn_checkall, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 360, 30, 30));

        btn_delete.setToolTipText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        getContentPane().add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 400, 30, 30));

        btn_clean.setToolTipText("Clean");
        btn_clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_clean, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 440, 30, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btn_registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registerActionPerformed

        String username = txt_username.getText();
        String password = new String(txt_password.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.isExistedUsernameInTable(username)) {
            JOptionPane.showMessageDialog(this, "Username is existed in table.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.txt_otpToken.setText("######");
        this.lb_timer.setText("...s");

        SecretGenerator secretGenerator = new DefaultSecretGenerator();
        String secret = secretGenerator.generate();

        // get hash
        String hash = SHA.Hash(password, secret);

        Object[] rowData = new Object[this.tableModel.getColumnCount()];
        int col = 0;
        rowData[col++] = username;
        rowData[col++] = hash;
        rowData[col++] = secret;
        rowData[FieldTable.CHECKBOX] = false;
        rowData[col++] = this.getToken(secret);
        this.tableModel.addRow(rowData);

        this.isUserLogined = false;
        this.indexUserLogined = -1;

        this.generateQR();

        JOptionPane.showMessageDialog(this, "Register successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btn_registerActionPerformed

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed

        String username = this.txt_username.getText();
        this.currentUsername = username;

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!this.isUserInDatabase(username)) {
            JOptionPane.showMessageDialog(this, "Username is not registered. Please register",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.isUserLogined = true;
        this.indexUserLogined = this.getIndexByUsername(username);

        String secretKey = this.tableModel.getValueAt(this.indexUserLogined, FieldTable.SECRET_KEY).toString();
        String token = this.getToken(secretKey);
        this.txt_otpToken.setText(token);
        this.startTime(null);

        JOptionPane.showMessageDialog(this, "Logined success.",
                "Success", JOptionPane.OK_CANCEL_OPTION);

    }//GEN-LAST:event_btn_loginActionPerformed

    private void btn_copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_copyActionPerformed

        StringSelection stringSelection = new StringSelection(this.txt_otpToken.getText());
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);

    }//GEN-LAST:event_btn_copyActionPerformed

    private void btn_checktokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_checktokenActionPerformed

        if (!isUserLogined) {
            JOptionPane.showMessageDialog(this, "You must be login before check token",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!this.currentUsername.equals(this.txt_username.getText())) {
            JOptionPane.showMessageDialog(this, "Field username have changed. You must be login again!\nBefore value: '" + this.currentUsername + "'.\nAfter (current) value: '" + this.txt_username.getText() + "'.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Checktoken text box cant not be empty, otherwise it will pop up an error message
        if (this.txt_checktoken.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "The token can't not be empty",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // get n-digits code.
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier codeVerifier = new DefaultCodeVerifier(codeGenerator, this.timeProvider);
        // secret = the shared secret for the user
        // code = the code submitted by the user
        String secret = this.tableModel.getValueAt(indexUserLogined, FieldTable.SECRET_KEY).toString();
        String code = this.txt_checktoken.getText();
        boolean successful = codeVerifier.isValidCode(secret, code);

        String password = new String(this.txt_password.getPassword());
        String hash = SHA.Hash(password, secret);

        if (successful && isSameHash(hash)) {
            this.lb_tokenStatus.setIcon(this.imageValidStatus);
        } else {
            this.lb_tokenStatus.setIcon(this.imageInvalidStatus);
            JOptionPane.showMessageDialog(this, "Unauthorized: Uncorrectly password or OTP.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_checktokenActionPerformed

    private void btn_openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_openFileActionPerformed

        JFileChooser fileChooser = new JFileChooser(this.DEFAULT_PATH_FILE_NAME);
        fileChooser.setDialogTitle("Open File TOTP");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try (FileReader reader = new FileReader(fileToOpen)) {
                // Parse JSON từ file
                JSONTokener tokener = new JSONTokener(reader);
                JSONArray jsonArray = new JSONArray(tokener);

                if (jsonArray.length() == 0) {
                    JOptionPane.showMessageDialog(this, "No data to import!",
                            "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int indexSameUsername = 0;

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject rowObject = jsonArray.getJSONObject(i);
                    Object[] rowData = new Object[this.tableModel.getColumnCount()];

                    // Nếu dữ liệu từ file giống trùng username thì bỏ qua
                    if (this.isExistedUsernameInTable((String) rowObject.get(FIELDS_JSON[FieldTable.USERNAME]))) {
                        indexSameUsername++;
                        continue;
                    }

                    // Lấy từng giá trị của mỗi cột theo tên cột
                    for (int col = 0; col < FIELDS_JSON.length; col++) {
                        rowData[col] = rowObject.get(FIELDS_JSON[col]);
                    }

                    rowData[FieldTable.TOKEN] = this.getToken((String) rowData[FieldTable.SECRET_KEY]);
                    rowData[FieldTable.CHECKBOX] = false;
                    this.startTime(null);
                    // Thêm hàng vào model
                    this.tableModel.addRow(rowData);

                }

                if (indexSameUsername == jsonArray.length()) {
                    JOptionPane.showMessageDialog(this, "You have been imported!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            String nameFile = fileToOpen.getAbsoluteFile().getAbsolutePath();
            txt_directory.setText(nameFile);
        }

    }//GEN-LAST:event_btn_openFileActionPerformed

    private void btn_saveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveFileActionPerformed

        JSONArray jsonArray = new JSONArray();

        // Duyệt qua các hàng và cột trong JTable
        for (int row = 0; row < this.tableModel.getRowCount(); row++) {
            JSONObject rowObject = new JSONObject();
            for (int col = 0; col <= FieldTable.SECRET_KEY; col++) { // model.getColumnCount()
                String columnName = this.tableModel.getColumnName(col);
                Object cellValue = this.tableModel.getValueAt(row, col);
                rowObject.put(columnName, cellValue);  // Gán giá trị cho từng cột
            }
            jsonArray.put(rowObject);  // Thêm hàng vào mảng JSON
        }

        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String fileName = fileToSave.getName();

            // Kiểm tra nếu người dùng không nhập phần mở rộng
            if (!fileName.contains(".")) {
                // Mặc định thêm đuôi .json nếu người dùng không nhập đuôi
                fileToSave = new File(fileToSave.getAbsolutePath() + ".json");
            }

            try (FileWriter fileWriter
                    = new FileWriter(fileToSave)) {
                fileWriter.write(jsonArray.toString(4));  // Ghi dữ liệu với indent = 4. // Định dạng đẹp với thụt đầu dòng 4 khoảng trắng
                fileWriter.flush();
                String nameFile = fileToSave.getAbsolutePath();
                txt_directory.setText(nameFile);
                JOptionPane.showMessageDialog(this, "Data saved successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_btn_saveFileActionPerformed

    private void tb_accountpoolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_accountpoolMouseClicked
    }//GEN-LAST:event_tb_accountpoolMouseClicked

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        //Khi table chưa có giá trị
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "None of all user to delete");
            return;
        }

        if (isCurrentUserLoginedSeletedToDeleted()) {
            JOptionPane.showMessageDialog(this, "Cannot delete account currently logging-in!");
            return;
        }

        //Khi checkbox được tích lên
        int response = JOptionPane.showConfirmDialog(this, "Do you want to delete the selected user(s)?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (response != JOptionPane.YES_OPTION) {
            return;
        }

        Boolean hasDataToDeleted = false;
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            Boolean isChecked = (Boolean) tableModel.getValueAt(i, FieldTable.CHECKBOX);
            if (isChecked != null && isChecked) {
                hasDataToDeleted = true;
                tableModel.removeRow(i);  // Xóa dòng đã chọn
            }
        }

        // Khi xóa hết dữ liệu trong table thì tự động reset lại form.
        if (hasDataToDeleted && this.tableModel.getRowCount() == 0) {
            this.reset_form();
        }

    }//GEN-LAST:event_btn_deleteActionPerformed

    private boolean isCurrentUserLoginedSeletedToDeleted() {
        for (int i = this.tableModel.getRowCount() - 1; i >= 0; i--) {
            Boolean isChecked = (Boolean) this.tableModel.getValueAt(i, FieldTable.CHECKBOX);
            if (isChecked != null && isChecked && i == this.indexUserLogined) {
                return true;
            }
        }
        return false;
    }

    private boolean isUserInDatabase(String username) {
        for (int i = 0; i < this.tableModel.getRowCount(); i++) {
            if (username.equals(this.tableModel.getValueAt(i, FieldTable.USERNAME).toString())) {
                return true;
            }
        }
        return false;
    }

//Default checked list = False
    private boolean isCheckedAll = false;

    private void updateCheckBox() {
        int countTrue = 0;
        for (int i = 0; i < this.tableModel.getRowCount(); i++) {
            boolean isChecked = (boolean) tableModel.getValueAt(i, FieldTable.CHECKBOX);
            if (isChecked) {
                countTrue++;
            }
        }
        if (countTrue == this.tableModel.getRowCount()) {
            isCheckedAll = false;
            return;
        }
        if (countTrue == 0) {
            isCheckedAll = true;
            return;
        }

        this.isCheckedAll = !this.isCheckedAll;
    }

    //Button to check all the check box
    private void btn_checkallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_checkallActionPerformed
        // TODO add your handling code here:
        updateCheckBox();

        for (int i = 0; i < this.tableModel.getRowCount(); i++) {
            this.tb_accountpool.setValueAt(this.isCheckedAll, i, FieldTable.CHECKBOX);
        }
    }//GEN-LAST:event_btn_checkallActionPerformed

    private void btn_cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanActionPerformed
        // TODO add your handling code here:
        this.reset_form();
    }//GEN-LAST:event_btn_cleanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_main().setVisible(true);
            }
        });
    }

    public void updateTableWithNewData(JTable table, DefaultTableModel newTableModel) {
        // Lấy model hiện tại của bảng
        DefaultTableModel currentTableModel = (DefaultTableModel) table.getModel();

        // Xóa tất cả hàng hiện tại
        currentTableModel.setRowCount(0);

        // Duyệt qua các hàng trong model mới
        for (int i = 0; i < newTableModel.getRowCount(); i++) {
            // Tạo mảng Object để chứa dữ liệu hàng mới
            Object[] rowData = new Object[newTableModel.getColumnCount()];

            // Lấy dữ liệu từ từng hàng trong model mới
            for (int j = 0; j < newTableModel.getColumnCount(); j++) {
                rowData[j] = newTableModel.getValueAt(i, j);
            }

            // Thêm hàng mới vào model hiện tại
            currentTableModel.addRow(rowData);
        }
    }

    private void reset_form() {
        final String EMPTY = "";
        this.txt_password.setText(EMPTY);
        this.txt_username.setText(EMPTY);
        this.txt_otpauth.setText(EMPTY);
        this.lb_tokenStatus.setIcon(null);
        this.lb_qrcode.setIcon(null);
        this.txt_otpToken.setText("######");
        this.lb_timer.setText(EMPTY);
        this.txt_directory.setText("File path.");
        this.txt_checktoken.setText(EMPTY);
        this.time.stop();
        this.isUserLogined = false;
        this.indexUserLogined = -1;

        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);  // Xóa dòng đã chọn
        }
        //restart timer when press clean botton
        this.time.start();
    }

    long currentTime = TIME_STEP - (timeProvider.getTime() % TIME_STEP);

    private void startTime(java.awt.event.ActionEvent evt) {
        // Hiện thời gian hay không?
        if (this.isUserLogined) {
            this.lb_timer.setText(String.valueOf(TIME_STEP - (timeProvider.getTime() % TIME_STEP)) + "s");
        }
    }

    private Boolean isSameHash(String hash) {
        return hash.equals(this.tableModel.getValueAt(this.indexUserLogined, FieldTable.HASH).toString());
    }

    private int getIndexByUsername(String username) {
        for (int i = 0; i < this.tableModel.getRowCount(); i++) {
            if (username.equals(this.tableModel.getValueAt(i, FieldTable.USERNAME).toString())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Generate a HMAC-SHA1 hash of the counter number.
     */
    private byte[] generateHash(String key, long counter) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] data = new byte[8];
        long value = counter;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        // Create a HMAC-SHA1 signing key from the shared key
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(key);
        SecretKeySpec signKey = new SecretKeySpec(decodedKey, HashingAlgorithm.SHA1.getHmacAlgorithm());
        Mac mac = Mac.getInstance(HashingAlgorithm.SHA1.getHmacAlgorithm());
        mac.init(signKey);
        // Create a hash of the counter value
        return mac.doFinal(data);
    }

    /**
     * Get the n-digit code for a given hash.
     */
    private String getDigitsFromHash(byte[] hash) {
        int offset = hash[hash.length - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= Math.pow(10, DIGIT_TOKEN);
        // Left pad with 0s for a n-digit code
        return String.format("%0" + DIGIT_TOKEN + "d", truncatedHash);
    }

    private String getToken(String secretKey) {
        String digits = "";
        try {
            TimeProvider timeProvider = new SystemTimeProvider();
            digits = this.getDigitsFromHash(this.generateHash(secretKey, timeProvider.getTime() / TIME_STEP));
        } catch (InvalidKeyException ex) {
            Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return digits;
    }

    private void generateQR() {
        QrData data = new QrData.Builder()
                .label(this.txt_username.getText())
                .secret(this.tableModel.getValueAt(this.tableModel.getRowCount() - 1, FieldTable.SECRET_KEY).toString())
                //                .secret(this.tableModel.getValueAt(indexUserLogined, FieldTable.SECRET_KEY).toString())
                .issuer("AeSoftware")
                .algorithm(HashingAlgorithm.SHA1) // More on this below
                .digits(DIGIT_TOKEN)
                .period(TIME_STEP)
                .build();

        this.txt_otpauth.setText(data.getUri());

        QrGenerator generator = new ZxingPngQrGenerator();
        try {
            byte[] imageData = generator.generate(data);
            String mimeType = generator.getImageMimeType();
            String dataUri = Utils.getDataUriForImage(imageData, mimeType);

            // Remove the prefix if the base64 string has "data:image/png;base64," or similar
            String base64Image = dataUri.split(",")[1];
            // Decode base64 string into byte array
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // Convert byte array into BufferedImage
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage image;
            try {
                image = ImageIO.read(bis);
                // Scale the image if necessary
                Image scaledImage = image.getScaledInstance(MAX_WIDTH_QRCODE, MAX_HEIGHT_QRCODE, Image.SCALE_DEFAULT);

                // Create ImageIcon and set it to JLabel
                ImageIcon imageIcon = new ImageIcon(scaledImage);

                this.lb_qrcode.setIcon(imageIcon);

                // set icon in the middle of the qrcode
                this.lb_icon.setIcon(this.imageIconQrcode);

            } catch (IOException ex) {
                Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (QrGenerationException ex) {
            Logger.getLogger(frm_main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //update timer in table

    private void updateTimerInTable() {
        TimeProvider timeProvider = new SystemTimeProvider();
        for (int i = 0; i < this.tableModel.getRowCount(); i++) {
            long currentTime = TIME_STEP - (timeProvider.getTime() % TIME_STEP);
            this.tableModel.setValueAt(String.valueOf(currentTime) + "s", i, 4);
        }
    }
//update token in table

    private void updateTokenInTable() {

        for (int i = 0; i < this.tableModel.getRowCount(); i++) {
            this.tableModel.setValueAt(this.getToken(this.tableModel.getValueAt(i, FieldTable.SECRET_KEY).toString()), i, FieldTable.TOKEN);
        }
    }

    private boolean isExistedUsernameInTable(String username) {

        for (int i = 0; i < this.tableModel.getRowCount(); i++) {
            if (username.equals(this.tableModel.getValueAt(i, FieldTable.USERNAME))) {
                return true;
            }
        }

        return false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_checkall;
    private javax.swing.JButton btn_checktoken;
    private javax.swing.JButton btn_clean;
    private javax.swing.JButton btn_copy;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_login;
    private javax.swing.JButton btn_openFile;
    private javax.swing.JButton btn_register;
    private javax.swing.JButton btn_saveFile;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lb_OTPtoken;
    private javax.swing.JLabel lb_checkOTP;
    private javax.swing.JLabel lb_icon;
    private javax.swing.JLabel lb_otpauth;
    private javax.swing.JLabel lb_password;
    private javax.swing.JLabel lb_qrcode;
    private javax.swing.JLabel lb_timer;
    private javax.swing.JLabel lb_title;
    private javax.swing.JLabel lb_tokenStatus;
    private javax.swing.JLabel lb_username;
    private javax.swing.JTable tb_accountpool;
    private javax.swing.JTextField txt_checktoken;
    private javax.swing.JTextArea txt_directory;
    private javax.swing.JTextField txt_otpToken;
    private javax.swing.JTextArea txt_otpauth;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
