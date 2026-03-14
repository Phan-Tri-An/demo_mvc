package vn.edu.demo_mvc.Service;

import vn.edu.demo_mvc.DTO.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO dto);
    List<ProductDTO> getAll();
    void delete(Long id);

    ProductDTO update(Long id, ProductDTO dto);
    List<ProductDTO>
    search(String name, BigDecimal minPrice, BigDecimal maxPrice, Boolean inStock);

    vn.edu.demo_mvc
            .DTO.PageResponseDTO<ProductDTO>
    getAllWithPagination(int page, int size, String sortBy, String sortDir);
}
