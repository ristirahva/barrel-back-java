--------------------- Триггер на добавление данных-------------------

CREATE OR REPLACE FUNCTION cooper_view_insert_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO cooper (name, url)
    VALUES (NEW.name, NEW.url);
        
    RETURN NEW;
END;
$$;

-- Создание триггера с улучшенной функцией
DROP TRIGGER IF EXISTS cooper_view_insert_instead_of ON cooper_view;

CREATE TRIGGER cooper_view_insert_instead_of
INSTEAD OF INSERT ON cooper_view
FOR EACH ROW
EXECUTE FUNCTION cooper_view_insert_trigger();

--------------------- Триггер на изменение данных-------------------

CREATE OR REPLACE FUNCTION cooper_view_update_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    cooper_exists BOOLEAN;
BEGIN
    -- проверка неизменности cooper.id
    IF NEW.id != OLD.id THEN
        RAISE EXCEPTION 'Изменение ID запрещено (старое: %, новое: %)', OLD.id, NEW.id USING ERRCODE = 'P0001';
    END IF; 
    -- Проверка существования id
    SELECT EXISTS(SELECT 1 FROM cooper WHERE id = NEW.id)
    INTO cooper_exists;

    -- Основная логика вставки
    IF cooper_exists THEN
        UPDATE cooper SET name = NEW.name, url = NEW.url
            WHERE id = NEW.id;
        
        RETURN NEW;
    ELSE
        -- Детализированная ошибка
        DECLARE
            error_msg TEXT := 'Не удалось изменить запись. Проблемы:';
        BEGIN
            IF NOT cooper_exists THEN
                error_msg := error_msg || ' cooper_id=' || NEW.id || ' не существует;';
            END IF;

            RAISE EXCEPTION '%', error_msg USING ERRCODE = 'P0002';
        END;
    END IF;
END;
$$;

-- Создание триггера 
DROP TRIGGER IF EXISTS cooper_view_update_instead_of ON cooper_view;

CREATE TRIGGER cooper_view_update_instead_of
INSTEAD OF UPDATE ON cooper_view
FOR EACH ROW
EXECUTE FUNCTION cooper_view_update_trigger();

--------------------- Триггер на удаление данных-------------------

CREATE OR REPLACE FUNCTION cooper_view_delete_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    cooper_exists BOOLEAN;
BEGIN
    -- Проверка существования id
    SELECT EXISTS(SELECT 1 FROM cooper WHERE id = OLD.id)
    INTO cooper_exists;
    
    -- Основная логика вставки
    IF cooper_exists THEN
        DELETE FROM cooper WHERE id = OLD.id;
        
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
DROP TRIGGER IF EXISTS cooper_view_delete_instead_of ON cooper_view;

CREATE TRIGGER cooper_view_delete_instead_of
INSTEAD OF DELETE ON cooper_view
FOR EACH ROW
EXECUTE FUNCTION cooper_view_delete_trigger();
