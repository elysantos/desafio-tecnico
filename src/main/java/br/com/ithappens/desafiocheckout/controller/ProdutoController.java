package br.com.ithappens.desafiocheckout.controller;

import br.com.ithappens.desafiocheckout.domain.Produto;
import br.com.ithappens.desafiocheckout.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "produtos", name = "produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /**
     * Cria um novo produto
     * @param produto Produto a ser validado e adicionado ao banco de dados
     * @return Produto adicionado ao banco
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Produto setItem(@RequestBody @Valid Produto produto){
        return this.produtoService.salvar(produto);
    }

    /**
     * Lista todos os produtos adicionados ao banco de dados
     * @return Lista com os produtos no banco de dados
     */
    @RequestMapping(value="", method= RequestMethod.GET, produces = "application/json")
    public List<Produto> getAll(){
        return this.produtoService.obterTodos();
    }

    /**
     * Obtem um produto do banco de dados
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Produto> getOne(@PathVariable("id") String id){
        return this.produtoService.obterUm(Long.parseLong(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public Produto apagar(@PathVariable("id") String id){
        return this.produtoService.apagar(Long.parseLong(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="", method=RequestMethod.PUT)
    public Produto atualizar(@RequestBody @Valid Produto produto){
        return this.produtoService.atualizar(produto);
    }
}
