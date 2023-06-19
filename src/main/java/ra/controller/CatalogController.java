package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Catalog;
import ra.model.service.ICatalogService;

import java.util.Optional;

@RestController
@RequestMapping("/api/catalogs")
public class CatalogController {
    @Autowired
    private ICatalogService catalogService;

    @GetMapping("/findAll")
    public ResponseEntity<Iterable<Catalog>> findAllCatalog(Pageable pageable) {
        Page<Catalog> catalogList = catalogService.findAll(pageable);
//        List<Catalog> catalogList = (List<Catalog>) catalogService.findAll();
        if (catalogList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(catalogList, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Catalog> createCatalog(@RequestBody Catalog catalog) {
        Catalog catalog1 = catalogService.save(catalog).get();
        return new ResponseEntity<>(catalog1, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Catalog> updateCatalog(@PathVariable("id") Long id, @RequestBody Catalog catalog) {
        Optional<Catalog> catalogUpdate = catalogService.findById(id);
        if (catalogUpdate.isPresent()) {
            catalog.setCatalogId(id);
            catalogService.save(catalog);
            return new ResponseEntity<>(catalog, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/changeStatusCatalog/{id}")
    public ResponseEntity<?> changeStatusCatalog(@PathVariable Long id) {
        Optional<Catalog> catalog = catalogService.findById(id);
        if (catalog.isPresent()) {
            catalog.get().setStatus(!catalog.get().isStatus());
            catalogService.save(catalog.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
