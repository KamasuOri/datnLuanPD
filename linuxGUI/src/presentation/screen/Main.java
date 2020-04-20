/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.screen;

import DAL.Evidence;
import DAL.EvidenceBonus;
import DAL.HistoryTime;
import DAL.Output;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author hantr
 */
public class Main extends javax.swing.JFrame {

    public static ArrayList<HistoryTime> listHistoryTimes = new ArrayList<>();
    public static ArrayList<Output> listOutputs = new ArrayList<>();

    Tmp tmp = new Tmp();

    String path;

    public static ArrayList<Evidence> listAllVolatile = Evidence.listAllVolatile();


    public static ArrayList<EvidenceBonus> listBonus = new ArrayList<>();


    public static void loadTable(Boolean a, JTable b, ArrayList<Evidence> list) {

        // Clear table
        b.setModel(new DefaultTableModel());
        // Model for Table
        DefaultTableModel model = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        b.setModel(model);

        // Add Column
        model.addColumn("CHỌN THU THẬP");
        model.addColumn("TÊN THÔNG TIN");
        model.addColumn("TÊN FILE CHỨA");
        model.addColumn("LOẠI FILE");

        int row = 0;
        for (int i = 0; i < list.size(); i++) {
            model.addRow(new Object[0]);
            model.setValueAt(a, row, 0); // Checkbox
            model.setValueAt(list.get(i).getName(), row, 1);
            model.setValueAt(list.get(i).getFileName(), row, 2);
            model.setValueAt(list.get(i).getFileType(), row, 3);
            row++;
        }
        TableColumn column = null;
        for (int i = 0; i <= 3; i++) {
            column = b.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(2);
            }
            if (i == 1) {
                column.setPreferredWidth(300);

            }
            if (i == 2) {
                column.setPreferredWidth(200);

            }
        }
        b.setRowHeight(30);

    }

    //
    public void loadTableOutput() {
        listOutputs = new ArrayList<>();

        for (int i = 0; i < bang1.getRowCount(); i++) {
            if (bang1.getValueAt(i, 0).toString().equals("true")) {
                Output o = new Output();
                o.setName(bang1.getValueAt(i, 1).toString());
                Path path = Paths.get(TaoMoi.diachi.getText() + "\\" + bang1.getValueAt(i, 2).toString() + bang1.getValueAt(i, 3).toString());

                double fileLength = new File(path.toString()).length();

                if (Files.exists(path) && (fileLength > 97)) {
                    o.setSuccess(TaoMoi.diachi.getText() + "\\" + bang1.getValueAt(i, 2).toString() + bang1.getValueAt(i, 3).toString());

                } else {
                    o.setSuccess("ERROR");

                }
                listOutputs.add(o);

            }
        }

        bang_phu.removeAll();
        String[] columns = {"TÊN THÔNG TIN", "OUTPUT"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (int i = 0; i < listOutputs.size(); i++) {
            Vector vector = new Vector();
            vector.add(listOutputs.get(i).getName());
            vector.add(listOutputs.get(i).getSuccess());
            model.addRow(vector);
        }

        bang_phu.setModel(model);
        bang_phu.setRowHeight(30);
        bang_phu.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (bang_phu.getSelectedRow() >= 0) {
                path = bang_phu.getValueAt(bang_phu.getSelectedRow(), 1).toString();
            }
        });

    }

    public static void loadTableHistory() {
        String[] columnsHistory = {"STT", "THỜI GIAN THU THẬP", "TỔNG SỐ THÔNG TIN", "SỐ THÀNH CÔNG", "SỐ THẤT BẠI", "TỔNG THỜI GIAN"};
        DefaultTableModel model = new DefaultTableModel(columnsHistory, 0);
        for (int i = 0; i < listHistoryTimes.size(); i++) {
            Vector vector = new Vector();
            vector.add(listHistoryTimes.get(i).getStt());
            vector.add(listHistoryTimes.get(i).getThoiGian());
            vector.add(listHistoryTimes.get(i).getTongSoTT());
            vector.add(listHistoryTimes.get(i).getSoThanhCong());
            vector.add(listHistoryTimes.get(i).getSoThatBai());
            vector.add(listHistoryTimes.get(i).getTongThoiGian());

            model.addRow(vector);
        }
        banglichsu.setRowHeight(30);
        banglichsu.setModel(model);
    }

    /**
     * Hàm lưu trạng thái phiên thu thập:
     */
    /**
     * Creates new form Main1
     */
    public Main() {
        initComponents();
        setSize(1366, 730);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);

        tabpane.setVisible(false);
        trang_thai_thu_thap_pane.setVisible(false);
        a1.setVisible(false);
        a2.setVisible(false);
        a3.setVisible(false);
        a4.setVisible(false);
        a5.setVisible(false);
        a6.setVisible(false);
        a7.setVisible(false);
        a8.setVisible(false);
        nut_them.setVisible(false);
        nut_huy.setVisible(false);

        proper.setVisible(false);
        b1.setVisible(false);
        b2.setVisible(false);
        valid_main.setVisible(false);
        valid_them.setVisible(false);

        reset.setVisible(false);

        editt.setEnabled(false);
        dang_thu_thap.setVisible(false);
        chon_tat_ca.setVisible(false);
        loadTableHistory();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenuBar5 = new javax.swing.JMenuBar();
        jMenu10 = new javax.swing.JMenu();
        jMenu11 = new javax.swing.JMenu();
        jMenuBar7 = new javax.swing.JMenuBar();
        jMenu16 = new javax.swing.JMenu();
        jMenu17 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        tao_lan_thu_thap_moi = new javax.swing.JButton();
        mo_lan_thu_thap_cu = new javax.swing.JButton();
        pan_tieu_de = new javax.swing.JPanel();
        thu_thap = new javax.swing.JButton();
        dung_thu_thap = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        valid_main = new javax.swing.JLabel();
        dang_thu_thap = new javax.swing.JLabel();
        chon_tat_ca = new javax.swing.JCheckBox();
        tabpane = new javax.swing.JTabbedPane();
        pan_volatile = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bang1 = new javax.swing.JTable();
        chontatca = new javax.swing.JButton();
        a1 = new javax.swing.JLabel();
        a2 = new javax.swing.JTextField();
        a3 = new javax.swing.JLabel();
        a4 = new javax.swing.JTextField();
        a5 = new javax.swing.JLabel();
        a7 = new javax.swing.JLabel();
        a8 = new javax.swing.JTextField();
        them_mot_loai = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        nut_them = new javax.swing.JButton();
        nut_huy = new javax.swing.JButton();
        valid_them = new javax.swing.JLabel();
        a6 = new javax.swing.JComboBox<>();
        reset = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        banglichsu = new javax.swing.JTable();
        project = new javax.swing.JInternalFrame();
        ten = new javax.swing.JLabel();
        diachi = new javax.swing.JLabel();
        nguoi = new javax.swing.JLabel();
        proper = new javax.swing.JPanel();
        proper_tieu_de = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tong = new javax.swing.JLabel();
        tong_thanh_cong = new javax.swing.JLabel();
        tong_that_bai = new javax.swing.JLabel();
        tong_thoi_gian = new javax.swing.JLabel();
        thoi_gian = new javax.swing.JLabel();
        he_dieu_hanh = new javax.swing.JLabel();
        b1 = new javax.swing.JLabel();
        b2 = new javax.swing.JLabel();
        diachi1 = new javax.swing.JLabel();
        inputDir = new javax.swing.JLabel();
        jMenuBar6 = new javax.swing.JMenuBar();
        jMenu13 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        editt = new javax.swing.JMenuItem();
        trang_thai_thu_thap_pane = new javax.swing.JPanel();
        trang_thai_thu_thap_pan_tieu_de = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        bang_phu = new javax.swing.JTable();
        nut_xem = new javax.swing.JButton();
        nut_refresh = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        jMenu4.setText("File");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar2.add(jMenu5);

        jMenu6.setText("File");
        jMenuBar3.add(jMenu6);

        jMenu7.setText("Edit");
        jMenuBar3.add(jMenu7);

        jMenu8.setText("File");
        jMenuBar4.add(jMenu8);

        jMenu9.setText("Edit");
        jMenuBar4.add(jMenu9);

        jMenu10.setText("File");
        jMenuBar5.add(jMenu10);

        jMenu11.setText("Edit");
        jMenuBar5.add(jMenu11);

        jMenu16.setText("File");
        jMenuBar7.add(jMenu16);

        jMenu17.setText("Edit");
        jMenuBar7.add(jMenu17);

        jMenuItem8.setText("jMenuItem8");

        jMenuItem16.setText("jMenuItem16");

        jMenuItem17.setText("jMenuItem17");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Phần mềm thu thập chứng cứ máy tính");

        tao_lan_thu_thap_moi.setText("Tạo mới lần thu thập");
        tao_lan_thu_thap_moi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tao_lan_thu_thap_moiActionPerformed(evt);
            }
        });

        mo_lan_thu_thap_cu.setText("Mở lần thu thập đã có");
        mo_lan_thu_thap_cu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mo_lan_thu_thap_cuActionPerformed(evt);
            }
        });

        pan_tieu_de.setBackground(new java.awt.Color(204, 204, 204));

        thu_thap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentation/img/Play-icon.png"))); // NOI18N
        thu_thap.setText("THU THẬP");
        thu_thap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        thu_thap.setEnabled(false);
        thu_thap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thu_thapActionPerformed(evt);
            }
        });
        thu_thap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                thu_thapKeyPressed(evt);
            }
        });

        dung_thu_thap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentation/img/stop-red-icon.png"))); // NOI18N
        dung_thu_thap.setText("DỪNG THU THẬP");
        dung_thu_thap.setEnabled(false);
        dung_thu_thap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dung_thu_thapActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentation/img/Button-Previous-icon.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentation/img/Button-Next-icon (1).png"))); // NOI18N

        valid_main.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        valid_main.setForeground(new java.awt.Color(255, 0, 0));
        valid_main.setText("Vui lòng chọn ít nhất một loại thông tin để thu thập!");

        dang_thu_thap.setText("Dang thu thap");

        chon_tat_ca.setText("CHỌN TẤT CẢ");
        chon_tat_ca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chon_tat_caActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pan_tieu_deLayout = new javax.swing.GroupLayout(pan_tieu_de);
        pan_tieu_de.setLayout(pan_tieu_deLayout);
        pan_tieu_deLayout.setHorizontalGroup(
            pan_tieu_deLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tieu_deLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(thu_thap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dung_thu_thap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chon_tat_ca)
                .addGap(81, 81, 81)
                .addComponent(valid_main)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dang_thu_thap)
                .addGap(98, 98, 98))
        );
        pan_tieu_deLayout.setVerticalGroup(
            pan_tieu_deLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tieu_deLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tieu_deLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tieu_deLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(thu_thap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dung_thu_thap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(pan_tieu_deLayout.createSequentialGroup()
                        .addGroup(pan_tieu_deLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addGroup(pan_tieu_deLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(valid_main, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dang_thu_thap)
                                .addComponent(chon_tat_ca, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pan_volatile.setBackground(new java.awt.Color(204, 204, 204));

        bang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(bang1);

        chontatca.setText("CHỌN TẤT CẢ");
        chontatca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chontatcaActionPerformed(evt);
            }
        });

        a1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        a1.setText("TÊN THÔNG TIN");

        a3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        a3.setText("TÊN FILE CHỨA");

        a5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        a5.setText("LOẠI FILE");

        a7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        a7.setText("LỆNH THU THẬP");

        them_mot_loai.setText("THÊM MỘT LOẠI THÔNG TIN");
        them_mot_loai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                them_mot_loaiActionPerformed(evt);
            }
        });

        jButton6.setText("LÀM MỚI");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        nut_them.setText("THÊM");
        nut_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nut_themActionPerformed(evt);
            }
        });

        nut_huy.setText("HỦY");
        nut_huy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nut_huyActionPerformed(evt);
            }
        });

        valid_them.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        valid_them.setForeground(new java.awt.Color(255, 0, 0));
        valid_them.setText("Vui lòng điền đầy đủ thông tin muốn thu thập!");

        a6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ".txt", ".docx", ".pdf", ".jpeg", ".png", ".sys", "default" }));

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pan_volatileLayout = new javax.swing.GroupLayout(pan_volatile);
        pan_volatile.setLayout(pan_volatileLayout);
        pan_volatileLayout.setHorizontalGroup(
            pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_volatileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_volatileLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(pan_volatileLayout.createSequentialGroup()
                        .addGroup(pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pan_volatileLayout.createSequentialGroup()
                                .addComponent(chontatca)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(them_mot_loai)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(valid_them))
                            .addGroup(pan_volatileLayout.createSequentialGroup()
                                .addComponent(nut_them)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reset)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nut_huy)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pan_volatileLayout.createSequentialGroup()
                        .addGroup(pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(a2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(a1))
                        .addGap(18, 18, 18)
                        .addGroup(pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(a4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(a3))
                        .addGap(18, 18, 18)
                        .addGroup(pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(a5)
                            .addComponent(a6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(a7)
                            .addComponent(a8, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 38, Short.MAX_VALUE))))
        );
        pan_volatileLayout.setVerticalGroup(
            pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_volatileLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chontatca)
                    .addComponent(them_mot_loai)
                    .addComponent(jButton6)
                    .addComponent(valid_them))
                .addGap(26, 26, 26)
                .addGroup(pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(a1)
                    .addComponent(a3)
                    .addComponent(a5)
                    .addComponent(a7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(a2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(a6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pan_volatileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nut_them)
                    .addComponent(nut_huy)
                    .addComponent(reset)))
        );

        tabpane.addTab("Dữ liệu volatile", pan_volatile);

        banglichsu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        banglichsu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                banglichsuMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(banglichsu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))
        );

        tabpane.addTab("Lịch sử phiên thu thập", jPanel1);

        project.setBackground(new java.awt.Color(204, 204, 204));
        project.setVisible(true);

        proper_tieu_de.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setText("PROPERTIES");

        javax.swing.GroupLayout proper_tieu_deLayout = new javax.swing.GroupLayout(proper_tieu_de);
        proper_tieu_de.setLayout(proper_tieu_deLayout);
        proper_tieu_deLayout.setHorizontalGroup(
            proper_tieu_deLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proper_tieu_deLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        proper_tieu_deLayout.setVerticalGroup(
            proper_tieu_deLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        tong.setText("Tổng số loại thông tin đã thu thập: ");

        tong_thanh_cong.setText("Tổng số thu thập thành công:");

        tong_that_bai.setText("Tổng số thu thập thất bại:");

        tong_thoi_gian.setText("Tổng thời gian thu thập:");

        thoi_gian.setText("Thời gian thu thập:");

        he_dieu_hanh.setText("Hệ điều hành: ");

        javax.swing.GroupLayout properLayout = new javax.swing.GroupLayout(proper);
        proper.setLayout(properLayout);
        properLayout.setHorizontalGroup(
            properLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proper_tieu_de, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(properLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(properLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tong)
                    .addComponent(tong_thanh_cong)
                    .addComponent(tong_that_bai)
                    .addComponent(tong_thoi_gian)
                    .addComponent(thoi_gian)
                    .addComponent(he_dieu_hanh))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        properLayout.setVerticalGroup(
            properLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(properLayout.createSequentialGroup()
                .addComponent(proper_tieu_de, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tong)
                .addGap(18, 18, 18)
                .addComponent(tong_thanh_cong)
                .addGap(18, 18, 18)
                .addComponent(tong_that_bai)
                .addGap(18, 18, 18)
                .addComponent(tong_thoi_gian)
                .addGap(18, 18, 18)
                .addComponent(thoi_gian)
                .addGap(18, 18, 18)
                .addComponent(he_dieu_hanh)
                .addGap(0, 82, Short.MAX_VALUE))
        );

        b1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        b1.setText("Name: ");

        b2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        b2.setText("Thư mục chứa thông tin:");

        jMenu13.setText("Project");

        jMenuItem14.setText("Add Libraries");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem14);

        jMenuBar6.add(jMenu13);

        edit.setText("Edit");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        editt.setText("Edit Information Collection");
        editt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edittActionPerformed(evt);
            }
        });
        edit.add(editt);

        jMenuBar6.add(edit);

        project.setJMenuBar(jMenuBar6);

        javax.swing.GroupLayout projectLayout = new javax.swing.GroupLayout(project.getContentPane());
        project.getContentPane().setLayout(projectLayout);
        projectLayout.setHorizontalGroup(
            projectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(projectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(projectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(diachi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nguoi, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addGroup(projectLayout.createSequentialGroup()
                        .addGroup(projectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b1)
                            .addComponent(b2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(diachi1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputDir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        projectLayout.setVerticalGroup(
            projectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projectLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(b1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ten, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(b2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diachi, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(nguoi, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diachi1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(inputDir, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(proper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        trang_thai_thu_thap_pane.setBackground(new java.awt.Color(204, 204, 204));

        trang_thai_thu_thap_pan_tieu_de.setBackground(new java.awt.Color(153, 153, 153));

        jLabel4.setText("TRẠNG THÁI THU THẬP");

        javax.swing.GroupLayout trang_thai_thu_thap_pan_tieu_deLayout = new javax.swing.GroupLayout(trang_thai_thu_thap_pan_tieu_de);
        trang_thai_thu_thap_pan_tieu_de.setLayout(trang_thai_thu_thap_pan_tieu_deLayout);
        trang_thai_thu_thap_pan_tieu_deLayout.setHorizontalGroup(
            trang_thai_thu_thap_pan_tieu_deLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trang_thai_thu_thap_pan_tieu_deLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        trang_thai_thu_thap_pan_tieu_deLayout.setVerticalGroup(
            trang_thai_thu_thap_pan_tieu_deLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        bang_phu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(bang_phu);

        nut_xem.setText("XEM");
        nut_xem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nut_xemActionPerformed(evt);
            }
        });

        nut_refresh.setText("REFRESH");
        nut_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nut_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout trang_thai_thu_thap_paneLayout = new javax.swing.GroupLayout(trang_thai_thu_thap_pane);
        trang_thai_thu_thap_pane.setLayout(trang_thai_thu_thap_paneLayout);
        trang_thai_thu_thap_paneLayout.setHorizontalGroup(
            trang_thai_thu_thap_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(trang_thai_thu_thap_pan_tieu_de, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(trang_thai_thu_thap_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(trang_thai_thu_thap_paneLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(nut_xem, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nut_refresh)
                .addGap(50, 50, 50))
        );
        trang_thai_thu_thap_paneLayout.setVerticalGroup(
            trang_thai_thu_thap_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trang_thai_thu_thap_paneLayout.createSequentialGroup()
                .addComponent(trang_thai_thu_thap_pan_tieu_de, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trang_thai_thu_thap_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nut_xem)
                    .addComponent(nut_refresh))
                .addContainerGap())
        );

        jMenu1.setText("File");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("New");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Save");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem6.setText("Save All");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem4.setText("Import");
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("Export");
        jMenu1.add(jMenuItem5);

        jMenuItem7.setText("Exit");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        jMenu15.setText("Setting");

        jMenuItem15.setText(" Brightness Mode");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem15);

        jMenuItem18.setText("Dark Mode");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem18);

        jMenuItem19.setText("Default Mode");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem19);

        jMenuItem12.setText("Plugins");
        jMenu15.add(jMenuItem12);

        jMenuItem13.setText("Options");
        jMenu15.add(jMenuItem13);

        jMenuBar1.add(jMenu15);

        jMenu2.setText("Help");

        jMenuItem9.setText("Help Contents");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuItem11.setText("Reports Issue");
        jMenu2.add(jMenuItem11);

        jMenuItem10.setText("About");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem10);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pan_tieu_de, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(project, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tao_lan_thu_thap_moi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mo_lan_thu_thap_cu))
                    .addComponent(tabpane, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trang_thai_thu_thap_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pan_tieu_de, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tao_lan_thu_thap_moi)
                            .addComponent(mo_lan_thu_thap_cu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabpane))
                    .addComponent(project)
                    .addComponent(trang_thai_thu_thap_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mo_lan_thu_thap_cuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mo_lan_thu_thap_cuActionPerformed
        // TODO add your handling code here:
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);
    }//GEN-LAST:event_mo_lan_thu_thap_cuActionPerformed

    private void tao_lan_thu_thap_moiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tao_lan_thu_thap_moiActionPerformed
        // TODO add your handling code here:

        mo_lan_thu_thap_cu.setVisible(false);
        tao_lan_thu_thap_moi.setVisible(false);
        new TaoMoi().setVisible(true);
        TaoMoi.OK_rename.setVisible(false);

    }//GEN-LAST:event_tao_lan_thu_thap_moiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new TaoMoi().setVisible(true);
        TaoMoi.OK_taomoi.setVisible(false);
        TaoMoi.Apply.setVisible(false);
        TaoMoi.ten.setText(ten.getText());
        TaoMoi.diachi.setText(diachi.getText());
    }//GEN-LAST:event_jButton1ActionPerformed
    /**
     * Sự kiên cho nút thu thập
     *
     * @param evt
     */
    private void thu_thapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thu_thapActionPerformed

        int d = 0;
        for (int i = 0; i < bang1.getRowCount(); i++) {
            if (bang1.getValueAt(i, 0).toString().equals("true")) {
                d++;
            }
        }

        if (d == 0) {
            valid_main.setVisible(true);
        } else {
            int chose = JOptionPane.showConfirmDialog(null, "Bắt đầu thu thập dữ liệu?", "Message", 0);
            if (chose == 0) {
                tmp.setVisible(true);

                //valid_main.setVisible(true);
                double thoiGianBatDau = new Date().getTime();

                //
                valid_main.setVisible(false);
                //check chọn trạng thái mạng:
                if (bang1.getValueAt(0, 0).toString().equals("true")) {

                    try {
                        business.Function.networkStatus();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                //check chọn các cổng được mở:
                if (bang1.getValueAt(1, 0).toString().equals("true")) {
                    business.Function.openingPort();
                }
                //check chọn các kết nối đã được thiết lập:
                if (bang1.getValueAt(2, 0).toString().equals("true")) {
                    business.Function.establishedConnection();
                }
                //check chọn bảng định tuyến:
                if (bang1.getValueAt(3, 0).toString().equals("true")) {
                    business.Function.routerTable();
                }

                //check chọn dữ liệu trao đổi qua các cổng:
//                if (bang1.getValueAt(4, 0).toString().equals("true")) {
//                    business.Function.dataPort();
//                }

             

                //NON VOLATILE:
                //check chọn cấu hình máy tính:


                //check chọn thông tin thêm đầu tiên bảng 1
                int t = listBonus.size();
                System.out.println(t);
                if (t > 0) {
                    for (int i = 16, j = 0; i <= 16 + t - 1; i++, j++) {
                        if (bang1.getValueAt(i, 0).toString().equals("true")) {
                            business.BaseFunction.baseFunction("cmd", listBonus.get(j).getCommand(), diachi.getText() + "\\" + bang1.getValueAt(i, 2).toString() + bang1.getValueAt(i, 3).toString());
                        }
                    }
                }

                bang_phu.setVisible(true);
                nut_xem.setVisible(true);
                nut_refresh.setVisible(true);

                //
                loadTableOutput();
                Main.proper.setVisible(true);
                //tong thu thap:

                tong.setText("Tổng số thông tin thu thập: " + d);
                //tổng số thu thập thành công:
                int tongSoThuThap = 0;
                for (int i = 0; i < bang_phu.getRowCount(); i++) {
                    if (bang_phu.getValueAt(i, 1).toString().equals("ERROR") == false) {
                        tongSoThuThap++;

                    }
                }

                tong_thanh_cong.setText("Tổng số thu thập thành công: " + tongSoThuThap);
                tong_that_bai.setText("Tổng số thu thập thất bại: " + (d - tongSoThuThap));

                double thoiGianKetThuc = new Date().getTime();

                tong_thoi_gian.setText("Tổng thời gian thu thập: " + (thoiGianKetThuc - thoiGianBatDau) / 1000 + "s");
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
                thoi_gian.setText("Thời gian thu thập: " + formatter.format(new Date()));
                he_dieu_hanh.setText("Hệ điều hành: Windows 10");
                tmp.setVisible(false);
                JOptionPane.showMessageDialog(this, "Đã thu thập xong!");
                //load table history:
                HistoryTime h = new HistoryTime();
                h.setStt(banglichsu.getRowCount() + 1);
                h.setThoiGian(formatter.format(new Date()));
                h.setTongSoTT(d);
                h.setSoThanhCong(tongSoThuThap);
                h.setSoThatBai(d - tongSoThuThap);
                h.setTongThoiGian((thoiGianKetThuc - thoiGianBatDau) / 1000 + "s");
                listOutputs = new ArrayList<>();

                for (int i = 0; i < bang1.getRowCount(); i++) {
                    if (bang1.getValueAt(i, 0).toString().equals("true")) {
                        Output o = new Output();
                        o.setName(bang1.getValueAt(i, 1).toString());
                        Path path = Paths.get(TaoMoi.diachi.getText() + "\\" + bang1.getValueAt(i, 2).toString() + bang1.getValueAt(i, 3).toString());

                        double fileLength = new File(path.toString()).length();

                        if (Files.exists(path) && (fileLength > 0)) {
                            o.setSuccess(TaoMoi.diachi.getText() + "\\" + bang1.getValueAt(i, 2).toString() + bang1.getValueAt(i, 3).toString());

                        } else {
                            o.setSuccess("ERROR");

                        }
                        listOutputs.add(o);

                    }
                }


                h.setBangChiTiet(listOutputs);
                listHistoryTimes.add(h);

                //
                loadTableHistory();

            }else{
                //this.dispose();
            }

        }


    }//GEN-LAST:event_thu_thapActionPerformed

    private void nut_xemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nut_xemActionPerformed
        // TODO add your handling code here:
        if (path.equals("ERROR")) {
            JOptionPane.showMessageDialog(this, "Lỗi không thu thập được dữ liệu!");

        } else {
            business.BaseFunction.openTxtFile(path);

        }


    }//GEN-LAST:event_nut_xemActionPerformed

    private void nut_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nut_refreshActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_nut_refreshActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:

        ArrayList<Evidence> moTrangThaiVolatile = new ArrayList<>();

        try {
            Evidence a1 = new Evidence();
            FileInputStream f = new FileInputStream("H:\\sinhvien.dat");
            ObjectInputStream oIT = new ObjectInputStream(f); // Sử dụng để đọc file theo từng Object
            a1 = (Evidence) oIT.readObject(); //Đọc Object đầu tiên ép kiểu về kiểu SinhVien sau đó gán bằng đối tượng a1
            moTrangThaiVolatile.add(a1);
            loadTable(false, bang1, moTrangThaiVolatile);
            oIT.close();
            f.close();
        } catch (IOException io) {
            System.out.println("Có lỗi xảy ra!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Không tìm thấy class");
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:

        mo_lan_thu_thap_cu.setVisible(false);
        tao_lan_thu_thap_moi.setVisible(false);
        new TaoMoi().setVisible(true);
        TaoMoi.OK_rename.setVisible(false);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        ArrayList<Evidence> luuManChoiVolatile = new ArrayList<>();
        ArrayList<Evidence> luuManChoiNonVolatile = new ArrayList<>();

        for (int i = 0; i < bang1.getRowCount(); i++) {
            Evidence e = new Evidence();
            e.setCollection((Boolean) bang1.getValueAt(i, 0));
            e.setName(bang1.getValueAt(i, 1).toString());
            e.setFileName(bang1.getValueAt(i, 2).toString());
            e.setFileType(bang1.getValueAt(i, 3).toString());
            luuManChoiVolatile.add(e);

        }

        try {
            FileOutputStream f = new FileOutputStream("H:\\sinhvien.dat");
            ObjectOutputStream oOT = new ObjectOutputStream(f); // Sử dụng để ghi file theo từng Object
            for (int i = 0; i < listAllVolatile.size(); i++) {
                oOT.writeObject(listAllVolatile.get(i)); // Ghi  Object là đối tượng a xuống file

            }
            oOT.close();
            f.close();
        } catch (IOException e) {
            System.out.println(listAllVolatile.get(0).getName());
            System.out.println("Có lỗi xảy ra!");
        }


    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        JFileChooser c = new JFileChooser();
        int rVal = c.showSaveDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            String filename = c.getSelectedFile().getName();
            String dir = c.getCurrentDirectory().toString();
            c.getSelectedFile().getName();
            c.getCurrentDirectory().toString();
            String filePath = dir + "\\" + filename;
        }

    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        new Help().setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_editActionPerformed

    private void edittActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edittActionPerformed
        // TODO add your handling code here:
        new TaoMoi().setVisible(true);
        TaoMoi.OK_taomoi.setVisible(false);
        TaoMoi.Apply.setVisible(false);
        TaoMoi.ten.setText(ten.getText());
        TaoMoi.diachi.setText(diachi.getText());


    }//GEN-LAST:event_edittActionPerformed

    private void thu_thapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_thu_thapKeyPressed
        // TODO add your handling code here:


    }//GEN-LAST:event_thu_thapKeyPressed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        pan_tieu_de.setBackground(Color.decode("#ffffff"));
        proper.setBackground(Color.decode("#ffffff"));
        proper_tieu_de.setBackground(Color.decode("#f4f4f4"));
        pan_volatile.setBackground(Color.decode("#ffffff"));
        trang_thai_thu_thap_pane.setBackground(Color.decode("#ffffff"));
        trang_thai_thu_thap_pan_tieu_de.setBackground(Color.decode("#f4f4f4"));
        project.setBackground(Color.decode("#ffffff"));


    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        pan_tieu_de.setBackground(Color.decode("#454644"));
        proper.setBackground(Color.decode("#6c6c6c"));
        proper_tieu_de.setBackground(Color.decode("#454644"));
        pan_volatile.setBackground(Color.decode("#6c6c6c"));
        trang_thai_thu_thap_pane.setBackground(Color.decode("#6c6c6c"));
        trang_thai_thu_thap_pan_tieu_de.setBackground(Color.decode("#454644"));
        project.setBackground(Color.decode("#ffffff"));
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        pan_tieu_de.setBackground(Color.decode("#a9a9a9"));
        proper.setBackground(Color.decode("#cccccb"));
        proper_tieu_de.setBackground(Color.decode("#a9a9a9"));
        pan_volatile.setBackground(Color.decode("#cccccb"));
        trang_thai_thu_thap_pane.setBackground(Color.decode("#cccccb"));
        trang_thai_thu_thap_pan_tieu_de.setBackground(Color.decode("#a9a9a9"));
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        new About().setVisible(true);

    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void dung_thu_thapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dung_thu_thapActionPerformed
        // TODO add your handling code here:
        tmp.setVisible(false);
        JOptionPane.showMessageDialog(this, "Bạn có chắc chắn muốn dừng thu thập?");
    }//GEN-LAST:event_dung_thu_thapActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.FILES_ONLY);
        f.showSaveDialog(null);
        String lib = f.getSelectedFile().toString();
        business.BaseFunction.baseFunction3(business.Function.cmd_prompt, "copy " + lib, "C:\\Project2\\src\\libraries");

    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void chon_tat_caActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chon_tat_caActionPerformed
        // TODO add your handling code here:
        if (chon_tat_ca.getText().equals("CHỌN TẤT CẢ")) {
            chon_tat_ca.setText("BỎ CHỌN TẤT CẢ");
            loadTable(true, bang1, listAllVolatile);


        } else {
            chon_tat_ca.setText("CHỌN TẤT CẢ");
            loadTable(false, bang1, listAllVolatile);

        }
    }//GEN-LAST:event_chon_tat_caActionPerformed

    private void banglichsuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_banglichsuMouseClicked
        // TODO add your handling code here:
        int a = banglichsu.getSelectedRow();

        System.out.println(listHistoryTimes.get(a).getBangChiTiet().size());

        listOutputs = listHistoryTimes.get(a).getBangChiTiet();

        bang_phu.removeAll();
        String[] columns = {"TÊN THÔNG TIN", "OUTPUT"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (int i = 0; i < listOutputs.size(); i++) {
            Vector vector = new Vector();
            vector.add(listOutputs.get(i).getName());
            vector.add(listOutputs.get(i).getSuccess());
            model.addRow(vector);
        }

        bang_phu.setModel(model);
        bang_phu.setRowHeight(30);
        bang_phu.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (bang_phu.getSelectedRow() >= 0) {
                path = bang_phu.getValueAt(bang_phu.getSelectedRow(), 1).toString();
            }
        });

        tong.setText("Tổng số thông tin thu thập: " + banglichsu.getValueAt(a, 2));
        tong_thanh_cong.setText("Tổng số thu thập thành công: " + banglichsu.getValueAt(a, 3));
        tong_that_bai.setText("Tổng số thu thập thất bại: " + banglichsu.getValueAt(a, 4));
        tong_thoi_gian.setText("Tổng thời gian thu thập: " + banglichsu.getValueAt(a, 5));
        thoi_gian.setText("Thời gian thu thập: " + banglichsu.getValueAt(a, 1));
    }//GEN-LAST:event_banglichsuMouseClicked

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        a2.setText("");
        a4.setText("");
        a8.setText("");
        valid_them.setVisible(false);
    }//GEN-LAST:event_resetActionPerformed

    private void nut_huyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nut_huyActionPerformed
        // TODO add your handling code here:
        a1.setVisible(false);
        a2.setVisible(false);
        a3.setVisible(false);
        a4.setVisible(false);
        a5.setVisible(false);
        a6.setVisible(false);
        a7.setVisible(false);
        a8.setVisible(false);
        them_mot_loai.setEnabled(true);
        nut_them.setVisible(false);
        nut_huy.setVisible(false);
        reset.setVisible(false);
        valid_them.setVisible(false);
    }//GEN-LAST:event_nut_huyActionPerformed

    private void nut_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nut_themActionPerformed
        // TODO add your handling code here:
        if ((a2.getText().equals("") == true) && (a4.getText().equals("") == true) && (a8.getText().equals("") == true)) {
            valid_them.setText("Vui lòng điền đầy đủ thông tin muốn thu thập!");
            valid_them.setForeground(Color.red);

            valid_them.setVisible(true);
        } else {
            valid_them.setVisible(false);
            Evidence e = new Evidence();
            e.setCollection(false);
            e.setName(a2.getText());
            e.setFileName(a4.getText());
            e.setFileType(a6.getSelectedItem().toString());
            listAllVolatile.add(e);
            //
            EvidenceBonus eb = new EvidenceBonus(a8.getText());
            listBonus.add(eb);

            loadTable(false, bang1, listAllVolatile);
            valid_them.setText("Đã thêm thành công loại thông tin thu thập!");
            valid_them.setVisible(true);
            valid_them.setForeground(Color.blue);

        }
    }//GEN-LAST:event_nut_themActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        loadTable(false, bang1, listAllVolatile);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void them_mot_loaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_them_mot_loaiActionPerformed
        // TODO add your handling code here:
        a1.setVisible(true);
        a2.setVisible(true);
        a3.setVisible(true);
        a4.setVisible(true);
        a5.setVisible(true);
        a6.setVisible(true);
        a7.setVisible(true);
        a8.setVisible(true);
        them_mot_loai.setEnabled(false);
        nut_them.setVisible(true);
        nut_huy.setVisible(true);
        reset.setVisible(true);
    }//GEN-LAST:event_them_mot_loaiActionPerformed

    private void chontatcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chontatcaActionPerformed
        // TODO add your handling code here:
        if (chontatca.getText().equals("CHỌN TẤT CẢ")) {
            loadTable(true, bang1, listAllVolatile);
            chontatca.setText("BỎ CHỌN TẤT CẢ");
        } else {
            loadTable(false, bang1, listAllVolatile);
            chontatca.setText("CHỌN TẤT CẢ");
        }
    }//GEN-LAST:event_chontatcaActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public static String execCMD(String cmd) throws IOException, InterruptedException{
        
        String command = "ping -c 3 www.google.com";

        Process proc = Runtime.getRuntime().exec(cmd);

        // Read the output

        BufferedReader reader =  
              new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
        String ret = "";
        while((line = reader.readLine()) != null) {
           ret +=line;
           ret +="\n";
        }
        proc.waitFor();
        
        String result = null;
        if ((ret != null) && (ret.length() > 0)) {
            result = ret.substring(0, ret.length() - 1);
        }
        return result;
    }
    
    public static void main(String args[]) throws IOException, InterruptedException {
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });

        
         
        

    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel a1;
    private javax.swing.JTextField a2;
    private javax.swing.JLabel a3;
    private javax.swing.JTextField a4;
    private javax.swing.JLabel a5;
    private javax.swing.JComboBox<String> a6;
    private javax.swing.JLabel a7;
    private javax.swing.JTextField a8;
    public static javax.swing.JLabel b1;
    public static javax.swing.JLabel b2;
    public static javax.swing.JTable bang1;
    public static javax.swing.JTable bang_phu;
    public static javax.swing.JTable banglichsu;
    public static javax.swing.JCheckBox chon_tat_ca;
    private javax.swing.JButton chontatca;
    public static javax.swing.JLabel dang_thu_thap;
    public static javax.swing.JLabel diachi;
    public static javax.swing.JLabel diachi1;
    public static javax.swing.JButton dung_thu_thap;
    public static javax.swing.JMenu edit;
    public static javax.swing.JMenuItem editt;
    private javax.swing.JLabel he_dieu_hanh;
    public static javax.swing.JLabel inputDir;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    public static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JMenuBar jMenuBar5;
    private javax.swing.JMenuBar jMenuBar6;
    private javax.swing.JMenuBar jMenuBar7;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    public static javax.swing.JButton mo_lan_thu_thap_cu;
    public static javax.swing.JLabel nguoi;
    private javax.swing.JButton nut_huy;
    public static javax.swing.JButton nut_refresh;
    private javax.swing.JButton nut_them;
    public static javax.swing.JButton nut_xem;
    private javax.swing.JPanel pan_tieu_de;
    private javax.swing.JPanel pan_volatile;
    private javax.swing.JInternalFrame project;
    public static javax.swing.JPanel proper;
    private javax.swing.JPanel proper_tieu_de;
    private javax.swing.JButton reset;
    public static javax.swing.JTabbedPane tabpane;
    public static javax.swing.JButton tao_lan_thu_thap_moi;
    public static javax.swing.JLabel ten;
    private javax.swing.JButton them_mot_loai;
    private javax.swing.JLabel thoi_gian;
    public static javax.swing.JButton thu_thap;
    private javax.swing.JLabel tong;
    private javax.swing.JLabel tong_thanh_cong;
    private javax.swing.JLabel tong_that_bai;
    private javax.swing.JLabel tong_thoi_gian;
    private javax.swing.JPanel trang_thai_thu_thap_pan_tieu_de;
    public static javax.swing.JPanel trang_thai_thu_thap_pane;
    private javax.swing.JLabel valid_main;
    private javax.swing.JLabel valid_them;
    // End of variables declaration//GEN-END:variables
}
