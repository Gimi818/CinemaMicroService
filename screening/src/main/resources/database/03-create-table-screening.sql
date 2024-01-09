--liquibase formatted sql
--changeset wgmiterek:3
CREATE TABLE IF NOT EXISTS screening (
                                         id BIGSERIAL PRIMARY KEY,
                                         date DATE,
                                         time TIME,
                                         film_id BIGINT,
                                         CONSTRAINT fk_film_id FOREIGN KEY (film_id) REFERENCES film(id)
    );
