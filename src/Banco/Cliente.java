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
public class Cliente {

    String dniCliente;
    private final String nombre;
    private final String fechaAlta;

    public Cliente(String dniCliente, String nombre, String fechaAlta) {
        this.dniCliente = dniCliente;
        this.nombre = nombre;
        this.fechaAlta = fechaAlta;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }
}
