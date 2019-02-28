/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

/**
 *
 * @author dam
 */
public class Movimiento {

    int idMovimiento;
    private final int id_cuenta_fk;
    private final String fechaMovimiento;
    private final String tipoMovimiento;
    private final float importeMovimiento;

    public Movimiento(int idMovimiento, int id_cuenta_fk, String fechaMovimiento, String tipoMovimiento, float importeMovimiento) {
        this.idMovimiento = idMovimiento;
        this.id_cuenta_fk = id_cuenta_fk;
        this.fechaMovimiento = fechaMovimiento;
        this.tipoMovimiento = tipoMovimiento;
        this.importeMovimiento = importeMovimiento;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public int getId_cuenta_fk() {
        return id_cuenta_fk;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public float getImporteMovimiento() {
        return importeMovimiento;
    }
}
