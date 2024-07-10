package sd.dtos;

import lombok.Data;
import sd.entities.Comment;

@Data
public class CommentDTO {
    private int id;
    private int userId;
    private String username;
    private String content;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUserId();
        this.username = comment.getUsername();
        this.content = comment.getContent();
    }
}