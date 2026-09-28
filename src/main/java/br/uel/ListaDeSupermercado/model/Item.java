package br.uel.ListaDeSupermercado.model;

// Importação do JPA para Banco de Dados MySQL - garante o funcionamento do Hibernate
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Importação das Validações - pacote Bean Validation
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "lista_de_supermercado")
public class Item {

    @Id  //define a ID como chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremento da id conforme novos registros
    private Long id;

    @NotBlank(message = "O nome do item é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull(message = "A quantidade a comprar é obrigatória.")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    @Column(nullable = false)
    private Integer quantidade = 1;

    @NotBlank(message = "A categoria é obrigatória.")
    @Column(nullable = false, length = 50)
    private String categoria;

    @Min(value = 0, message = "Atenção, preço não pode ser negativo.")
    @Column
    private Double precoUnitario;

    @Column(nullable = false)
    private Boolean comprado = false;

    @NotBlank(message = "Unidade de medida é obrigatória.")
    @Column(nullable = false, length = 20)
    private String unidadeMedida;

    @Min(value = 0, message = "Atenção, não é possível adicionar quantidade em estoque negativa.")
    @Column
    private Integer quantidadeEstoque; // Opcional (Integer permite null)

    @Min(value = 0, message = "O estoque não pode ser negativo.")
    @Column
    private Integer estoqueMinimo; // Opcional (Integer permite null)

    // Construtor Padrão
    public Item() {
    }

    // Construtor Parametrizado Corrigido
    public Item(String nome, Integer quantidade, String categoria, Double precoUnitario,
                Boolean comprado, String unidadeMedida, Integer quantidadeEstoque, Integer estoqueMinimo) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.precoUnitario = precoUnitario;
        this.comprado = comprado;
        this.unidadeMedida = unidadeMedida;
        this.quantidadeEstoque = quantidadeEstoque;
        this.estoqueMinimo = estoqueMinimo;
    }

    // Método de Negócio para o Status do Estoque (Protegido contra NullPointer)
    public String getStatusEstoque() {
        if (estoqueMinimo == null || quantidadeEstoque == null) {
            return "Sem Controle";
        }

        if (quantidadeEstoque == 0) {
            return "Compra urgente";
        } else if (quantidadeEstoque < estoqueMinimo) {
            return "Atenção: Estoque Abaixo do Mínimo";
        } else if (quantidadeEstoque.equals(estoqueMinimo)) {
            return "Estoque Mínimo";
        } else {
            return "Status OK - Acima do Mínimo";
        }
    }

    // Getters e Setters
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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Boolean getComprado() {
        return comprado;
    }

    // RESOLVE O ERRO DO ItemService: Método de conveniência/atalho boolean
    public Boolean isComprado() {
        return comprado;
    }

    public void setComprado(Boolean comprado) {
        this.comprado = comprado;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }
}