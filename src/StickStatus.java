//COULD-DO: Create a specifiable interval constant for Sleep in CheckThread
/*TO-DO:
 *  1) Add in option for Thread Status, to show where current checking is
 */
import java.awt.*;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.*;
import java.util.Vector;
import snoozesoft.systray4j.*;


public class StickStatus extends javax.swing.JFrame implements SysTrayMenuListener
{
    //System Tray
    private SysTrayMenu tray;
    private ToolTipManager tooltip;
    //Add Dialog data
    private String add_name;
    private String add_id;
    
    //Frame Stuff
    private OptionsData optionsData;
    private DefaultTableModel model; //Holds name and onlineStatus
    private ColorRenderer colorRenderer;
    //Other
    private CheckThread thread;
    private boolean initialStart = true; //Used to signify program start. Used to stop log writes on startup
    //Loggin
    java.io.FileOutputStream writer;
    
    public StickStatus()
    {
        initComponents();
        this.setLocationRelativeTo(null); //Center main frame
        addDialog.pack(); //Pack addDialog
        colorRenderer = new ColorRenderer(status_list); //Setup status_list colorer
        //Setup Tray icon
        tray = new SysTrayMenu(new SysTrayMenuIcon("normalT.ico"),"Stick Stat");
        tray.getIcon().addSysTrayMenuListener(this);
        tray.hideIcon();
        
        optionsData = OptionsData.serializeIn("pref.dat");
        if(optionsData == null)
            System.exit(1);
        //Display Users
        Object[] row = new Object[3];
        model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Status");
        model.addColumn("Alert?");
        status_list.setDefaultRenderer(Boolean.class,new AlertRenderer());
        for(int i=0;i<optionsData.getIds().size();i++)
        {
            row[0] = optionsData.getNames().get(i);
            row[1] = new String("Checking...");
            row[2] = optionsData.getAlertOnChange().get(i);
            model.addRow(row);
        }
        //Set Status Column render
        status_list.setModel(model);
        status_list.getColumnModel().getColumn(2).setCellRenderer(new AlertRenderer());
        status_list.getColumnModel().getColumn(2).setCellEditor(new AlertRenderer());
        //If we have users, create and start CheckThread
        if(optionsData.getIds().size() > 0 && optionsData.getAutoStart())
        {
            menu_checkOnActionPerformed(null);
        }
        this.doUpdate(-1);
    }
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        addDialog = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        add_txt_name = new javax.swing.JTextField();
        add_txt_id = new javax.swing.JTextField();
        add_btn_ok = new javax.swing.JButton();
        add_btn_cancel = new javax.swing.JButton();
        frame_options = new javax.swing.JFrame();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        opt_lbl_online = new javax.swing.JLabel();
        opt_lbl_offline = new javax.swing.JLabel();
        opt_lbl_live = new javax.swing.JLabel();
        opt_lbl_unknown = new javax.swing.JLabel();
        opt_chk_autoStart = new javax.swing.JCheckBox();
        opt_chk_minimizeTray = new javax.swing.JCheckBox();
        opt_chk_sortName = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_logfile = new javax.swing.JTextField();
        opt_chk_logenable = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        opt_chk_logonline = new javax.swing.JCheckBox();
        opt_chk_logoffline = new javax.swing.JCheckBox();
        opt_chk_loglive = new javax.swing.JCheckBox();
        opt_btn_logBrowse = new javax.swing.JButton();
        opt_btn_default = new javax.swing.JButton();
        opt_btn_ok = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btn_add = new javax.swing.JButton();
        btn_remove = new javax.swing.JButton();
        btn_pref = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        status_list = new JTable()
        {
            public Component
            prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                if(column == 1)
                c.setBackground(getStatusColor(model.getValueAt(row,1).toString()));
                else
                c.setBackground(getBackground());
                return c;
            }
        };
        jPanel2 = new javax.swing.JPanel();
        statusBar = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menu_checkOn = new javax.swing.JMenuItem();
        menu_checkOff = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        menu_exit = new javax.swing.JMenuItem();

        addDialog.setTitle("Add user");
        jLabel1.setText("Name:");

        jLabel2.setText("ID:");

        add_txt_name.setNextFocusableComponent(add_txt_id);

        add_txt_id.setNextFocusableComponent(add_btn_ok);

        add_btn_ok.setText("Ok");
        add_btn_ok.setNextFocusableComponent(add_btn_cancel);
        add_btn_ok.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                add_btn_okActionPerformed(evt);
            }
        });

        add_btn_cancel.setText("Cancel");
        add_btn_cancel.setNextFocusableComponent(add_txt_name);
        add_btn_cancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                add_btn_cancelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout addDialogLayout = new org.jdesktop.layout.GroupLayout(addDialog.getContentPane());
        addDialog.getContentPane().setLayout(addDialogLayout);
        addDialogLayout.setHorizontalGroup(
            addDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(addDialogLayout.createSequentialGroup()
                .addContainerGap()
                .add(addDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, addDialogLayout.createSequentialGroup()
                        .add(add_btn_ok, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(add_btn_cancel))
                    .add(add_txt_id, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 167, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(add_txt_name))
                .addContainerGap())
        );

        addDialogLayout.linkSize(new java.awt.Component[] {add_btn_cancel, add_btn_ok}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        addDialogLayout.setVerticalGroup(
            addDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(addDialogLayout.createSequentialGroup()
                .addContainerGap()
                .add(addDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(add_txt_name, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(add_txt_id, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, Short.MAX_VALUE)
                .add(addDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(add_btn_cancel)
                    .add(add_btn_ok))
                .addContainerGap())
        );
        frame_options.setTitle("Options");
        frame_options.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                frame_optionsWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                frame_optionsWindowOpened(evt);
            }
        });

        jLabel3.setText("Single Click: Edit Background Color");

        jLabel8.setText("Double Click: Edit Text Color");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        opt_lbl_online.setFont(new java.awt.Font("Tahoma", 1, 12));
        opt_lbl_online.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        opt_lbl_online.setText("Online");
        opt_lbl_online.setName("lbl_online");
        opt_lbl_online.setOpaque(true);
        opt_lbl_online.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                opt_StatusColor_MouseClicked(evt);
            }
        });

        opt_lbl_offline.setFont(new java.awt.Font("Tahoma", 1, 12));
        opt_lbl_offline.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        opt_lbl_offline.setText("Offline");
        opt_lbl_offline.setName("lbl_offline");
        opt_lbl_offline.setOpaque(true);
        opt_lbl_offline.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                opt_StatusColor_MouseClicked(evt);
            }
        });

        opt_lbl_live.setFont(new java.awt.Font("Tahoma", 1, 12));
        opt_lbl_live.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        opt_lbl_live.setText("Live");
        opt_lbl_live.setName("lbl_live");
        opt_lbl_live.setOpaque(true);
        opt_lbl_live.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                opt_StatusColor_MouseClicked(evt);
            }
        });

        opt_lbl_unknown.setFont(new java.awt.Font("Tahoma", 1, 12));
        opt_lbl_unknown.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        opt_lbl_unknown.setText("Unknown");
        opt_lbl_unknown.setName("lbl_unknown");
        opt_lbl_unknown.setOpaque(true);
        opt_lbl_unknown.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                opt_StatusColor_MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(opt_lbl_online, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .add(opt_lbl_live, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .add(opt_lbl_unknown, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .add(opt_lbl_offline, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(opt_lbl_online)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(opt_lbl_offline)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(opt_lbl_live)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(opt_lbl_unknown)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        opt_chk_autoStart.setText("Auto-Start Check");
        opt_chk_autoStart.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        opt_chk_autoStart.setMargin(new java.awt.Insets(0, 0, 0, 0));
        opt_chk_autoStart.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                opt_chk_autoStartActionPerformed(evt);
            }
        });

        opt_chk_minimizeTray.setText("Enable Minimize to Tray");
        opt_chk_minimizeTray.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        opt_chk_minimizeTray.setMargin(new java.awt.Insets(0, 0, 0, 0));
        opt_chk_minimizeTray.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                opt_chk_minimizeTrayActionPerformed(evt);
            }
        });

        opt_chk_sortName.setText("Sort by Name (Not Implemented)");
        opt_chk_sortName.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        opt_chk_sortName.setMargin(new java.awt.Insets(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(opt_chk_sortName))
                    .add(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(opt_chk_minimizeTray))
                    .add(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(opt_chk_autoStart)))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(opt_chk_autoStart)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(opt_chk_sortName)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(opt_chk_minimizeTray)
                .add(78, 78, 78))
        );
        jTabbedPane1.addTab("Main", jPanel5);

        jLabel4.setText("jLabel4");

        txt_logfile.setText("jTextField1");
        txt_logfile.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                txt_logfileFocusLost(evt);
            }
        });

        opt_chk_logenable.setText("Enable Logging");
        opt_chk_logenable.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        opt_chk_logenable.setMargin(new java.awt.Insets(0, 0, 0, 0));
        opt_chk_logenable.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                opt_chk_logenableActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setEnabled(false);
        opt_chk_logonline.setText("Log Online Events");
        opt_chk_logonline.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        opt_chk_logonline.setEnabled(false);
        opt_chk_logonline.setMargin(new java.awt.Insets(0, 0, 0, 0));
        opt_chk_logonline.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                opt_chk_loggingActionPerformed(evt);
            }
        });

        opt_chk_logoffline.setText("Log Offline Events");
        opt_chk_logoffline.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        opt_chk_logoffline.setEnabled(false);
        opt_chk_logoffline.setMargin(new java.awt.Insets(0, 0, 0, 0));
        opt_chk_logoffline.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                opt_chk_loggingActionPerformed(evt);
            }
        });

        opt_chk_loglive.setText("Log Live Events");
        opt_chk_loglive.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        opt_chk_loglive.setEnabled(false);
        opt_chk_loglive.setMargin(new java.awt.Insets(0, 0, 0, 0));
        opt_chk_loglive.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                opt_chk_loggingActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(opt_chk_logonline)
                    .add(opt_chk_logoffline)
                    .add(opt_chk_loglive))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .add(opt_chk_logonline)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(opt_chk_logoffline)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(opt_chk_loglive)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        opt_btn_logBrowse.setText("Browse");
        opt_btn_logBrowse.setMargin(new java.awt.Insets(2, 5, 2, 5));
        opt_btn_logBrowse.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                opt_btn_logBrowseActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel6Layout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(txt_logfile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 154, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(opt_btn_logBrowse, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                    .add(opt_chk_logenable))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(txt_logfile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(opt_btn_logBrowse))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(opt_chk_logenable)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(163, Short.MAX_VALUE))
        );
        jTabbedPane1.addTab("Logging", jPanel6);

        opt_btn_default.setText("Default");
        opt_btn_default.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                opt_btn_defaultActionPerformed(evt);
            }
        });

        opt_btn_ok.setText("Close");
        opt_btn_ok.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                opt_btn_okActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout frame_optionsLayout = new org.jdesktop.layout.GroupLayout(frame_options.getContentPane());
        frame_options.getContentPane().setLayout(frame_optionsLayout);
        frame_optionsLayout.setHorizontalGroup(
            frame_optionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, frame_optionsLayout.createSequentialGroup()
                .addContainerGap()
                .add(opt_btn_default)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 129, Short.MAX_VALUE)
                .add(opt_btn_ok)
                .addContainerGap())
        );
        frame_optionsLayout.setVerticalGroup(
            frame_optionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(frame_optionsLayout.createSequentialGroup()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 289, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(frame_optionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(opt_btn_ok)
                    .add(opt_btn_default))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Stick Stat");
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowActivated(java.awt.event.WindowEvent evt)
            {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
            public void windowIconified(java.awt.event.WindowEvent evt)
            {
                formWindowIconified(evt);
            }
        });

        btn_add.setFont(new java.awt.Font("Arial", 1, 14));
        btn_add.setText("+");
        btn_add.setToolTipText("Add");
        btn_add.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btn_add.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btn_addActionPerformed(evt);
            }
        });

        btn_remove.setFont(new java.awt.Font("Arial", 1, 14));
        btn_remove.setText("-");
        btn_remove.setToolTipText("Remove");
        btn_remove.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btn_remove.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btn_removeActionPerformed(evt);
            }
        });

        btn_pref.setFont(new java.awt.Font("Arial", 1, 14));
        btn_pref.setText("*");
        btn_pref.setToolTipText("Option");
        btn_pref.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btn_pref.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btn_prefActionPerformed(evt);
            }
        });

        status_list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null}
            },
            new String []
            {
                "", "", ""
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, true
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        status_list.setShowHorizontalLines(false);
        status_list.setShowVerticalLines(false);
        jScrollPane1.setViewportView(status_list);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        statusBar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(statusBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusBar)
        );

        jMenu1.setText("Stat");
        menu_checkOn.setText("Check On");
        menu_checkOn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menu_checkOnActionPerformed(evt);
            }
        });

        jMenu1.add(menu_checkOn);

        menu_checkOff.setText("Check Off");
        menu_checkOff.setEnabled(false);
        menu_checkOff.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menu_checkOffActionPerformed(evt);
            }
        });

        jMenu1.add(menu_checkOff);

        jMenu1.add(jSeparator2);

        menu_exit.setText("Exit");
        menu_exit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menu_exitActionPerformed(evt);
            }
        });

        jMenu1.add(menu_exit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(btn_add, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btn_remove, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btn_pref, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btn_add)
                    .add(btn_pref, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btn_remove))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(new java.awt.Component[] {btn_pref, btn_remove}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opt_btn_logBrowseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_opt_btn_logBrowseActionPerformed
    {//GEN-HEADEREND:event_opt_btn_logBrowseActionPerformed
        JFileChooser logChoose = new JFileChooser();
        if(logChoose.showSaveDialog(frame_options) == JFileChooser.APPROVE_OPTION) {
            optionsData.setLogFile(logChoose.getSelectedFile().getAbsolutePath());
            txt_logfile.setText(logChoose.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_opt_btn_logBrowseActionPerformed

    private void txt_logfileFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_txt_logfileFocusLost
    {//GEN-HEADEREND:event_txt_logfileFocusLost
        optionsData.setLogFile(txt_logfile.getText());
    }//GEN-LAST:event_txt_logfileFocusLost

    private void opt_chk_loggingActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_opt_chk_loggingActionPerformed
    {//GEN-HEADEREND:event_opt_chk_loggingActionPerformed
        JCheckBox source = ((JCheckBox)evt.getSource());
        if(source == opt_chk_logonline)
            optionsData.setLogOnline(source.isSelected());
        else if(source == opt_chk_logoffline)
            optionsData.setLogOffline(source.isSelected());
        else if(source == opt_chk_loglive)
            optionsData.setLogLive(source.isSelected());
    }//GEN-LAST:event_opt_chk_loggingActionPerformed

    private void opt_chk_logenableActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_opt_chk_logenableActionPerformed
    {//GEN-HEADEREND:event_opt_chk_logenableActionPerformed
        if(opt_chk_logenable.isSelected()) {
            opt_chk_logonline.setEnabled(true);
            opt_chk_logoffline.setEnabled(true);
            opt_chk_loglive.setEnabled(true);
        }
        else {
            opt_chk_logonline.setEnabled(false);
            opt_chk_logoffline.setEnabled(false);
            opt_chk_loglive.setEnabled(false);
        }
        optionsData.setLoggingEnabled(opt_chk_logenable.isSelected());
    }//GEN-LAST:event_opt_chk_logenableActionPerformed
    
    private void opt_chk_minimizeTrayActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_opt_chk_minimizeTrayActionPerformed
    {//GEN-HEADEREND:event_opt_chk_minimizeTrayActionPerformed
        optionsData.setMinimizeTray(opt_chk_minimizeTray.isSelected());
    }//GEN-LAST:event_opt_chk_minimizeTrayActionPerformed
    
    private void opt_chk_autoStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_opt_chk_autoStartActionPerformed
    {//GEN-HEADEREND:event_opt_chk_autoStartActionPerformed
        optionsData.setAutoStart(opt_chk_autoStart.isSelected());
    }//GEN-LAST:event_opt_chk_autoStartActionPerformed
    
    private void formWindowIconified(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowIconified
    {//GEN-HEADEREND:event_formWindowIconified
        if(optionsData.getMinimizeTray())
        {
            this.setVisible(false);
            tray.setIcon(new SysTrayMenuIcon("normalT.ico"));
            tray.getIcon().addSysTrayMenuListener(this);
            tray.showIcon();
        }
    }//GEN-LAST:event_formWindowIconified
    
    private void frame_optionsWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_frame_optionsWindowClosing
    {//GEN-HEADEREND:event_frame_optionsWindowClosing
        opt_btn_okActionPerformed(null);
    }//GEN-LAST:event_frame_optionsWindowClosing
    
    private void btn_prefActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btn_prefActionPerformed
    {//GEN-HEADEREND:event_btn_prefActionPerformed
        frameOptions_pollOptions();
        frame_options.setLocationRelativeTo(this);
        frame_options.setVisible(true);
        frame_options.setEnabled(true);
        frame_options.pack();
        this.setEnabled(false);
    }//GEN-LAST:event_btn_prefActionPerformed
    
    private void opt_btn_defaultActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_opt_btn_defaultActionPerformed
    {//GEN-HEADEREND:event_opt_btn_defaultActionPerformed
        if(JOptionPane.showConfirmDialog(frame_options,"This will reset settings. Are you sure?","Restore default",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            optionsData.resetDefault();
        }
    }//GEN-LAST:event_opt_btn_defaultActionPerformed
    
    private void opt_btn_okActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_opt_btn_okActionPerformed
    {//GEN-HEADEREND:event_opt_btn_okActionPerformed
        
        this.setEnabled(true);
        this.doUpdate(-1);
        frame_options.dispose();
        this.toFront();
    }//GEN-LAST:event_opt_btn_okActionPerformed
    
    private void opt_StatusColor_MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_opt_StatusColor_MouseClicked
    {//GEN-HEADEREND:event_opt_StatusColor_MouseClicked
        JLabel source;
        Color color = null;
        if(evt.getSource().getClass() != JLabel.class)
            return;
        source = ((JLabel)evt.getSource());
        switch(evt.getClickCount())
        {
            case 1: //Background
                color = JColorChooser.showDialog(frame_options,"Choose Backgound Color...",((JLabel)source).getBackground());
                break;
            default: //Text color
                color = JColorChooser.showDialog(frame_options,"Choose Text Color...",((JLabel)source).getForeground());
                break;
        }
        
        if(color != null)
        {
            if(source.getName().equals("lbl_online"))
            {
                if(evt.getClickCount() == 1)
                {
                    optionsData.setOnlineColor(color);
                    opt_lbl_online.setBackground(color);
                }
                else
                {
                    optionsData.setOnlineTextColor(color);
                    opt_lbl_online.setForeground(color);
                }
            }
            else if(source.getName().equals("lbl_offline"))
            {
                if(evt.getClickCount() == 1)
                {
                    optionsData.setOfflineColor(color);
                    opt_lbl_offline.setBackground(color);
                }
                else
                {
                    optionsData.setOfflineTextColor(color);
                    opt_lbl_offline.setForeground(color);
                }
            }
            else if(source.getName().equals("lbl_live"))
            {
                if(evt.getClickCount() == 1)
                {
                    optionsData.setLiveColor(color);
                    opt_lbl_live.setBackground(color);
                }
                else
                {
                    optionsData.setLiveTextColor(color);
                    opt_lbl_live.setForeground(color);
                }
            }
            else if(source.getName().equals("lbl_unknown"))
            {
                if(evt.getClickCount() == 1)
                {
                    optionsData.setUnknownColor(color);
                    opt_lbl_unknown.setBackground(color);
                }
                else
                {
                    optionsData.setUnknownTextColor(color);
                    opt_lbl_unknown.setForeground(color);
                }
            }
            else //No idea
                return;
        }
    }//GEN-LAST:event_opt_StatusColor_MouseClicked
    
    private void frame_optionsWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_frame_optionsWindowOpened
    {//GEN-HEADEREND:event_frame_optionsWindowOpened
        frameOptions_pollOptions();
        return;
    }//GEN-LAST:event_frame_optionsWindowOpened
    
    private void menu_exitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menu_exitActionPerformed
    {//GEN-HEADEREND:event_menu_exitActionPerformed
        this.formWindowClosing(null);
    }//GEN-LAST:event_menu_exitActionPerformed
    
    private void menu_checkOffActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menu_checkOffActionPerformed
    {//GEN-HEADEREND:event_menu_checkOffActionPerformed
        thread.kill();
        thread = null;
        this.menu_checkOff.setEnabled(false);
        this.menu_checkOn.setEnabled(true);
    }//GEN-LAST:event_menu_checkOffActionPerformed
    
    private void menu_checkOnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menu_checkOnActionPerformed
    {//GEN-HEADEREND:event_menu_checkOnActionPerformed
        if(thread == null)
        {
            thread = new CheckThread(this,optionsData.getIds(),model);
            thread.start();
        }
        this.menu_checkOn.setEnabled(false);
        this.menu_checkOff.setEnabled(true);
    }//GEN-LAST:event_menu_checkOnActionPerformed
    
    private void formWindowActivated(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowActivated
    {//GEN-HEADEREND:event_formWindowActivated
        if(addDialog.isVisible())
            addDialog.toFront();
        else if(frame_options.isVisible())
            frame_options.toFront();
    }//GEN-LAST:event_formWindowActivated
    
    private void btn_removeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btn_removeActionPerformed
    {//GEN-HEADEREND:event_btn_removeActionPerformed
        //Make sure something is selected
        if(status_list.getSelectedRow() < 0)
            return; //Nothing selected
        //Are you sure you want to remove?
        if(JOptionPane.showConfirmDialog(this,"Are you sure you?","Remove",JOptionPane.YES_NO_OPTION) ==
                JOptionPane.NO_OPTION)
            return;
        //Remove from model - Selected people
        for(int i=0;i<status_list.getSelectedRows().length;i++)
        {
            model.removeRow(i);
            optionsData.remove(i);
        }
        //Update Thread
        thread.updateIds = optionsData.getIds();
        thread.updateModel = model;
        thread.dataUpdate = true;
        //Update Grahpics
        this.doUpdate(-1);
        
        //Do check, if all users removed stop Thread
        if(model.getRowCount() == 0)
        {
            thread.kill();
            thread = null;
        }
    }//GEN-LAST:event_btn_removeActionPerformed
    
    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        if(!OptionsData.serializeOut("pref.dat",optionsData))
            System.err.println("Error writing pref.dat");
        if(thread != null)
        {
            thread.kill();
            thread = null;
            this.menu_checkOn.setEnabled(true);
            this.menu_checkOff.setEnabled(false);
        }

        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing
    
    private void add_btn_cancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_add_btn_cancelActionPerformed
    {//GEN-HEADEREND:event_add_btn_cancelActionPerformed
        //Set return holders null, no data.
        add_name = null;
        add_id = null;
        //Clear textboxes for later use
        add_txt_name.setText("");
        add_txt_id.setText("");
        //Dispose of dialog
        addDialog.dispose();
        //Return to parent
        this.setEnabled(true);
        this.toFront();
    }//GEN-LAST:event_add_btn_cancelActionPerformed
    
    private void add_btn_okActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_add_btn_okActionPerformed
    {//GEN-HEADEREND:event_add_btn_okActionPerformed
        //Update return holders
        add_name = add_txt_name.getText();
        add_id = add_txt_id.getText();
        //Clear boxes for later use
        add_txt_name.setText("");
        add_txt_id.setText("");
        //Dispose of dialog
        addDialog.dispose();
        //Return control parent
        this.setEnabled(true);
        this.toFront();
        //Let parent know we are returning
        this.addReturn();
    }//GEN-LAST:event_add_btn_okActionPerformed
    
    private void btn_addActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btn_addActionPerformed
    {//GEN-HEADEREND:event_btn_addActionPerformed
        this.setEnabled(false);
        addDialog.setLocationRelativeTo(this);
        addDialog.setVisible(true);
        addDialog.toFront();
    }//GEN-LAST:event_btn_addActionPerformed
    
    private void addReturn()
    {
        if(add_name != null || add_id != null || add_name.equals("") || add_id.equals(""))
        {
            //Update Model
            Object[] row = { add_name, new Status("Checking..."), new Boolean(false) };
            model.addRow(row);
            //Update Option Data
            optionsData.add(add_name,add_id);
            //Update data in Thread. USE SHARED MEMORY RESOURCE!!
            //If thread == null, then we need to make a thread
            if(thread == null && optionsData.getAutoStart())
            {
                //doUpdate()
                this.doUpdate(-1);
                thread = new CheckThread(this,optionsData.getIds(),model);
                thread.start();
                this.menu_checkOn.setEnabled(false);
                this.menu_checkOff.setEnabled(true);
            }
            else if(thread != null) //There is a thread
            {
                //doUpdate()
                this.doUpdate(-1);
                
                thread.updateModel = model;
                thread.updateIds = optionsData.getIds();
                thread.dataUpdate = true;
            }
        }
    }
    
    public void doUpdate(int i)
    {
        //TO-DO: Could add in thing to make the window flash when there is a change
        if(i != -1)
        { //This isn't a basic update
            //Check if Alert is set
            if(optionsData.getAlert(i))
            {
                //If current status is now either Online or Live, alert
                if(model.getValueAt(i,1).equals("Online") || model.getValueAt(i,1).equals("Live"))
                {
                    if(this.getExtendedState() == Frame.ICONIFIED)
                        iconLeftDoubleClicked(null);
                    else
                        this.setVisible(true);
                    
                    this.statusBar.setText(optionsData.getNames().get(i) + " is " + model.getValueAt(i,1));
                }
            }
        }
        status_list.setModel(model);
        this.repaint();
    }
    
    public void log(String newVal, int i) {
         if(true) { //Make sure logfile is valid (DEBUG)
            try {
                //Open log file
                writer = new java.io.FileOutputStream(optionsData.getLogFile(),true);
                //Check make sure we arn't in Checking... phase. AKA Startup'
              //  if(model.getValueAt(i,1).equals("Checking..."))
              //      return;
                //Write current status
                if(newVal.equals("Live"))
                    writer.write((optionsData.getNames().get(i) + " goes LIVE at " + java.util.Calendar.getInstance().getTime().toString() + "\n").getBytes());
                else if(newVal.equals("Online"))
                    writer.write((optionsData.getNames().get(i) + " goes ONLINE at " + java.util.Calendar.getInstance().getTime().toString() + "\n").getBytes());
                else if(newVal.equals("Offline"))
                    writer.write((optionsData.getNames().get(i) + " goes OFFLINE at " + java.util.Calendar.getInstance().getTime().toString() + "\n").getBytes());
                writer.close();
            }
            catch(java.io.IOException e) {
                e.printStackTrace();
                System.err.println("Error: Writing log file");
            }
        }
    }
    
    public Color getStatusColor(String value)
    {
        if(value.equals("Online"))
            return optionsData.getOnlineColor();
        else if(value.equals("Live"))
            return optionsData.getLiveColor();
        else if(value.equals("Offline"))
            return optionsData.getOfflineColor();
        else
            return optionsData.getUnknownColor();
    }
    
    private void frameOptions_pollOptions()
    {
        opt_lbl_online.setBackground(optionsData.getOnlineColor());
        opt_lbl_online.setForeground(optionsData.getOnlineTextColor());
        opt_lbl_offline.setBackground(optionsData.getOfflineColor());
        opt_lbl_offline.setForeground(optionsData.getOfflineTextColor());
        opt_lbl_live.setBackground(optionsData.getLiveColor());
        opt_lbl_live.setForeground(optionsData.getLiveTextColor());
        opt_lbl_unknown.setBackground(optionsData.getUnknownColor());
        opt_lbl_unknown.setForeground(optionsData.getUnknownTextColor());
        
        opt_chk_autoStart.setSelected(optionsData.getAutoStart());
        opt_chk_sortName.setSelected(optionsData.getSortName());
        opt_chk_minimizeTray.setSelected(optionsData.getMinimizeTray());
        
        //Logging
        txt_logfile.setText(optionsData.getLogFile());
        opt_chk_logenable.setSelected(optionsData.getLoggingEnabled());
        opt_chk_logonline.setSelected(optionsData.getLogOnline());
        opt_chk_logoffline.setSelected(optionsData.getLogOffline());
        opt_chk_loglive.setSelected(optionsData.getLogLive());
        
        //Set Log Chk enable status
        opt_chk_logonline.setEnabled(opt_chk_logenable.isSelected());
        opt_chk_logoffline.setEnabled(opt_chk_logenable.isSelected());
        opt_chk_loglive.setEnabled(opt_chk_logenable.isSelected());
        
        frame_options.repaint();
    }
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new StickStatus().setVisible(true);
            }
        });
    }
    
    public void menuItemSelected(SysTrayMenuEvent sysTrayMenuEvent)
    {
    }
    
    public void iconLeftClicked(SysTrayMenuEvent sysTrayMenuEvent)
    {
    }
    
    public void iconLeftDoubleClicked(SysTrayMenuEvent sysTrayMenuEvent)
    {
        this.setVisible(true);
        this.toFront();
        tray.hideIcon();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog addDialog;
    private javax.swing.JButton add_btn_cancel;
    private javax.swing.JButton add_btn_ok;
    private javax.swing.JTextField add_txt_id;
    private javax.swing.JTextField add_txt_name;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_pref;
    private javax.swing.JButton btn_remove;
    private javax.swing.JFrame frame_options;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem menu_checkOff;
    private javax.swing.JMenuItem menu_checkOn;
    private javax.swing.JMenuItem menu_exit;
    private javax.swing.JButton opt_btn_default;
    private javax.swing.JButton opt_btn_logBrowse;
    private javax.swing.JButton opt_btn_ok;
    private javax.swing.JCheckBox opt_chk_autoStart;
    private javax.swing.JCheckBox opt_chk_logenable;
    private javax.swing.JCheckBox opt_chk_loglive;
    private javax.swing.JCheckBox opt_chk_logoffline;
    private javax.swing.JCheckBox opt_chk_logonline;
    private javax.swing.JCheckBox opt_chk_minimizeTray;
    private javax.swing.JCheckBox opt_chk_sortName;
    private javax.swing.JLabel opt_lbl_live;
    private javax.swing.JLabel opt_lbl_offline;
    private javax.swing.JLabel opt_lbl_online;
    private javax.swing.JLabel opt_lbl_unknown;
    private javax.swing.JLabel statusBar;
    private javax.swing.JTable status_list;
    private javax.swing.JTextField txt_logfile;
    // End of variables declaration//GEN-END:variables
    
    public class AlertRenderer extends JCheckBox implements TableCellRenderer, TableCellEditor, java.awt.event.ActionListener
    {
        private int row;
        public AlertRenderer()
        {
            super();
            this.addActionListener(this);
        }
        
        public void actionPerformed(java.awt.event.ActionEvent e)
        {
            optionsData.setAlert(this.row,!optionsData.getAlert(row));
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            if(value.getClass() == Boolean.class && column == 2)
                this.setSelected(optionsData.getAlert(row));
            this.row = row;
            return this;
        }
        
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            this.row = row;
            if(value.getClass() == Boolean.class && column == 2)
            {
                //Check if data updated
                this.setSelected(optionsData.getAlert(row));
            }
            return this;
        }
        
        public Object getCellEditorValue()
        {
            return this.isSelected();
        }
        
        public boolean isCellEditable(EventObject anEvent)
        {
            return true;
        }
        
        public boolean shouldSelectCell(EventObject anEvent)
        {
            return true;
        }
        
        public boolean stopCellEditing()
        {
            return true;
        }
        
        public void cancelCellEditing()
        {
        }
        
        public void addCellEditorListener(CellEditorListener l)
        {
        }
        
        public void removeCellEditorListener(CellEditorListener l)
        {
        }
    }
}
