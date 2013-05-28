--
-- Tablo döküm verisi 'additional_info'
--

INSERT INTO additional_info (text, user_tcno) VALUES
('ek bişeyler', 9011003000);


--
-- Tablo döküm verisi 'announcement'
--

INSERT INTO announcement (id, description, poster_path, registered_date, title, announcement_type_id, user_tcno) VALUES
(1, 'asdasdad', '1369774504461.jpg', '2013-05-28 23:55:04', 'duyuru1', 1, 9011003000),
(2, 'asdasdaasdasdas', '1369774524328.jpg', '2013-05-28 23:55:24', 'duyuru2', 2, 9011003000),
(3, 'açıklamaarrrr', '1369774549598.jpg', '2013-05-28 23:55:49', 'duyuru3', 3, 9011003000);

--
-- Tablo döküm verisi 'area_of_interest'
--

INSERT INTO area_of_interest (id, area_of_interest_name, area_of_interest_title, experience_level, user_tcno) VALUES
(1, 'açıklama', 'başlık', 9, 9011003000);

--
-- Tablo döküm verisi 'certification'
--

INSERT INTO certification (id, certificate_date, certificate_name, description, file_path, institution, user_tcno) VALUES
(1, '2013-05-01 00:00:00', 'deneme', 'adsasdas', '1369774988459.jpg', 'x firmas', 9011003000);



INSERT INTO classical_cv (cv_path, user_tcno) VALUES
('1369774814407.jpg', 9011003000);



--
--
--
--
--
--
-- Tablo döküm verisi 'event'
--

INSERT INTO event (id, description, end_date, poster_path, registered_date, start_date, title, user_tcno) VALUES
(1, 'asd', '2013-05-30 00:00:00', '1369774465797.jpg', '2013-05-28 23:54:25', '2013-05-29 00:00:00', 'etkinlik1', 9011003000),
(2, 'asasdasd', '2013-05-30 00:00:00', '1369774488696.jpg', '2013-05-28 23:54:48', '2013-05-29 00:00:00', 'etkinlik2', 9011003000),
(3, 'asdasdasddsadasds', '2013-05-30 00:00:00', '1369774603251.jpg', '2013-05-28 23:56:43', '2013-05-29 00:00:00', 'etkinlik3', 9011003000);


INSERT INTO foreign_language (id, reading_rank, registered_date, speaking_rank, writing_rank, language_id, user_tcno) VALUES
(1, 7, '2013-05-29 00:01:35', 8, 6, 3, 9011003000);


--
-- Tablo döküm verisi 'job_experience'
--

INSERT INTO job_experience (id, company_name, description, end_date, registered_date, start_date, city_id, country_code, position_id, sector_id, user_tcno, working_type_id) VALUES
(1, 'şirket ismi', 'asdasdfsdfsdfsdfd', NULL, '2013-05-28 23:59:22', '2013-05-26 00:00:00', 129, 'ABW', 1, 1, 9011003000, 1);

--

--
-- Tablo döküm verisi 'photo'
--
INSERT INTO photo_album (id, album_name, date, description, place, user_tcno) VALUES
(1, 'albüm2013', '2013-05-28 23:57:14', '', '', 9011003000),
(2, 'albüm2', '2013-05-28 23:57:23', '', '', 9011003000);


INSERT INTO photo (id, date, description, photo_path, place, album_id) VALUES
(1, '2013-05-28 23:57:36', '', '1369774656974.jpg', '', 1),
(2, '2013-05-28 23:57:51', '', '1369774671853.jpg', '', 2);


INSERT INTO post_history (id, content_id, content_type, description, published_date, user_tcno) VALUES
(1, NULL, 'TEXT', 'gönderi1', '2013-05-28 23:53:55', 9011003000),
(2, NULL, 'TEXT', 'gönderi2', '2013-05-28 23:54:06', 9011003000),
(3, 1, 'EVENT', 'gönderi3', '2013-05-28 23:54:25', 9011003000),
(4, 2, 'EVENT', 'gönderi4', '2013-05-28 23:54:48', 9011003000),
(5, 1, 'ANNOUNCEMENT', 'gönderi5', '2013-05-28 23:55:04', 9011003000),
(6, 2, 'ANNOUNCEMENT', 'gönderi6', '2013-05-28 23:55:24', 9011003000);


INSERT INTO share_list (id, post_history_id, user_tcno) VALUES
(1, 1, 9011062000),
(2, 1, 10011032000),
(3, 1, 10011041000),
(4, 1, 10011064000),
(5, 1, 10011062000),
(6, 1, 10011013000),
(7, 1, 10011095000),
(8, 1, 9011061000),
(9, 1, 11111111111),
(10, 1, 22222222222),
(11, 1, 33333333333),
(12, 1, 44444444444),
(13, 1, 9011003000),
(14, 2, 9011062000),
(15, 2, 10011032000),
(16, 2, 10011041000),
(17, 2, 10011064000),
(18, 2, 10011062000),
(19, 2, 10011013000),
(20, 2, 10011095000),
(21, 2, 9011061000),
(22, 2, 11111111111),
(23, 2, 22222222222),
(24, 2, 33333333333),
(25, 2, 44444444444),
(26, 2, 9011003000),
(27, 3, 9011062000),
(28, 3, 10011032000),
(29, 3, 10011041000),
(30, 3, 10011064000),
(31, 3, 10011062000),
(32, 3, 10011013000),
(33, 3, 10011095000),
(34, 3, 9011061000),
(35, 3, 11111111111),
(36, 3, 22222222222),
(37, 3, 33333333333),
(38, 3, 44444444444),
(39, 3, 9011003000),
(40, 4, 9011062000),
(41, 4, 10011032000),
(42, 4, 10011041000),
(43, 4, 10011064000),
(44, 4, 10011062000),
(45, 4, 10011013000),
(46, 4, 10011095000),
(47, 4, 9011061000),
(48, 4, 11111111111),
(49, 4, 22222222222),
(50, 4, 33333333333),
(51, 4, 44444444444),
(52, 4, 9011003000),
(53, 5, 9011062000),
(54, 5, 10011032000),
(55, 5, 10011041000),
(56, 5, 10011064000),
(57, 5, 10011062000),
(58, 5, 10011013000),
(59, 5, 10011095000),
(60, 5, 9011061000),
(61, 5, 11111111111),
(62, 5, 22222222222),
(63, 5, 33333333333),
(64, 5, 44444444444),
(65, 5, 9011003000),
(66, 6, 9011062000),
(67, 6, 10011032000),
(68, 6, 10011041000),
(69, 6, 10011064000),
(70, 6, 10011062000),
(71, 6, 10011013000),
(72, 6, 10011095000),
(73, 6, 9011061000),
(74, 6, 11111111111),
(75, 6, 22222222222),
(76, 6, 33333333333),
(77, 6, 44444444444),
(78, 6, 9011003000);
