package ra.model.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.Catalog;
import ra.model.repository.CatalogRepository;
import ra.model.service.ICatalogService;

import java.util.Optional;

@Service
public class CatalogServiceImpl implements ICatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public Page<Catalog> findAll(Pageable pageable) {
        pageable= PageRequest.of(pageable.getPageNumber(), 5);
        return catalogRepository.findAll(pageable);
    }

    @Override
    public Page<Catalog> findCatalogByCatalogName(String catalogName,Pageable pageable) {
        pageable=PageRequest.of(pageable.getPageNumber(), 5);
        return catalogRepository.findCatalogByCatalogName(catalogName,pageable);
    }

    @Override
    public Iterable<Catalog> findAll() {
        return catalogRepository.findAll();
    }

    @Override
    public Optional<Catalog> findById(Long id) {
        return catalogRepository.findById(id);
    }

    @Override
    public Optional<Catalog> save(Catalog catalog) {
        return Optional.of(catalogRepository.save(catalog));
    }

    @Override
    public void deleteById(Long id) {
        catalogRepository.deleteById(id);
    }
}
