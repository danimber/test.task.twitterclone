package test.task.twitterclone.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import test.task.twitterclone.model.User
import test.task.twitterclone.service.UserService

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    UserService userService

    @PostMapping("/register")
    def registerUser(@RequestBody User user) {
        userService.createUser(user.username, user.email, user.password)
        return ResponseEntity.ok("User created")
    }

    @PutMapping("/{id}")
    def updateUser(@PathVariable("id") String id, @RequestBody User user) {
        userService.updateUser(id, user.username, user.email, user.password)
        return ResponseEntity.ok("User updated")
    }

    @DeleteMapping("/{id}")
    def deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id)
        return ResponseEntity.ok("User deleted")
    }

    @GetMapping
    def getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    @GetMapping("/{id}/followers")
    def getUserFollowers(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getUserFollowers(id))
    }

    @GetMapping("/{id}/following")
    def getUserFollowing(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getUserFollowing(id))
    }
    @PostMapping("/{targetUserId}/follow")
    def followUser(@PathVariable("targetUserId") String targetUserId, @RequestParam("userId") String userId) {
        userService.subscribeUser(userId, targetUserId)
        return ResponseEntity.ok("User subscribed successfully")
    }

    @PostMapping("/{id}/unfollow")
    def unfollowUser(@PathVariable("id") String id, @RequestParam("userId") String userId) {
        userService.unsubscribeUser(id, userId)
        return ResponseEntity.ok("User unsubscribed successfully")
    }

    @GetMapping("/{id}/feed")
    def getUserFeed(@PathVariable("id") String id) {
        def feed = userService.getUserFeed(id)
        return ResponseEntity.ok(feed)
    }
}
