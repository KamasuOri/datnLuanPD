/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.util.ArrayList;

/**
 *
 * @author hantr
 */
public class Evidence {

    private Boolean collection;
    private String name;
    private String fileName;
    private String fileType;

    public Evidence() {
    }

    public Evidence(Boolean collection, String name, String fileName, String fileType) {
        this.collection = collection;
        this.name = name;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Boolean getCollection() {
        return collection;
    }

    public void setCollection(Boolean collection) {
        this.collection = collection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public static ArrayList<Evidence> listAllVolatile() {
        ArrayList<Evidence> listVolatiles = new ArrayList<Evidence>();
        listVolatiles.add(new Evidence(false, "getBrowserCache", "getBrowserCache", ".txt"));
        listVolatiles.add(new Evidence(false, "getUserLoginHistory", "getUserLoginHistory", ".txt"));
        listVolatiles.add(new Evidence(false, "getNetworkConfig", "getNetworkConfig", ".txt"));
        listVolatiles.add(new Evidence(false, "getRDPHistory", "getRDPHistory", ".txt"));
        listVolatiles.add(new Evidence(false, "copyChosenFile", "copyChosenFile", ".txt"));

        return listVolatiles;
    }


}
