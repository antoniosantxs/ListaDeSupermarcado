package br.uel.ListaDeSupermercado.repository;

import br.uel.ListaDeSupermercado.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    //pesquisa por nome (ignora a diferença de maiúsculas e minúsculas)
    List<Item> findByNomeContainingIgnoreCase(String nome);

    //Ordenacao por Nome
    List<Item> findAllByOrderByNomeAsc();
    List<Item> findAllByOrderByNomeDesc();

    //Ordenacao por Quantidade
    List<Item> findAllByOrderByQuantidadeAsc();
    List<Item> findAllByOrderByQuantidadeDesc();

    List<Item> findAllByOrderByCategoriaAsc();
    List<Item> findAllByOrderByCategoriaDesc();
    List<Item> findAllByOrderByPrecoUnitarioAsc();
    List<Item> findAllByOrderByPrecoUnitarioDesc();

}