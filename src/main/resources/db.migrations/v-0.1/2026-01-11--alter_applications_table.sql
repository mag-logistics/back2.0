alter table extraction_applications
    add extraction_response_id varchar(36);

alter table extraction_applications
    add constraint extraction_applications_extraction_response_id_fk
        foreign key (extraction_response_id) references extraction_response (id);

alter table hunter_application
    add hunter_response_id varchar(36);

alter table hunter_application
    add constraint hunter_application_hunter_response_id_fk
        foreign key (hunter_response_id) references hunter_response (id);