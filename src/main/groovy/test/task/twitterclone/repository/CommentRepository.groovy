package test.task.twitterclone.repository

import org.springframework.data.mongodb.repository.MongoRepository
import test.task.twitterclone.model.Comment

interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String postId)

}
