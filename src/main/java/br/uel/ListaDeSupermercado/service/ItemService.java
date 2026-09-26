package br.uel.ListaDeSupermercado.service;

import br.uel.ListaDeSupermercado.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ItemService {

    // Lista em memória usada como armazenamento provisório.
    // Quando a persistência com JPA/MySQL for feita, essa lista
    // será substituída por um repository de verdade, sem mudar
    // as regras de negócio abaixo.
    private final List<Item> itens = new ArrayList<>();
    private Long proximoId = 1L;

    public Item cadastrar(Item item) {
        if (item.getNome() == null || item.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        item.setId(proximoId);
        proximoId++;
        itens.add(item);
        return item;
    }

    public List<Item> listarTodos() {
        return new ArrayList<>(itens);
    }

    public Item buscarPorId(Long id) {
        for (Item item : itens) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new ItemNaoEncontradoException(id);
    }

    public List<Item> buscarPorNome(String nome) {
        List<Item> encontrados = new ArrayList<>();
        for (Item item : itens) {
            if (item.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(item);
            }
        }
        return encontrados;
    }

    public Item atualizar(Long id, Item dadosAtualizados) {
        Item item = buscarPorId(id);

        if (dadosAtualizados.getNome() == null || dadosAtualizados.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }

        item.setNome(dadosAtualizados.getNome());
        item.setQuantidade(dadosAtualizados.getQuantidade());
        item.setCategoria(dadosAtualizados.getCategoria());
        item.setPrecoUnitario(dadosAtualizados.getPrecoUnitario());
        item.setComprado(dadosAtualizados.isComprado());
        item.setUnidadeMedida(dadosAtualizados.getUnidadeMedida());

        return item;
    }

    public void excluir(Long id) {
        Item item = buscarPorId(id);
        itens.remove(item);
    }

    public List<Item> ordenarPorNome(boolean crescente) {
        List<Item> lista = new ArrayList<>(itens);
        Comparator<Item> comparador = Comparator.comparing(Item::getNome, String.CASE_INSENSITIVE_ORDER);
        if (!crescente) {
            comparador = comparador.reversed();
        }
        lista.sort(comparador);
        return lista;
    }

    public List<Item> ordenarPorPreco(boolean crescente) {
        List<Item> lista = new ArrayList<>(itens);
        Comparator<Item> comparador = Comparator.comparingDouble(Item::getPrecoUnitario);
        if (!crescente) {
            comparador = comparador.reversed();
        }
        lista.sort(comparador);
        return lista;
    }
}