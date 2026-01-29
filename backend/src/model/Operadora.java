import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_operadora")
public class operadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, length = 14)
    private String cnpj;

    @Column(nullable = false)
    private String razaoSocial;

    @Column
    private Integer trimestre;

    @Column
    private Integer ano;

    @Column(precision = 19, scale = 2)
    private BigDecimal valorDespesas;
}
