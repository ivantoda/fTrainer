CREATE TABLE grade (
    id SERIAL PRIMARY KEY,
    trainer_id INT NOT NULL,
    client_id INT NOT NULL,
    grade INT NOT NULL,
    comment VARCHAR(255) NOT NULL,
    CONSTRAINT fk_trainer FOREIGN KEY (trainer_id) REFERENCES users(id),
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES users(id)
);

