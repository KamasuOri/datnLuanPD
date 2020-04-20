/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author hantr
 */
public class BaseFunction {

    /**
     * Hàm cơ sở để xây dựng thu thập cho tất cả các hàm riêng hàm này chỉ chạy
     * 1 câu lệnh
     */
    public static void baseFunction(String way, String command, String path) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(way);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println(command + " > " + path);
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        appendDataToFile(path);

    }

    /**
     * Hàm cơ sở trong đó có hai câu lệnh được chạy
     */
    public static void baseFunction2(String way, String command1, String command2, String path) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(way);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println(command1);
            stdin.println(command2 + " > " + path);
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        appendDataToFile(path);

    }

    /**
     * Ham chi thu hien duy nhat 1 cau lenh de xuat, khong thuc hien sao chep ra
     * file txt
     */
    public static void baseFunction3(String way, String command, String path) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(way);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println(command + " " + path);
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        appendDataToFile(path);

    }

    /**
     * Ham co di chuyen vao trong thu vien cua usb, nhung khong co >
     */
    public static void baseFunction4(String way, String command1, String command2, String path) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(way);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println(command1);
            stdin.println(command2 + " " + path);
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        appendDataToFile(path);

    }

    /**
     * Ham de mo file txt
     */
    public static void openTxtFile(String path) {
        Process p;
        try {
            p = Runtime.getRuntime().exec("cmd");
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println(path);
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Ham ghi them noi dung vao file txt
     */
    public static void appendDataToFile(String pathFile) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
            String thoiGianThuThap = formatter.format(new Date());
            //dia chi ip va mac:
            String diaChiMac = null;
            String diaChiIP = null;

            InetAddress ip;
            try {

                ip = InetAddress.getLocalHost();
                diaChiIP = ip.getHostAddress();

                NetworkInterface network = NetworkInterface.getByInetAddress(ip);

                byte[] mac = network.getHardwareAddress();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                diaChiMac = sb.toString();

            } catch (UnknownHostException e) {

                e.printStackTrace();

            } catch (SocketException e) {

                e.printStackTrace();

            }
            String heDieuHanh = System.getProperty("os.name").toString();

            String data = "\nTime: " + thoiGianThuThap + "\nIP Address: " + diaChiIP + "\nMAC Address: " + diaChiMac + "\nOS: " + heDieuHanh;

            File file = new File(pathFile);

            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
