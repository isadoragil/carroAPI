package com.example.carroAPI.principal;

import com.example.carroAPI.model.Dados;
import com.example.carroAPI.model.Modelos;
import com.example.carroAPI.model.Veiculo;
import com.example.carroAPI.model.VeiculosStats;
import com.example.carroAPI.service.ConsumoAPI;
import com.example.carroAPI.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar:
                """;
        System.out.println(menu);
        var opcao = sc.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else if (opcao.toLowerCase().contains("caminh")) {
            endereco = URL_BASE + "caminhoes/marcas";
        } else {
            endereco = null;
            System.out.println("Opção não disponível. Verifique!");
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class); //(List<Dados>)
        System.out.println(marcas);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("Informe ao código da marca para consumo: ");
        var codigoMarca = sc.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);

        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos desta marca:");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado:");
        var nomeVeiculo = sc.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo escolhido:");
        var codigoModelo = sc.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);

        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        List<VeiculosStats> listaVeiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            String enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);

            VeiculosStats veiculosStats = new VeiculosStats(veiculo);
            listaVeiculos.add(veiculosStats);
        }

        System.out.println("\nTodos os veículos filtrados com avaliações por ano:");
//        veiculos.forEach(System.out::println);
//        System.out.println("---------------------------------");
        listaVeiculos.forEach(System.out::println);
        System.out.println();
        List<VeiculosStats> valores = listaVeiculos.stream()
                .sorted(Comparator.comparing(VeiculosStats::getValor))
                .collect(Collectors.toList());

        valores.forEach(System.out::println);

        DoubleSummaryStatistics stats = valores.stream()
                .collect(Collectors.summarizingDouble(VeiculosStats::getValor));
        System.out.println("Média de preço: " + String.format("R$ %.2f", stats.getAverage()));
        System.out.println("Valor mais em conta: " + String.format("R$ %.2f", stats.getMin()));

    }
}
