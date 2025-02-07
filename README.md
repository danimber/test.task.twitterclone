Twitter Clone API

This is a simple Twitter-like API built with Spring Boot and Groovy for the backend and MongoDB for data storage. The API allows users to register, create posts, follow other users, like/unlike posts, comment on posts, and view feeds from their followed users.
Features

    User management: Register, update, and delete users.
    Post management: Create, update, delete, like/unlike posts.
    Commenting: Add and view comments on posts.
    User Feed: View posts from users you're following.
    Follow/Unfollow: Follow/unfollow other users.
    MongoDB: All data is stored in MongoDB.

Endpoints
User Endpoints

    POST /users/register: Register a new user.
    Request Body:

{
  "username": "exampleUser",
  "email": "user@example.com",
  "password": "password123"
}

PUT /users/{id}: Update an existing user.
Request Body:

    {
      "username": "newUsername",
      "email": "newEmail@example.com",
      "password": "newPassword123"
    }

    DELETE /users/{id}: Delete a user by ID.

    GET /users/{id}/followers: Get the followers of a user.

    GET /users/{id}/following: Get the users a user is following.

    POST /users/{targetUserId}/follow: Follow a user.
    Request Param: userId

    POST /users/{id}/unfollow: Unfollow a user.
    Request Param: userId

    GET /users/{id}/feed: Get the user's feed (posts from followed users).

Post Endpoints

    POST /posts/add-post: Create a new post.
    Request Body:

{
  "userId": "user123",
  "content": "This is a post"
}

PUT /posts/{id}: Update an existing post.
Request Body:

{
  "content": "Updated post content"
}

DELETE /posts/{id}: Delete a post by ID.

POST /posts/{userId}/likeUnlike/{postId}: Like or unlike a post.

POST /posts/{id}/comment: Comment on a post.
Request Body:

    {
      "userId": "user123",
      "content": "Great post!"
    }

    GET /posts/get-all-posts: Get all posts.

    GET /posts/{postId}/comments: Get all comments for a specific post.

Example Responses
Register User

Response:

{
  "message": "User created"
}

Create Post

Response:

{
  "message": "Post created"
}

Like Post

Response:

{
  "message": "Post liked"
}

Dependencies

    Spring Boot: Framework for building the application.
    Groovy: For writing the backend logic.
    MongoDB: Database for storing user and post data.
    Spock: Framework for writing tests.
    Gradle: Build automation tool.

Running the Project

    Clone the repository:

git clone <repository-url>

Navigate to the project directory:

cd <project-directory>

Build and run the project:

./gradlew bootRun

The API will be accessible at http://localhost:8080.
