CREATE TABLE IF NOT EXISTS Indicators (
    id serial primary key ,
    price real,
    market_cap int,
    income int,
    revenue int
);

CREATE TABLE IF NOT EXISTS Company (
    id serial primary key,
    name varchar,
    logo varchar,
    description varchar,
    ticker varchar,
    indicators_id int,
    FOREIGN KEY (indicators_id) REFERENCES Indicators(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Forecast (
    id serial primary key ,
    invest_house varchar,
    date_publishing timestamp,
    date_end timestamp,
    date_update timestamp,
    goal_price int,
    forecast varchar,
    description varchar,
    company_id int,
    FOREIGN KEY (company_id) REFERENCES Company(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS News (
    id serial primary key,
    title varchar,
    date_public timestamp,
    content varchar,
    url varchar,
    author varchar
);

CREATE TABLE IF NOT EXISTS Users (
     id bigserial primary key,
     username varchar not null ,
     password varchar,
     email varchar,
     name varchar,
     surname varchar
);

CREATE TABLE IF NOT EXISTS Roles (
     id bigserial primary key,
     name varchar not null
);





DROP TABLE IF EXISTS Forecast;
DROP TABLE IF EXISTS Company;
DROP TABLE IF EXISTS Indicators;
DROP TABLE IF EXISTS News;

INSERT INTO Indicators(PRICE, MARKET_CAP,  INCOME, REVENUE) VALUES
(21312, 3423, 100, 1123);

INSERT INTO Company(name, logo, description, ticker, indicators_id) VALUES
('Zambia', 'adsfasdf', 'xcvcvbcvx', 'cvkmfdks', 1),
('Xerox', 'nbcvfgsddsg', 'aasdfasdfasdllaalla', 'cvkmfdks', 1),
('Microsoft', 'zxczcxvlalal', 'allaallxcxcvbxcvb', 'cvkmfdks', 1);

INSERT INTO Forecast(invest_house, date_publishing, date_end, date_update, goal_price, forecast, description, company_id) VALUES
('Tinkoff', '2022-05-21', '2022-05-25', '2022-05-23', 1300, 'Buy', 'We recommend to buy this sheet', 2),
('Investing', '2022-05-21', '2022-05-25', '2022-05-23', 1300, 'Sell', 'We recommend to buy this sheet', 2),
('Bks', '2022-05-21', '2022-05-25', '2022-05-23', 1300, 'Strong Buy', 'We recommend to buy this sheet', 2),
('Bks', '2022-05-21', '2022-05-25', '2022-05-23', 1300, 'Buy', 'We recommend to buy this sheet', 3);

INSERT INTO News(title, date_public, content, url, author) VALUES
('Amazing company!', '2022-05-05', 'Today we know that apple very good company in whole world but...', 'https:/amazing.com', 'Kristian Roberto'),
('Bad company!', '2021-05-05', 'We know that miscrisoft baddes company in whole world but...', 'https:/amazing.com', 'Ivan Robertov'),
('Very good!', '2022-10-05', 'i think that amazon now a really good service in the world but...', 'https:/amazon.com', 'Kiril Petrov');


DROP TABLE IF EXISTS  Users;


CREATE TABLE IF NOT EXISTS Users_roles (
    user_id bigint not null ,
    role_id bigint not null ,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

INSERT INTO Roles(name) VALUES
('ROLE_USER'),
('ROLE_ADMING');

INSERT INTO Users(username, password, email, name) VALUES
('user', '$2y$10$an2dgz3wGQBdNhR9Tghy3.GVpdyC.cPF5SLkuJoPgclDxCJhBiiuy', 'akrikofff@gmail.com', 'Anton Krikov');

