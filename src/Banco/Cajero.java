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
        int pin, opcion;
        boolean existe, existeCuenta;
        System.out.println("Bienvenido.");
        dni = DNI.pedirDNI();
        existe = clienteExiste(dni);
        if (!existe) {
            opcion = Number.askForNumberInt("No está registrado. ¿Desea registrarse como cliente? <1 si> <2 no>: ", 1, 2);
            switch (opcion) {
                case 1:
                    registrarCliente(dni);
                    menu();
                    break;
                case 2:
                    System.out.println("Saliendo.");
                    break;
            }
        } else {
            pin = PIN.pedirPIN();
            while (existe && pin != -1) {
                existeCuenta = cuentaExiste(dni);
                if (!existeCuenta) {
                    crearCuenta(dni);
                } else {
                    menu();
                }
            }
        }
    }

    public void menu() {
        try {

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
        } catch (NumberFormatException ex) {
        }

    }

    public void ingresar() {
        float importe;
        importe = Number.askForNumberFloat("Que importe desea ingresar?: ", 10, 5000);
        String istCuenta = "INSERT INTO cuenta (balance_cuenta) "
                + "VALUES ("
                + "'" + importe + "'"
                + ")";
        try {
            st.executeUpdate(istCuenta);
            System.out.println("Ingreso realizado satisfactoriamente.");
        } catch (NumberFormatException | SQLException | NullPointerException ex) {
        }
    }

    public void retirar() {
        float retiro;
        retiro = Number.askForNumberFloat("Que importe desea retirar?: ", 10, 500);
        String istCuenta = "INSERT INTO cuenta (balance_cuenta) "
                + "VALUES ("
                + "'" + retiro + "'"
                + ")";
    }

    public void movimientos() {

    }

    public void mostrarBalance() {

    }

    public String pedirString(String mensaje) {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        String cadena = "";
        try {
            System.out.print(mensaje);
            cadena = teclado.readLine();
            while (cadena.equals("")) {
                System.out.println("Campo obligatorio.");
                cadena = teclado.readLine();
            }
        } catch (IOException ex) {
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
        } catch (SQLException | NullPointerException ex) {
        }
    }

    private void movimientosToArrayList() {
        try {
            String query = "SELECT * FROM movimiento";
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
        } catch (SQLException | NullPointerException ex) {
        }
    }

    private void cuentasToArrayList() {
        try {
            String query = "SELECT * FROM cuenta";
            st = acceso.getSt();
            rs = st.executeQuery(query);
            while (rs.next()) {
                int idCuenta = rs.getInt("id_cuenta");
                int id_cliente_fk = rs.getInt("id_cliente_fk");
                float balance = rs.getFloat("balance_cuenta");
                String fechaApertura = rs.getString("fecha_apertura_cuenta");
                cuentas.add(new Cuenta(idCuenta, id_cliente_fk, balance, fechaApertura));
            }
        } catch (SQLException | NullPointerException ex) {
        }
    }

    public boolean clienteExiste(String dni) {
        String dniFound;
        boolean result = false;
        try {
            String query = "SELECT dni_cliente FROM cliente "
                    + "WHERE "
                    + "dni_cliente=" + "'" + dni + "'";
            st = acceso.getSt();
            rs = st.executeQuery(query);
            while (rs.next()) {
                dniFound = rs.getString("dni_cliente");
                if (dni.equals(dniFound)) {
                    result = true;
                }
            }
        } catch (SQLException | NullPointerException ex) {
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
        } catch (SQLException | NullPointerException ex) {
        }
        return result;
    }

    public void registrarCliente(String dni) {
        Date fAlta = new Date();
        String nombre;
        System.out.println("Registrando cliente.");
        nombre = pedirString("Introduzca su nombre: ");
        int pin = PIN.pedirPIN();
        String istClientes = "INSERT INTO cliente (dni_cliente,nombre_cliente,pin_cliente,fecha_alta_cliente) "
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
        } catch (SQLException | NullPointerException ex) {
        }
    }

    public void crearCuenta(String dni) {
        Date fApertura = new Date();
        float balance = 0f;
        String istCuentas = "INSERT INTO cuenta (id_cliente_fk,balance_cuenta,fecha_apertura_cuenta) "
                + "VALUES ("
                + "'" + dni + "'"
                + ","
                + "'" + balance + "'"
                + ","
                + "'" + fApertura.completeDate() + "'"
                + ")";
        try {
            st.executeUpdate(istCuentas);
            System.out.println("Cuenta creada satisfactoriamente.");
        } catch (SQLException | NullPointerException ex) {
        }
    }
}
