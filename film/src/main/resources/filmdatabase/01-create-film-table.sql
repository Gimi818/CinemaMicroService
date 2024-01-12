--liquibase formatted sql
--changeset wgmiterek:1
CREATE TABLE IF NOT EXISTS film (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    category VARCHAR(255),
    duration_film_in_minutes INT
    );

