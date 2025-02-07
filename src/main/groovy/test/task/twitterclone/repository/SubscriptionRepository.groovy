package test.task.twitterclone.repository

import org.springframework.data.mongodb.repository.MongoRepository
import test.task.twitterclone.model.Subscription

interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    List<Subscription> findByUserId(String userId)

    Subscription findByUserIdAndTargetUserId(String userId, String targetUserId)

    boolean existsByUserIdAndTargetUserId(String userId, String targetUserId)

    List<Subscription> findByTargetUserId(String targetUserId)
}