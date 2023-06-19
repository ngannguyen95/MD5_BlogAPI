package ra.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "replys")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;
    @Lob
    private String content;
    private LocalDate replyDate;
    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "commentId")
    private Comment comments;
    private boolean status;
}
