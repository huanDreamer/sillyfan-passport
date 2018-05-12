package top.sillyfan.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.sillyfan.model.security.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
