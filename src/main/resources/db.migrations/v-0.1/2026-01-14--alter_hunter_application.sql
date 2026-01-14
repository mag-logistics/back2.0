alter table hunter_application
    add hunter_id varchar(36);

alter table hunter_application
    add constraint hunter_application_hunters_id_fk
        foreign key (hunter_id) references hunters;

