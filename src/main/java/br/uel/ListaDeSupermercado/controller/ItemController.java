//intermedio html e service/model
package br.uel.ListaDeSupermercado.controller;

import br.uel.ListaDeSupermercado.model.Item;
import br.uel.ListaDeSupermercado.service.ItemNaoEncontradoException;
import br.uel.ListaDeSupermercado.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller //indica ao Spring que esta classe gerencia as páginas HTML
@RequestMapping("/itens") //rota HTTP para todas as requisições da controller
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    //exibe a listagem na página index
    @GetMapping
    public String listar(@RequestParam(required = false) String buscar,
                         @RequestParam(required = false) String ordem,
                         Model model) {
        model.addAttribute("itens", service.listarTodos(buscar, ordem));
        model.addAttribute("buscar", buscar);
        return "index"; // Retorna o arquivo index.html em src/main/resources/templates
    }

    //tela para cadastro de novos itens (form)
    @GetMapping("/novo")
    public String formularioNovo(Model model) {
        model.addAttribute("item", new Item());
        return "form"; // Retorna o arquivo form.html em src/main/resources/templates
    }

    //salvar o registro do item anterior
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("item") Item item,
                         BindingResult result,
                         RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "form"; // Se houver erro de validação, volta para o formulário destacando o erro
        }
        service.salvar(item);
        attributes.addFlashAttribute("mensagemSucesso", "Item salvo com sucesso!");
        return "redirect:/itens"; // Redireciona para evitar reenvio com F5 (PRG)
    }

    //edição de itens criados previamente - busca pela id e abreo form
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        try {
            model.addAttribute("item", service.buscarPorId(id));
            return "form";
        } catch (ItemNaoEncontradoException e) {
            attributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/itens";
        }
    }

    //Exclusão de Registro
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            service.excluirPorId(id);
            attributes.addFlashAttribute("mensagemSucesso", "Item excluído com sucesso!");
        } catch (ItemNaoEncontradoException e) {
            attributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/itens";
    }
}