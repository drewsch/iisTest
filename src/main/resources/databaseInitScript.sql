create table employer_info
(
    id            serial constraint employer_info_pk primary key,
    "DepCode"     varchar(20) not null,
    "DepJob"      varchar(100) not null,
    "Description" varchar(255),
    constraint dep_code_dep_job_unique
        unique ("DepCode", "DepJob")
);

alter table employer_info
    owner to postgres;

insert into public.employer_info (id, DepCode, DepJob, Description)
values  (1, 'prog', 'qa', 'testing all the code'),
        (2, 'prog', 'manager', 'staring at the programmer every day'),
        (3, 'study', 'principal', 'sitting his chair'),
        (8, 'prog', 'programmer', 'doing all stuff');