alter table extraction_applications
    add hunter_app_id varchar(36);

alter table extraction_applications
    add constraint extraction_applications_hunter_application_id_fk
        foreign key (hunter_app_id) references hunter_application;

alter table hunter_application
    drop constraint hunter_application_extraction_app_id_fkey;
