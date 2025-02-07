package test.task.twitterclone.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import test.task.twitterclone.model.User

@Repository
interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findUserByUsername(String username);

    Optional<User> findByEmail(String email);
}
