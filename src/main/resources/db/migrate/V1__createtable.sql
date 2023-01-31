
CREATE TABLE `book_tbl` (
  `id` int(11) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `book_name` varchar(255) DEFAULT NULL,
  `cover_image` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `pages` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `category` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `borrowed_book`
--

CREATE TABLE `borrowed_book` (
  `id` binary(16) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `total_price` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_borrow`
--

CREATE TABLE `detail_borrow` (
  `id` int(11) NOT NULL,
  `date_return` datetime DEFAULT NULL,
  `book_id_id` int(11) DEFAULT NULL,
  `borrow_id_id` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `school`
--

CREATE TABLE `school` (
  `id` char(36) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `income` double NOT NULL,
  `school_name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_tbl`
--

CREATE TABLE `user_tbl` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` int(11) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_tbl_books`
--

CREATE TABLE `user_tbl_books` (
  `user_id` int(11) NOT NULL,
  `books_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `book_tbl`
--
ALTER TABLE `book_tbl`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKr0aapgov2wjirvsv5n7emuro4` (`category`);

--
-- Indeks untuk tabel `borrowed_book`
--
ALTER TABLE `borrowed_book`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5c61u427lwoviiyg8ugvirf87` (`user_id`);

--
-- Indeks untuk tabel `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `detail_borrow`
--
ALTER TABLE `detail_borrow`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5e560y466h3ojwjrgx5qtv4mv` (`book_id_id`),
  ADD KEY `FKlreymy6xt6mv1ufmefxhnr8lb` (`borrow_id_id`);

--
-- Indeks untuk tabel `flyway_schema_history`
--


--
-- Indeks untuk tabel `school`
--
ALTER TABLE `school`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `user_tbl`
--
ALTER TABLE `user_tbl`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_i4ygcc30htflmb5xe5mjcydid` (`email`) USING HASH,
  ADD UNIQUE KEY `UK_xkjl2orevvtyrqqshcot355j` (`username`) USING HASH;

--
-- Indeks untuk tabel `user_tbl_books`
--
ALTER TABLE `user_tbl_books`
  ADD UNIQUE KEY `UK_aphusofgbbm59xx89cro2qche` (`books_id`),
  ADD KEY `FKkr8uafacttooar56nnpmav3b6` (`user_id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `book_tbl`
--
ALTER TABLE `book_tbl`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `detail_borrow`
--
ALTER TABLE `detail_borrow`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `user_tbl`
--
ALTER TABLE `user_tbl`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;
