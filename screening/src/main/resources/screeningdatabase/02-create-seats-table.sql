--liquibase formatted sql
--changeset wgmiterek:2
CREATE TABLE IF NOT EXISTS seats (
                                     id BIGSERIAL PRIMARY KEY,
                                     rows_number INT NOT NULL,
                                     seat_in_row INT NOT NULL,
                                     screening_id BIGINT,
                                     status VARCHAR(255) NOT NULL,
    FOREIGN KEY (screening_id) REFERENCES screening (id)
    );
