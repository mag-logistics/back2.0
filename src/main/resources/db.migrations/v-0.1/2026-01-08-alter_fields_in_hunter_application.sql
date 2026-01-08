alter table hunter_application
    rename column status_id to status;

alter table hunter_application
    alter column hunter_response_id drop not null;