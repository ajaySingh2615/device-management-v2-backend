package com.cadt.devicemanagementv2.mapper;

import com.cadt.devicemanagementv2.dto.request.ProductCreateRequest;
import com.cadt.devicemanagementv2.dto.request.ProductUpdateRequest;
import com.cadt.devicemanagementv2.dto.response.ProductResponse;
import com.cadt.devicemanagementv2.model.Product;
import com.cadt.devicemanagementv2.repository.ProductRepository;
import org.mapstruct.*;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends BaseMapper<Product, ProductResponse> {

    @Override
    @Mapping(target = "createdAt", expression = "java(fmt(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(fmt(entity.getUpdatedAt()))")
    @Mapping(target = "storageType", source = "storageType")
    @Mapping(target = "grade", source = "grade")
    ProductResponse toDto(Product entity);

    default String fmt(java.time.Instant i){
        return i == null ? null : DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneOffset.UTC).format(i);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "storageType", expression = "java(Product.StorageType.valueOf(req.storageType().toUpperCase()))")
    @Mapping(target = "grade", expression = "java(Product.Grade.valueOf(req.grade().toUpperCase()))")
    Product toEntity(ProductCreateRequest req);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "storageType",
            expression = "java(req.storageType()==null? entity.getStorageType() : Product.StorageType.valueOf(req.storageType().toUpperCase()))")
    @Mapping(target = "grade",
            expression = "java(req.grade()==null? entity.getGrade() : Product.Grade.valueOf(req.grade().toUpperCase()))")
    void update(@MappingTarget Product entity, ProductUpdateRequest req);
}
