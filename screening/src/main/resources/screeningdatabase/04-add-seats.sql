--liquibase formatted sql
--changeset wgmiterek:4
INSERT INTO seats (screening_id, rows_number, seat_in_row, status)
SELECT
    s.id AS screening_id,
    t1.n AS rows_number,
    t2.n AS seat_in_row,
    'AVAILABLE'
FROM
    screening s
        CROSS JOIN
    (SELECT 1 AS n UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT
        5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t1
        CROSS JOIN
    (SELECT 1 AS n UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT
        5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) t2
ORDER BY s.id, t1.n, t2.n;
