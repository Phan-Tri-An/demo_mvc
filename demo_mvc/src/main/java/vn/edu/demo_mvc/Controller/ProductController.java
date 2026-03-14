package vn.edu.demo_mvc.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.demo_mvc.DTO.PageResponseDTO;
import vn.edu.demo_mvc.DTO.ProductDTO;
import vn.edu.demo_mvc.Service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDTO create(@Valid @RequestBody ProductDTO dto) {

        return productService.create(dto);
    }

    @GetMapping
    public List<ProductDTO> getAll(){

        return productService.getAll();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id){

        productService.delete(id);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return productService.update(id, dto);
    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean inStock) {
        return productService.search(name, minPrice, maxPrice, inStock);
    }
    @GetMapping("/paged")
    public PageResponseDTO<ProductDTO> getAllProductsPaged(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "dir", defaultValue = "asc") String sortDir
    ) {
        return productService.getAllWithPagination(page, size, sortBy, sortDir);
    }
}