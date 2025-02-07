package test.task.twitterclone.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "comments")
class Comment {
    @Id
    String id

    String postId

    String username

    String userId

    String content
}
