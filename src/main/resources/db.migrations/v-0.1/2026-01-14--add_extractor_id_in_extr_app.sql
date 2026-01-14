alter table extraction_applications
    add extractor_id varchar(36);

alter table extraction_applications
    add constraint extraction_applications_extractors_id_fk
        foreign key (extractor_id) references extractors;

