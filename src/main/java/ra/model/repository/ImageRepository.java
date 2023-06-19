package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ra.model.entity.Image;

import java.util.Optional;
@EnableJpaRepositories
@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Optional<Image> findByImage(String fileName);
    Optional<Image> findImageById(Long id);
}
