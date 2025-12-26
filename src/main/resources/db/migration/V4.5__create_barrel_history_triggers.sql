--------------------- Триггер на добавление данных-------------------

CREATE OR REPLACE FUNCTION barrel_history_view_insert_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    barrel_exists BOOLEAN;
    drink_exists BOOLEAN;
    error_msg TEXT := 'Не удалось вставить запись. Проблемы:';
BEGIN
    -- Проверка существования barrel_id
    SELECT EXISTS(SELECT 1 FROM barrel WHERE id = NEW.barrel_id)
    INTO barrel_exists;
    
    -- Проверка существования drink_id
    SELECT EXISTS(SELECT 1 FROM drink WHERE id = NEW.drink_id)
    INTO drink_exists;
    
    -- Основная логика вставки
    IF barrel_exists AND drink_exists THEN
        INSERT INTO barrel_history (barrel_id, drink_id, date_start, alcohol_start)
        VALUES (NEW.barrel_id, NEW.drink_id, NEW.date_start, NEW.alcohol_start);
        
        RETURN NEW;
    ELSE
        -- Детализированная ошибка
        IF NOT barrel_exists THEN
            error_msg := error_msg || ' barrel_id=' || NEW.barrel_id || ' не существует;';
        END IF;
            
        IF NOT drink_exists THEN
            error_msg := error_msg || ' drink_id=' || NEW.drink_id || ' не существует;';
        END IF;
            
        RAISE EXCEPTION '%', error_msg;
    END IF;
END;
$$;

-- Создание триггера 
DROP TRIGGER IF EXISTS barrel_history_view_insert_instead_of ON barrel_history_view;

CREATE TRIGGER barrel_history_view_insert_instead_of
INSTEAD OF INSERT ON barrel_history_view
FOR EACH ROW
EXECUTE FUNCTION barrel_history_view_insert_trigger();


--------------------- Триггер на изменение данных-------------------

CREATE OR REPLACE FUNCTION barrel_history_view_update_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    barrel_history_exists BOOLEAN;
    barrel_exists BOOLEAN;
    drink_exists BOOLEAN;
BEGIN
    -- проверка неизменности barrel_history.id
    IF NEW.id != OLD.id THEN
        RAISE EXCEPTION 'Изменение ID записи запрещено (id: % -> %)', OLD.id, NEW.id USING ERRCODE = 'P0001';
    END IF;
    -- проверка неизменности barrel.id
    IF NEW.barrel_id != OLD.barrel_id THEN
        RAISE EXCEPTION 'Выбор другой бочки запрещён (id: % -> %)', OLD.barrel_id, NEW.barrel_id USING ERRCODE = 'P0001';
    END IF; 
    -- проверка неизменности drink.id
    IF NEW.drink_id != OLD.drink_id THEN
        RAISE EXCEPTION 'Выбор другого напитка запрещён (id: % -> %)', OLD.drink_id, NEW.drink_id USING ERRCODE = 'P0001';
    END IF; 
 
    -- Проверка существования id
    SELECT EXISTS(SELECT 1 FROM barrel_history WHERE id = NEW.id)
    INTO barrel_history_exists;

    -- Проверка существования barrel_id
    SELECT EXISTS(SELECT 1 FROM barrel WHERE id = NEW.barrel_id)
    INTO barrel_exists;
    
    -- Проверка существования drink_id
    SELECT EXISTS(SELECT 1 FROM drink WHERE id = NEW.drink_id)
    INTO drink_exists;
    
    -- Основная логика вставки
    IF barrel_history_exists AND barrel_exists AND drink_exists THEN
        UPDATE barrel_history SET date_start = NEW.date_start, alcohol_start = NEW.alcohol_start, barrel_id = NEW.barrel_id, drink_id = NEW.drink_id
            WHERE id = NEW.id;
        
        RETURN NEW;
    ELSE
        -- Детализированная ошибка
        DECLARE
            error_msg TEXT := 'Не удалось изменить запись. Проблемы:';
        BEGIN
            IF NOT barrel_history_exists THEN
                error_msg := error_msg || ' barrel_history_id=' || NEW.id || ' не существует;';
            END IF;

            IF NOT barrel_exists THEN
                error_msg := error_msg || ' barrel_id=' || NEW.barrel_id || ' не существует;';
            END IF;
            
            IF NOT drink_exists THEN
                error_msg := error_msg || ' drink_id=' || NEW.drink_id || ' не существует;';
            END IF;
            
            RAISE EXCEPTION '%', error_msg USING ERRCODE = 'P0002';
        END;
    END IF;
END;
$$;

-- Создание триггера 
DROP TRIGGER IF EXISTS barrel_history_view_update_instead_of ON barrel_history_view;

CREATE TRIGGER barrel_history_view_update_instead_of
INSTEAD OF UPDATE ON barrel_history_view
FOR EACH ROW
EXECUTE FUNCTION barrel_history_view_update_trigger();

--------------------- Триггер на удаление данных-------------------

CREATE OR REPLACE FUNCTION barrel_history_view_delete_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    barrel_history_exists BOOLEAN;
BEGIN
    -- Проверка существования id
    SELECT EXISTS(SELECT 1 FROM barrel_history WHERE id = OLD.id)
    INTO barrel_history_exists;
    
    -- Основная логика вставки
    IF barrel_history_exists THEN
        DELETE FROM barrel_history WHERE id = OLD.id;
        
        RETURN OLD;
    ELSE
        -- Детализированная ошибка
        DECLARE
            error_msg TEXT := 'Не удалось изменить запись. Проблемы:';
        BEGIN
            error_msg := error_msg || ' id=' || OLD.id || ' не существует;';
            RAISE EXCEPTION '%', error_msg USING ERRCODE = 'P0002';
        END;
    END IF;
END;
$$;

-- Создание триггера 
DROP TRIGGER IF EXISTS barrel_history_view_delete_instead_of ON barrel_history_view;

CREATE TRIGGER barrel_history_view_delete_instead_of
INSTEAD OF DELETE ON barrel_history_view
FOR EACH ROW
EXECUTE FUNCTION barrel_history_view_delete_trigger();
