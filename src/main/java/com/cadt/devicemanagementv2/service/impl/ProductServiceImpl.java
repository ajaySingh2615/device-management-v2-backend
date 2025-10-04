package com.cadt.devicemanagementv2.service.impl;

import com.cadt.devicemanagementv2.dto.request.ProductCreateRequest;
import com.cadt.devicemanagementv2.dto.request.ProductUpdateRequest;
import com.cadt.devicemanagementv2.dto.response.ProductResponse;
import com.cadt.devicemanagementv2.exception.ApiException;
import com.cadt.devicemanagementv2.exception.ResourceNotFoundException;
import com.cadt.devicemanagementv2.mapper.ProductMapper;
import com.cadt.devicemanagementv2.model.Product;
import com.cadt.devicemanagementv2.repository.ProductRepository;
import com.cadt.devicemanagementv2.repository.spec.ProductSpecs;
import com.cadt.devicemanagementv2.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repo, ProductMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ProductResponse create(ProductCreateRequest req) {
        if (repo.existsBySku(req.sku())) {
            throw new ApiException("SKU already exists");
        }
        Product entity = mapper.toEntity(req);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse get(Long id) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return mapper.toDto(p);
    }

    @Override
    public ProductResponse update(Long id, ProductUpdateRequest req) {
        Product entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        mapper.update(entity, req);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Product not found");
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> list(String q, String brand, String grade, BigDecimal minPrice,
                                      BigDecimal maxPrice, Pageable pageable) {
        Specification<Product> spec = Specification.allOf(
                ProductSpecs.q(q),
                ProductSpecs.brand(brand),
                ProductSpecs.grade(grade),
                ProductSpecs.minPrice(minPrice),
                ProductSpecs.maxPrice(maxPrice)
        );
        return repo.findAll(spec, pageable).map(mapper::toDto);
    }
}
