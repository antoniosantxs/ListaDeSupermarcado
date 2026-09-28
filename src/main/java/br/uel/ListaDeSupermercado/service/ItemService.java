package br.uel.ListaDeSupermercado.service;

import br.uel.ListaDeSupermercado.model.Item;
import br.uel.ListaDeSupermercado.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repository;

    // Injeção de dependência via construtor
    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    // Listagem padrão
    public List<Item> listarTodos() {
        return repository.findAll();
    }

    // Busca por termo no nome
    public List<Item> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return repository.findAll();
        }
        return repository.findByNomeContainingIgnoreCase(nome);
    }
    // Ordenação dinâmica por campo e ordem (asc/desc)
    public List<Item> listarOrdenado(String campo, String ordem) {
        boolean desc = "desc".equalsIgnoreCase(ordem);

        if ("quantidade".equalsIgnoreCase(campo) || "qtd".equalsIgnoreCase(campo)) {
            return desc ? repository.findAllByOrderByQuantidadeDesc()
                    : repository.findAllByOrderByQuantidadeAsc();
        }

        // NOVO: ordenação por categoria
        if ("categoria".equalsIgnoreCase(campo)) {
            return desc ? repository.findAllByOrderByCategoriaDesc()
                    : repository.findAllByOrderByCategoriaAsc();
        }

        // NOVO: ordenação por preço
        if ("precoUnitario".equalsIgnoreCase(campo)) {
            return desc ? repository.findAllByOrderByPrecoUnitarioDesc()
                    : repository.findAllByOrderByPrecoUnitarioAsc();
        }

        return desc ? repository.findAllByOrderByNomeDesc()
                : repository.findAllByOrderByNomeAsc();
    }
    // Salva/Atualiza o registro
    public Item salvar(Item item) {
        return repository.save(item);
    }

    // Busca por ID passando apenas o parâmetro 'id' (Long) para a exceção
    public Item buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNaoEncontradoException(id));
    }

    // Exclusão verificando existência prévia
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ItemNaoEncontradoException(id);
        }
        repository.deleteById(id);
    }

    // Alias/Sobrecarga para compatibilidade com excluirPorId
    public void excluirPorId(Long id) {
        this.excluir(id);
    }
}