-- ======= restaurant table =======
CREATE TABLE restaurant
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    name           VARCHAR(255) NOT NULL,
    address        VARCHAR(500) NOT NULL,
    phone          VARCHAR(15)  NOT NULL,
    email          VARCHAR(100) NOT NULL,
    contact_person VARCHAR(50)  NOT NULL,
    pincode        VARCHAR(10)  NOT NULL,
    city           VARCHAR(25)  NOT NULL,
    state          VARCHAR(25)  NOT NULL,
    image_url      VARCHAR(255),
    description    VARCHAR(500),
    cuisine        VARCHAR(50)  NOT NULL,
    status         VARCHAR(30)  NOT NULL,
    created_at     TIMESTAMP    NOT NULL,
    updated_at     TIMESTAMP    NOT NULL
);

CREATE INDEX idx_restaurant_city ON restaurant (city);
CREATE INDEX idx_restaurant_cuisine ON restaurant (cuisine);

-- ======= menu table =======
CREATE TABLE menu
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(50) NOT NULL,
    restaurant_id BIGINT      NOT NULL,
    status        VARCHAR(30) NOT NULL,
    description   VARCHAR(500),
    created_at    TIMESTAMP   NOT NULL,
    updated_at    TIMESTAMP   NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE INDEX idx_menu_restaurant ON menu (restaurant_id);

-- ======= menu_item table =======
CREATE TABLE menu_item
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255)   NOT NULL,
    description VARCHAR(500),
    price       DECIMAL(10, 2) NOT NULL,
    status      VARCHAR(20)    NOT NULL,
    food_type   VARCHAR(30)    NOT NULL,
    category    VARCHAR(30)    NOT NULL,
    image_url   VARCHAR(255),
    menu_id     BIGINT         NOT NULL,
    created_at  TIMESTAMP      NOT NULL,
    updated_at  TIMESTAMP      NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
);

CREATE INDEX idx_item_menu ON menu_item (menu_id);
CREATE INDEX idx_item_category ON menu_item (category);
CREATE INDEX idx_item_food_type ON menu_item (food_type);
