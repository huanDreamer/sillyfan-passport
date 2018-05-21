package top.sillyfan.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.sillyfan.model.security.User;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByUsername(String username);
}
