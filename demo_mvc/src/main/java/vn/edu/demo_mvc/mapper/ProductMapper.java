package vn.edu.demo_mvc.mapper;

import org.springframework.stereotype.Service;
import vn.edu.demo_mvc.DTO.ProductDTO;
import vn.edu.demo_mvc.Entity.Product;

@Service
public class ProductMapper {
        public static ProductDTO toDTO(Product product){
            return new ProductDTO(product.getId(), product.getName(),
                        product.getPrice(), product.getQuantity());
        }
        public static Product toEntity(ProductDTO dto){
            return Product.builder().id(dto.getId())
                    .name(dto.getName())
                    .price(dto.getPrice())
                    .quantity(dto.getQuantity())
                    .build();
        }
}
