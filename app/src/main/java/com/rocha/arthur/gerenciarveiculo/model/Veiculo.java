package com.rocha.arthur.gerenciarveiculo.model;

import java.io.Serializable;

/**
 * Created by arthur on 06/07/16.
 */

public class Veiculo implements Serializable {

    private Long id;
    private String marca;
    private String modelo;
    private String placa;
    private String ano;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
