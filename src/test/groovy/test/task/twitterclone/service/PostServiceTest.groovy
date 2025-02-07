package test.task.twitterclone.service

import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.junit.jupiter.api.Test
import test.task.twitterclone.service.PostService

import static org.mockito.Mockito.*
import static org.junit.jupiter.api.Assertions.*

import test.task.twitterclone.model.Post
import test.task.twitterclone.model.Comment
import test.task.twitterclone.model.User
import test.task.twitterclone.repository.PostRepository
import test.task.twitterclone.repository.CommentRepository
import test.task.twitterclone.repository.UserRepository

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService  // Сервис, который будем тестировать

    @Mock
    PostRepository postRepository  // Мокируем репозиторий постов

    @Mock
    CommentRepository commentRepository  // Мокируем репозиторий комментариев

    @Mock
    UserRepository userRepository  // Мокируем репозиторий пользователей

    @Test
    void testCreatePost() {
        // Подготовка данных
        def post = new Post(userId: 'user123', content: 'Test post')

        // Настройка мокирования для save
        when(postRepository.save(any(Post.class))).thenReturn(post)

        // Вызов метода для создания поста
        postService.createPost('user123', 'Test post')

        // Проверка, что метод save был вызван с правильными параметрами
        verify(postRepository, times(1)).save(any(Post.class))

        // Дополнительная проверка на отладку
        println("Пост был создан: ${post}")

        // Проверка, что пост был сохранен с ожидаемыми значениями
        assertNotNull(post.userId)
        assertEquals('user123', post.userId)
        assertEquals('Test post', post.content)
    }

    @Test
    void testUpdatePost() {
        def existingPost = new Post(id: '1', userId: 'user123', content: 'Old content')

        // Мокаем findById, чтобы он возвращал Optional с существующим постом
        when(postRepository.findById('1')).thenReturn(Optional.of(existingPost))

        // Мокаем save, чтобы он возвращал тот же пост
        when(postRepository.save(any(Post.class))).thenReturn(existingPost)

        // Вызываем метод updatePost с новым контентом
        postService.updatePost('1', 'Updated content')

        // Проверяем, что контент был обновлен
        assertEquals('Updated content', existingPost.content)

        // Проверяем, что save был вызван один раз с правильным объектом
        verify(postRepository, times(1)).save(existingPost)
    }


    @Test
    void testDeletePost() {
        def post = new Post(id: '1', userId: 'user123', content: 'Test post')
        when(postRepository.findById('1')).thenReturn(Optional.of(post))

        postService.deletePost('1')

        verify(postRepository, times(1)).deleteById('1')
    }

    @Test
    void testLikeUnlikePost() {
        def post = new Post(id: '1', userId: 'user123', content: 'Test post', likes: ['user123'])
        when(postRepository.findById('1')).thenReturn(Optional.of(post))
        when(postRepository.save(any(Post.class))).thenReturn(post)

        // Лайк пользователя
        postService.likeUnlike('1', 'user456')
        assertTrue(post.likes.contains('user456'))
        verify(postRepository, times(1)).save(post)

        // Удаление лайка
        postService.likeUnlike('1', 'user123')
        assertFalse(post.likes.contains('user123'))
        verify(postRepository, times(2)).save(post)
    }

    @Test
    void testAddComment() {
        def post = new Post(id: '1', userId: 'user123', content: 'Test post', comments: [])
        def user = new User(id: 'user123', username: 'user1')
        when(postRepository.findById('1')).thenReturn(Optional.of(post))
        when(userRepository.findById('user123')).thenReturn(Optional.of(user))
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment(postId: '1', userId: 'user123', username: 'user1', content: 'Nice post!'))

        postService.addComment('1', 'user123', 'Nice post!')

        assertEquals(1, post.comments.size())
        assertEquals('Nice post!', post.comments[0].content)
        verify(commentRepository, times(1)).save(any(Comment.class))
    }

    @Test
    void testGetCommentsForPost() {
        def post = new Post(id: '1', userId: 'user123', content: 'Test post', comments: [
                new Comment(postId: '1', userId: 'user123', username: 'user1', content: 'Nice post!')
        ])
        def user = new User(id: 'user123', username: 'user1')
        when(postRepository.findById('1')).thenReturn(Optional.of(post))
        when(userRepository.findById('user123')).thenReturn(Optional.of(user))

        def comments = postService.getCommentsForPost('1')

        assertEquals(1, comments.size())
        assertEquals('Nice post!', comments[0].content)
        assertEquals('user1', comments[0].username)
    }

    @Test
    void testGetAllPosts() {
        def post1 = new Post(id: '1', userId: 'user123', content: 'Post 1')
        def post2 = new Post(id: '2', userId: 'user456', content: 'Post 2')
        when(postRepository.findAll()).thenReturn([post1, post2])

        def posts = postService.getAllPosts()

        assertEquals(2, posts.size())
        assertEquals('Post 1', posts[0].content)
        assertEquals('Post 2', posts[1].content)
    }
}
