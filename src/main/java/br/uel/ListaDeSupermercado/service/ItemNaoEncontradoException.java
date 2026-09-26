package br.uel.ListaDeSupermercado.service;

public class ItemNaoEncontradoException extends RuntimeException {

    public ItemNaoEncontradoException(Long id) {
        super("Item com id " + id + " não encontrado.");
    }
}