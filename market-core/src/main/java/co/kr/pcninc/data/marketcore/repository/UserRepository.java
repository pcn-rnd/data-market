package co.kr.pcninc.data.marketcore.repository;

import co.kr.pcninc.data.marketcore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT email FROM DM_USER_INFOS", nativeQuery = true)
    List<String> getAllEmails();

    @Query(value = "SELECT user_name FROM DM_USER_INFOS", nativeQuery = true)
    List<String> getAllNames();

    @Query(value = "SELECT user_key FROM DM_USER_INFOS", nativeQuery = true)
    List<String> getAllKeys();
}
