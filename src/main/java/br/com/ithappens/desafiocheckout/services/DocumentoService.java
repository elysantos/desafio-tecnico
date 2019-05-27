package br.com.ithappens.desafiocheckout.services;

import br.com.ithappens.desafiocheckout.domain.DocumentoFiscal;
import br.com.ithappens.desafiocheckout.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {
    @Autowired
    private DocumentoRepository documentoRepository;

    public DocumentoFiscal salvar(DocumentoFiscal documento)throws ResponseStatusException {
        Optional<DocumentoFiscal> documentoExistente = this.documentoRepository.findByChave(documento.getChave());
        if(documentoExistente.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DocumentoFiscal já existe no banco de dados");
        }
        return this.documentoRepository.save(documento);
    }

    public List<DocumentoFiscal> obterTodos(){
        return this.documentoRepository.findAll();
    }


    public ResponseEntity<DocumentoFiscal> obterUm(Long id){
        Optional<DocumentoFiscal> documentoEncontrado = this.documentoRepository.findById(id);
        if(documentoEncontrado.isPresent()){
            return ResponseEntity.ok(documentoEncontrado.get());
        }
        return ResponseEntity.notFound().build();
    }


    public DocumentoFiscal apagar(Long id){
        Optional<DocumentoFiscal> documentoEncontrado = this.documentoRepository.findById(id);
        if(documentoEncontrado.isPresent()){
            this.documentoRepository.delete(documentoEncontrado.get());
            return documentoEncontrado.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DocumentoFiscal não encontrado");
    }

    public DocumentoFiscal atualizar(DocumentoFiscal documento){
        Optional<DocumentoFiscal> documentoEncontrado = this.documentoRepository.findById(documento.getId());
        if(documentoEncontrado.isPresent()){
            this.documentoRepository.save(documento);
            return documento;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DocumentoFiscal não encontrado");
    }
}
