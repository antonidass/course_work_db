CREATE TABLE IF NOT EXISTS Indicators (
    id serial primary key ,
    price real,
    market_cap real,
    income int,
    revenue bigint
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
    goal_price real,
    forecast varchar,
    description varchar,
    company_id int not null,
    FOREIGN KEY (company_id) REFERENCES Company(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS News (
    id serial primary key,
    title varchar,
    date_public timestamp,
    content varchar,
    url varchar,
    author varchar,
    company_id int not null,
    FOREIGN KEY (company_id) REFERENCES Company(id) ON DELETE CASCADE
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

CREATE TABLE IF NOT EXISTS Users_roles (
   user_id bigint not null ,
   role_id bigint not null ,
   primary key (user_id, role_id),
   foreign key (user_id) references users (id),
   foreign key (role_id) references roles (id)
);

\copy Indicators FROM '/Users/akrik/Desktop/курсач бд/database/data_gen/data/indicators.csv' DELIMITER ';' CSV;
\copy Company FROM '/Users/akrik/Desktop/курсач бд/database/data_gen/data/company.csv' DELIMITER ';' CSV;
\copy Forecast FROM '/Users/akrik/Desktop/курсач бд/database/data_gen/data/forecasts.csv' DELIMITER ';' CSV;
\copy News FROM '/Users/akrik/Desktop/курсач бд/database/data_gen/data/news.csv' DELIMITER ';' CSV;
\copy Users FROM '/Users/akrik/Desktop/курсач бд/database/data_gen/data/users.csv' DELIMITER ';' CSV;
\copy Users_roles FROM '/Users/akrik/Desktop/курсач бд/database/data_gen/data/users_roles.csv' DELIMITER ';' CSV;


CREATE OR REPLACE FUNCTION truncate_tables() RETURNS void AS $$
DECLARE
BEGIN
    TRUNCATE Indicators CASCADE;
    TRUNCATE Users CASCADE;
END;
$$ LANGUAGE plpgsql;


DROP TABLE IF EXISTS Forecast CASCADE ;
DROP TABLE IF EXISTS Indicators CASCADE ;
DROP TABLE IF EXISTS Company CASCADE ;
DROP TABLE IF EXISTS News CASCADE ;
DROP TABLE IF EXISTS  Users CASCADE ;
DROP TABLE IF EXISTS  Users_roles CASCADE ;


INSERT INTO Roles(name) VALUES
('admin'),
('analyst'),
('user_');
