package co.kr.pcninc.data.marketcore.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "DM_CAT_INFOS")
public class Category {

    @Id
    @Column(name = "cat_id")
    private int catId;

    @Column(name = "cat_name")
    private String catName;

   /* @JsonBackReference
    @OneToMany(mappedBy = "catId", fetch = FetchType.LAZY)
    private List<Dataset> sets;*/

}
