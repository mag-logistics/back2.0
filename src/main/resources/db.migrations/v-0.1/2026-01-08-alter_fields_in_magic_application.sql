alter table magic_applications
    alter column extraction_app_id drop not null;

alter table magic_applications
    alter column magic_response_id drop not null;