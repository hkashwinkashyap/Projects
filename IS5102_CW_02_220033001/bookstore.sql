-- Enforcing the foreign key constraints.
PRAGMA foreign_keys = TRUE;


--------------------------------------------------------------------
-- Dropping the tables following the foreign key constraints.
--------------------------------------------------------------------
-- Uncomment the following if trying to re-run.
/*
 DROP table supplier_supplies;
 DROP table supplier_phone;
 DROP table supplier;
 DROP table review;
 DROP table order_contains;
 DROP table genre;
 DROP table edition;
 DROP table book_order;
 DROP table book;
 DROP table customer_address;
 DROP table customer_phone;
 DROP table customer;
 */
--------------------------------------------------------------------
-- Declaring the tables for the bookstore.
--------------------------------------------------------------------


CREATE TABLE customer (
	customer_id			VARCHAR (10),
	first_name			VARCHAR(20) NOT NULL,
	last_name			VARCHAR(20) NOT NULL,
	email				VARCHAR(20) NOT NULL,
	PRIMARY KEY 		(customer_id));

CREATE TABLE customer_phone (
	phonenumber			VARCHAR(10),
	customer_id			VARCHAR(10),
	phone_type			VARCHAR(6) NOT NULL,
	PRIMARY KEY 		(phonenumber),
	FOREIGN KEY 		(customer_id) REFERENCES customer
		ON DELETE CASCADE);


CREATE TABLE customer_address (
	customer_id			VARCHAR(10),
	street				VARCHAR(20) NOT NULL,
	city				VARCHAR(10) NOT NULL,
	post_code			VARCHAR(10) NOT NULL,
	country				VARCHAR(20) NOT NULL,
	PRIMARY KEY 		(customer_id) 
	FOREIGN KEY 		(customer_id) REFERENCES customer
		ON DELETE CASCADE);

	
CREATE TABLE book (
	book_id				VARCHAR(20),
	title				VARCHAR(20) NOT NULL,
	author_name			VARCHAR(40) NOT NULL,
	publisher			VARCHAR(30) NOT NULL,
	PRIMARY KEY			(book_id));

	
CREATE TABLE genre (
	book_id				VARCHAR(20),
	genre_name			VARCHAR(30),
	PRIMARY KEY			(book_id, genre_name),
	FOREIGN KEY 		(book_id) REFERENCES book
		ON DELETE CASCADE);

	
CREATE TABLE review (
	customer_id			VARCHAR(10),
	book_id				VARCHAR(20),
	rating				NUMERIC(1,0),
	PRIMARY KEY			(customer_id, book_id),
	FOREIGN KEY			(customer_id) REFERENCES customer,
	FOREIGN KEY			(book_id)     REFERENCES book,
						CONSTRAINT rating_range
						CHECK (rating BETWEEN 1 AND 5));
					
	
CREATE TABLE book_order (
	order_id			VARCHAR(10),
	customer_id			VARCHAR(10) NOT NULL,
	street				VARCHAR(20) NOT NULL,
	city				VARCHAR(10) NOT NULL,
	post_code			VARCHAR(10) NOT NULL,
	country				VARCHAR(20) NOT NULL,
	date_ordered		TEXT NOT NULL,
	date_delivered		TEXT NOT NULL,
	PRIMARY KEY			(order_id),
	FOREIGN KEY			(customer_id) REFERENCES customer
		ON DELETE CASCADE);

	
CREATE TABLE edition (
	book_id				VARCHAR(20),
	book_edition		NUMERIC(4, 0),
	edition_type		VARCHAR(20),
	order_id			VARCHAR(10)  NOT NULL,
	price				NUMERIC(5,2) NOT NULL,
	quantity_in_stock	VARCHAR(10)  NOT NULL,
	PRIMARY KEY			(book_id, book_edition, edition_type),
	FOREIGN KEY			(book_id)  REFERENCES book,
	FOREIGN KEY			(order_id) REFERENCES book_order
		ON DELETE CASCADE);

	
CREATE TABLE order_contains (
	order_id			VARCHAR(10),
	book_id				VARCHAR(20)  NOT NULL,
	book_edition		NUMERIC(4, 0)  NOT NULL,
	edition_type		VARCHAR(20)  NOT NULL,
	amount				NUMERIC(5,0) NOT NULL,
	PRIMARY KEY 		(order_id),
	FOREIGN KEY 		(order_id) REFERENCES book_order,
	FOREIGN KEY 		(book_id)  REFERENCES book
		ON UPDATE CASCADE
		ON DELETE CASCADE);

	
CREATE TABLE supplier (
	supplier_id			VARCHAR(10),
	first_name			VARCHAR(20) NOT NULL,
	last_name			VARCHAR(20) NOT NULL,
	account_number		VARCHAR(20) NOT NULL,
	PRIMARY KEY 		(supplier_id));

	
CREATE TABLE supplier_phone (
	phonenumber			VARCHAR(10),
	supplier_id			VARCHAR(10) NOT NULL,
	PRIMARY KEY			(phonenumber),
	FOREIGN KEY			(supplier_id) REFERENCES supplier
		ON DELETE CASCADE);
		

CREATE TABLE supplier_supplies (
	supplier_id			VARCHAR(10),
	book_id				VARCHAR(20),
	book_edition		NUMERIC(4, 0),
	edition_type		VARCHAR(20),
	supply_price		NUMERIC(4,2) NOT NULL,
	PRIMARY KEY 		(supplier_id, book_id, book_edition, edition_type),
	FOREIGN KEY			(supplier_id) REFERENCES supplier,
	FOREIGN KEY			(book_id)     REFERENCES book
		ON UPDATE CASCADE
		ON DELETE CASCADE);
	

--------------------------------------------------------------------
-- Populating the above tables with test data.
--------------------------------------------------------------------


INSERT INTO customer
VALUES	('0000000001', 'Pawan', 'Kalyan', 'pspk@ok.com'), 
		('0000000002', 'Trivikram', 'Srinivas', 'guruji@ok.com'),
		('0000000003', 'Nanda', 'Gopal', 'athadu@ok.com'),
		('0000000004', 'Rama', 'Rao', 'ntr@ok.com'),
		('0000000005', 'Nageshwar', 'Rao', 'anr@ok.com'),
		('0000000006', 'Ravi', 'Chintakayala', 'rchinta@ok.com'),
		('0000000007', 'Sanjay', 'Saahu', 'jalsa@ok.com'),
		('0000000008', 'Goutham', 'Nanda', 'attarintikidaredi@ok.com'),
		('0000000009', 'Anand', 'Vihari', 'aaa@ok.com'),
		('0000000010', 'Raghava', 'Veera', 'aravindhasametha@ok.com');
	
INSERT INTO customer_phone
VALUES	('1234567890', '0000000001', 'Mobile'),
		('1234567891', '0000000002', 'Home'),
		('1234567892', '0000000003', 'Mobile'),
		('1234567893', '0000000004', 'Mobile'),
		('1234567894', '0000000005', 'Mobile'),
		('1234567895', '0000000006', 'Personal'),
		('1234567896', '0000000007', 'Mobile'),
		('1234567897', '0000000008', 'Mobile'),
		('1234567898', '0000000009', 'Phone'),
		('1234567899', '0000000010', 'Mobile');
	
INSERT INTO customer_address
VALUES	('0000000001', 'street 1', 'Edinburgh', 'edinb1', 'Scotland'),
		('0000000002', 'street 2 new', 'Glasgow', 'glasg0', 'Scotland'),
		('0000000003', '3rd street', 'St Andrews', 'ky16ss', 'Scotland'),
		('0000000004', 'street no. 4', 'Hyderabad', '500035', 'India'),
		('0000000005', '5th street', 'Mysore', '570008', 'India'),
		('0000000006', '6 no. street', 'Edinburgh', 'eding3', 'Scotland'),
		('0000000007', '6 street old', 'St Andrews', 'standr1', 'Scotland'),
		('0000000008', 'new avenue', 'London', 'lon1', 'England'),
		('0000000009', 'street 9 old', 'Dundee', 'dund2', 'Scotland'),
		('0000000010', 'street 9 new', 'London', 'lon4', 'England');
	
INSERT INTO book
VALUES	('1', 'Earth Metals', 'Frank Leo', 'Ultimate Books'),
		('2', 'Samurai', 'Zhen Lee', 'Classic Publisher'),
		('3', 'Neptune', 'Karls James', 'Ultimate Books'),
		('4', 'Chips', 'Chegovera Thomas', 'True Prints'),
		('5', 'Nanos', 'Charles Sobhraj, Inkodu Evaro', 'Authentic Books'),
		('6', 'Helium O', 'Samara Veera', 'Only Publisher'),
		('7', 'Super', 'Evaro Idi', 'True Prints'),
		('8', 'Zero', 'Telusuga Manamani', 'Ultimate Books'),
		('9', 'No', 'Hummus Chil', 'True Prints'),
		('10', 'Ladder', 'Chris Schok', 'Only Publisher');
	
INSERT INTO genre
VALUES	('1', 'Science Fiction'),
		('2', 'Drama'),
		('3', 'Science Fiction'),
		('4', 'Science and Technology'),
		('5', 'Science and Technology'),
		('6', 'Science Fiction'),
		('7', 'Science and Technology'),
		('8', 'Science Fiction'),
		('9', 'Self Help'),
		('10', 'Business');
	
INSERT INTO review
VALUES	('0000000001', '1', 4),
		('0000000002', '1', 4),
		('0000000003', '5', 4),
		('0000000004', '5', 3),
		('0000000006', '8', 2),
		('0000000008', '7', 3),
		('0000000009', '4', 4),
		('0000000002', '4', 1),
		('0000000001', '4', 5),
		('0000000005', '1', 4);
	
INSERT INTO book_order
VALUES	('1', '0000000001', 'street 1', 'Edinburgh', 'edinb1', 'Scotland', '2019-12-20', '2019-12-24'),
		('2', '0000000006', '6 no. street', 'Edinburgh', 'eding3', 'Scotland', '2020-01-20', '2020-01-24'),
		('3', '0000000004', 'street no. 4', 'Hyderabad', '500035', 'India', '2020-01-21', '2020-02-24'),
		('4', '0000000003', '3rd street', 'St Andrews', 'ky16ss', 'Scotland', '2020-08-13', '2020-08-13'),
		('5', '0000000004', 'street no. 4', 'Hyderabad', '500035', 'India', '2020-10-20', '2020-10-24'),
		('6', '0000000004', 'street 1', 'Hyderabad', '500024', 'India', '2020-11-20', '2020-11-24'),
		('7', '0000000001', 'street 1', 'Edinburgh', 'edinb1', 'Scotland', '2021-02-20', '2021-02-24'),
		('8', '0000000008', 'new avenue', 'London', 'lon1', 'England', '2021-09-20', '2021-09-24'),
		('10', '0000000001', 'street 1', 'Edinburgh', 'edinb1', 'Scotland', '2022-01-20', '2022-01-24'),
		('11', '0000000004', 'street no. 4', 'Hyderabad', '500035', 'India', '2020-10-21', '2020-10-24'),
		('9', '0000000001', 'street 1', 'Edinburgh', 'edinb1', 'Scotland', '2022-04-20', '2022-04-24');
	
INSERT INTO edition
VALUES	('1', 1, 'Hard Cover', '1', 39.99, 3),
		('2', 4, 'Paper Back', '2', 20, 5),
		('3', 10, 'Hard Cover', '3', 10, 2),
		('4', 9, 'Hard Cover', '4', 39.99, 50),
		('5', 2, 'Audio Book', '5', 39.99, 50),
		('6', 1, 'Hard Cover', '6', 39.99, 50),
		('7', 3, 'Hard Cover', '7', 4.90, 50),
		('8', 2, 'Paper Back', '8', 39.99, 50),
		('9', 1, 'Hard Cover', '9', 8.00, 1),
		('10', 1, 'Hard Cover', '10', 39.99, 50);
	
INSERT INTO order_contains
VALUES	('1', '1', 1, 'Hard Cover', 39.99),
		('2', '2', 4, 'Paper Back', 20),
		('3', '3', 10, 'Hard Cover', 10),
		('4', '4', 9, 'Hard Cover', 39.99),
		('5', '5', 2, 'Audio Book', 39.99),
		('11', '5', 2, 'Audio Book', 39.99),
		('6', '6', 1, 'Hard Cover', 39.99),
		('7', '7', 3, 'Hard Cover', 4.90),
		('8', '8', 2, 'Paper Back', 39.99),
		('9', '9', 1, 'Hard Cover', 8.00),
		('10', '10', 1, 'Hard Cover', 39.99);

	
INSERT INTO supplier
VALUES	('1a', 'Rehman', 'Shekar', 'ab12'),
		('1b', 'Shaik', 'Md', 'ab13'),
		('1c', 'Ranga', 'Rao', 'ab14'),
		('1d', 'Pandu', 'Gadu', 'ab15'),
		('1e', 'Pratha', 'Saradhi', 'ab16'),
		('1f', 'Narasimha', 'Naidu', 'ab17'),
		('1g', 'Chennakeshava', 'Reddy', 'ab18'),
		('1h', 'Mike', 'Shaw', 'ab19'),
		('1i', 'David', 'Russell', 'ab20'),
		('1j', 'Basha', 'Manikchand', 'ab21');
	
INSERT INTO supplier_phone
VALUES	('123456780', '1a'),
		('123456781', '1b'),
		('123456782', '1c'),
		('123456783', '1d'),
		('123456784', '1e'),
		('123456785', '1f'),
		('123456786', '1g'),
		('123456787', '1h'),
		('123456788', '1i'),
		('123456789', '1j');
	
INSERT INTO supplier_supplies
VALUES	('1a', '1', 1, 'Hard Cover', 19.99),
		('1b', '2', 4, 'Paper Back', 10),
		('1c', '3', 10, 'Hard Cover', 5),
		('1d', '1', 9, 'Hard Cover', 14.99),
		('1e', '5', 2, 'Audio Book', 19.99),
		('1f', '6', 1, 'Hard Cover', 19.99),
		('1g', '1', 3, 'Hard Cover', 12.90),
		('1h', '8', 2, 'Paper Back', 19.99),
		('1i', '9', 1, 'Hard Cover', 4.00),
		('1j', '10', 1, 'Hard Cover', 19.99);
		
	
--------------------------------------------------------------------
-- Queries.
--------------------------------------------------------------------

-- List all books published by “Ultimate Books” which are in the “Science Fiction” genre.
	
SELECT title
	FROM book
	WHERE publisher = 'Ultimate Books'
	AND book.book_id IN (
		SELECT book_id
		FROM genre
		WHERE genre_name = 'Science Fiction');
	
--------------------------------------------------------------------
	
-- List titles and ratings of all books in the “Science and Technology” genre, ordered first by rating (top rated first), and then by the title.
	
SELECT 
	review.rating,
	book.title 
	FROM review
	NATURAL JOIN book
	WHERE book_id IN (
		SELECT book_id 
		FROM genre 
		WHERE genre_name = 'Science and Technology')
	ORDER BY rating DESC, title;
	
--------------------------------------------------------------------

-- List all orders placed by customers with customer address in the city of Edinburgh, since 2020, in chronological order, latest first.

SELECT *
	FROM customer
	NATURAL JOIN customer_address
	NATURAL JOIN book_order
	WHERE book_order.date_ordered > '2019-12-31'
	AND customer_address.city = 'Edinburgh'
	ORDER BY date_ordered DESC;
	
--------------------------------------------------------------------

-- List all book editions which have less than 5 items in stock, together with the name, account number and supply price of the minimum priced supplier for that edition.

SELECT *
	FROM supplier
	NATURAL JOIN edition
	NATURAL JOIN supplier_supplies
	WHERE supplier_supplies.supply_price IN (
		SELECT MIN(supply_price) 
			FROM supplier_supplies
			GROUP BY supplier_id)
		AND edition.quantity_in_stock < 5;
		
--------------------------------------------------------------------

-- Calculate the total value of all audiobook sales since 2020 for each publisher.
	
SELECT book.publisher, SUM(order_contains.amount) 
	FROM book
	NATURAL JOIN order_contains
	NATURAL JOIN book_order
	WHERE order_contains.edition_type = 'Audio Book'
	AND book_order.date_ordered > '2019-12-31'
	GROUP by book.publisher;
	
--------------------------------------------------------------------

-- Calculate the total number of books ordered in 'Science Fiction' genre.

SELECT COUNT(*), genre.genre_name
	FROM genre
	NATURAL JOIN order_contains
	WHERE order_contains.book_id = genre.book_id AND genre.genre_name = 'Science Fiction';
	
--------------------------------------------------------------------

-- List all the phone numbers and customer names that are living in 'Hyderabad' city.

SELECT customer_phone.phonenumber,
	customer.first_name, customer.last_name
	FROM customer
	NATURAL JOIN customer_address
	NATURAL JOIN customer_phone
	WHERE customer_address.city = 'Hyderabad';
	
--------------------------------------------------------------------

-- List all the orders delivered in less than 4 days.

SELECT order_id, JULIANDAY(date_delivered) - JULIANDAY(date_ordered) AS d 
	FROM book_order
	WHERE d < 4;
	
--------------------------------------------------------------------

-- Give the name of the author and book title along with the number of books sold with book_id '5'.

SELECT book.author_name,
	book.title,
	COUNT(order_contains.book_id)  
	FROM book
	NATURAL JOIN order_contains
	WHERE order_contains.book_id = '5';


--------------------------------------------------------------------
-- Views.
--------------------------------------------------------------------

-- View for all the details related to Supplier and the books they supply.

CREATE VIEW supplier_supplying_books AS
	SELECT *
	FROM supplier
	NATURAL JOIN supplier_phone
	NATURAL JOIN supplier_supplies
	NATURAL JOIN book;

-- List all the details of the supplier and books they supply with supplier_id '1g'.
	
SELECT * FROM supplier_supplying_books
	WHERE supplier_id = '1g';
	
--------------------------------------------------------------------

-- View for all the details of the customers with their email and phone numbers who have rated atleast one book along with the genres.

CREATE VIEW customer_reviewed AS
	SELECT *
	FROM customer
	NATURAL JOIN customer_phone
	NATURAL JOIN review
	NATURAL JOIN genre;

-- List all the customers with their phonenumbers and email who have reviewed for genre 'Science Fiction'.

SELECT *
	FROM customer_reviewed 
	WHERE genre_name = 'Science Fiction';
	
--------------------------------------------------------------------

-- View for the time taken to deliver all the orders.

CREATE VIEW delivery_time AS
	SELECT order_id, JULIANDAY(date_delivered) - JULIANDAY(date_ordered) AS d 
	FROM book_order;
	
-- List the delivery time taken for all the orders.

SELECT *
 FROM delivery_time;
 
--------------------------------------------------------------------
