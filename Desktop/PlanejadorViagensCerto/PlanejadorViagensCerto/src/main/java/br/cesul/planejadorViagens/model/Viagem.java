package br.cesul.planejadorViagens.model;

import org.bson.types.ObjectId;

import java.time.LocalDate;

// Espelhar os dados do banco atraves de uma entidade
// esta classe vai representar UMA VIAGEM
// O que tem que ter:
// - Construtor
// - Atributos com getters/setters
public class Viagem {
    private ObjectId id; // Armazenar o _id gerado pelo mongo
    private String destino;
    private Double custo;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    // POJO Codec
    // POJO significa: Plane Old Java Object


    public Viagem(){}

    public Viagem(ObjectId id, String destino, Double custo, LocalDate dataInicio, LocalDate dataFim) {
        this.id = id;
        this.destino = destino;
        this.custo = custo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
