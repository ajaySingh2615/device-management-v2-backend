package com.cadt.devicemanagementv2.service;

import com.cadt.devicemanagementv2.dto.request.ProductCreateRequest;
import com.cadt.devicemanagementv2.dto.request.ProductUpdateRequest;
import com.cadt.devicemanagementv2.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(ProductCreateRequest req);

    ProductResponse get(Long id);

    ProductResponse update(Long id, ProductUpdateRequest req);

    void delete(Long id);

    Page<ProductResponse> list(String q, String brand, String grade,
                               java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice,
                               Pageable pageable);
}
