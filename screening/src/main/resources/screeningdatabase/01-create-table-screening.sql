--liquibase formatted sql
--changeset wgmiterek:1
CREATE TABLE IF NOT EXISTS screening (
                                         id BIGSERIAL PRIMARY KEY,
                                         date DATE,
                                         time TIME,
                                         film_id BIGINT

    );
