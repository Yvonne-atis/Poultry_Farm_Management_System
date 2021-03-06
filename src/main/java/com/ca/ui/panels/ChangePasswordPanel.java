package com.ca.ui.panels;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ca.db.model.LoginUser;
import com.ca.db.service.LoginUserServiceImpl;
import com.gt.common.ResourceManager;
import com.gt.common.constants.StrConstants;
import com.gt.common.utils.StringUtils;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ChangePasswordPanel extends AbstractFunctionPanel {
    JPanel fullPanel;
    JPanel innerPanel;
    private JTextField userName;
    private JPasswordField passWord;
    private JLabel lblDept;
    private JLabel lblInventoryManagementSystem;
    private JLabel lblTitile;
    private JLabel lblImg;
    private JTextField txtNewUsrName;
    private JPasswordField txtNewPass1;
    private JPasswordField txtNewpass;

    public ChangePasswordPanel() {
        add(getLoginPanel());
        init();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame jf = new JFrame();
                ChangePasswordPanel panel = new ChangePasswordPanel();
                jf.setBounds(panel.getBounds());
                jf.getContentPane().add(panel);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private JPanel getLoginPanel() {
        fullPanel = new JPanel();
        fullPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel = new JPanel();
        innerPanel.setBounds(41, 96, 336, 144);
        fullPanel.add(innerPanel);
        innerPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(18dlu;default)"),
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(21dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(61dlu;default):grow"),
                FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(65dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(65dlu;default)"),}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(20dlu;default)"),
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(16dlu;default)"),
                FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(16dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
                RowSpec.decode("max(17dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(17dlu;default)"),
                FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(18dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

        lblImg = new JLabel("img");
        lblImg.setIcon(ResourceManager.getImageIcon("logo2.png"));
        innerPanel.add(lblImg, "2, 6, 3, 5");

        lblTitile = new JLabel(ResourceManager.getString(StrConstants.COMPANY_NAME));
        lblTitile.setFont(new Font("Tahoma", Font.BOLD, 16));
        innerPanel.add(lblTitile, "6, 6, 7, 1, left, default");

        lblDept = new JLabel(ResourceManager.getString(StrConstants.DEPARTMENT));
        lblDept.setFont(new Font("Tahoma", Font.BOLD, 13));
        innerPanel.add(lblDept, "6, 8, 7, 1, left, default");

        lblInventoryManagementSystem = new JLabel(ResourceManager.getString(StrConstants.APP_TITLE));
        lblInventoryManagementSystem.setFont(new Font("Tahoma", Font.BOLD, 13));
        innerPanel.add(lblInventoryManagementSystem, "6, 10, 7, 1, left, default");

        JLabel lblNewLabel = new JLabel("Username :");
        innerPanel.add(lblNewLabel, "4, 14, left, default");

        userName = new JTextField();
        innerPanel.add(userName, "8, 14, 3, 1, fill, fill");


        JLabel lblPassword = new JLabel("Password :");
        innerPanel.add(lblPassword, "4, 16, left, default");

        passWord = new JPasswordField();


        innerPanel.add(passWord, "8, 16, 3, 1, fill, fill");

        JLabel lblNewUsername = new JLabel("New username:");
        innerPanel.add(lblNewUsername, "4, 18");

        txtNewUsrName = new JTextField();
        innerPanel.add(txtNewUsrName, "8, 18, 3, 1, fill, fill");
        txtNewUsrName.setColumns(10);

        JLabel lblNewPassword = new JLabel("New Password:");
        innerPanel.add(lblNewPassword, "4, 20");

        txtNewPass1 = new JPasswordField();
        innerPanel.add(txtNewPass1, "8, 20, 3, 1, fill, fill");
        
        JLabel lblRetypePassword = new JLabel("Confirm Password:");
        innerPanel.add(lblRetypePassword, "4, 22");

        txtNewpass = new JPasswordField();
        innerPanel.add(txtNewpass, "8, 22, 3, 1, fill, fill");

        JButton loginButton = new JButton("Reset");
        innerPanel.add(loginButton, "8, 24, fill, default");
        JButton restPassword = new JButton("Undo");
        innerPanel.add(restPassword, "10, 24");
        restPassword.addActionListener(e -> clearAll());
        loginButton.addActionListener(e -> change());
        return fullPanel;
    }

    protected final void clearAll() {
        UIUtils.clearAllFields(innerPanel);

    }

    private void change() {

        if (StringUtils.isEmpty(txtNewpass.getText()) || StringUtils.isEmpty(userName.getText()) || StringUtils.isEmpty(passWord.getText())
                || StringUtils.isEmpty(txtNewPass1.getText())) {
            JOptionPane.showMessageDialog(null, "Please enter new and old username/password as required");
            return;
        }
        if (!txtNewpass.getText().trim().equals(txtNewPass1.getText().trim())) {
            JOptionPane.showMessageDialog(null, "password does not match");
            return;
        }

        LoginUserServiceImpl lus;
        try {
            lus = new LoginUserServiceImpl();
            LoginUser user = LoginUserServiceImpl.getLoginUser(userName.getText().trim(), passWord.getText().trim());
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Original Username Password Error");
                passWord.setText("");
                userName.requestFocus();
                return;
            }

            lus = new LoginUserServiceImpl();
            // finally change password/username
            LoginUserServiceImpl.changeLogin(txtNewUsrName.getText().trim(), txtNewpass.getText().trim());

            JOptionPane.showMessageDialog(null, "Username/Password Changed Successfully\n New UserName is " + txtNewUsrName.getText().trim());
            clearAll();
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "DB Connection Error");
        }

    }

    @Override
    public final String getFunctionName() {
        return "Login";
    }

    @Override
    public void handleSaveAction() {

    }

    @Override
    public final void init() {
        super.init();
        userName.requestFocus();
        isReadyToClose = true;
    }

    @Override
    public void enableDisableComponents() {
        // TODO Auto-generated method stub

    }

}