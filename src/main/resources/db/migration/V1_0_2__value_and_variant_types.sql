-- ###############
-- Create Tables.
-- ###############

-- Create table for ValueType.
create table value_type (
    id CHAR(36) not null,
    name varchar(255) not null,
    description varchar(2048) not null,
    analysis_id CHAR(36) not null,
    primary key (id)) engine=MyISAM;

alter table value_type
    add constraint FK_value_type__analysis_id__analysis__id
    foreign key (analysis_id)
    references analysis (id);

-- Create table for VariantType.
create table variant_type (
    id CHAR(36) not null,
    name varchar(255) not null,
    description varchar(2048) not null,
    analysis_id CHAR(36) not null,
    primary key (id)) engine=MyISAM;

alter table variant_type
    add constraint FK_variant_type__analysis_id__analysis__id
    foreign key (analysis_id)
    references analysis (id);



