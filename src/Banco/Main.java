/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author dam
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws IOException, SQLException {
        // TODO code application logic here
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        Cajero cajero = new Cajero();
        while (timeOfDay >= 0 && timeOfDay <= 24) {
            if (timeOfDay < 4 || timeOfDay > 3) {
                cajero.inicio();
            } else {
                cajero.getSt().close();
                System.out.println("Mantenimiento.");
            }
        }
    }
}
