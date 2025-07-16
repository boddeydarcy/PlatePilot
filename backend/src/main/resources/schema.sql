CREATE TABLE stock_item (
                    id VARCHAR(255) PRIMARY KEY,
                    name VARCHAR(255),
                    quantity INT
);

CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(20) DEFAULT 'STAFF'
);