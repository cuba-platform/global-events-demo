-- begin GLEVTDEMO_EVENT_REGISTRATION
create table GLEVTDEMO_EVENT_REGISTRATION (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    --
    EVENT_CLASS varchar(255),
    EVENT_PAYLOAD varchar(255),
    RECEIVER varchar(255),
    RECEIVED_AT varchar(255),
    --
    primary key (ID)
)^
-- end GLEVTDEMO_EVENT_REGISTRATION
