--------------------- Триггер на добавление данных-------------------

CREATE OR REPLACE FUNCTION wood_view_insert_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO wood (name, name_lat)
    VALUES (NEW.name, NEW.name_lat);
        
    RETURN NEW;
END;
$$;

DROP TRIGGER IF EXISTS wood_view_insert_instead_of ON wood_view;

CREATE TRIGGER wood_view_insert_instead_of
INSTEAD OF INSERT ON wood_view
FOR EACH ROW
EXECUTE FUNCTION wood_view_insert_trigger();

--------------------- Триггер на изменение данных-------------------

CREATE OR REPLACE FUNCTION wood_view_update_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    wood_exists BOOLEAN;
BEGIN
    -- проверка неизменности wood.id
    IF NEW.id != OLD.id THEN
        RAISE EXCEPTION 'Изменение ID запрещено (старое: %, новое: %)', OLD.id, NEW.id USING ERRCODE = 'P0001';
    END IF; 
    -- Проверка существования id
    SELECT EXISTS(SELECT 1 FROM wood WHERE id = NEW.id)
    INTO wood_exists;

    -- Основная логика вставки
    IF wood_exists THEN
        UPDATE wood SET name = NEW.name, name_lat = NEW.name_lat
            WHERE id = NEW.id;
        
        RETURN NEW;
    ELSE
        -- Детализированная ошибка
        DECLARE
            error_msg TEXT := 'Не удалось изменить запись. Проблемы:';
        BEGIN
            IF NOT wood_exists THEN
                error_msg := error_msg || ' wood_id=' || NEW.id || ' не существует;';
            END IF;

            RAISE EXCEPTION '%', error_msg USING ERRCODE = 'P0002';
        END;
    END IF;
END;
$$;

-- Создание триггера 
DROP TRIGGER IF EXISTS wood_view_update_instead_of ON wood_view;

CREATE TRIGGER wood_view_update_instead_of
INSTEAD OF UPDATE ON wood_view
FOR EACH ROW
EXECUTE FUNCTION wood_view_update_trigger();

--------------------- Триггер на удаление данных-------------------

CREATE OR REPLACE FUNCTION wood_view_delete_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    wood_exists BOOLEAN;
BEGIN
    -- Проверка существования id
    SELECT EXISTS(SELECT 1 FROM wood WHERE id = OLD.id)
    INTO wood_exists;
    
    -- Основная логика вставки
    IF wood_exists THEN
        DELETE FROM wood WHERE id = OLD.id;        
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
DROP TRIGGER IF EXISTS wood_view_delete_instead_of ON wood_view;

CREATE TRIGGER wood_view_delete_instead_of
INSTEAD OF DELETE ON wood_view
FOR EACH ROW
EXECUTE FUNCTION wood_view_delete_trigger();
