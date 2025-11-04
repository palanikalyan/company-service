DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS bank_details;
DROP TABLE IF EXISTS users;

-- ============================================
-- 🧱 CREATE TABLES
-- ============================================

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(50) DEFAULT 'company'
);

CREATE TABLE IF NOT EXISTS bank_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bank_name VARCHAR(200),
    account_number VARCHAR(100),
    ifsc VARCHAR(34),
    account_holder VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS company (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    ceo_name VARCHAR(100),
    point_of_contact VARCHAR(100),
    about_company TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    budget DECIMAL(19,2) DEFAULT 0,
    monthly_budget DECIMAL(19,2) DEFAULT 0,
    mom_growth_percent DECIMAL(5,2) DEFAULT 0,
    currency VARCHAR(10) DEFAULT 'USD',
    bank_details_id BIGINT,
    pan_number VARCHAR(20),
    CONSTRAINT fk_bank FOREIGN KEY (bank_details_id) REFERENCES bank_details(id)
);