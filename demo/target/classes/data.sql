INSERT INTO users (username, password, email, role) VALUES
('neha', '12345', 'neha@gmail.com', 'company'),
('kiran', 'password', 'kiran@gmail.com', 'company'),
('admin', 'admin123', 'admin@company.com', 'admin');

-- ============================================
-- 🏦 INSERT BANK DETAILS
-- ============================================
INSERT INTO bank_details (bank_name, account_number, ifsc, account_holder) VALUES
('First National Bank', '1234567890', 'SBIN0001234', 'TechNova Ltd'),
('State Bank', '0987654321', 'SBIN0005678', 'NextGen Systems'),
('CityBank', '555666777888', 'ICIC0001111', 'CodeCrafters'),
('HDFC Bank', '111222333444', 'HDFC0009876', 'FutureVision Pvt Ltd'),
('Axis Bank', '777888999000', 'UTIB0004321', 'Innovatech Solutions');

-- ============================================
-- 🏢 INSERT COMPANY DETAILS
-- ============================================
INSERT INTO company 
(name, ceo_name, point_of_contact, about_company, is_active, budget, monthly_budget, mom_growth_percent, currency, bank_details_id, pan_number)
VALUES
('TechNova Ltd', 'Ravi Kumar', 'Meena Patel', 'We build enterprise SaaS products and provide scalable cloud solutions.', TRUE, 1000000.00, 83333.33, 2.50, 'USD', 1, 'AACCT1234N'),
('NextGen Systems', 'Aditi Sharma', 'Rahul Jain', 'IT consulting and digital transformation solutions.', FALSE, 500000.00, 41666.67, -1.20, 'USD', 2, 'BBXCS5678M'),
('CodeCrafters', 'Arjun Rao', 'Sneha Iyer', 'Freelance software and creative design agency.', TRUE, 150000.00, 12500.00, 5.00, 'USD', 3, 'CCVDE9876L'),
('FutureVision Pvt Ltd', 'Priya Mehta', 'Kunal Joshi', 'AI-driven analytics and business automation solutions.', TRUE, 2500000.00, 208333.33, 4.75, 'INR', 4, 'DDFGT5432R'),
('Innovatech Solutions', 'Vikram Das', 'Sonia Gupta', 'Cutting-edge R&D for IoT and smart devices.', TRUE, 750000.00, 62500.00, 3.20, 'USD', 5, 'EEFHG2233P');
