-- alter table extraction_applications
--     add extraction_response_id varchar(36);
--
-- alter table extraction_applications
--     add constraint extraction_applications_extraction_response_id_fk
--         foreign key (extraction_response_id) references extraction_response;

alter table extraction_response
    add extraction_application_id varchar(36);
