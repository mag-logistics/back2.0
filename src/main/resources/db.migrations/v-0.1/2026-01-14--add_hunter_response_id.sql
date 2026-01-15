alter table hunter_response
    drop constraint hunter_response_hunter_application_id_fkey;

alter table hunter_response
    drop column hunter_application_id;

alter table hunter_response
    add hunter_application_id varchar(36);
