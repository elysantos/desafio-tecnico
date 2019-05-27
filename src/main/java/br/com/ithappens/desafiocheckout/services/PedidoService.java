package br.com.ithappens.desafiocheckout.services;

import br.com.ithappens.desafiocheckout.domain.ItemFiscal;
import br.com.ithappens.desafiocheckout.domain.Pedido;
import br.com.ithappens.desafiocheckout.domain.Produto;
import br.com.ithappens.desafiocheckout.domain.StatusCompra;
import br.com.ithappens.desafiocheckout.repository.ItemRepository;
import br.com.ithappens.desafiocheckout.repository.PedidoRepository;
import br.com.ithappens.desafiocheckout.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Pedido salvar(Pedido pedido)throws ResponseStatusException {
        Optional<Pedido> pedidoExistente = this.pedidoRepository.findById(pedido.getId());
        if(pedidoExistente.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido já existe no banco de dados");
        }
        pedido.setStatusCompra(StatusCompra.STATUS_AGUARDANDO_PAGAMENTO);
        return this.pedidoRepository.save(pedido);
    }

    public Pedido confirmarPagamento(Long idPedido) throws ResponseStatusException{
        Optional<Pedido> pedidoExistente = this.pedidoRepository.findById(idPedido);
        if(!pedidoExistente.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não existe no banco de dados");
        }
        Pedido pedido = pedidoExistente.get();
        pedido.setStatusCompra(StatusCompra.STATUS_PAGO);
        return this.pedidoRepository.save(pedido);
    }

    public List<Pedido> obterTodos(){
        return this.pedidoRepository.findAll();
    }


    public ResponseEntity<Pedido> obterUm(Long id){
        Optional<Pedido> pedidoEncontrado = this.pedidoRepository.findById(id);
        if(pedidoEncontrado.isPresent()){
            return ResponseEntity.ok(pedidoEncontrado.get());
        }
        return ResponseEntity.notFound().build();
    }


    public Pedido apagar(Long id){
        Optional<Pedido> pedidoEncontrado = this.pedidoRepository.findById(id);
        if(pedidoEncontrado.isPresent()){
            this.pedidoRepository.delete(pedidoEncontrado.get());
            return pedidoEncontrado.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");
    }

    public Pedido atualizar(Pedido pedido){
        Optional<Pedido> pedidoEncontrado = this.pedidoRepository.findById(pedido.getId());
        if(pedidoEncontrado.isPresent()){
            this.pedidoRepository.save(pedido);
            return pedido;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");
    }

    public Pedido adicionarItem(Long idPedido, Produto produto){
        Optional<Pedido> pedidoEncontrado = this.pedidoRepository.findById(idPedido);
        if(pedidoEncontrado.isPresent()){
            Pedido pedido = pedidoEncontrado.get();
            pedido.addProduto(produto);

            return this.pedidoRepository.save(pedido);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");
    }
}
