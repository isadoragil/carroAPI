package com.example.carroAPI.model;

public class VeiculosStats {
    private Double valor;
    private String marca;
    private String modelo;
    private Integer ano;
    private String tipoCombustivel;

    public VeiculosStats(Veiculo veiculo) {
        this.valor = Double.valueOf(veiculo.valor()
                .replaceAll("R\\$", "")
                        .replaceAll(" ", " ")
                .replaceAll("\\.", "")
                .replaceAll(",", "."));
        this.marca = veiculo.marca();
        this.modelo = veiculo.modelo();
        this.ano = veiculo.ano();
        this.tipoCombustivel = veiculo.tipoCombustivel();
    }

    public Double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return marca + " " + ano + ", valor: R$" + valor;
    }
}
