CREATE OR REPLACE FUNCTION irrelevant_forecast_removing()
RETURNS TRIGGER
LANGUAGE plpgsql AS $$
BEGIN 
    DELETE FROM Forecast
    WHERE date_end < now();
    RETURN null;
END
$$;

CREATE TRIGGER remove_irrelevant_forecast
AFTER UPDATE ON Forecast
FOR EACH ROW
EXECUTE PROCEDURE irrelevant_forecast_removing();


CREATE OR REPLACE FUNCTION add_role()
RETURNS TRIGGER
LANGUAGE plpgsql AS $$
BEGIN
    IF NEW.id IN (SELECT users.id FROM users)
    THEN
        INSERT INTO users_roles(user_id, role_id) VALUES (new.id, 3);
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER add_role_to_new_user
AFTER INSERT ON Users
FOR EACH ROW
EXECUTE PROCEDURE add_role();




CREATE OR REPLACE FUNCTION add_indicators()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE indicators_max_id int;
BEGIN
    indicators_max_id := (select max(id) from indicators) + 1;
    INSERT INTO
            indicators(id, price, market_cap, income, revenue)
            VALUES
            (indicators_max_id, null, null, null, null);
    UPDATE company
    SET indicators_id = indicators_max_id
    WHERE id =new.id;
    RETURN null;
END;
$$;

CREATE TRIGGER add_indicators_for_company
AFTER INSERT ON Company
FOR EACH ROW
EXECUTE PROCEDURE add_indicators();




drop trigger add_indicators_for_company on company;
drop trigger remove_irrelevant_forecast on Forecast;
drop trigger add_role_to_new_user on Users;

