package test.task.twitterclone.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
class User {
    @Id
    String id
    String username
    String email
    String password
    Set<String> followers = []
    Set<String> following = []

    String getPassword() {
        return password
    }
}
