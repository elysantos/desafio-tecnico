package br.com.ithappens.desafiocheckout.services;

import br.com.ithappens.desafiocheckout.domain.Cliente;
import br.com.ithappens.desafiocheckout.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente)throws ResponseStatusException {
        Optional<Cliente> clienteExistente = this.clienteRepository.findByCpfAndNome(cliente.getCpf(), cliente.getNome());
        if(clienteExistente.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente já existe no banco de dados");
        }
        return this.clienteRepository.save(cliente);
    }

    public List<Cliente> obterTodos(){
        return this.clienteRepository.findAll();
    }


    public ResponseEntity<Cliente> obterUm(Long id){
        Optional<Cliente> clienteEncontrado = this.clienteRepository.findById(id);
        if(clienteEncontrado.isPresent()){
            return ResponseEntity.ok(clienteEncontrado.get());
        }
        return ResponseEntity.notFound().build();
    }


    public Cliente apagar(Long id){
        Optional<Cliente> clienteEncontrado = this.clienteRepository.findById(id);
        if(clienteEncontrado.isPresent()){
            this.clienteRepository.delete(clienteEncontrado.get());
            return clienteEncontrado.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
    }

    public Cliente atualizar(Cliente cliente){
        Optional<Cliente> clienteEncontrado = this.clienteRepository.findById(cliente.getId());
        if(clienteEncontrado.isPresent()){
            this.clienteRepository.save(cliente);
            return cliente;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
    }
}
