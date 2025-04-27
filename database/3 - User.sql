CREATE TABLE TODO.USER ( -- Tabela de usuários
    ID_USER BIGSERIAL PRIMARY KEY, -- Identificador único de usuário
    FR_NAME VARCHAR(50) NOT NULL, -- Primeiro nome
    MD_NAME VARCHAR(100), -- Nome do meio
    LT_NAME VARCHAR(50) NOT NULL, -- Último nome
    USER_EMAIL VARCHAR(50) NOT NULL, -- E-mail do usuário
    DT_INCLUSION TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Data de inclusão do usuário
    IS_ACTIVE BOOLEAN NOT NULL DEFAULT TRUE -- Indica se o usuário está ativo
);

COMMENT ON TABLE TODO.USER IS 'Tabela de usuários';
COMMENT ON COLUMN TODO.USER.ID_USER IS 'Identificador único de usuário';
COMMENT ON COLUMN TODO.USER.FR_NAME IS 'Primeiro nome';
COMMENT ON COLUMN TODO.USER.MD_NAME IS 'Nome do meio';
COMMENT ON COLUMN TODO.USER.LT_NAME IS 'Último nome';
COMMENT ON COLUMN TODO.USER.USER_EMAIL IS 'E-mail do usuário';
COMMENT ON COLUMN TODO.USER.DT_INCLUSION IS 'Data de inclusão do usuário';
COMMENT ON COLUMN TODO.USER.IS_ACTIVE IS 'Indica se o usuário está ativo';
