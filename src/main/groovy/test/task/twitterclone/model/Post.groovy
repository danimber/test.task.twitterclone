package test.task.twitterclone.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "posts")
class Post {
    @Id
    String id
    String userId
    String content
    Set<String> likes = []
    List<Comment> comments = []
}