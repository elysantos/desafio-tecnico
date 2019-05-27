package br.com.ithappens.desafiocheckout.services;


import br.com.ithappens.desafiocheckout.domain.Produto;
import br.com.ithappens.desafiocheckout.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


    public Produto salvar(Produto produto)throws ResponseStatusException {
        Optional<Produto> produtoExistente = this.produtoRepository.findByDescricao(produto.getDescricao());
        if(produtoExistente.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto já existe no banco de dados");
        }
        return this.produtoRepository.save(produto);
    }

    public List<Produto> obterTodos(){
        return this.produtoRepository.findAll();
    }


    public ResponseEntity<Produto> obterUm(Long id){
        Optional<Produto> produtoEncontrado = this.produtoRepository.findById(id);
        if(produtoEncontrado.isPresent()){
            return ResponseEntity.ok(produtoEncontrado.get());
        }
        return ResponseEntity.notFound().build();
    }


    public Produto apagar(Long id){
        Optional<Produto> produtoEncontrado = this.produtoRepository.findById(id);
        if(produtoEncontrado.isPresent()){
            this.produtoRepository.delete(produtoEncontrado.get());
            return produtoEncontrado.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
    }

    public Produto atualizar(Produto produto){
        Optional<Produto> produtoEncontrado = this.produtoRepository.findById(produto.getId());
        if(produtoEncontrado.isPresent()){
            this.produtoRepository.save(produto);
            return produto;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
    }
}
