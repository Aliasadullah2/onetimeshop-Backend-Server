package com.tuts.ots.onetimeshop.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    private List<CatagoryDto> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElement;
    private int totalPages;

    private boolean lastPage;


}
