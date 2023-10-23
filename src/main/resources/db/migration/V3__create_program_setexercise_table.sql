CREATE TABLE set_exercise (
    id SERIAL PRIMARY KEY,
    set_count INT NOT NULL,
    exercise_count INT NOT NULL,
    exercise_id INT NOT NULL,
    FOREIGN KEY (exercise_id) REFERENCES exercise(id)
);


CREATE TABLE program (
    id SERIAL PRIMARY KEY,
    trainer_id INT NOT NULL,
    client_id INT NOT NULL
);

CREATE TABLE program_set_exercise (
    program_id INT NOT NULL,
    set_exercise_id INT NOT NULL,
    PRIMARY KEY (program_id, set_exercise_id),
    FOREIGN KEY (program_id) REFERENCES program(id),
    FOREIGN KEY (set_exercise_id) REFERENCES set_exercise(id)
);