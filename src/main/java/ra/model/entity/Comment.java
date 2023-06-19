package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Lob
    private String comment;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;
    private boolean status;

}
