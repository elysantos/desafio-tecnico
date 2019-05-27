package br.com.ithappens.desafiocheckout.controller;

import br.com.ithappens.desafiocheckout.domain.Pedido;
import br.com.ithappens.desafiocheckout.domain.Produto;
import br.com.ithappens.desafiocheckout.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(name = "pedidos", value = "pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * Cria um novo pedido
     * @param pedido Pedido a ser validado e adicionado ao banco de dados
     * @return Pedido adicionado ao banco
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Pedido setItem(@RequestBody @Valid Pedido pedido){
        return this.pedidoService.salvar(pedido);
    }

    /**
     * Lista todos os pedidos adicionados ao banco de dados
     * @return Lista com os pedidos no banco de dados
     */
    @RequestMapping(value="", method= RequestMethod.GET, produces = "application/json")
    public List<Pedido> getAll(){
        return this.pedidoService.obterTodos();
    }

    /**
     * Obtem um pedido do banco de dados
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Pedido> getOne(@PathVariable("id") String id){
        return this.pedidoService.obterUm(Long.parseLong(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public Pedido apagar(@PathVariable("id") String id){
        return this.pedidoService.apagar(Long.parseLong(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="", method=RequestMethod.PUT)
    public Pedido atualizar(@RequestBody @Valid Pedido pedido){
        return this.pedidoService.atualizar(pedido);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Pedido adicionarItem(@PathVariable("id") String id, @RequestBody @Valid Produto produto){
        return this.pedidoService.adicionarItem(Long.parseLong(id), produto);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/{id}/confirmar", method = RequestMethod.PUT)
    public void confirmarPagamento(@PathVariable("id") String id){
        this.pedidoService.confirmarPagamento(Long.parseLong(id));
    }


}
