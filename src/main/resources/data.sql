INSERT INTO users (email, name, password, age)
VALUES
    ('lezhin1@test.com',  '한우진', 'lezhin123!', 18),
    ('lezhin2@test.com',  '한우짱', 'lezhin123!', 20),
    ('lezhin3@test.com',  '도가영', 'lezhin123!', 18),
    ('lezhin4@test.com',  '도나영', 'lezhin123!', 19),
    ('lezhin5@test.com',  '도다영', 'lezhin123!', 20),
    ('lezhin6@test.com',  '도라영', 'lezhin123!', 21),
    ('lezhin7@test.com',  '도마영', 'lezhin123!', 22),
    ('lezhin8@test.com',  '도바영', 'lezhin123!', 23),
    ('lezhin9@test.com',  '도사영', 'lezhin123!', 33),
    ('lezhin10@test.com', '도차영', 'lezhin123!', 30);


INSERT INTO toon_view_history (user_id, toon_id, viewed_at, created_at)
VALUES
    (1, 1, '2025-04-10 10:00:00', NOW()),
    (2, 2, '2025-04-10 11:00:00', NOW()),
    (3, 3, '2025-04-10 12:00:00', NOW()),
    (4, 4, '2025-04-10 13:00:00', NOW()),
    (5, 5, '2025-04-10 14:00:00', NOW()),
    (6, 6, '2025-04-10 15:00:00', NOW()),
    (7, 7, '2025-04-10 16:00:00', NOW()),
    (8, 8, '2025-04-10 17:00:00', NOW()),
    (9, 9, '2025-04-10 18:00:00', NOW()),
    (10, 10, '2025-04-10 19:00:00', NOW()),
    (1, 11, '2025-04-10 20:00:00', NOW()),
    (2, 12, '2025-04-10 21:00:00', NOW()),
    (3, 13, '2025-04-10 22:00:00', NOW()),
    (4, 14, '2025-04-10 23:00:00', NOW()),
    (5, 15, '2025-04-11 00:00:00', NOW()),
    (6, 1, '2025-04-11 01:00:00', NOW()),
    (7, 2, '2025-04-11 02:00:00', NOW()),
    (8, 3, '2025-04-11 03:00:00', NOW()),
    (9, 4, '2025-04-11 04:00:00', NOW()),
    (10, 5, '2025-04-11 05:00:00', NOW()),
    (1, 6, '2025-04-11 06:00:00', NOW()),
    (2, 7, '2025-04-11 07:00:00', NOW()),
    (3, 8, '2025-04-11 08:00:00', NOW()),
    (4, 9, '2025-04-11 09:00:00', NOW()),
    (5, 10, '2025-04-11 10:00:00', NOW()),
    (6, 11, '2025-04-11 11:00:00', NOW()),
    (7, 12, '2025-04-11 12:00:00', NOW()),
    (8, 13, '2025-04-11 13:00:00', NOW()),
    (9, 14, '2025-04-11 14:00:00', NOW()),
    (10, 15, '2025-04-11 15:00:00', NOW()),
    (1, 1, '2025-04-11 16:00:00', NOW()),
    (2, 2, '2025-04-11 17:00:00', NOW()),
    (3, 3, '2025-04-11 18:00:00', NOW()),
    (4, 4, '2025-04-11 19:00:00', NOW()),
    (5, 5, '2025-04-11 20:00:00', NOW()),
    (6, 6, '2025-04-11 21:00:00', NOW()),
    (7, 7, '2025-04-11 22:00:00', NOW()),
    (8, 8, '2025-04-11 23:00:00', NOW()),
    (9, 9, '2025-04-12 00:00:00', NOW()),
    (10, 10, '2025-04-12 01:00:00', NOW()),
    (1, 11, '2025-04-12 02:00:00', NOW()),
    (2, 12, '2025-04-12 03:00:00', NOW()),
    (3, 13, '2025-04-12 04:00:00', NOW()),
    (4, 14, '2025-04-12 05:00:00', NOW()),
    (5, 15, '2025-04-12 06:00:00', NOW()),
    (6, 1, '2025-04-12 07:00:00', NOW()),
    (7, 2, '2025-04-12 08:00:00', NOW()),
    (8, 3, '2025-04-12 09:00:00', NOW()),
    (9, 4, '2025-04-12 10:00:00', NOW()),
    (10, 5, '2025-04-12 11:00:00', NOW());

INSERT INTO toon (id, title, is_adult_only, price, price_type, state, genre, schedule_day, created_at, updated_at)
VALUES
    (1, '밤을 걷는 선비', true, 3, 'PAID', 'SCHEDULED', 'ROMANCE', 'MONDAY', NOW(), NOW()),
    (2, '혈연지정', true, 3, 'PAID', 'COMPLETED', 'ROMANCE', 'TUESDAY', NOW(), NOW()),
    (3, '프리드로우', false, 3, 'PAID', 'SCHEDULED', 'ACTION', 'WEDNESDAY', NOW(), NOW()),
    (4, '19금 과외', true, 3, 'PAID', 'COMPLETED', 'ROMANCE', 'THURSDAY', NOW(), NOW()),
    (5, '호러전파상', true, 3, 'PAID', 'SCHEDULED', 'ACTION', 'FRIDAY', NOW(), NOW()),
    (6, '우리사이느은', false, 0, 'FREE', 'COMPLETED', 'ROMANCE', 'SATURDAY', NOW(), NOW()),
    (7, '레사', false, 3, 'PAID', 'SCHEDULED', 'ACTION', 'SUNDAY', NOW(), NOW()),
    (8, '살인자ㅇ난감', false, 0, 'FREE', 'COMPLETED', 'ACTION', 'MONDAY', NOW(), NOW()),
    (9, '스피릿 핑거스', false, 3, 'PAID', 'SCHEDULED', 'ROMANCE', 'TUESDAY', NOW(), NOW()),
    (10, '오렌지 마말레이드', false, 0, 'FREE', 'COMPLETED', 'ROMANCE', 'WEDNESDAY', NOW(), NOW()),
    (11, '결혼까지만 해드립니다', true, 3, 'PAID', 'SCHEDULED', 'ROMANCE', 'THURSDAY', NOW(), NOW()),
    (12, '수업시간 그녀', true, 3, 'PAID', 'COMPLETED', 'ROMANCE', 'FRIDAY', NOW(), NOW()),
    (13, '당신만 몰라!', false, 3, 'PAID', 'SCHEDULED', 'ROMANCE', 'SATURDAY', NOW(), NOW()),
    (14, '취향저격 그녀', false, 0, 'FREE', 'COMPLETED', 'ROMANCE', 'SUNDAY', NOW(), NOW()),
    (15, '용감한 시민', false, 3, 'PAID', 'SCHEDULED', 'ACTION', 'MONDAY', NOW(), NOW());

INSERT INTO webtoon_price_policy (id, webtoon_id, start_date, end_date, price, created_at, updated_at)
VALUES
    (1, 1, '2025-04-01', '2025-04-30', 0, NOW(), NOW()),  -- 밤을 걷는 선비
    (2, 4, '2025-04-15', '2025-05-01', 0, NOW(), NOW()),  -- 19금 과외
    (3, 12, '2025-04-10', '2025-04-25', 0, NOW(), NOW()); -- 수업시간 그녀

INSERT INTO user_coin (id, user_id, balance, created_at, updated_at)
VALUES
    (1, 1, 10, NOW(), NOW()),
    (2, 2, 30, NOW(), NOW()),
    (3, 3, 50, NOW(), NOW()),
    (4, 4, 10, NOW(), NOW()),
    (5, 5, 30, NOW(), NOW()),
    (6, 6, 50, NOW(), NOW()),
    (7, 7, 30, NOW(), NOW()),
    (8, 8, 10, NOW(), NOW()),
    (9, 9, 50, NOW(), NOW()),
    (10, 10, 30, NOW(), NOW());
