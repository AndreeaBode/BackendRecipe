package sd.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailDetails {
    private String from;
    private String to;
    private String subject;
    private String body;
    private String replyTo;
}
