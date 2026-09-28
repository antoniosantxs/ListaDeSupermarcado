package br.uel.ListaDeSupermercado.service;

import br.uel.ListaDeSupermercado.model.Item;
import br.uel.ListaDeSupermercado.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repository;

    // Injeção de dependência do Repository
    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    // Aceita busca e ordenação em uma única chamada para a Controller
    public List<Item> listarTodos(String buscar, String ordem) {
        // Se houver termo de busca, filtra por nome
        if (buscar != null && !buscar.isBlank()) {
            return repository.findByNomeContainingIgnoreCase(buscar);
        }

        // Se houver parâmetro de ordenação
        if (ordem != null && !ordem.isBlank()) {
            switch (ordem.toLowerCase()) {
                case "nome_asc":
                case "asc":
                    return repository.findAllByOrderByNomeAsc();
                case "nome_desc":
                case "desc":
                    return repository.findAllByOrderByNomeDesc();
                case "qtd_asc":
                    return repository.findAllByOrderByQuantidadeAsc();
                case "qtd_desc":
                    return repository.findAllByOrderByQuantidadeDesc();
            }
        }

        // Retorno padrão sem filtros
        return repository.findAll();
    }

    // Sobrecarga sem argumentos para casos de listagem simples
    public List<Item> listarTodos() {
        return repository.findAll();
    }

    // Salva ou atualiza registros no banco
    public Item salvar(Item item) {
        return repository.save(item);
    }

    // Busca um item por ID
    public Item buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNaoEncontradoException(id));
    }

    // Método renomeado para excluirPorId para corresponder à chamada da Controller
    public void excluirPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new ItemNaoEncontradoException(id);
        }
        repository.deleteById(id);
    }
}