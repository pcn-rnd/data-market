package co.kr.pcninc.data.marketcore.repository;

import co.kr.pcninc.data.marketcore.domain.Log;
import co.kr.pcninc.data.marketcore.domain.LogSimpleMapping;
import co.kr.pcninc.data.marketcore.domain.LogUrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {

    List<LogSimpleMapping> findAllBy();

    @Query(value = "SELECT LOG_ID, HOST, PORT FROM DM_LOG_TBL", nativeQuery = true)
    List<Object[]> findUrls();

    @Query(value = "SELECT * FROM DM_LOG_TBL WHERE LOG_LEVEL = 'ERROR'", nativeQuery = true)
    List<Log> findErrors();

    @Query(value = "SELECT log_id, system_name FROM DM_LOG_TBL", nativeQuery = true)
    List<Object[]> findSystems();

    @Query(value = "SELECT log_id FROM DM_LOG_TBL", nativeQuery = true)
    List<Integer> getIds();

    @Query(value = "SELECT log_level FROM DM_LOG_TBL", nativeQuery = true)
    List<String> getLevels();

    @Query(value = "SELECT http_method FROM DM_LOG_TBL", nativeQuery = true)
    List<String> getMethods();

    @Query(value = "SELECT system_name FROM DM_LOG_TBL", nativeQuery = true)
    List<String> getSnames();

    @Query(value = "SELECT host FROM DM_LOG_TBL", nativeQuery = true)
    List<String> getHosts();

    @Query(value = "SELECT port FROM DM_LOG_TBL", nativeQuery = true)
    List<Integer> getPorts();

    @Query(value = "SELECT api_action FROM DM_LOG_TBL", nativeQuery = true)
    List<String> getActions();

    @Query(value = "SELECT class_name FROM DM_LOG_TBL", nativeQuery = true)
    List<String> getCnames();

    @Query(value = "SELECT actor FROM DM_LOG_TBL", nativeQuery = true)
    List<String> getActors();

    @Query(value = "SELECT reg_dt FROM DM_LOG_TBL", nativeQuery = true)
    List<Date> getDates();

    @Query(value = "SELECT ac_role FROM DM_LOG_TBL", nativeQuery = true)
    List<String> getRoles();

    @Query(value = "SELECT path FROM DM_LOG_TBL", nativeQuery = true)
    List<String> getPaths();

}
