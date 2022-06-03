





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


drop trigger remove_irrelevant_forecast on Forecast;



