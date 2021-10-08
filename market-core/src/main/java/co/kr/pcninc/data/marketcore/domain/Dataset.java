package co.kr.pcninc.data.marketcore.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DM_DATASET_INFOS")
@Builder
public class Dataset {

    @Id
    @Column(name = "set_id")
    @GeneratedValue
    private int setId;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category catId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    @Column(name = "public_yn")
    private String publicYn;

    @Column(name = "views")
    private int views;

    /*@Column(name = "crt_dt")
    private String crtDt;*/

    @Temporal(TemporalType.TIMESTAMP)
    private Date crtDt;

    @Column(name = "urls_info")
    private String urlsInfo;

    @Column(name = "datatype")
    private String datatype;

    @Column(name = "ws_id")
    private Integer wsId;

    @Column(name = "path")
    private String path;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "f_names")
    private String fNames;

}

