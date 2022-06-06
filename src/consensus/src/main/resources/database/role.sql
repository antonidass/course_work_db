CREATE ROLE admin WITH
LOGIN
PASSWORD 'admin';

GRANT SELECT on indicators to admin;
GRANT SELECT on forecast to admin;
GRANT SELECT on company to admin;
GRANT SELECT on news to admin;
GRANT SELECT, INSERT, UPDATE, DELETE on users to admin;
GRANT SELECT, INSERT, UPDATE, DELETE on roles to admin;
GRANT SELECT, INSERT, UPDATE, DELETE on users_roles to admin;


CREATE ROLE analyst WITH
LOGIN
PASSWORD 'analyst';

GRANT SELECT, INSERT, UPDATE, DELETE on indicators to analyst;
GRANT SELECT, INSERT, UPDATE, DELETE on forecast to analyst;
GRANT SELECT, INSERT, UPDATE, DELETE on company to analyst;
GRANT SELECT, INSERT, UPDATE, DELETE on news to analyst;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO analyst;

CREATE ROLE user_ WITH
LOGIN
PASSWORD 'user';

GRANT SELECT on indicators to user_;
GRANT SELECT on forecast to user_;
GRANT SELECT on company to user_;
GRANT SELECT on news to user_;

REVOKE ALL PRIVILEGES on indicators from user_;
REVOKE ALL PRIVILEGES on company from user_;
REVOKE ALL PRIVILEGES on news from user_;
REVOKE ALL PRIVILEGES on forecast from user_;

REVOKE ALL PRIVILEGES on indicators from admin;
REVOKE ALL PRIVILEGES on company from admin;
REVOKE ALL PRIVILEGES on news from admin;
REVOKE ALL PRIVILEGES on forecast from admin;
REVOKE ALL PRIVILEGES on users_roles from admin;
REVOKE ALL PRIVILEGES on users from admin;
REVOKE ALL PRIVILEGES on roles from admin;

REVOKE ALL PRIVILEGES on indicators from analyst;
REVOKE ALL PRIVILEGES on company from analyst;
REVOKE ALL PRIVILEGES on news from analyst;
REVOKE ALL PRIVILEGES on forecast from analyst;


DROP ROLE admin;
DROP ROLE user_;
DROP ROLE analyst;

