package com.gt.uilib.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import com.ca.ui.panels.AboutPanel;
import com.ca.ui.panels.ChangePasswordPanel;
import com.gt.common.ResourceManager;
import com.gt.common.constants.StrConstants;
import com.gt.common.utils.Logger;
import com.gt.uilib.components.button.ActionButton;
import com.gt.uilib.components.button.ExitButton;
import com.gt.uilib.components.button.LogOutButton;

/**
 *
 * @author IVONEE ONIRTH
 * com.ca.ui-AppFrame.java
 */
public class AppFrame extends JFrame {

    public static final String loginPanel = "com.ca.ui.panels.LoginPanel";

    public static AbstractFunctionPanel currentWindow;
    public static boolean isLoggedIn = false;
    public static boolean debug = true;
    private static AppFrame _instance;
    private static JMenuBar menuBar;
    private static JPanel bodyPanel;
    private static JPanel toolBarPanel;
    WindowListener exitListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
        }
    };
    private JLabel statusLbl;

    private AppFrame() {
        setTitle(StrConstants.APP_TITLE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 850, 500);

        this.setIconImage(ResourceManager.getImage("logo2.png"));
        
        getContentPane().setLayout(new BorderLayout(0, 0));
        setJMenuBar(getMenuBarr());
        getContentPane().add(getStatusPanel(), BorderLayout.SOUTH);
        getContentPane().add(getBodyPanel(), BorderLayout.CENTER);
        getContentPane().add(getToolBarPanel(), BorderLayout.NORTH);
        
        addWindowListener(exitListener);
        
        currentWindow = getFunctionPanelInstance(loginPanel);
        setWindow(currentWindow);
        
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();

        setMaximizedBounds(e.getMaximumWindowBounds());
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

    }

    public static AppFrame getInstance() {
        if (_instance == null) {
            _instance = new AppFrame();
        }
        return _instance;
    }

    public static void loginSuccess() {
        isLoggedIn = true;
        getInstance().setWindow("com.ca.ui.panels.HomeScreenPanel");
        Logger.I("logged in");
    }

    private JMenuBar getMenuBarr() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            /**
             * First menu -
             */
            JMenu startMnu = new JMenu("Application");
            JMenuItem logOutMnuItem = new JMenuItem("Log Out");
            logOutMnuItem.addActionListener(e -> LogOutButton.handleLogout());
            startMnu.add(logOutMnuItem);
            JMenuItem exitMnuItem = new JMenuItem("Close");
            exitMnuItem.addActionListener(e -> ExitButton.handleExit());
            startMnu.add(exitMnuItem);
            menuBar.add(startMnu);
            /**
             * Entry Menu
             */
            JMenu entryMenu = new JMenu("Entry");
           // entryMenu.add(ActionMenuItem.create("New Expense", "sitementry", "com.ca.ui.panels.ItemEntryPanel"));
            //entryMenu.add(ActionMenuItem.create("Flock Management", "sitemnikasha", "com.ca.ui.panels.ItemTransferPanel"));
           // entryMenu.add(ActionMenuItem.create("New Treatment", "sitemnikasha", "com.ca.ui.panels.ItemReturnPanel"));
           // entryMenu.add(new JSeparator());
            JMenu initRecordMenuSub = new JMenu("Initial Records");

            initRecordMenuSub.add(ActionMenuItem.create("Add Breed", "a", "com.ca.ui.panels.CategoryPanel"));
            initRecordMenuSub.add(ActionMenuItem.create("Add Supplier", "vendor", "com.ca.ui.panels.VendorPanel"));
            initRecordMenuSub.add(ActionMenuItem.create("Add Customer", "user", "com.ca.ui.panels.PersonPanel"));
            initRecordMenuSub.add(ActionMenuItem.create("Add Sale Item", "a", "com.ca.ui.panels.BranchOfficePanel"));
            initRecordMenuSub.add(ActionMenuItem.create("Add Expense Type", "a", "com.ca.ui.panels.UnitsStringPanel"));
            entryMenu.add(initRecordMenuSub);
            menuBar.add(entryMenu);

            /**
             * Search
             */
            JMenu searchMnu = new JMenu("Search");
            searchMnu.add(ActionMenuItem.create("Hatchery Query", "sfind", "com.ca.ui.panels.StockQueryPanel"));
            searchMnu.add(ActionMenuItem.create("Mortality query", "sfind", "com.ca.ui.panels.TransferQueryPanel"));
            searchMnu.add(ActionMenuItem.create("Treatment Query", "sfind", "com.ca.ui.panels.ReturnQueryPanel"));
            menuBar.add(searchMnu);

            /**
             * View All Reports
             */
            JMenu reportsMenu = new JMenu("Reports");
            reportsMenu.add(ActionMenuItem.create("Summaries ", "sfind", "com.ca.ui.report.LedgerReportPanel"));
            menuBar.add(reportsMenu);
            /**
             * Tools Menu This ActionMenuItem should be displayed on JDialog
             */
            JMenu toolsMenu = new JMenu("Tools");
            //toolsMenu.add(ActionMenuItem.create("Account Transfer/Close", "backup", "com.ca.ui.panels.AccountCloseItemEntryPanel"));
            //toolsMenu.add(new JSeparator());
            JMenuItem jmChang = new JMenuItem("Change UserName/Password");
            jmChang.addActionListener(e -> {
                if (isLoggedIn) {
                    GDialog cd = new GDialog(AppFrame.this, "Change Username/Password", true);
                    ChangePasswordPanel vp = new ChangePasswordPanel();
                    cd.setAbstractFunctionPanel(vp, new Dimension(480, 340));
                    cd.setResizable(false);
                    cd.setVisible(true);
                    cd.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            // setWindow("com.ca.ui.panels.HomeScreenPanel");
                            handleLogOut();
                        }
                    });

                }

            });
            toolsMenu.add(jmChang);
            menuBar.add(toolsMenu);
            /**
             * Last Menu
             */
            JMenu helpMenu = new JMenu("Help");
            JMenuItem readmanualItem = new JMenuItem("Read Manual");
            helpMenu.add(readmanualItem);
            helpMenu.add(new JSeparator());
            JMenuItem supportMnu = new JMenuItem("Support");
            helpMenu.add(supportMnu);

            readmanualItem.addActionListener(e -> {
                try {
                    String cmd = "cmd.exe /c start ";
                    String file = "help.pdf";
                    Runtime.getRuntime().exec(cmd + file);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(AppFrame.this, "Could not open help file " + e2.getMessage(), "Error opening file",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            supportMnu.addActionListener(e -> {
                GDialog cd = new GDialog(AppFrame.this, "About/Support", true);
                AboutPanel vp = new AboutPanel();
                cd.setAbstractFunctionPanel(vp, new Dimension(400, 190));
                cd.setVisible(true);

            });
            menuBar.add(helpMenu);
        }
        return menuBar;

    }

    private JPanel getStatusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.add(getStatusLbl());
        return statusPanel;
    }

    public final JLabel getStatusLbl() {
        if (statusLbl == null) {
            statusLbl = new JLabel("Developed by: IVONEE");
        }
        return statusLbl;

    }

    private static JPanel getBodyPanel() {
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.add(new JLabel("Hello"));

        return bodyPanel;
    }

    public final void setWindow(String curQfn) {
        AbstractFunctionPanel cur = getFunctionPanelInstance(curQfn);
        if (cur != null && isLoggedIn) {
            if (!currentWindow.getFunctionName().equals(cur.getFunctionName())) {
                setWindow(cur);
            }

        }
        if (!isLoggedIn) {
            JOptionPane.showMessageDialog(null, "Enter login credentials first!");
        }
    }

    protected static final AbstractFunctionPanel getFunctionPanelInstance(String className) {
        AbstractFunctionPanel object = null;
        try {
            object = (AbstractFunctionPanel) Class.forName(className).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            System.err.println(e);
        }
        return object;
    }

    private void setWindow(final AbstractFunctionPanel next) {
        SwingUtilities.invokeLater(() -> {
            currentWindow = next;
            bodyPanel.removeAll();
            bodyPanel.add(next, BorderLayout.CENTER);
            bodyPanel.revalidate();
            bodyPanel.repaint();
            setTitle(StrConstants.APP_TITLE + " : " + next.getFunctionName());
            next.init();
        });

    }

    private JPanel getToolBarPanel() {
        if (toolBarPanel == null) {
            toolBarPanel = new JPanel();
            toolBarPanel.setLayout(new BorderLayout(20, 10));

            List<JLabel> buttons = new ArrayList<>();
            buttons.add(ActionButton.create("HOME", "home", "com.ca.ui.panels.HomeScreenPanel"));
            buttons.add(ActionButton.create("Hatchery", "find", "com.ca.ui.panels.StockQueryPanel"));
            buttons.add(ActionButton.create("Expenditure", "itementry", "com.ca.ui.panels.ItemEntryPanel"));
            buttons.add(ActionButton.create("Flock ", "itemtransfer", "com.ca.ui.panels.ItemTransferPanel"));
            buttons.add(ActionButton.create("Inventory", "return", "com.ca.ui.panels.ItemReturnPanel"));
            buttons.add(ActionButton.create("Treatment", "returnquerya", "com.ca.ui.panels.ReturnQueryPanel"));
            buttons.add(ActionButton.create("Summaries", "stock", "com.ca.ui.report.LedgerReportPanel"));
            buttons.add(new JLabel());
            buttons.add(LogOutButton.create("Logout", "logout", "com.ca.ui.panels.HomeScreenPanel"));
            buttons.add(ExitButton.create("Exit", "exit", "com.ca.ui.panels.HomeScreenPanel"));
            
            toolBarPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            toolBarPanel.setPreferredSize(new Dimension(getWidth(), 80));
            toolBarPanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 0.5;
            for (JLabel button : buttons) {
                toolBarPanel.add(button, c);
                c.gridx++;
            }
        }
        return toolBarPanel;
    }

    public static final void showDBConnectionErrorMessage(Exception e) {
        JOptionPane.showMessageDialog(null, "Could not start DB Connection " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        Logger.L(Logger.E, "DB connection failed" + e.getMessage());
    }

    public final void handleLogOut() {
        setWindow(AppFrame.loginPanel);
        isLoggedIn = false;

    }

}
