//package org.jaesung.springbootdeveloper.service;
//
//import org.jaesung.springbootdeveloper.domain.Article;
//import org.jaesung.springbootdeveloper.dto.AddArticleRequest;
//import org.jaesung.springbootdeveloper.dto.UpdateArticleRequest;
//import org.jaesung.springbootdeveloper.repository.BlogRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//class BlogServiceTest {
//
//    @Mock
//    private BlogRepository blogRepository;
//
//    @InjectMocks
//    private BlogService blogService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void save_ShouldSaveArticle() {
//        // given
//        AddArticleRequest request = new AddArticleRequest("Test Title", "Test Content");
//        Article article = request.toEntity();
//        when(blogRepository.save(any(Article.class))).thenReturn(article);
//
//        // when
//        Article savedArticle = blogService.save(request);
//
//        // then
//        assertNotNull(savedArticle);
//        assertEquals("Test Title", savedArticle.getTitle());
//        assertEquals("Test Content", savedArticle.getContent());
//        verify(blogRepository, times(1)).save(any(Article.class));
//    }
//
//    @Test
//    void findAll_ShouldReturnAllArticles() {
//        // given
//        Article article1 = new Article("Title 1", "Content 1");
//        Article article2 = new Article("Title 2", "Content 2");
//        List<Article> articles = Arrays.asList(article1, article2);
//        when(blogRepository.findAll()).thenReturn(articles);
//
//        // when
//        List<Article> foundArticles = blogService.findAll();
//
//        // then
//        assertEquals(2, foundArticles.size());
//        verify(blogRepository, times(1)).findAll();
//    }
//
//    @Test
//    void findById_ShouldReturnArticle_WhenArticleExists() {
//        // given
//        Article article = new Article("Test Title", "Test Content");
//        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(article));
//
//        // when
//        Article foundArticle = blogService.findById(1L);
//
//        // then
//        assertNotNull(foundArticle);
//        assertEquals("Test Title", foundArticle.getTitle());
//        assertEquals("Test Content", foundArticle.getContent());
//        verify(blogRepository, times(1)).findById(anyLong());
//    }
//
//    @Test
//    void findById_ShouldThrowException_WhenArticleDoesNotExist() {
//        // given
//        when(blogRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        // when & then
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> blogService.findById(1L));
//        assertEquals("not found : 1", exception.getMessage());
//    }
//
//    @Test
//    void delete_ShouldDeleteArticle() {
//        // given
//        doNothing().when(blogRepository).deleteById(anyLong());
//
//        // when
//        blogService.delete(1L);
//
//        // then
//        verify(blogRepository, times(1)).deleteById(anyLong());
//    }
//
//    @Test
//    void update_ShouldUpdateArticle_WhenArticleExists() {
//        // given
//        Article article = new Article("Old Title", "Old Content");
//        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(article));
//
//        UpdateArticleRequest request = new UpdateArticleRequest("New Title", "New Content");
//
//        // when
//        Article updatedArticle = blogService.update(1L, request);
//
//        // then
//        assertEquals("New Title", updatedArticle.getTitle());
//        assertEquals("New Content", updatedArticle.getContent());
//        verify(blogRepository, times(1)).findById(anyLong());
//    }
//
//    @Test
//    void update_ShouldThrowException_WhenArticleDoesNotExist() {
//        // given
//        when(blogRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        UpdateArticleRequest request = new UpdateArticleRequest("New Title", "New Content");
//
//        // when & then
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> blogService.update(1L, request));
//        assertEquals("not found : 1", exception.getMessage());
//    }
//}
