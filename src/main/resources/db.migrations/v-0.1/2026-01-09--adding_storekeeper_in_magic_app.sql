alter table magic_applications
    add storekeeper_id varchar(36);

alter table magic_applications
    add constraint magic_applications_storekeeper_id_fk
        foreign key (storekeeper_id) references storekeepers (id);