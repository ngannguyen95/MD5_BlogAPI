package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Catalog;

public interface ICatalogService extends IGenericService<Catalog,Long>{
    Page<Catalog> findAll(Pageable pageable);
    Page<Catalog> findCatalogByCatalogName(String catalogName,Pageable pageable);
}
