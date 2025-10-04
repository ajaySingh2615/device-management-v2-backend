CREATE TABLE IF NOT EXISTS product(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    sku VARCHAR(64) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(120) NOT NULL,

    cpu VARCHAR(120),
    ram_gb INT NOT NULL DEFAULT 8,
    storage_type ENUM('HDD', 'SSD', 'NVME') DEFAULT 'SSD',
    storage_gb INT NOT NULL DEFAULT 256,
    screen_size DECIMAL(4, 1),

    grade ENUM('A', 'B', 'C') NOT NULL DEFAULT 'A',  -- cosmetic grade for refurbished
    price DECIMAL(12, 2) NOT NULL,
    currency VARCHAR(8) NOT NULL DEFAULT 'INR',
    quantity INT NOT NULL DEFAULT 0,
    warranty_months INT NOT NULL DEFAULT 3,

    primary_image_url VARCHAR(500),
    condition_notes VARCHAR(500),
    specs_json JSON NULL
);

CREATE INDEX idx_product_brand ON product (brand);
CREATE INDEX idx_product_grade ON product (grade);
CREATE INDEX idx_product_title ON product (title);

-- Seeds
INSERT INTO product (sku, title, brand, model, cpu, ram_gb, storage_type, storage_gb, screen_size, grade, price, quantity, warranty_months, primary_image_url, condition_notes)
VALUES
    ('SKU-DEL-5410-A','Dell Latitude 5410 i5 10th Gen','Dell','Latitude 5410','Intel Core i5-10210U',16,'SSD',512,14.0,'A',32999.00,5,6,NULL,'Excellent condition'),
    ('SKU-HP-840G5-B','HP EliteBook 840 G5 i5','HP','EliteBook 840 G5','Intel Core i5-8350U',8,'SSD',256,14.0,'B',25999.00,8,3,NULL,'Minor scratches'),
    ('SKU-LEN-T490-A','Lenovo ThinkPad T490 i7','Lenovo','ThinkPad T490','Intel Core i7-8565U',16,'NVME',512,14.0,'A',38999.00,3,6,NULL,'A-Grade, like new');