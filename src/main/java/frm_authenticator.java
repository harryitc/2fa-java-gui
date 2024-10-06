
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.Base64;
import org.apache.commons.codec.binary.Base32;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;
import io.reactivex.disposables.Disposable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.swing.Timer;
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author test
 */
public class frm_authenticator extends javax.swing.JFrame {

    private static final String REGISTRATION_FILE = "registration.key";
    private boolean isLoggedIn = false;

    private Logger logger = Logger.getLogger(frm_authenticator.class.getName());
    private final int TIME_STEP = 30;

    Timer time;

    private Disposable countdownDisposable; // Biến để quản lý countdown
    private boolean isCounting = false; // Để kiểm tra trạng thái countdown

//    private String ENDPOINT = "https://www.token2.com/tools/qrencode.php?otpauth://totp/TOKEN2:user@token2.com?secret=phanngtestoccuong&issuer=Token2";
//    String ASSETS = "2FA/resources/testDataIcons/filling.png";
//    ImageIcon imageIcon1 = new ImageIcon("test.png"); //100, 100 add your own size
//    ImageIcon imageIcon2 = new ImageIcon("thank.jpg"); //100, 100 add your own size
//    Image image = Toolkit.getDefaultToolkit().getImage("test.png");
    /**
     * Creates new form frm_authenticatore
     */
    public frm_authenticator() {
        initComponents();
        setLocationRelativeTo(this);

//        try {
//            getContentPane().add(new JPanelWithBackground("test.png"));
//        } catch (IOException e) {
//                        this.logger.log(Level.SEVERE, e.getMessage());
//            throw new RuntimeException(e);
//        }
        setSize(900, 640);

        this.lb_qrcode.setText("");
        this.txt_messageStatus.setText("");
        this.txt_time.setText("");

        // code Harryitc
//        try {
////            label1.setIcon(imageIcon1);
////            this.lb_qrcode.setText("");
//            this.lb_qrcode.setIcon(imageIcon1);
//        } catch (Exception e) {
//            this.logger.log(Level.SEVERE, e.getMessage());
//        }
//        this.txt_secret.lis((observable, oldValue, newValue) -> {
//            System.out.println("textfield changed from " + oldValue + " to " + newValue);
//        });
//        this.txt_secret.addKeyListener(event -> {
//            this.
//        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_time = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        btn_login = new javax.swing.JButton();
        pn_image = new javax.swing.JPanel();
        lb_qrcode = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_validate = new javax.swing.JTextField();
        txt_username = new javax.swing.JTextField();
        txt_secret = new javax.swing.JTextField();
        txt_token = new javax.swing.JTextField();
        btn_check = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_messageStatus = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_oauth = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        btn_Register = new javax.swing.JButton();
        txt_password = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Demo TOTP");
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Username");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Password");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, -1));

        txt_time.setText("time");
        getContentPane().add(txt_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 420, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("QRCode");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, -1, -1));

        jButton2.setText("Clean");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 570, 110, 30));

        btn_login.setText("login");
        btn_login.setEnabled(false);
        btn_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_loginMouseClicked(evt);
            }
        });
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });
        getContentPane().add(btn_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 90, 30));

        pn_image.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lb_qrcode.setText("jLabel12");

        javax.swing.GroupLayout pn_imageLayout = new javax.swing.GroupLayout(pn_image);
        pn_image.setLayout(pn_imageLayout);
        pn_imageLayout.setHorizontalGroup(
            pn_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_imageLayout.createSequentialGroup()
                .addComponent(lb_qrcode)
                .addGap(0, 253, Short.MAX_VALUE))
        );
        pn_imageLayout.setVerticalGroup(
            pn_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_imageLayout.createSequentialGroup()
                .addComponent(lb_qrcode)
                .addContainerGap(282, Short.MAX_VALUE))
        );

        getContentPane().add(pn_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 300, 300));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Secret key");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        txt_validate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_validateActionPerformed(evt);
            }
        });
        getContentPane().add(txt_validate, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 450, 130, 30));
        getContentPane().add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 200, 30));

        txt_secret.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_secret.setEnabled(false);
        txt_secret.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                txt_secretHierarchyChanged(evt);
            }
        });
        txt_secret.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txt_secretInputMethodTextChanged(evt);
            }
        });
        txt_secret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_secretActionPerformed(evt);
            }
        });
        txt_secret.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                textChangedHandler(evt);
            }
        });
        txt_secret.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_secretKeyPressed(evt);
            }
        });
        getContentPane().add(txt_secret, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 300, 30));

        txt_token.setEditable(false);
        txt_token.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txt_token.setText("******");
        txt_token.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_token.setDoubleBuffered(true);
        getContentPane().add(txt_token, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 450, 150, 90));

        btn_check.setText("Check");
        btn_check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_checkActionPerformed(evt);
            }
        });
        getContentPane().add(btn_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 450, 90, 30));

        jButton6.setText("Clear");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 60, 30));

        jButton7.setText("Clear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 60, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("OTPAuth");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Check Validation");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, -1, -1));

        txt_messageStatus.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_messageStatus.setText("valid");
        getContentPane().add(txt_messageStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 490, -1, -1));

        jLabel15.setText("*Note: reset everything in screen.");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 580, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Status:");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 490, -1, -1));

        txt_oauth.setEditable(false);
        txt_oauth.setColumns(20);
        txt_oauth.setLineWrap(true);
        txt_oauth.setRows(5);
        txt_oauth.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txt_oauth);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 400, 60));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Token");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        btn_Register.setText("Register");
        btn_Register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RegisterActionPerformed(evt);
            }
        });
        getContentPane().add(btn_Register, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, 30));
        getContentPane().add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 200, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        // TODO add your handling code here:
        String username = txt_username.getText();
        String password = new String(txt_password.getPassword());

        if (!username.isEmpty() && !password.isEmpty()) {
            isLoggedIn = true;
        } else {
            JOptionPane.showMessageDialog(this, "Username and Password cannot be empty.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

//        SecretGenerator secretGenerator = new DefaultSecretGenerator();
//        String secret = secretGenerator.generate();
//        this.txt_secret.setText(secret);

    }//GEN-LAST:event_btn_loginActionPerformed

    private void textChangedHandler(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_textChangedHandler
        // TODO add your handling code here:
    }//GEN-LAST:event_textChangedHandler

    private void txt_secretInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txt_secretInputMethodTextChanged
        // TODO add your handling code here:
        System.out.println("new value = " + this.txt_secret.getText());
        System.out.println("event = " + evt.getText());
    }//GEN-LAST:event_txt_secretInputMethodTextChanged

    private void txt_secretKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_secretKeyPressed

    }//GEN-LAST:event_txt_secretKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        final String EMPTY = "";
        this.txt_password.setText(EMPTY);
        this.txt_secret.setText(EMPTY);
        this.txt_username.setText(EMPTY);
        this.txt_validate.setText(EMPTY);
        this.txt_messageStatus.setText(EMPTY);
        this.lb_qrcode.setIcon(null);
        this.txt_token.setText("******");
        this.txt_validate.setText(EMPTY);
        this.txt_oauth.setText(EMPTY);
        this.txt_time.setText(EMPTY);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_validateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_validateActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_validateActionPerformed

    private void txt_secretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_secretActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_secretActionPerformed

    private void txt_secretHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_txt_secretHierarchyChanged
        // TODO add your handling code here:
        System.out.println("frm_authenticator.txt_secretHierarchyChanged()");
    }//GEN-LAST:event_txt_secretHierarchyChanged

    private void btn_loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_loginMouseClicked
        // TODO add your handling code here:
        System.out.println("frm_authenticator.jButton4MouseClicked() = " + this.txt_secret.getText());
        this.txt_secretKeyPressed(null);
    }//GEN-LAST:event_btn_loginMouseClicked

    private void btn_checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_checkActionPerformed
        // TODO add your handling code here:

        TimeProvider timeProvider = new SystemTimeProvider();

//        System.out.println("frm_authenticator.btn_checkActionPerformed() + " + timeProvider.getTime());
        // get n-digits code.
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier codeVerifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

        // secret = the shared secret for the user
        // code = the code submitted by the user
        boolean successful = codeVerifier.isValidCode(this.txt_secret.getText(), this.txt_validate.getText());
        System.out.println(this.txt_secret.getText() + " | " + this.txt_validate.getText() + successful);

        this.txt_messageStatus.setText(successful ? "VALID" : "INVALID");
    }//GEN-LAST:event_btn_checkActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_formKeyPressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        final String EMPTY = "";
        this.txt_username.setText(EMPTY);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        final String EMPTY = "";
        this.txt_password.setText(EMPTY);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btn_RegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RegisterActionPerformed
        // TODO add your handling code here:
        String username = txt_username.getText();
        String password = new String(txt_password.getPassword());

        if (!username.isEmpty() && !password.isEmpty()) {
            SecretGenerator secretGenerator = new DefaultSecretGenerator();
            this.txt_secret.setText(secretGenerator.generate());
            this.myLogic();
        } else {
            JOptionPane.showMessageDialog(this, "Username and Passowrd cannot be empty.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_RegisterActionPerformed

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
            java.util.logging.Logger.getLogger(frm_authenticator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_authenticator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_authenticator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_authenticator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_authenticator().setVisible(true);
            }
        });
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
        truncatedHash %= Math.pow(10, 6);
        // Left pad with 0s for a n-digit code
        return String.format("%0" + 6 + "d", truncatedHash);
    }

    long currentTime = TIME_STEP - (new SystemTimeProvider().getTime() % TIME_STEP);

    private void start(java.awt.event.ActionEvent evt) {
        TimeProvider timeProvider = new SystemTimeProvider();
        this.txt_time.setText(String.valueOf(TIME_STEP - (timeProvider.getTime() % TIME_STEP)));
        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String digits = "";
                TimeProvider timeProvider1 = new SystemTimeProvider();
                try {
                    currentTime = TIME_STEP - (timeProvider1.getTime() % TIME_STEP);
                    txt_time.setText(String.valueOf(currentTime));
                    digits = getDigitsFromHash(generateHash(txt_secret.getText(), timeProvider1.getTime() / TIME_STEP));
                    txt_token.setText(digits);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(frm_authenticator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(frm_authenticator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        time.start();
    }

    private void myLogic() {
        QrData data = new QrData.Builder()
                .label(new String(this.txt_password.getPassword()))
                .secret(this.txt_secret.getText())
                .issuer(this.txt_username.getText())
                .algorithm(HashingAlgorithm.SHA1) // More on this below
                .digits(6)
                .period(TIME_STEP)
                .build();

        String digits;
        try {
            TimeProvider timeProvider = new SystemTimeProvider();
            digits = this.getDigitsFromHash(this.generateHash(this.txt_secret.getText(), timeProvider.getTime() / TIME_STEP));
            this.txt_token.setText(digits);
            this.start(null);

        } catch (InvalidKeyException ex) {
            Logger.getLogger(frm_authenticator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(frm_authenticator.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.txt_oauth.setText(data.getUri());

        QrGenerator generator = new ZxingPngQrGenerator();
        try {
            byte[] imageData = generator.generate(data);
            String mimeType = generator.getImageMimeType();
            String dataUri = getDataUriForImage(imageData, mimeType);

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
                Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_DEFAULT);

                // Create ImageIcon and set it to JLabel
                ImageIcon imageIcon = new ImageIcon(scaledImage);

                this.lb_qrcode.setIcon(imageIcon);

            } catch (IOException ex) {
                Logger.getLogger(frm_authenticator.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (QrGenerationException ex) {
            Logger.getLogger(frm_authenticator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Register;
    private javax.swing.JButton btn_check;
    private javax.swing.JButton btn_login;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_qrcode;
    private javax.swing.JPanel pn_image;
    private javax.swing.JLabel txt_messageStatus;
    private javax.swing.JTextArea txt_oauth;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_secret;
    private javax.swing.JLabel txt_time;
    private javax.swing.JTextField txt_token;
    private javax.swing.JTextField txt_username;
    private javax.swing.JTextField txt_validate;
    // End of variables declaration//GEN-END:variables

}
