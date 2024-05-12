package com.tuts.ots.onetimeshop.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private List<UserDto> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElement;
    private int totalPages;

    private boolean lastPage;
}
