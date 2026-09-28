package br.uel.ListaDeSupermercado.controller;

import br.uel.ListaDeSupermercado.model.Item;
import br.uel.ListaDeSupermercado.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // 1. Listagem com suporte a busca e ordenação
    @GetMapping
    public String listar(@RequestParam(required = false) String nome,
                         @RequestParam(required = false) String campo,
                         @RequestParam(required = false, defaultValue = "asc") String ordem,
                         Model model) {

        if (nome != null && !nome.isBlank()) {
            model.addAttribute("itens", itemService.buscarPorNome(nome));
            model.addAttribute("termoBusca", nome);
        } else if (campo != null && !campo.isBlank()) {
            model.addAttribute("itens", itemService.listarOrdenado(campo, ordem));
        } else {
            model.addAttribute("itens", itemService.listarTodos());
        }

        return "index"; // Retorna o arquivo index.html em src/main/resources/templates
    }

    // 2. Form para novo cadastro
    @GetMapping("/novo")
    public String formularioNovo(Model model) {
        model.addAttribute("item", new Item());
        return "form"; // Retorna o arquivo form.html em src/main/resources/templates
    }

    // 3. Salvar (Atende tanto inclusão quanto edição via POST /itens/salvar)
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("item") Item item,
                         BindingResult result,
                         RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "form"; // Se houver erros de validação, volta para a tela destacando os erros
        }

        itemService.salvar(item);
        attributes.addFlashAttribute("mensagemSucesso", "Item salvo com sucesso!");
        return "redirect:/itens"; // Padrão PRG (Post/Redirect/Get)
    }

    // 4. Carrega formulário preenchido para edição
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model, RedirectAttributes attributes) {
        try {
            Item item = itemService.buscarPorId(id);
            model.addAttribute("item", item);
            return "form";
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/itens";
        }
    }

    // 5. Exclusão de registro via GET /itens/excluir/{id}
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            itemService.excluir(id);
            attributes.addFlashAttribute("mensagemSucesso", "Item excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", e.getMessage());
        }
        return "redirect:/itens";
    }
}