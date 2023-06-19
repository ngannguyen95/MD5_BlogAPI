package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;
import ra.model.entity.Reply;
@RestController
public interface ReplyRepository extends JpaRepository<Reply,Long> {
}
