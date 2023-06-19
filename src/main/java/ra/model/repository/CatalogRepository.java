package ra.model.repository;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Catalog;

import java.util.Optional;


@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Optional<Catalog> findCatalogByCatalogId(Long id);

    Page<Catalog> findCatalogByCatalogName(String catalogName, Pageable pageable);
}
