--------------------- Триггер на добавление данных-------------------

CREATE OR REPLACE FUNCTION drink_view_insert_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO drink (source, alcohol, name)
    VALUES (NEW.source, NEW.alcohol, NEW.name);
        
    RETURN NEW;
END;
$$;

DROP TRIGGER IF EXISTS drink_view_insert_instead_of ON drink_view;

CREATE TRIGGER drink_view_insert_instead_of
INSTEAD OF INSERT ON drink_view
FOR EACH ROW
EXECUTE FUNCTION drink_view_insert_trigger();


--------------------- Триггер на изменение данных-------------------

CREATE OR REPLACE FUNCTION drink_view_update_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    drink_exists BOOLEAN;
BEGIN
    -- проверка неизменности drink.id
    IF NEW.id != OLD.id THEN
        RAISE EXCEPTION 'Изменение ID запрещено (старое: %, новое: %)', OLD.id, NEW.id USING ERRCODE = 'P0001';
    END IF; 
    -- Проверка существования id
    SELECT EXISTS(SELECT 1 FROM drink WHERE id = NEW.id)
    INTO drink_exists;

    -- Основная логика вставки
    IF drink_exists THEN
        UPDATE drink SET source = NEW.source, alcohol = NEW.alcohol, name = NEW.name
            WHERE id = NEW.id;
        
        RETURN NEW;
    ELSE
        -- Детализированная ошибка
        DECLARE
            error_msg TEXT := 'Не удалось изменить запись. Проблемы:';
        BEGIN
            IF NOT drink_exists THEN
                error_msg := error_msg || ' drink_id=' || NEW.id || ' не существует;';
            END IF;

            RAISE EXCEPTION '%', error_msg USING ERRCODE = 'P0002';
        END;
    END IF;
END;
$$;

-- Создание триггера 
DROP TRIGGER IF EXISTS drink_view_update_instead_of ON drink_view;

CREATE TRIGGER drink_view_update_instead_of
INSTEAD OF UPDATE ON drink_view
FOR EACH ROW
EXECUTE FUNCTION drink_view_update_trigger();

--------------------- Триггер на удаление данных-------------------

CREATE OR REPLACE FUNCTION drink_view_delete_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    drink_exists BOOLEAN;
BEGIN
    -- Проверка существования id
    SELECT EXISTS(SELECT 1 FROM drink WHERE id = OLD.id)
    INTO drink_exists;
    
    -- Основная логика вставки
    IF drink_exists THEN
        DELETE FROM drink WHERE id = OLD.id;
        
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
DROP TRIGGER IF EXISTS drink_view_delete_instead_of ON drink_view;

CREATE TRIGGER drink_view_delete_instead_of
INSTEAD OF DELETE ON drink_view
FOR EACH ROW
EXECUTE FUNCTION drink_view_delete_trigger();
