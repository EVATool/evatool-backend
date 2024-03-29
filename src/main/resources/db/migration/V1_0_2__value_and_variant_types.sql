-- noinspection SqlNoDataSourceInspectionForFile

-- ###############
-- Create Tables.
-- ###############

-- Create table for ValueType.
create table value_type (
    id CHAR(36) not null,
    realm varchar(255) not null,
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
    realm varchar(255) not null,
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

DELIMITER //
CREATE PROCEDURE migrate_value_and_variant_types()
    BEGIN
        DECLARE existing_analysis_id CHAR(36);
        DECLARE social_value_type_id CHAR(36);
        DECLARE economic_value_type_id CHAR(36);
        DECLARE default_variant_type_id CHAR(36);
        DECLARE analysis_realm varchar(255);
        DECLARE finished INT DEFAULT FALSE;
        DECLARE existing_analysis_ids CURSOR FOR SELECT id FROM analysis;
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = TRUE;

        -- Iterate over all existing analyses.
        OPEN existing_analysis_ids;
        fetch_loop: LOOP
            FETCH NEXT FROM existing_analysis_ids INTO existing_analysis_id;

            -- Exit if cursor was empty.
            IF finished THEN
                LEAVE fetch_loop;
            END IF;

            -- Get realm of analysis.
            SELECT analysis.realm INTO analysis_realm FROM analysis WHERE analysis.id=existing_analysis_id;

            -- Add ValueTypes that replace the enum values [SOCIAL, ECONOMIC].
            SELECT UUID() INTO social_value_type_id;
            insert into value_type (id, realm, name, description, analysis_id)
                values (social_value_type_id, analysis_realm, "Social", "", existing_analysis_id);
            SELECT UUID() INTO economic_value_type_id;
            insert into value_type (id, realm, name, description, analysis_id)
                values (economic_value_type_id, analysis_realm, "Economic", "", existing_analysis_id);

            -- Assign the ValueTypes [SOCIAL, ECONOMIC].
            update value set value_type_id=social_value_type_id
                where analysis_id=existing_analysis_id and type=0;
            update value set value_type_id=economic_value_type_id
                where analysis_id=existing_analysis_id and type=1;

            -- Add a default VariantType.
            SELECT UUID() INTO default_variant_type_id;
            insert into variant_type (id, realm, name, description, analysis_id)
                values (default_variant_type_id, analysis_realm, "Default", "", existing_analysis_id);

            -- Assign the default VariantType.
            update variant set variant_type_id=default_variant_type_id
                where analysis_id=existing_analysis_id;

        END LOOP;
        CLOSE existing_analysis_ids;
    END //
DELIMITER ;

CALL migrate_value_and_variant_types();



-- ###############
-- Enforce foreign key relations.
-- ###############

-- Make value_type_id not null.
alter table value
    modify value_type_id CHAR(36) not null;

-- Add foreign key constraint to value.
alter table value
    add constraint FK_value__value_type_id__value_type__id
    foreign key (value_type_id)
    references value_type (id);

-- Make variant_type_id not null.
alter table variant
    modify variant_type_id CHAR(36) not null;

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
