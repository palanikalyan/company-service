INSERT INTO users (username, password, email) VALUES
('neha', '12345', 'neha@gmail.com'),
('kiran', 'password', 'kiran@gmail.com');

-- Insert bank details first
INSERT INTO bank_details (bank_name, account_number, ifsc, account_holder) VALUES
('First National Bank', '1234567890', 'SBIN0001234', 'TechNova Ltd'),
('State Bank', '0987654321', 'SBIN0005678', 'NextGen Systems'),
('CityBank', '555666777888', 'ICIC0001111', 'CodeCrafters');

-- Then insert companies referencing the bank_details rows (ids 1,2,3)
INSERT INTO company (name, ceo_name, point_of_contact, about_company, is_active, budget, monthly_budget, mom_growth_percent, currency, bank_details_id)
VALUES
('TechNova Ltd', 'Ravi Kumar', 'Meena Patel', 'We build enterprise SaaS products.', true, 1000000.00, 83333.33, 2.50, 'USD', 1),
('NextGen Systems', 'Aditi Sharma', 'Rahul Jain', 'IT consulting and solutions provider.', false, 500000.00, 41666.67, -1.20, 'USD', 2),
('CodeCrafters', 'Arjun Rao', 'Sneha Iyer', 'Freelance software and design agency.', true, 150000.00, 12500.00, 5.00, 'USD', 3);
