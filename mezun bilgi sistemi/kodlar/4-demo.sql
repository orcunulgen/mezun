INSERT INTO `additional_info` (`text`, `user_tcno`) VALUES
('Müzik yapmayı çok seviyorum,10 yıldan fazladır gitar ve keman çalıyorum...', 9011003000),
('Çok sigara içiyorum sanırım...', 9011061000);

--
-- Tablo döküm verisi `announcement`
--

INSERT INTO `announcement` (`id`, `description`, `poster_path`, `registered_date`, `title`, `announcement_type_id`, `user_tcno`) VALUES
(1, 'sınavlar ertelendi', '1370207238558.jpg', '2013-06-03 00:07:18', 'Final sınavı tarihleri', 2, 9011003000),
(2, 'yine yarışmada 1.olduk', '1370207305203.jpg', '2013-06-03 00:08:25', 'Bölüm başarıları', 1, 9011003000),
(3, '3.sınıf öğrencisi olan ,java enterprise uygulamalar hakkında staj yapmak isteyenler aşağıdaki adrese', '1370208645874.jpg', '2013-06-03 00:30:45', 'ibm stajer alacak', 4, 9011061000),
(4, 'Oracle DB uzmanı aranıyor', '1370209165379.jpg', '2013-06-03 00:39:25', 'İş arayanlar', 3, 9011062000),
(5, 'd111 de cüzdan bulundu sahibi olan arkadaş blöüm sekreterliğine uğrasın', '1370209742419.jpg', '2013-06-03 00:49:02', 'Cüzdan düşürülmüş', 2, 10011013000),
(6, 'Besiktas kampusunun kapıları kapalı,onceliginiz burası olmasın', '1370212240087.jpg', '2013-06-03 01:30:40', 'YTUde olay var', 1, 10011032000),
(7, 'Programlamaya giriÅ dersinin notlarÄ± fakulte fotokopisindedir', '1370213303390.jpg', '2013-06-03 01:48:23', 'Bolum', 2, 10011041000),
(8, 'Sınav sonucları yıldız maillerinize gonderilmistir,kontrol ediniz', '1370214078935.jpg', '2013-06-03 02:01:18', 'Lineer cebir sınav sonucları', 2, 10011062000),
(9, 'd007 sınıfta telefon bulundu', '1370220217643.jpg', '2013-06-03 03:43:37', 'Telefon bulundu', 2, 10011064000);

--
-- Tablo döküm verisi `area_of_interest`
--

INSERT INTO `area_of_interest` (`id`, `area_of_interest_name`, `area_of_interest_title`, `experience_level`, `user_tcno`) VALUES
(1, 'Java enterprise application', 'Java', 8, 9011003000),
(2, 'operation system and socket programming', 'C', 7, 9011003000),
(3, 'pascalda yazan bi ben kaldım', 'pascal', 8, 9011061000),
(4, 'mobil uygulamalar', 'Android', 3, 9011062000),
(5, '16f877 ccs c developer', 'Mikroişlemciler', 6, 9011062000),
(6, 'CCS C ', 'Gömülü Sistemler', 7, 10011013000),
(7, 'sistem programlama', 'Fortran', 5, 10011032000),
(8, '2010 Uludağ kayak şampiyonu', 'Kayak', 10, 10011062000),
(9, 'c# .Net developer', 'c#', 7, 10011064000);

--
-- Tablo döküm verisi `certification`
--

INSERT INTO `certification` (`id`, `certificate_date`, `certificate_name`, `description`, `file_path`, `institution`, `user_tcno`) VALUES
(1, '2013-01-01 00:00:00', 'IT Essential', 'temel seviye teknik bilgilendir', '1370207745793.pdf', 'cisco', 9011003000),
(2, '2013-05-01 00:00:00', 'Beden dili', 'Beden dilini kullanma', '1370212587166.jpg', 'Bilge adam', 10011032000),
(3, '2013-06-01 00:00:00', 'Fortran', 'Fortran uzerine yazilim sertifikası', '1370213592229.jpg', 'Bilge adam', 10011041000),
(4, '2013-05-08 00:00:00', 'Yabancı dil', 'Ingilizce Intermediate level', '1370214267190.jpg', 'British English', 10011062000);
--
-- Tablo döküm verisi `chat_list`
--

INSERT INTO `chat_list` (`id`, `user_tcno`) VALUES
(1, 9011003000),
(3, 9011061000),
(2, 9011062000),
(4, 10011013000),
(5, 10011032000),
(6, 10011041000),
(7, 10011062000),
(8, 10011064000);

--
-- Tablo döküm verisi `chat_group`
--

INSERT INTO `chat_group` (`id`, `group_name`, `chat_list_id`) VALUES
(1, 'Bölüm Arkadaşlarım', 1),
(2, 'Bölüm Hocalarım', 1),
(3, 'arkadaşlarım', 2),
(4, 'hocalarım', 2),
(5, 'bölümden elemanlar', 3),
(6, 'kankalarım', 4),
(7, 'sevdiğim hocalarım', 4),
(8, 'Bolum', 5),
(9, 'okul', 6),
(10, 'Bolum', 7),
(11, 'arkiler', 8);


--
-- Tablo döküm verisi `chat_person`
--

INSERT INTO `chat_person` (`id`, `activation`, `registered_date`, `chat_group_id`, `user_tcno`) VALUES
(4, 0, '2013-05-18 23:21:16', 1, 9011062000),
(5, 0, '2013-05-18 23:21:18', 1, 10011032000),
(6, 0, '2013-05-18 23:21:19', 1, 10011041000),
(7, 0, '2013-05-18 23:22:11', 1, 10011064000),
(8, 0, '2013-05-18 23:22:21', 1, 10011062000),
(9, 0, '2013-05-18 23:22:50', 1, 10011013000),
(16, 0, '2013-05-18 23:33:59', 2, 11111111111),
(18, 0, '2013-05-18 23:34:28', 2, 22222222222),
(19, 0, '2013-05-18 23:34:41', 2, 33333333333),
(20, 0, '2013-05-18 23:38:01', 3, 9011003000),
(21, 0, '2013-05-18 23:38:06', 3, 10011062000),
(22, 0, '2013-05-18 23:38:10', 3, 10011032000),
(23, 0, '2013-05-18 23:38:25', 4, 33333333333),
(24, 0, '2013-05-18 23:38:32', 4, 11111111111),
(25, 0, '2013-05-20 17:37:39', 2, 44444444444),
(26, 0, '2013-05-20 17:38:11', 1, 10011095000),
(27, 0, '2013-05-20 17:40:19', 1, 9011061000),
(28, 0, '2013-06-03 00:32:42', 5, 9011003000),
(29, 0, '2013-06-03 00:32:56', 5, 10011013000),
(30, 0, '2013-06-03 00:50:04', 6, 9011003000),
(31, 0, '2013-06-03 00:50:11', 6, 9011061000),
(32, 0, '2013-06-03 00:50:34', 7, 11111111111),
(33, 0, '2013-06-03 00:50:48', 7, 33333333333),
(34, 0, '2013-06-03 01:32:10', 8, 10011064000),
(35, 0, '2013-06-03 01:32:17', 8, 10011041000),
(36, 0, '2013-06-03 01:32:30', 8, 10011062000),
(37, 0, '2013-06-03 01:51:52', 9, 10011064000),
(38, 0, '2013-06-03 01:52:01', 9, 10011032000),
(39, 0, '2013-06-03 01:52:17', 9, 10011062000),
(40, 0, '2013-06-03 02:04:53', 10, 10011064000),
(41, 0, '2013-06-03 02:04:59', 10, 10011041000),
(42, 0, '2013-06-03 02:05:28', 10, 10011032000),
(43, 0, '2013-06-03 03:44:59', 11, 10011041000),
(44, 0, '2013-06-03 03:45:05', 11, 10011062000),
(45, 0, '2013-06-03 03:45:13', 11, 10011032000);

--
-- Tablo döküm verisi `classical_cv`
--

INSERT INTO `classical_cv` (`cv_path`, `user_tcno`) VALUES
('1370207658411.doc', 9011003000);

--
-- Tablo döküm verisi `education_feedback`
--


--
-- Tablo döküm verisi `education_info`
--

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

--
-- Tablo döküm verisi `event`
--

INSERT INTO `event` (`id`, `description`, `end_date`, `poster_path`, `registered_date`, `start_date`, `title`, `user_tcno`) VALUES
(1, 'd111 nolu sınıftayız', '2013-06-05 00:00:00', '1370207388756.jpg', '2013-06-03 00:09:48', '2013-06-04 00:00:00', 'Pilav Günü', 9011003000),
(2, 'Tüm türkiye destek oluyor biz de olalım,arkadaşlarımızı bilinçlendirelim....', '2013-06-06 00:00:00', '1370207452258.jpg', '2013-06-03 00:10:52', '2013-06-05 00:00:00', 'Direnişe Destek', 9011003000),
(3, 'biraz oyun oynayalım artık', '2013-06-06 00:00:00', '1370208435810.jpg', '2013-06-03 00:27:15', '2013-06-04 00:00:00', 'ps3 etkinliği', 9011061000),
(4, 'hadi gelin havamızı atalım', '2013-06-05 00:00:00', '1370208482440.jpg', '2013-06-03 00:28:02', '2013-06-04 00:00:00', 'erasmuslular toplanıyor', 9011061000),
(5, 'Kült filmler bizi bekliyor gelin sabaha kadar izleyelim ;)', '2013-06-05 00:00:00', '1370209077748.jpg', '2013-06-03 00:37:57', '2013-06-04 00:00:00', 'Film izleme etkinliği', 9011062000),
(6, 'Sektörden uzman mühendisler geliyor,kaçırmayın', '2013-06-08 00:00:00', '1370209687422.jpg', '2013-06-03 00:48:07', '2013-06-05 00:00:00', 'Java günleri', 10011013000),
(7, 'Format atma', '2013-06-06 00:00:00', '1370212163794.jpg', '2013-06-03 01:29:23', '2013-06-05 00:00:00', 'Bilgisayar semineri', 10011032000),
(8, 'Izci kampina gidiyoruz', '2013-06-30 00:00:00', '1370213211079.jpg', '2013-06-03 01:46:51', '2013-06-22 00:00:00', 'izci Kampi', 10011041000),
(9, 'Ytu Besiktas kampusu', '2013-06-21 00:00:00', '1370214026681.jpg', '2013-06-03 02:00:26', '2013-06-20 00:00:00', 'Sıla konseri', 10011062000),
(10, 'Beşiktaşa desteğe gidiyoruz', '2013-06-05 00:00:00', '1370220146548.jpg', '2013-06-03 03:42:26', '2013-06-04 00:00:00', 'diren gezi parkı', 10011064000);

--
-- Tablo döküm verisi `foreign_language`
--

INSERT INTO `foreign_language` (`id`, `reading_rank`, `registered_date`, `speaking_rank`, `writing_rank`, `language_id`, `user_tcno`) VALUES
(1, 7, '2013-06-03 00:13:21', 3, 5, 2, 9011003000),
(2, 2, '2013-06-03 00:13:36', 4, 3, 3, 9011003000),
(3, 8, '2013-06-03 00:35:16', 8, 8, 2, 9011061000),
(4, 4, '2013-06-03 00:35:25', 6, 5, 5, 9011061000),
(5, 8, '2013-06-03 00:45:34', 5, 8, 2, 9011062000),
(6, 10, '2013-06-03 00:51:52', 10, 10, 2, 10011013000),
(7, 3, '2013-06-03 01:34:29', 1, 2, 4, 10011032000),
(8, 4, '2013-06-03 02:03:25', 3, 3, 2, 10011062000),
(9, 10, '2013-06-03 03:46:01', 10, 10, 2, 10011064000);

--
-- Tablo döküm verisi `high_school`
--

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

--
-- Tablo döküm verisi `job_experience`
--

INSERT INTO `job_experience` (`id`, `company_name`, `description`, `end_date`, `registered_date`, `start_date`, `city_id`, `country_code`, `position_id`, `sector_id`, `user_tcno`, `working_type_id`) VALUES
(1, 'arneca teknoloji', 'java developer ,soap ve restful webservislerin implementasyonu', '2013-01-01 00:00:00', '2013-06-03 00:23:37', '2012-10-02 00:00:00', 3357, 'TUR', 7, 2, 9011003000, 1),
(2, 'Prime teknoloji', 'java enterprise applications', NULL, '2013-06-03 00:25:32', '2013-03-13 00:00:00', 3357, 'TUR', 5, 2, 9011003000, 1),
(3, 'XY Danışmanlık', 'web projeleri geliştiriyoruz', NULL, '2013-06-03 00:44:00', '2013-06-02 00:00:00', 3358, 'TUR', 1, 1, 9011061000, 1),
(4, 'arox bilişim', 'sanal gerçeklik', NULL, '2013-06-03 00:46:33', '2013-06-02 00:00:00', 3357, 'TUR', 7, 4, 9011062000, 3),
(5, 'İTÜ', 'donanım stajı', '2013-03-06 00:00:00', '2013-06-03 00:53:27', '2013-01-02 00:00:00', 3357, 'TUR', 8, 3, 10011013000, 3),
(6, 'Aselsan', 'Java Programlama', NULL, '2013-06-03 01:33:54', '2013-03-05 00:00:00', 3358, 'TUR', 7, 5, 10011032000, 3),
(7, 'Bilge adam', 'IT stajyer', NULL, '2013-06-03 01:49:31', '2013-05-14 00:00:00', 3357, 'TUR', 7, 2, 10011041000, 3),
(8, 'Microsoft TR', 'c# developer', '2013-06-02 00:00:00', '2013-06-03 03:47:38', '2013-04-01 00:00:00', 3359, 'TUR', 7, 2, 10011064000, 3);
--
-- Tablo döküm verisi `photo_album`
--

INSERT INTO `photo_album` (`id`, `album_name`, `date`, `description`, `place`, `user_tcno`) VALUES
(1, 'albüm2013', '2013-06-03 00:12:07', '', '', 9011003000),
(2, 'albüm2013-2', '2013-06-03 00:12:20', '', '', 9011003000),
(3, 'barcelona', '2013-06-03 00:31:40', '', '', 9011061000),
(4, 'yurttan kareler', '2013-06-03 00:39:46', '', '', 9011062000),
(5, 'çiçekler', '2013-06-03 00:49:19', '', '', 10011013000),
(6, 'Marmaris', '2013-06-03 01:31:17', '', '', 10011032000),
(7, 'Isyeri', '2013-06-03 01:50:18', '', '', 10011041000),
(8, 'Istanbul', '2013-06-03 02:01:39', '', '', 10011062000),
(9, 'manzara', '2013-06-03 03:43:59', '', '', 10011064000);

--
-- Tablo döküm verisi `photo`
--

INSERT INTO `photo` (`id`, `date`, `description`, `photo_path`, `place`, `album_id`) VALUES
(1, '2013-06-03 00:12:34', '', '1370207554972.jpg', '', 1),
(2, '2013-06-03 00:12:51', '', '1370207571841.jpg', '', 2),
(3, '2013-06-03 00:19:43', 'burası kadıköy', '1370207983265.jpg', '', 1),
(4, '2013-06-03 00:32:08', '', '1370208728037.jpg', '', 3),
(5, '2013-06-03 00:40:01', '', '1370209201953.jpg', '', 4),
(6, '2013-06-03 00:49:37', '', '1370209777613.jpg', '', 5),
(7, '2013-06-03 01:31:37', 'Tatil', '1370212297007.jpg', '', 6),
(8, '2013-06-03 01:50:38', 'isyeri', '1370213438957.jpg', '', 7),
(9, '2013-06-03 02:01:55', 'Bogaz', '1370214115769.jpg', '', 8),
(10, '2013-06-03 03:44:26', 'ne güzel yaaa', '1370220266584.jpg', '', 9);


--
-- Tablo döküm verisi `post_history`
--

INSERT INTO `post_history` (`id`, `content_id`, `content_type`, `description`, `published_date`, `user_tcno`) VALUES
(1, 1, 'ANNOUNCEMENT', 'müjdeeee', '2013-06-03 00:08:44', 9011003000),
(2, 2, 'ANNOUNCEMENT', '', '2013-06-03 00:08:58', 9011003000),
(3, 2, 'EVENT', 'Herkesi bekleriz....', '2013-06-03 00:11:23', 9011003000),
(4, 1, 'EVENT', 'Hadi gelin de yiyelim', '2013-06-03 00:11:44', 9011003000),
(5, NULL, 'TEXT', 'Birşeyler paylaşsam mı...', '2013-06-03 00:18:19', 9011003000),
(6, 3, 'PHOTO', 'biz türkiyenin arkasındayız', '2013-06-03 00:19:43', 9011003000),
(7, 3, 'EVENT', 'Sonra bir de counter döneriz...', '2013-06-03 00:28:19', 9011061000),
(8, 4, 'EVENT', 'come on!!!', '2013-06-03 00:28:48', 9011061000),
(9, 3, 'ANNOUNCEMENT', '2013 Staj ilanı', '2013-06-03 00:31:02', 9011061000),
(10, 4, 'PHOTO', 'holaaaa', '2013-06-03 00:32:08', 9011061000),
(11, NULL, 'TEXT', 'Beşiktaşa destek bekliyoruz...', '2013-06-03 00:36:34', 9011061000),
(12, 5, 'EVENT', 'Filmmmmm', '2013-06-03 00:38:11', 9011062000),
(13, 4, 'ANNOUNCEMENT', 'İş arayanlar buraya...', '2013-06-03 00:39:25', 9011062000),
(14, 6, 'EVENT', '', '2013-06-03 00:48:10', 10011013000),
(15, 5, 'ANNOUNCEMENT', 'DİKKAT!!', '2013-06-03 00:49:02', 10011013000),
(16, NULL, 'TEXT', 'Türkiye''nin arkasındayız', '2013-06-03 01:28:11', 10011032000),
(17, 7, 'EVENT', 'Davetlisiniz', '2013-06-03 01:29:23', 10011032000),
(18, 6, 'ANNOUNCEMENT', '', '2013-06-03 01:30:40', 10011032000),
(19, NULL, 'TEXT', 'Twitter''ın onemi cok iyi kavranmalı', '2013-06-03 01:40:57', 10011041000),
(20, 8, 'EVENT', '', '2013-06-03 01:46:51', 10011041000),
(21, 7, 'ANNOUNCEMENT', '', '2013-06-03 01:48:23', 10011041000),
(22, NULL, 'TEXT', 'Yıldızın son günlerdeki parlak yukselisi', '2013-06-03 01:59:01', 10011062000),
(23, 9, 'EVENT', 'Eglenceye hazır mıyız?', '2013-06-03 02:00:26', 10011062000),
(24, 8, 'ANNOUNCEMENT', '', '2013-06-03 02:01:18', 10011062000),
(25, NULL, 'TEXT', 'offf çok yoruldum', '2013-06-03 03:41:36', 10011064000),
(26, 10, 'EVENT', 'hadi herkes sokaklara', '2013-06-03 03:42:26', 10011064000),
(27, 9, 'ANNOUNCEMENT', 'Telefon kaybeden var mı?', '2013-06-03 03:43:37', 10011064000),
(28, 10, 'PHOTO', 'Yeni evim ;)', '2013-06-03 03:44:26', 10011064000);
