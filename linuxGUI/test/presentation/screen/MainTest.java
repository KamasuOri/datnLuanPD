/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.screen;

import DAL.Evidence;
import java.util.ArrayList;
import javax.swing.JTable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author LuanPD
 */
public class MainTest {
    
    public MainTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of loadTable method, of class Main.
     */
    @Test
    public void testLoadTable() {
        System.out.println("loadTable");
        Boolean a = null;
        JTable b = null;
        ArrayList<Evidence> list = null;
        Main.loadTable(a, b, list);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadTableOutput method, of class Main.
     */
    @Test
    public void testLoadTableOutput() {
        System.out.println("loadTableOutput");
        Main instance = new Main();
        instance.loadTableOutput();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadTableHistory method, of class Main.
     */
    @Test
    public void testLoadTableHistory() {
        System.out.println("loadTableHistory");
        Main.loadTableHistory();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkAdmin method, of class Main.
     */
    @Test
    public void testCheckAdmin() {
        System.out.println("checkAdmin");
        String expResult = "";
        String result = Main.checkAdmin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        Main.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
