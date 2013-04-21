INSERT INTO `user` (`tcno`, `birthdayYear`, `email`, `enabled`, `name`, `password`, `profile_photo_path`, `registered_date`, `send_mail`, `surname`, `city_id`, `country_code`) VALUES
(9011003000, 1991, 'orcun.ulgen@gmail.com', 1, 'orcun', 'asd', NULL, '2013-04-14 21:17:24', 1, 'ulgen', 3374, 'TUR'),
(9011061000, 1990, 'tayfurgurlesin@gmail.com', 1, 'tayfur', 'asd', NULL, '2013-04-14 21:28:13', 1, 'gurlesin', 3373, 'TUR'),
(9011062000, 1991, 'burakozer@gmail.com', 1, 'burak', 'asd', NULL, '2013-04-14 21:22:10', 1, 'ozer', 3371, 'TUR'),
(10011013000, 1991, 'ekremselcukoymak@gmail.com', 1, 'ekrem selcuk', 'asd', NULL, '2013-04-14 21:19:22', 1, 'oymak', 3359, 'TUR'),
(10011032000, 1992, 'ilyasoktay@gmail.com', 1, 'ilyas', 'asd', NULL, '2013-04-14 21:39:32', 1, 'oktay', 3357, 'TUR'),
(10011041000, 1991, 'halduntereman@gmail.com', 1, 'haldun', 'asd', NULL, '2013-04-14 21:38:23', 1, 'tereman', 3409, 'TUR'),
(10011062000, 1991, 'semihkahya@gmail.com', 1, 'semih', 'asd', NULL, '2013-04-14 21:23:13', 1, 'kahya', 3393, 'TUR'),
(10011064000, 1992, 'silanturkdogan@gmail.com', 1, 'silan', 'asd', NULL, '2013-04-14 21:27:17', 1, 'turkdogan', 3358, 'TUR'),
(10011095000, 1992, 'berkaytacyildiz@gmail.com', 1, 'berkay', 'asd', NULL, '2013-04-14 21:24:42', 1, 'tacyildiz', 3404, 'TUR'),
(11111111111, 1983, 'amacguvensan@gmail.com', 1, 'amac', 'asd', NULL, '2013-04-14 22:29:36', 1, 'guvensan', 3357, 'TUR'),
(22222222222, 1983, 'ziyacihantaysi@gmail.com', 1, 'ziya cihan', 'asd', NULL, '2013-04-14 22:37:11', 1, 'taysi', 3357, 'TUR'),
(33333333333, 1965, 'ahmettevfikinan@gmail.com', 1, 'ahmet tevfik', 'asd', NULL, '2013-04-14 22:38:28', 1, 'inan', 3357, 'TUR'),
(44444444444, 1965, 'gokhanyavuz@gmail.com', 1, 'gokhan', 'asd', NULL, '2013-04-14 22:39:22', 1, 'yavuz', 3357, 'TUR');


INSERT INTO `user_role` (`user_tcno`, `roles_id`) VALUES
(10011013000, 2),
(9011062000, 2),
(10011062000, 2),
(10011095000, 2),
(10011064000, 2),
(9011061000, 2),
(10011041000, 2),
(10011032000, 2),
(9011003000, 2),
(11111111111, 3),
(22222222222, 3),
(33333333333, 3),
(44444444444, 3),
(11111111111, 1);