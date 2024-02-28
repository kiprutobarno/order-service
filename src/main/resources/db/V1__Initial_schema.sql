CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    price FLOAT,
    quantity INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP NOT NULL,
    version INTEGER NOT NULL
);