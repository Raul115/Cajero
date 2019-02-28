/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

/**
 *
 * @author Rulo
 */
public class Cuenta {

    int idCuenta;
    private final int id_cliente_fk;
    private final float balance;
    private final String fechaApertura;

    public Cuenta(int idCuenta, int id_cliente_fk, float balance, String fechaApertura) {
        this.idCuenta = idCuenta;
        this.id_cliente_fk = id_cliente_fk;
        this.balance = balance;
        this.fechaApertura = fechaApertura;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public int getId_cliente_fk() {
        return id_cliente_fk;
    }

    public float getBalance() {
        return balance;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }
}
