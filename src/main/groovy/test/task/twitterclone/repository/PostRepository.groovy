package test.task.twitterclone.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import test.task.twitterclone.model.Post

interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findAllByUserId(String userId)

    List<Post> findByUserIdIn(List<String> userIds) // <- Исправленный метод
}
