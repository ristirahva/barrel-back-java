--------------------- Триггер на добавление данных-------------------

CREATE OR REPLACE FUNCTION barrel_view_insert_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    cooper_exists BOOLEAN;
    wood_exists BOOLEAN;
BEGIN
    -- Проверка существования cooper_id
    SELECT EXISTS(SELECT 1 FROM cooper WHERE id = NEW.cooper_id)
    INTO cooper_exists;
    
    -- Проверка существования wood_id
    SELECT EXISTS(SELECT 1 FROM wood WHERE id = NEW.wood_id)
    INTO wood_exists;
    
    -- Основная логика вставки
    IF cooper_exists AND wood_exists THEN
        INSERT INTO barrel (volume, description, cooper_id, wood_id)
        VALUES (NEW.volume, NEW.description, NEW.cooper_id, NEW.wood_id);
        
        RETURN NEW;
    ELSE
        -- Детализированная ошибка
        DECLARE
            error_msg TEXT := 'Не удалось вставить запись с несуществующими внешними ключами.';
        BEGIN
            IF NOT cooper_exists THEN
                error_msg := error_msg || ' cooper_id=' || NEW.cooper_id || ' не существует;';
            END IF;
            
            IF NOT wood_exists THEN
                error_msg := error_msg || ' wood_id=' || NEW.wood_id || ' не существует;';
            END IF;
            
            RAISE EXCEPTION '%', error_msg USING ERRCODE = 'P0002';
        END;
    END IF;
END;
$$;

-- Создание триггера 
DROP TRIGGER IF EXISTS barrel_view_insert_instead_of ON barrel_view;

CREATE TRIGGER barrel_view_insert_instead_of
INSTEAD OF INSERT ON barrel_view
FOR EACH ROW
EXECUTE FUNCTION barrel_view_insert_trigger();


--------------------- Триггер на изменение данных-------------------

CREATE OR REPLACE FUNCTION barrel_view_update_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    barrel_exists BOOLEAN;
    cooper_exists BOOLEAN;
    wood_exists BOOLEAN;
BEGIN
    -- проверка неизменности barrel.id
    IF NEW.id != OLD.id THEN
        RAISE EXCEPTION 'Изменение ID запрещено (старое: %, новое: %)', OLD.id, NEW.id USING ERRCODE = 'P0001';
    END IF; 
    -- Проверка существования id
    SELECT EXISTS(SELECT 1 FROM barrel WHERE id = NEW.id)
    INTO barrel_exists;

    -- Проверка существования cooper_id
    SELECT EXISTS(SELECT 1 FROM cooper WHERE id = NEW.cooper_id)
    INTO cooper_exists;
    
    -- Проверка существования wood_id
    SELECT EXISTS(SELECT 1 FROM wood WHERE id = NEW.wood_id)
    INTO wood_exists;
    
    -- Основная логика вставки
    IF barrel_exists AND cooper_exists AND wood_exists THEN
        UPDATE barrel SET volume = NEW.volume, description = NEW.description, cooper_id = NEW.cooper_id, wood_id = NEW.wood_id
            WHERE id = NEW.id;
        
        RETURN NEW;
    ELSE
        -- Детализированная ошибка
        DECLARE
            error_msg TEXT := 'Не удалось изменить запись. Проблемы:';
        BEGIN
            IF NOT barrel_exists THEN
                error_msg := error_msg || ' barrel_id=' || NEW.id || ' не существует;';
            END IF;

            IF NOT cooper_exists THEN
                error_msg := error_msg || ' cooper_id=' || NEW.cooper_id || ' не существует;';
            END IF;
            
            IF NOT wood_exists THEN
                error_msg := error_msg || ' wood_id=' || NEW.wood_id || ' не существует;';
            END IF;
            
            RAISE EXCEPTION '%', error_msg USING ERRCODE = 'P0002';
        END;
    END IF;
END;
$$;

-- Создание триггера 
DROP TRIGGER IF EXISTS barrel_view_update_instead_of ON barrel_view;

CREATE TRIGGER barrel_view_update_instead_of
INSTEAD OF UPDATE ON barrel_view
FOR EACH ROW
EXECUTE FUNCTION barrel_view_update_trigger();

--------------------- Триггер на удаление данных-------------------

CREATE OR REPLACE FUNCTION barrel_view_delete_trigger()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS $$
DECLARE
    barrel_exists BOOLEAN;
BEGIN
    -- Проверка существования id
    SELECT EXISTS(SELECT 1 FROM barrel WHERE id = OLD.id)
    INTO barrel_exists;
    
    -- Основная логика вставки
    IF barrel_exists THEN
        DELETE FROM barrel WHERE id = OLD.id;
        
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
DROP TRIGGER IF EXISTS barrel_view_delete_instead_of ON barrel_view;

CREATE TRIGGER barrel_view_delete_instead_of
INSTEAD OF DELETE ON barrel_view
FOR EACH ROW
EXECUTE FUNCTION barrel_view_delete_trigger();
