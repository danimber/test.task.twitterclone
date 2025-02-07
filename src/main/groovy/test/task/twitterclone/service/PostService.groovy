package test.task.twitterclone.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import test.task.twitterclone.model.Post
import test.task.twitterclone.model.Comment
import test.task.twitterclone.model.User

import test.task.twitterclone.repository.PostRepository
import test.task.twitterclone.repository.CommentRepository
import test.task.twitterclone.repository.UserRepository

@Service
class PostService {
    @Autowired
    PostRepository postRepository

    @Autowired
    CommentRepository commentRepository

    @Autowired
    UserRepository userRepository

    def createPost(String userId, String content) {
        def post = new Post(userId: userId, content: content)
        postRepository.save(post)
    }

    def updatePost(String postId, String content) {
        def post = postRepository.findById(postId).orElseThrow()
        post.content = content
        postRepository.save(post)
    }

    def deletePost(String postId) {
        postRepository.deleteById(postId)
    }

    def likeUnlike(String postId, String userId) {
        def post = postRepository.findById(postId).orElseThrow()
        if (post.likes.contains(userId)) {
            post.likes.remove(userId)
        } else {
            post.likes.add(userId)
        }
        postRepository.save(post)
    }

    def addComment(String postId, String userId, String content) {
        Post post = postRepository.findById(postId).orElseThrow {
            new RuntimeException("Post not found")
        }

        User user = userRepository.findById(userId).orElseThrow {
            new RuntimeException("User not found")
        }

        Comment comment = new Comment(postId: postId, userId: userId, username: user.username, content: content)

        comment = commentRepository.save(comment)

        post.comments.add(comment)

        postRepository.save(post)
    }

    def getComments(String postId) {
        return commentRepository.findByPostId(postId)
    }

    def getAllPosts() {
        def allPosts = postRepository.findAll()

        allPosts = allPosts.collect { post ->
            return new Post(
                    id: post.id,
                    userId: post.userId,
                    content: post.content
            )
        }

        return allPosts
    }


    def getCommentsForPost(String postId) {
        def post = postRepository.findById(postId).orElse(null) // Извлекаем объект или null

        if (post == null) {
            return []
        }

        def comments = post.comments?.collect { comment ->
            def user = userRepository.findById(comment.userId).orElse(null) // Извлекаем объект или null
            return [
                    username: user?.username ?: "Unknown", // Проверяем null перед обращением к username
                    content: comment.content
            ]
        } ?: []

        return comments
    }

}
