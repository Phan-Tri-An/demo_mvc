package vn.edu.demo_mvc.Service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.demo_mvc.DTO.PageResponseDTO;
import vn.edu.demo_mvc.DTO.ProductDTO;
import vn.edu.demo_mvc.Entity.Product;
import vn.edu.demo_mvc.Service.ProductService;
import vn.edu.demo_mvc.mapper.ProductMapper;
import vn.edu.demo_mvc.repository.ProductRepository;
import org.springframework.data.domain.Sort;


import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final vn.edu.demo_mvc.repository.OrderDetailRepository orderDetailRepository;

    @Override
    public ProductDTO create(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);

        product.setDeleted(false);
        return ProductMapper.toDTO(repository.save(product));
    }

    @Override
    public List<ProductDTO> getAll() {

        return repository.findAllByDeletedFalse()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Product product = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Không thấy sản phẩm hoặc sản phẩm đã bị xóa rồi!"));

        if (orderDetailRepository.existsByProductId(id)) {
            throw new RuntimeException("Sản phẩm đã có đơn hàng, không thể xóa!");
        }

        product.setDeleted(true);
        repository.save(product);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        Product existingProduct = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm hoặc sản phẩm đã bị xóa!"));

        existingProduct.setName(dto.getName());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setQuantity(dto.getQuantity());

        return ProductMapper.toDTO(repository.save(existingProduct));
    }

    @Override
    public List<ProductDTO> search(String name, BigDecimal minPrice, BigDecimal maxPrice, Boolean inStock) {
        return List.of();
    }

    @Override
    public PageResponseDTO<ProductDTO> getAllWithPagination(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = repository.findAllByDeletedFalse(pageable);

        List<ProductDTO> content = productPage.getContent().stream()
                .map(ProductMapper::toDTO)
                .toList();

        return PageResponseDTO.<ProductDTO>builder()
                .pageNo(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .isLast(productPage.isLast())
                .content(content)
                .build();
    }
}