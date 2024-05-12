package com.tuts.ots.onetimeshop.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProductRespone {
    private List<ProductDto> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElement;
    private int totalPages;

    private boolean lastPage;
}
