package co.kr.pcninc.data.marketcore.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "DM_USER_INFOS")
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "pwd")
    //@Transient
    @NotNull
    private String pwd;

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "user_key")
    private String userKey;


}
