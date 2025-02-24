package test.task.twitterclone.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "subscriptions")
class Subscription {
    @Id
    String id

    String userId

    String targetUserId
}
