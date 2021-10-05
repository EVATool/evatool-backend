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



-- ###############
-- Add relations.
-- ###############

-- Add ValueTypeId to Value.
alter table value
    add value_type_id CHAR(36) null;

-- Add VariantTypeId to Variant.
alter table variant
    add variant_type_id CHAR(36) null;



-- ###############
-- Migrate existing analyses from enum to entity.
-- ###############

-- Create the ValueTypes (from previous enum [SOCIAL, ECONOMIC]) for each existing analysis
-- and assign the correct ValueType to all values.



-- ###############
-- Enforce foreign key relations.
-- ###############

-- Make value_type_id not null.
alter table value
    alter column value_type_id CHAR(36) not null;

-- Add foreign key constraint to value.
alter table value
    add constraint FK_value__value_type_id__value_type__id
    foreign key (value_type_id)
    references value_type (id);

-- Make variant_type_id not null.
alter table variant
    alter column variant_type_id CHAR(36) not null;

-- Add foreign key constraint to variant.
alter table variant
    add constraint FK_variant__variant_type_id__variant_type__id
    foreign key (variant_type_id)
    references variant_type (id);



-- ###############
-- Remove deprecated columns.
-- ###############

-- Remove type column from value table.
alter table value
    drop column type;
