-- ========= Sample Restaurants =========
INSERT INTO restaurant (name, address, phone, email, contact_person, pincode, city, state, image_url, description, cuisine, status, created_at, updated_at)
VALUES
    ('Vaishali', 'FC Road, Pune', '02025531234', 'contact@vaishali.in', 'Ramesh Kulkarni', '411005', 'Pune', 'Maharashtra', 'https://images.vaishali.in/vaishali.jpg', 'Iconic South Indian eatery', 'SOUTH_INDIAN', 'ACTIVE', NOW(), NOW()),
    ('Durvankur', 'Tilak Road, Pune', '02024451234', 'info@durvankur.in', 'Suresh Patil', '411030', 'Pune', 'Maharashtra', 'https://images.durvankur.in/thali.jpg', 'Famous Maharashtrian thali spot', 'INDIAN', 'ACTIVE', NOW(), NOW()),
    ('The Biryani Story', 'Baner, Pune', '02027121212', 'hello@tbs.in', 'Asif Khan', '411045', 'Pune', 'Maharashtra', NULL, 'Popular chain for Hyderabadi biryani', 'NORTH_INDIAN', 'ACTIVE', NOW(), NOW()),
    ('Barbeque Nation', 'JM Road, Pune', '02066006600', 'bbq@bnation.in', 'Sunita Sharma', '411004', 'Pune', 'Maharashtra', NULL, 'Buffet & grill dining chain', 'MULTI_CUISINE', 'ACTIVE', NOW(), NOW()),
    ('Wadeshwar', 'FC Road, Pune', '02025532111', 'support@wadeshwar.in', 'Nitin Joshi', '411005', 'Pune', 'Maharashtra', NULL, 'Quick South Indian & Maharashtrian eats', 'SOUTH_INDIAN', 'ACTIVE', NOW(), NOW()),
    ('SP’s Biryani', 'Tilak Road, Pune', '02024452222', 'orders@spbiryani.in', 'Pritam Jadhav', '411030', 'Pune', 'Maharashtra', NULL, 'Spicy, authentic Pune biryani', 'NORTH_INDIAN', 'ACTIVE', NOW(), NOW()),
    ('Hite Bar', 'Aundh, Pune', '02025881234', 'contact@hitebar.in', 'Ravi Singh', '411007', 'Pune', 'Maharashtra', NULL, 'Classic fast food & rolls', 'CHINESE', 'ACTIVE', NOW(), NOW()),
    ('Cream Stone', 'Viman Nagar, Pune', '02026669900', 'ice@creamstone.in', 'Tina Rao', '411014', 'Pune', 'Maharashtra', NULL, 'Premium ice cream desserts', 'MULTI_CUISINE', 'ACTIVE', NOW(), NOW()),
    ('Café Goodluck', 'Deccan, Pune', '02025520100', 'goodluck@cafe.in', 'Ahmed Shaikh', '411004', 'Pune', 'Maharashtra', NULL, 'Iconic Irani cafe in Pune', 'INDIAN', 'ACTIVE', NOW(), NOW()),
    ('Terttulia', 'Koregaon Park, Pune', '02030451234', 'hi@terttulia.in', 'Nidhi Kapoor', '411001', 'Pune', 'Maharashtra', NULL, 'Modern European bistro', 'ITALIAN', 'ACTIVE', NOW(), NOW());

-- ========= Sample Menus =========
INSERT INTO menu (name, restaurant_id, status, description, created_at, updated_at)
VALUES
    ('Breakfast Menu', 1, 'ACTIVE', 'Popular South Indian breakfast', NOW(), NOW()),
    ('Thali Menu', 2, 'ACTIVE', 'Authentic Maharashtrian dishes', NOW(), NOW()),
    ('Biryani Specials', 3, 'ACTIVE', 'Hyderabadi style biryanis', NOW(), NOW()),
    ('Grill Buffet', 4, 'ACTIVE', 'Live grill buffet options', NOW(), NOW()),
    ('Snack Time', 5, 'ACTIVE', 'Quick eats and snacks', NOW(), NOW()),
    ('Signature Biryani', 6, 'ACTIVE', 'SP’s top biryani options', NOW(), NOW()),
    ('Rolls & More', 7, 'ACTIVE', 'Rolls, Chinese starters', NOW(), NOW()),
    ('Dessert Delights', 8, 'ACTIVE', 'Signature desserts', NOW(), NOW()),
    ('Cafe Classics', 9, 'ACTIVE', 'Goodluck’s Irani special items', NOW(), NOW()),
    ('Continental Mains', 10, 'ACTIVE', 'Italian and continental mains', NOW(), NOW());


-- ========= Sample Menu Items =========
INSERT INTO menu_item (name, description, price, status, food_type, category, image_url, menu_id, created_at, updated_at)
VALUES
    ('Masala Dosa', 'Crispy dosa with spiced potato filling', 80.00, 'AVAILABLE', 'VEG', 'MAIN_COURSE', NULL, 1, NOW(), NOW()),
    ('Idli Sambar', 'Steamed rice cakes with lentil soup', 60.00, 'AVAILABLE', 'VEG', 'STARTER', NULL, 1, NOW(), NOW()),
    ('Puran Poli', 'Sweet lentil stuffed flatbread', 55.00, 'AVAILABLE', 'VEG', 'DESSERT', NULL, 2, NOW(), NOW()),
    ('Chicken Biryani', 'Hyderabadi style layered biryani', 180.00, 'AVAILABLE', 'NON_VEG', 'MAIN_COURSE', NULL, 3, NOW(), NOW()),
    ('Paneer Tikka', 'Grilled cottage cheese cubes', 150.00, 'AVAILABLE', 'VEG', 'STARTER', NULL, 4, NOW(), NOW()),
    ('Veg Sandwich', 'Grilled sandwich with vegetables', 90.00, 'AVAILABLE', 'VEG', 'SNACK', NULL, 5, NOW(), NOW()),
    ('SP Special Biryani', 'Signature biryani from SP’s', 200.00, 'AVAILABLE', 'NON_VEG', 'MAIN_COURSE', NULL, 6, NOW(), NOW()),
    ('Chicken Roll', 'Stuffed chicken in rumali roti', 120.00, 'AVAILABLE', 'NON_VEG', 'SNACK', NULL, 7, NOW(), NOW()),
    ('Gulab Jamun', 'Soft round sweet soaked in syrup', 50.00, 'AVAILABLE', 'VEG', 'DESSERT', NULL, 8, NOW(), NOW()),
    ('Bun Maska', 'Soft bun with butter', 40.00, 'AVAILABLE', 'VEG', 'SNACK', NULL, 9, NOW(), NOW()),
    ('Pasta Alfredo', 'Creamy white sauce pasta', 170.00, 'AVAILABLE', 'VEG', 'MAIN_COURSE', NULL, 10, NOW(), NOW()),
    ('Egg Curry', 'Boiled eggs in spicy gravy', 130.00, 'AVAILABLE', 'EGG', 'MAIN_COURSE', NULL, 3, NOW(), NOW()),
    ('Medu Vada', 'Fried lentil fritters', 65.00, 'AVAILABLE', 'VEG', 'SNACK', NULL, 1, NOW(), NOW()),
    ('Thalipeeth', 'Spicy multigrain flatbread', 75.00, 'AVAILABLE', 'VEG', 'MAIN_COURSE', NULL, 2, NOW(), NOW()),
    ('Mutton Rogan Josh', 'Spicy Kashmiri mutton curry', 220.00, 'AVAILABLE', 'NON_VEG', 'MAIN_COURSE', NULL, 4, NOW(), NOW()),
    ('Chocolate Ice Cream', 'Rich chocolate flavored scoop', 100.00, 'AVAILABLE', 'VEG', 'DESSERT', NULL, 8, NOW(), NOW()),
    ('Filter Coffee', 'Strong South Indian style coffee', 40.00, 'AVAILABLE', 'VEG', 'BEVERAGE', NULL, 1, NOW(), NOW()),
    ('Egg Roll', 'Egg wrapped in layered paratha', 90.00, 'AVAILABLE', 'EGG', 'SNACK', NULL, 7, NOW(), NOW()),
    ('Caesar Salad', 'Lettuce, cheese and dressing', 140.00, 'AVAILABLE', 'VEG', 'SALAD', NULL, 10, NOW(), NOW()),
    ('Fruit Custard', 'Seasonal fruits in creamy base', 110.00, 'AVAILABLE', 'VEG', 'DESSERT', NULL, 9, NOW(), NOW());
