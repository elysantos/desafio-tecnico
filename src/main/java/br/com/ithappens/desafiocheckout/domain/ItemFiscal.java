package br.com.ithappens.desafiocheckout.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ItemFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
