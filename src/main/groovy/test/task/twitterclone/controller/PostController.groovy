package test.task.twitterclone.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import test.task.twitterclone.model.Post
import test.task.twitterclone.service.PostService

@RestController
@RequestMapping("/posts")
class PostController {
    @Autowired
    PostService postService

    @PostMapping("/add-post")
    def createPost(@RequestBody Post post) {
        postService.createPost(post.userId, post.content)
        return ResponseEntity.ok("Post created")
    }

    @PutMapping("/{id}")
    def updatePost(@PathVariable String id, @RequestBody Post post) {
        postService.updatePost(id, post.content)
        return ResponseEntity.ok("Post updated")
    }

    @DeleteMapping("/{id}")
    def deletePost(@PathVariable("id") String id) {
        postService.deletePost(id)
        return ResponseEntity.ok("Post deleted")
    }

    @PostMapping("/{userId}/likeUnlike/{postId}")
    def likePost(@PathVariable("postId") String postId, @PathVariable("userId") String userId) {
        postService.likeUnlike(postId, userId)
        return ResponseEntity.ok("Post liked")
    }

    @PostMapping("/{id}/comment")
    def commentOnPost(@PathVariable("id") String id, @RequestBody Map<String, String> comment) {
        def userId = comment.userId
        def content = comment.content
        postService.addComment(id, userId, content)
        return ResponseEntity.ok("Comment added successfully")
    }

    @GetMapping("/get-all-posts")
    def getAllPosts() {
        return postService.getAllPosts()
    }

    @GetMapping("/{postId}/comments")
    def getPostComments(@PathVariable("postId") String postId) {
        def comments = postService.getCommentsForPost(postId)
        return ResponseEntity.ok([postId: postId, comments: comments])
    }
}
