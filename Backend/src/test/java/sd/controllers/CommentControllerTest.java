package sd.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.entities.Comment;
import sd.services.CommentService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        int recipeId = 1;
        int userId = 1;
        String username = "user1";
        String additionalPath = "path";
        Comment comment = new Comment();
        comment.setId(1);
        comment.setContent("This is a test comment");

        when(commentService.addComment(anyInt(), anyInt(), anyString(), anyString(), any(Comment.class)))
                .thenReturn(ResponseEntity.ok(comment));

        ResponseEntity<Comment> response = commentController.addComment(recipeId, userId, username, additionalPath, comment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment.getId(), response.getBody().getId());
        assertEquals(comment.getContent(), response.getBody().getContent());
    }

    @Test
    void testGetCommentsByRecipeId() {
        String username = "user1";
        int recipeId = 1;
        String additionalPath = "path";
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setContent("Comment 1");
        comments.add(comment1);

        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setContent("Comment 2");
        comments.add(comment2);

        when(commentService.getCommentsByRecipeId(anyString(), anyInt(), anyString()))
                .thenReturn(comments);

        ResponseEntity<List<Comment>> response = commentController.getCommentsByRecipeId(username, recipeId, additionalPath);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(comment1.getId(), response.getBody().get(0).getId());
        assertEquals(comment1.getContent(), response.getBody().get(0).getContent());
        assertEquals(comment2.getId(), response.getBody().get(1).getId());
        assertEquals(comment2.getContent(), response.getBody().get(1).getContent());
    }
}
