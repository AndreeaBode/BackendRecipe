package sd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sd.entities.Comment;
import sd.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Transactional
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Transactional
    @PostMapping("/{recipeId}/{userId}/{username}/{additionalPath}/addComment")
    public ResponseEntity<Comment> addComment(@PathVariable("recipeId") int recipeId,
                                              @PathVariable("userId") int userId,
                                              @PathVariable("username") String username,
                                              @PathVariable("additionalPath") String additionalPath,
                                              @RequestBody Comment comment) {
        return commentService.addComment(recipeId, userId, username,additionalPath, comment);
    }

    @Transactional
    @GetMapping("getComment/{username}/{recipeId}/{additionalPath}")
    public ResponseEntity<List<Comment>> getCommentsByRecipeId(@PathVariable String username, @PathVariable int recipeId, @PathVariable("additionalPath") String additionalPath) {
        List<Comment> comments = commentService.getCommentsByRecipeId(username,recipeId , additionalPath);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
