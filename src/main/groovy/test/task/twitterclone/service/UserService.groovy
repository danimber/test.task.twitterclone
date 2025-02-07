package test.task.twitterclone.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import test.task.twitterclone.model.Subscription
import test.task.twitterclone.model.User
import test.task.twitterclone.repository.CommentRepository
import test.task.twitterclone.repository.PostRepository
import test.task.twitterclone.repository.SubscriptionRepository
import test.task.twitterclone.repository.UserRepository

@Service
class UserService {

    @Autowired
    UserRepository userRepository

    @Autowired
    SubscriptionRepository subscriptionRepository

    @Autowired
    PostRepository postRepository

    @Autowired
    CommentRepository commentRepository

    def createUser(String username, String email, String password) {
        def user = new User(username: username, email: email, password: password)
        userRepository.save(user)
    }

    def updateUser(String userId, String username, String email, String password) {
        def user = userRepository.findById(userId).orElseThrow()
        user.username = username
        user.email = email
        user.password = password
        userRepository.save(user)
    }

    def deleteUser(String userId) {
        subscriptionRepository.findByTargetUserId(userId).each { subscription ->
            subscriptionRepository.delete(subscription)
        }

        subscriptionRepository.findByUserId(userId).each { subscription ->
            subscriptionRepository.delete(subscription)
        }

        userRepository.findAll().each { user ->
            if (user.followers.contains(userId)) {
                user.followers.remove(userId)
                userRepository.save(user)
            }
            if (user.following.contains(userId)) {
                user.following.remove(userId)
                userRepository.save(user)
            }
        }

        def posts = postRepository.findAllByUserId(userId)
        posts.each { post ->
            postRepository.delete(post)
        }

        userRepository.deleteById(userId)
    }

    def getAllUsers() {
        return userRepository.findAll().collect { user ->
            [
                    id      : user.id,
                    username: user.username,
                    email   : user.email
            ]
        }
    }

    def getUserFollowers(String userId) {
        def user = userRepository.findById(userId).orElse(null)

        if (!user) {
            return [id: userId, followers: [], followerUsernames: [], username: null]
        }

        def followersIds = subscriptionRepository.findByTargetUserId(userId)
                .collect { it.userId }

        def followers = followersIds.collect { followerId ->
            def follower = userRepository.findById(followerId).orElse(null)
            return follower ? [id: follower.id, username: follower.username] : null
        }.findAll { it != null }

        return [id: userId, username: user?.username, followers: followers]
    }


    def getUserFollowing(String userId) {
        def followingIds = subscriptionRepository.findByUserId(userId)
                .collect { it.targetUserId }

        def following = followingIds.collect { followingId ->
            def followedUser = userRepository.findById(followingId).orElse(null)
            return followedUser ? [id: followedUser.id, username: followedUser.username] : null
        }.findAll { it != null }

        return [id: userId, following: following]
    }


    def subscribeUser(String userId, String targetUserId) {
        def user = userRepository.findById(userId).orElseThrow { new RuntimeException("User not found") }
        def targetUser = userRepository.findById(targetUserId).orElseThrow { new RuntimeException("Target user not found") }

        if (subscriptionRepository.existsByUserIdAndTargetUserId(userId, targetUserId)) {
            throw new IllegalStateException("Already subscribed")
        }

        def subscription = new Subscription(userId: userId, targetUserId: targetUserId)
        subscriptionRepository.save(subscription)

        user.following.add(targetUserId)
        targetUser.followers.add(userId)

        userRepository.save(user)
        userRepository.save(targetUser)
    }


    def unsubscribeUser(String userId, String targetUserId) {
        def subscription = subscriptionRepository.findByUserIdAndTargetUserId(userId, targetUserId)
        if (!subscription) {
            throw new IllegalStateException("Subscription not found")
        }
        subscriptionRepository.delete(subscription)
    }



    def getUserFeed(String userId) {
        def subscriptions = subscriptionRepository.findByUserId(userId)
        println "Subscriptions: ${subscriptions}"

        def targetUserIds = subscriptions?.collect { it.targetUserId } ?: []
        println "Target User IDs: ${targetUserIds}"

        if (targetUserIds.isEmpty()) {
            return []
        }

        def posts = postRepository.findByUserIdIn(targetUserIds)
        println "Posts: ${posts}"

        if (posts.isEmpty()) {
            return []
        }

        // Добавляем комментарии и список лайкнувших пользователей к каждому посту
        def enrichedPosts = posts.collect { post ->
            def comments = commentRepository.findByPostId(post.id)  // Подтягиваем комментарии

            return [
                    id: post.id,
                    userId: post.userId,
                    content: post.content,
                    likesCount: (post.likes ?: []).size(),  // Количество лайков
                    likesUserIds: post.likes ?: [],  // Список ID пользователей, которые лайкнули
                    comments: comments.collect { comment ->
                        [
                                id: comment.id,
                                userId: comment.userId,
                                username: comment.username,
                                content: comment.content
                        ]
                    }
            ]
        }

        return enrichedPosts
    }



//    def getAllUsersWithSubscriptions() {
//        def users = userRepository.findAll()
//
//        def usersWithSubscriptions = []
//
//        users.each { user ->
//            def followingSubscriptions = subscriptionRepository.findByUserId(user.id)
//            def following = followingSubscriptions.collect { it.targetUserId }
//
//            def followerSubscriptions = subscriptionRepository.findByTargetUserId(user.id)
//            def followers = followerSubscriptions.collect { it.userId }
//
//            usersWithSubscriptions.add([
//                    id       : user.id,
//                    username : user.username,
//                    email    : user.email,
//                    followers: followers,
//                    following: following
//            ])
//        }
//
//        return usersWithSubscriptions
//    }

//    @Override
//    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findUserByUsername(username).orElseThrow {
//            new UsernameNotFoundException(String.format("User '%s' not found", username))
//        }
//        return UserDetailsImpl.build(user)
//    }


}
