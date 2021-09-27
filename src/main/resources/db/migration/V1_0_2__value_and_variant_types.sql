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
    add value_type_id CHAR(36) null

-- Add VariantTypeId to Variant.
alter table variant
    add variant_type_id CHAR(36) null



-- ###############
-- Migrate existing analyses from enum to entity.
-- ###############

-- Create the ValueTypes (from previous enum [SOCIAL, ECONOMIC]) for each existing analysis
-- and assign the correct ValueType to all values.
DECLARE @analysis_id CHAR(36)
DECLARE existing_analysis_ids CURSOR FOR SELECT id FROM analysis
OPEN existing_analysis_ids
FETCH NEXT FROM existing_analysis_ids INTO @analysis_id

-- Iterate over all existing analyses.
WHILE @@FETCH_STATUS = 0
BEGIN
    PRINT @analysis_id
    -- Add ValueTypes that replace the enum values [SOCIAL, ECONOMIC].
    DECLARE @social_value_type_id CHAR(36)
    DECLARE @economic_value_type_id CHAR(36)
    insert into value_type values (null, "", "", @analysis_id)
    SELECT LAST_INSERT_ID() INTO @social_value_type_id
    insert into value_type values (null, "", "", @analysis_id)
    SELECT LAST_INSERT_ID() INTO @economic_value_type_id

    -- Assign the ValueTypes [SOCIAL, ECONOMIC].
    update value set value_type_id=@social_value_type_id where analysis_id=@analysis_id and type="SOCIAL"
    update value set value_type_id=@economic_value_type_id where analysis_id=@analysis_id and type="ECONOMIC"

    -- Add a default VariantType.
    DECLARE @default_variant_type_id CHAR(36)
    insert into variant_type values (null, "", "", @analysis_id)
    SELECT LAST_INSERT_ID() INTO @default_variant_type_id

    -- Assign the default VariantType.
    update variant set variant_type_id=@default_variant_type_id where analysis_id=@analysis_id

    FETCH NEXT FROM existing_analysis_ids INTO @analysis_id
END

CLOSE existing_analysis_ids
DEALLOCATE existing_analysis_ids



-- ###############
-- Enforce foreign key relations.
-- ###############

-- Make value_type_id not null.
alter table value
    alter column value_type_id CHAR(36) not null

-- Add foreign key constraint to value.
alter table value
    add constraint FK_value__value_type_id__value_type__id
    foreign key (value_type_id)
    references value_type (id);

-- Make variant_type_id not null.
alter table variant
    alter column variant_type_id CHAR(36) not null

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