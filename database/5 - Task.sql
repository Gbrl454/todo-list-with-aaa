CREATE TABLE TODO.TASK ( -- Tabela de atividades
    ID_TASK BIGSERIAL PRIMARY KEY, -- Identificador único de atividade
    HASH_TASK VARCHAR(50) NOT NULL, -- Hash da atividade
    ID_USER_OWNER BIGINT NOT NULL, -- Usuário dono da atividade
    NM_TASK VARCHAR(50) NOT NULL, -- Nome da atividade
    DS_TASK TEXT NOT NULL, -- Descrição da atividade
    IS_PRIVATE_TASK BOOLEAN NOT NULL DEFAULT TRUE, -- Indica se a atividade é privada
    DT_DEADLINE TIMESTAMP NOT NULL, -- Data limite da atividade
    DT_DO TIMESTAMP, -- Data de realização da atividade
    DT_INCLUSION TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Data de inclusão da atividade
    IS_ACTIVE BOOLEAN NOT NULL DEFAULT TRUE -- Indica se a atividade está ativa
);

COMMENT ON TABLE TODO.TASK IS 'Tabela de atividades';
COMMENT ON COLUMN TODO.TASK.ID_TASK IS 'Identificador único de atividade';
COMMENT ON COLUMN TODO.TASK.HASH_TASK IS 'Hash da atividade';
COMMENT ON COLUMN TODO.TASK.ID_USER_OWNER IS 'Usuário dono da atividade';
COMMENT ON COLUMN TODO.TASK.NM_TASK IS 'Nome da atividade';
COMMENT ON COLUMN TODO.TASK.DS_TASK IS 'Descrição da atividade';
COMMENT ON COLUMN TODO.TASK.IS_PRIVATE_TASK IS 'Indica se a atividade é privada';
COMMENT ON COLUMN TODO.TASK.DT_DEADLINE IS 'Data limite da atividade';
COMMENT ON COLUMN TODO.TASK.DT_DO IS 'Data de realização da atividade';
COMMENT ON COLUMN TODO.TASK.DT_INCLUSION IS 'Data de inclusão da atividade';
COMMENT ON COLUMN TODO.TASK.IS_ACTIVE IS 'Indica se a atividade está ativa';

ALTER TABLE TODO.TASK
    ADD CONSTRAINT FK_TASK_USER_ID_USER_OWNER
        FOREIGN KEY (ID_USER_OWNER) REFERENCES TODO.USER(ID_USER);
