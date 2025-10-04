package com.cadt.devicemanagementv2.repository.spec;

import com.cadt.devicemanagementv2.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecs {

    public static Specification<Product> q(String q) {
        if (q == null || q.isBlank()) return null;
        String like = "%" + q.toLowerCase() + "%";
        return (root, cq, cb) -> cb.or(
                cb.like(cb.lower(root.get("title")), like),
                cb.like(cb.lower(root.get("brand")), like),
                cb.like(cb.lower(root.get("model")), like)
        );
    }

    public static Specification<Product> brand(String brand) {
        if (brand == null || brand.isBlank()) return null;
        return (root, cq, cb) -> cb.equal(cb.lower(root.get("brand")), brand.toLowerCase());
    }

    public static Specification<Product> grade(String grade) {
        if (grade == null || grade.isBlank()) return null;
        return (root, cq, cb) -> cb.equal(root.get("grade"), Product.Grade.valueOf(grade.toUpperCase()));
    }

    public static Specification<Product> minPrice(BigDecimal min) {
        if (min == null) return null;
        return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<Product> maxPrice(BigDecimal max) {
        if (max == null) return null;
        return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get("price"), max);
    }
}
