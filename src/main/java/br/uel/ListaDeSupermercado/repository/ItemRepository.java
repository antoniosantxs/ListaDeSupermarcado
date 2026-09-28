package br.uel.ListaDeSupermercado.repository;

import br.uel.ListaDeSupermercado.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    //Busca por nome - case insensitive
    List<Item> findByNomeContainingIgnoreCase(String nome);

    //Ordenação por Nome
    List<Item> findAllByOrderByNomeAsc(); //lista ascendente
    List<Item> findAllByOrderByNomeDesc(); //lista descentente

    //Ordenação pela Quantidade a Comprar
    List<Item> findAllByOrderByQuantidadeComprarAsc(); //lista ascendente
    List<Item> findAllByOrderByQuantidadeComprarDesc(); //lista descendente
}