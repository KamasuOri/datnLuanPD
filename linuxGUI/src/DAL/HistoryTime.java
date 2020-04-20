/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author hantr
 */
public class HistoryTime {
    private int stt;
    private String thoiGian;
    private int tongSoTT ;
    private int soThanhCong ;
    private int soThatBai ;
    private String tongThoiGian ;
    private ArrayList<Output> bangChiTiet ;

    public HistoryTime() {
    }

    public HistoryTime(int stt, String thoiGian, int tongSoTT, int soThanhCong, int soThatBai, String tongThoiGian, ArrayList<Output> bangChiTiet) {
        this.stt = stt;
        this.thoiGian = thoiGian;
        this.tongSoTT = tongSoTT;
        this.soThanhCong = soThanhCong;
        this.soThatBai = soThatBai;
        this.tongThoiGian = tongThoiGian;
        this.bangChiTiet = bangChiTiet;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getTongSoTT() {
        return tongSoTT;
    }

    public void setTongSoTT(int tongSoTT) {
        this.tongSoTT = tongSoTT;
    }

    public int getSoThanhCong() {
        return soThanhCong;
    }

    public void setSoThanhCong(int soThanhCong) {
        this.soThanhCong = soThanhCong;
    }

    public int getSoThatBai() {
        return soThatBai;
    }

    public void setSoThatBai(int soThatBai) {
        this.soThatBai = soThatBai;
    }

    public String getTongThoiGian() {
        return tongThoiGian;
    }

    public void setTongThoiGian(String tongThoiGian) {
        this.tongThoiGian = tongThoiGian;
    }

    public ArrayList<Output> getBangChiTiet() {
        return bangChiTiet;
    }

    public void setBangChiTiet(ArrayList<Output> bangChiTiet) {
        this.bangChiTiet = bangChiTiet;
    }
    
    
    
    
}
