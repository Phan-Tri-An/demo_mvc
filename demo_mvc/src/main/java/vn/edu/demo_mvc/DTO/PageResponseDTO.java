package vn.edu.demo_mvc.DTO;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageResponseDTO<T> {
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
    private List<T> content;
}
