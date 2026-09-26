package br.uel.ListaDeSupermercado.model;

public class Item {

    private Long id;
    private String nome;
    private int quantidade;
    private String categoria;
    private double precoUnitario;
    private boolean comprado;
    private String unidadeMedida;

    public Item() {
    }

    public Item(String nome, int quantidade, String categoria, double precoUnitario,
                boolean comprado, String unidadeMedida) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.precoUnitario = precoUnitario;
        this.comprado = comprado;
        this.unidadeMedida = unidadeMedida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}