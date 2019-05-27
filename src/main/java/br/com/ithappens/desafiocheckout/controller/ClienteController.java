package br.com.ithappens.desafiocheckout.controller;

import br.com.ithappens.desafiocheckout.domain.Cliente;
import br.com.ithappens.desafiocheckout.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "clientes", name = "clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Cria um novo cliente
     * @param cliente Cliente a ser validado e adicionado ao banco de dados
     * @return Cliente adicionado ao banco
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Cliente setItem(@RequestBody @Valid Cliente cliente){
        return this.clienteService.salvar(cliente);
    }

    /**
     * Lista todos os clientes adicionados ao banco de dados
     * @return Lista com os clientes no banco de dados
     */
    @RequestMapping(value="", method= RequestMethod.GET, produces = "application/json")
    public List<Cliente> getAll(){
        return this.clienteService.obterTodos();
    }

    /**
     * Obtem um cliente do banco de dados
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Cliente> getOne(@PathVariable("id") String id){
        return this.clienteService.obterUm(Long.parseLong(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public Cliente apagar(@PathVariable("id") String id){
        return this.clienteService.apagar(Long.parseLong(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="", method=RequestMethod.PUT)
    public Cliente atualizar( @RequestBody @Valid Cliente cliente){
        return this.clienteService.atualizar(cliente);
    }
}
