package ra.model.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Reply;
import ra.model.repository.ReplyRepository;
import ra.model.service.IReplyService;

import java.util.Optional;

@Service
public class ReplyServiceImpl implements IReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public Iterable<Reply> findAll() {
        return null;
    }

    @Override
    public Optional<Reply> findById(Long id) {
        return replyRepository.findById(id);
    }

    @Override
    public Optional<Reply> save(Reply reply) {
        return Optional.of(replyRepository.save(reply));
    }

    @Override
    public void deleteById(Long id) {
        replyRepository.deleteById(id);
    }
}
