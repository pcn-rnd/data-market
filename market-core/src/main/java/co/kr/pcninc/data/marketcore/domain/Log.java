package co.kr.pcninc.data.marketcore.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


/**
 * 공인인증용
 */
@Data
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DM_LOG_TBL")
@Builder
public class Log {
    @Id
    @Column(name = "log_id")
    int logId;

    @Column(name = "log_level")
    String logLevel;

    @Column(name = "http_method")
    String httpMethod;

    @Column(name = "system_name")
    String systemName;

    @Column(name = "host")
    String host;

    @Column(name = "port")
    int port;

    @Column(name = "api_action")
    String apiAction;

    @Column(name = "class_name")
    String className;

    @Column(name = "actor")
    String actor;

    @Column(name = "reg_dt")
    Date regDt;

    @Column(name = "ac_role")
    String acRole;

    @Column(name = "path")
    String path;
}
