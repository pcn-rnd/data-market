package co.kr.pcninc.data.marketcore.repository;

import co.kr.pcninc.data.marketcore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
