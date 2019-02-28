/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

import numbers.Number;
import dates.Date;
import dni.DNI;
import pin.PIN;
import java.io.*;
import java.util.*;
import java.sql.*;

/**
 *
 * @author dam
 */
public class Cajero {

    DBConnector acceso;
    private Statement st;
    private ResultSet rs;

    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Cuenta> cuentas = new ArrayList<>();
    ArrayList<Movimiento> movimientos = new ArrayList<>();

    public Cajero() {
        acceso = new DBConnector();
        st = acceso.getSt();
        clientesToArrayList();
        cuentasToArrayList();
        movimientosToArrayList();
    }

    public void inicio() throws IOException {
        String dni;
        int pin;
        boolean existe, existeCuenta;
        dni = DNI.pedirDNI();
        pin = PIN.pedirPIN();
        existe = clienteExiste(dni);
        while (existe && pin != -1) {
            existeCuenta = cuentaExiste(dni);
            if (!existeCuenta) {
                crearCuenta(dni);
            }

            menu();
        }
        if (!existe) {
            System.out.println("Registrando cliente.");
            registrarCliente(dni, pin);
        }
    }

    public void menu() throws IOException {
        int opcion = Number.askForNumberInt("Selecciona las siguientes opciones: "
                + "<0 salir> <1 ingresar> <2 retirar> <3 movimientos>: ", 0, 3);
        while (opcion != 0) {
            switch (opcion) {
                case 1:
                    ingresar();
                    break;
                case 2:
                    retirar();
                    break;
                case 3:
                    movimientos();
                    break;
            }
            opcion = Number.askForNumberInt("Selecciona las siguientes opciones: "
                    + "<0 salir> <1 ingresar> <2 retirar> <3 movimientos>: ", 0, 3);
        }
        System.out.println("Cerraste sesion.");
    }

    public void ingresar() throws IOException {
        float importe;
        importe = Number.askForNumberInt("Que importe desea ingresar?:", 10, 5000);
        String istCuentas = "INSERT INTO cuentas (balance) "
                + "VALUES ("
                + "'" + importe + "'"
                + ")";
        try {
            st.executeUpdate(istCuentas);
            System.out.println("Cliente registrado satisfactoriamente.");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void retirar() {
        float retiro;
    }

    public void movimientos() {

    }

    public String pedirString(String mensaje) {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        String cadena = "";
        try {
            System.out.println(mensaje);
            cadena = teclado.readLine();
            while (cadena.equals("")) {
                System.out.println("Campo obligatorio.");
                cadena = teclado.readLine();
            }
        } catch (IOException es) {
        }
        return cadena;
    }

    public Statement getSt() {
        return st;
    }

    private void clientesToArrayList() {
        try {
            String query = "SELECT * FROM clientes";
            st = acceso.getSt();
            rs = st.executeQuery(query);
            while (rs.next()) {
                String dni = rs.getString("dni_cliente");
                String nombre = rs.getString("nombre");
                int pin = rs.getInt("pin_cliente");
                String fechaAlta = rs.getString("fecha_alta");
                clientes.add(new Cliente(dni, nombre, fechaAlta));
            }
        } catch (SQLException | NullPointerException a) {
        }
    }

    private void movimientosToArrayList() {
        try {
            String query = "SELECT * FROM movimientos";
            st = acceso.getSt();
            rs = st.executeQuery(query);
            while (rs.next()) {
                int idMovimiento = rs.getInt("id_movimiento");
                int id_cuenta_fk = rs.getInt("id_cuenta_fk");
                String fechaAlta = rs.getString("fecha_movimiento");
                String tipoMovimiento = rs.getString("tipo_movimiento");
                float importeMovimiento = rs.getFloat("importe_movimiento");
                movimientos.add(new Movimiento(idMovimiento, id_cuenta_fk, fechaAlta, tipoMovimiento, importeMovimiento));
            }
        } catch (SQLException | NullPointerException a) {
        }
    }

    private void cuentasToArrayList() {
        try {
            String query = "SELECT * FROM cuentas";
            st = acceso.getSt();
            rs = st.executeQuery(query);
            while (rs.next()) {
                int idCuenta = rs.getInt("id_cuenta");
                int id_cliente_fk = rs.getInt("id_cliente_fk");
                float balance = rs.getFloat("balance");
                String fechaApertura = rs.getString("fecha_apertura");
                cuentas.add(new Cuenta(idCuenta, id_cliente_fk, balance, fechaApertura));
            }
        } catch (SQLException | NullPointerException a) {
        }
    }

    public boolean clienteExiste(String dni) {
        String dniFound;
        boolean result = false;
        try {
            String query = "SELECT dni_cliente FROM clientes "
                    + "WHERE "
                    + "dni_cliente=" + "'" + dni + "'";
            st = acceso.getSt();
            rs = st.executeQuery(query);
            while (rs.next()) {
                dni = rs.getString("dni_cliente");
                dniFound = dni;
                if (dniFound.compareTo(dni) != 0) {
                    result = true;
                }
            }
        } catch (SQLException | NullPointerException a) {
        }
        return result;
    }

    public boolean cuentaExiste(String dni) {
        String dniFound;
        boolean result = false;
        try {
            String query = "SELECT id_cliente_fk FROM cuentas "
                    + "WHERE "
                    + "id_cliente_fk=" + "'" + dni + "'";
            st = acceso.getSt();
            rs = st.executeQuery(query);
            while (rs.next()) {
                dni = rs.getString("dni_cliente");
                dniFound = dni;
                if (dniFound.compareTo(dni) != 0) {
                    result = true;
                }
            }
        } catch (SQLException | NullPointerException a) {
        }
        return result;
    }

    public void registrarCliente(String dni, int pin) {
        Date fAlta = new Date();
        String nombre;
        nombre = pedirString("Introduzca su nombre: ");
        String istClientes = "INSERT INTO clientes (dni_cliente,nombre,pin_cliente,fecha_alta) "
                + "VALUES ("
                + "'" + dni + "'"
                + ","
                + "'" + nombre + "'"
                + ","
                + "'" + pin + "'"
                + ","
                + "'" + fAlta.completeDate() + "'"
                + ")";
        try {
            st.executeUpdate(istClientes);
            System.out.println("Cliente registrado satisfactoriamente.");
        } catch (SQLException | NullPointerException a) {
        }
    }

    public void crearCuenta(String dni) {
        Date fApertura = new Date();
        float balance = 0.00f;
        String istCuentas = "INSERT INTO clientes (id_cuenta,id_cliente_fk,balance,fecha_apertura) "
                + "VALUES ("
                + "'" + dni + "'"
                + ","
                + "'" + balance + "'"
                + ","
                + "'" + fApertura + "'"
                + ")";
        try {
            st.executeUpdate(istCuentas);
            System.out.println("Cliente registrado satisfactoriamente.");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}
