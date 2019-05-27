package br.com.ithappens.desafiocheckout.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CPF
    @NotNull
    @Size(min=11, max = 11)
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotNull
    @Size(max = 200)
    @Column(name= "nome", length = 200, nullable = false)
    private String nome;

    @Size(min=9, max = 14)
    @Column(name="telefone", length = 14)
    private String telefone;

    @Email
    @Size(max = 144)
    @Column(name="email", length = 144)
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm", timezone = "GMT-3")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;

    private boolean isAtivo = true;
}
