CREATE TABLE images{
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    image bytea,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
    }