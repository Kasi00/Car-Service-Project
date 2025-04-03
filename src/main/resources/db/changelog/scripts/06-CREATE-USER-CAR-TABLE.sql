CREATE TABLE user_car (
                          user_id BIGINT NOT NULL,
                          car_id BIGINT NOT NULL,
                          PRIMARY KEY (user_id, car_id),
                          FOREIGN KEY (user_id) REFERENCES app_user(id),
                          FOREIGN KEY (car_id) REFERENCES car(id)
);