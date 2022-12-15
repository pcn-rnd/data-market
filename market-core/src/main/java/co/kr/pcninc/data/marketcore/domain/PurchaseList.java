package co.kr.pcninc.data.marketcore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "DM_PURCHASE_LIST")
public class PurchaseList {

    @Id
    @Column(name = "list_id")
    @GeneratedValue
    private int listId;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "set_id")
    private Dataset setId;

    @ManyToOne
    @JoinColumn(name = "customer")
    private User customer;

    //@Column(name = "pur_dt")
    //private String purDt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date purDt;

    @Column(name = "able_yn")
    private String ableYn;
}
