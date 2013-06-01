INSERT INTO `user` (`tcno`, `activation`, `birthdayYear`, `email`, `enabled`, `name`, `password`, `profile_photo_path`, `registered_date`, `send_mail`, `surname`, `city_id`, `country_code`) VALUES
(9011003000, 1, 1991, 'orcun.ulgen@gmail.com', 1, 'orcun', 'dkPw8rMX0oVcuVaieG/PFg==', 'orcun.jpg', '2013-04-14 21:17:24', 1, 'ulgen', 3374, 'TUR'),
(9011061000, 1, 1990, 'tayfurgurlesin@gmail.com', 1, 'tayfur', 'dkPw8rMX0oVcuVaieG/PFg==', 'tayfur.jpg', '2013-04-14 21:28:13', 1, 'gurlesin', 3373, 'TUR'),
(9011062000, 1, 1991, 'burakozer@gmail.com', 1, 'burak', 'dkPw8rMX0oVcuVaieG/PFg==', 'burak.jpg', '2013-04-14 21:22:10', 1, 'ozer', 3371, 'TUR'),
(10011013000, 1, 1991, 'ekremselcukoymak@gmail.com', 1, 'ekrem selcuk', 'dkPw8rMX0oVcuVaieG/PFg==', 'selcuk.jpg', '2013-04-14 21:19:22', 1, 'oymak', 3359, 'TUR'),
(10011032000, 1, 1992, 'ilyasoktay@gmail.com', 1, 'ilyas', 'dkPw8rMX0oVcuVaieG/PFg==', 'ilyas.jpg', '2013-04-14 21:39:32', 1, 'oktay', 3357, 'TUR'),
(10011041000, 1, 1991, 'halduntereman@gmail.com', 1, 'haldun', 'dkPw8rMX0oVcuVaieG/PFg==', 'haldun.jpg', '2013-04-14 21:38:23', 1, 'tereman', 3409, 'TUR'),
(10011062000, 1, 1991, 'semihkahya@gmail.com', 1, 'semih', 'dkPw8rMX0oVcuVaieG/PFg==', 'semih.jpg', '2013-04-14 21:23:13', 1, 'kahya', 3393, 'TUR'),
(10011064000, 1, 1992, 'silanturkdogan@gmail.com', 1, 'silan', 'dkPw8rMX0oVcuVaieG/PFg==', 'silan.jpg', '2013-04-14 21:27:17', 1, 'turkdogan', 3358, 'TUR'),
(10011095000, 1, 1992, 'berkaytacyildiz@gmail.com', 1, 'berkay', 'dkPw8rMX0oVcuVaieG/PFg==', 'berkay.jpg', '2013-04-14 21:24:42', 1, 'tacyildiz', 3404, 'TUR'),
(11111111111, 1, 1983, 'amacguvensan@gmail.com', 1, 'amac', 'dkPw8rMX0oVcuVaieG/PFg==', 'mag.jpg', '2013-04-14 22:29:36', 1, 'guvensan', 3357, 'TUR'),
(22222222222, 1, 1983, 'ziyacihantaysi@gmail.com', 1, 'ziya cihan', 'dkPw8rMX0oVcuVaieG/PFg==', 'zct.jpg', '2013-04-14 22:37:11', 1, 'taysi', 3357, 'TUR'),
(33333333333, 1, 1965, 'ahmettevfikinan@gmail.com', 1, 'ahmet tevfik', 'dkPw8rMX0oVcuVaieG/PFg==', 'ati.jpg', '2013-04-14 22:38:28', 1, 'inan', 3357, 'TUR'),
(44444444444, 1, 1965, 'gokhanyavuz@gmail.com', 1, 'gokhan', 'dkPw8rMX0oVcuVaieG/PFg==', 'agy.jpg', '2013-04-14 22:39:22', 1, 'yavuz', 3357, 'TUR');



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

INSERT INTO `admin_basic_info` (`user_tcno`, `department_id`, `faculty_id`, `university_id`) VALUES
(11111111111, 4, 2, 1);


INSERT INTO `contact` (`address`, `facebook_account`, `mobile_phone`, `telephone`, `twitter_account`, `user_tcno`) VALUES
('TSK haydarpaşa öğrenci yurdu üsküdar/istanbul', '', '0536-676-31-08', '', '', 9011003000),
('lale apartmanı deneme sokak nişantaşı/istanbul', '', '0553-546-73-09', '', '', 9011061000),
('TSK Haydarpaşa öğrenci yurdu üsküdar/istanbul', '', '0554-544-23-56', '', '', 9011062000),
('FSM öğrenci yurdu davutpaşa/istanbul', '', '0567-777-64-32', '', '', 10011013000),
('beşiktaş/istanbul ', '', '0435-986-55-44', '', '', 10011032000),
('FSM öğrenci yurdu davutpaşa ', '', '0598-765-43-21', '', '', 10011041000),
('çapa öğrenci yurdu çapa/istanbul', '', '0532-223-78-64', '', '', 10011062000),
('bakırköy/istanbul\r\n', '', '0234-556-78-91', '', '', 10011064000),
('bahçelievler/istanbul', '', '0522-222-23-45', '', '', 10011095000),
('YTÜ bilgisayar müh. güngören/istanbul', '', '0553-344-43-43', '', '', 11111111111),
('bahçelievler /istanbul', '', '0233-256-40-20', '', '', 22222222222),
('yeşiköy/istanbul', '', '0544-632-36-79', '', '', 33333333333),
('kadıköy/istanbul\r\n', '', '0545-433-32-25', '', '', 44444444444);

INSERT INTO `education_info` (`id`, `class_info`, `end_year`, `graduation_degree`, `start_year`, `transcript_path`, `department_id`, `education_level_id`, `faculty_id`, `grading_system_id`, `university_id`, `user_tcno`) VALUES
(1, 3, NULL, '2.96', 2009, NULL, 4, 1, 2, 1, 1, 9011003000),
(2, 3, NULL, '2.8', 2009, NULL, 4, 1, 2, 1, 1, 9011061000),
(3, 3, NULL, '3.34', 2009, NULL, 4, 1, 2, 1, 1, 9011062000),
(4, 3, NULL, '2.6', 2010, NULL, 4, 1, 2, 1, 1, 10011013000),
(5, 3, NULL, '2.5', 2009, NULL, 4, 1, 2, 1, 1, 10011032000),
(6, 3, NULL, '3', 2009, NULL, 4, 1, 2, 1, 1, 10011041000),
(7, 3, NULL, '3.2', 2010, NULL, 4, 1, 2, 1, 1, 10011062000),
(8, 3, NULL, '2.2', 2010, NULL, 4, 1, 2, 1, 1, 10011064000),
(9, 2, NULL, '3.1', 2010, NULL, 4, 1, 2, 1, 1, 10011095000),
(10, NULL, 2008, '3.8', 2006, NULL, 4, 2, 2, 1, 1, 11111111111),
(11, NULL, 2008, '3.6', 2006, NULL, 4, 2, 2, 1, 1, 22222222222),
(12, NULL, 1998, '3.96', 1991, NULL, 4, 3, 2, 1, 1, 33333333333),
(13, NULL, 1998, '3.96', 1991, NULL, 4, 3, 2, 1, 1, 44444444444);


INSERT INTO `high_school` (`end_year`, `graduation_degree`, `high_school_department`, `high_school_name`, `oss_first_choise`, `oss_second_choise`, `oss_third_choise`, `ytu_ce_order_of_preference`, `user_tcno`, `grading_system_id`, `high_school_type_id`) VALUES
(2009, '4.5', 'sayısal', 'Buca anadolu lisesi', 'boğaziçi bilgisayar müh.', 'odtü bilgisayar müh.', 'bilkent bilgisayar müh.', 5, 9011003000, 2, 1),
(2009, '4.5', 'sayısal', 'denizli anadolu lisesi', 'boğaziçi bilgisayar müh.', 'odtü bilgisayar müh.', 'itü bilgisayar müh.', 10, 9011061000, 2, 1),
(2009, '4.92', 'sayısal', 'malatya anadolu lisesi', 'odtü elektrik-elektronik müh', 'itü elektronik müh.', 'itü bilgisayar müh.', 4, 9011062000, 2, 1),
(2009, '4.8', 'sayısal', 'denizli anadolu lisesi', 'boğaziçi bilgisayar müh.', 'odtü elektrik-elektronik müh', 'itü bilgisayar müh.', 5, 10011013000, 2, 1),
(2009, '4.5', 'sayısal', 'beşiktaş anadolu lisesi', 'odtü elektrik-elektronik müh', 'itü elektronik müh.', 'bilkent bilgisayar müh.', 12, 10011032000, 2, 1),
(2009, '5', 'sayısal', 'akhisar anadolu öğretmen lisesi', 'boğaziçi bilgisayar müh.', 'boğaziçi elektrik-elektronik müh.', 'boğaziçi makina müh.', 7, 10011041000, 2, 6),
(2009, '4.8', 'sayısal', 'trabzon anadolu lisesi', 'boğaziçi bilgisayar müh.', 'itü elektronik müh.', 'itü bilgisayar müh.', 5, 10011062000, 2, 1),
(2010, '5', 'sayısal', 'ankara anadolu lisesi', 'boğaziçi bilgisayar müh.', 'boğaziçi elektrik-elektronik müh.', 'bilkent bilgisayar müh.', 5, 10011064000, 2, 1),
(2010, '5', 'sayısal', 'Büyükşehir hüseyin yıldız anadolu lisesi', 'boğaziçi bilgisayar müh.', 'itü elektronik müh.', 'bilkent bilgisayar müh.', 4, 10011095000, 2, 1);


INSERT INTO `parent_info` (`father_birthday_year`, `father_job`, `father_name`, `mother_birthday_year`, `mother_job`, `mother_name`, `parent_address`, `parent_monthly_income`, `parent_phone_number`, `user_tcno`) VALUES
(1967, 'astsubay', 'cevat', 1967, 'öğretmen', 'serpil', 'adres deneme 1-2', 4000, '0533-333-33-33', 9011003000),
(1964, 'doktor', 'mehmet', 1964, 'ev hanımı', 'ayşe', 'ailemin adresi burada ', 5000, '0555-123-33-21', 9011061000),
(1966, 'teknisyen', 'ismail', 1966, 'ev hanımı', 'gülten', 'malatya merkez ', 5500, '0544-422-32-32', 9011062000),
(1960, 'öğretmen', 'ahmet', 1960, 'ev hanımı', 'deniz', 'denizli merkez', 4500, '0556-423-68-96', 10011013000),
(1968, 'makine mühendisi', 'gökhan', 1968, 'mimar', 'derya', 'beşiktaş/istanbul\r\n', 5550, '0554-444-44-45', 10011032000),
(1965, 'sınıf öğretmeni', 'selim', 1962, 'ev hanımı', 'şeyma', 'akhisar merkez akhisar', 5600, '0546-778-88-53', 10011041000),
(1961, 'öğretmen', 'kemal', 1961, 'ev hanımı', 'demet', 'artvin merkez', 4555, '0233-112-32-32', 10011062000),
(1967, 'inşaat müh.', 'hakan', 1967, 'ateşe', 'züleyha', 'afrika\r\n', 7000, '0494-546-44-32', 10011064000),
(1969, 'elektrik-elektronik müh.', 'samet', 1969, 'muhasebeci', 'nurcan', 'bahçelievler/istanbul\r\n', 5000, '0536-777-54-32', 10011095000);




