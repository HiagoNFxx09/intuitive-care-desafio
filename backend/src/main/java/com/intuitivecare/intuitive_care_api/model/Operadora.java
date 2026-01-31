package com.intuitivecare.intuitive_care_api.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tb_operadora")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Operadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 14)
    private String cnpj;

    @Column(length = 2)
    private String uf;

    @Column(nullable = false)
    private String razaoSocial;

    @Column
    private Integer trimestre;

    @Column
    private Integer ano;

    @Column(precision = 19, scale = 2)
    private BigDecimal valorDespesas;
}
