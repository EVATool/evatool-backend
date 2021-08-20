-- Create entity tables.
create table analysis (
    id CHAR(36) not null,
    realm varchar(255) not null,
    sequence_id integer not null,
    description varchar(2048) not null,
    image_url varchar(255),
    is_template bit not null,
    last_updated bigint not null,
    name varchar(255) not null,
    primary key (id)) engine=MyISAM;

create table impact (
    id CHAR(36) not null,
    realm varchar(255) not null,
    sequence_id integer not null,
    description varchar(2048) not null,
    merit float not null,
    analysis_id CHAR(36) not null,
    stakeholder_id CHAR(36) not null,
    value_id CHAR(36) not null,
    primary key (id)) engine=MyISAM;

create table parent_child_counter (
    id integer not null auto_increment,
    child_class varchar(255) not null,
    counter integer not null,
    parent_class varchar(255) not null,
    parent_id varchar(255) not null,
    primary key (id)) engine=MyISAM;

create table requirement (
    id CHAR(36) not null,
    realm varchar(255) not null,
    sequence_id integer not null,
    description varchar(2048) not null,
    analysis_id CHAR(36) not null,
    primary key (id)) engine=MyISAM;

create table requirement_delta (
    id CHAR(36) not null,
    realm varchar(255) not null,
    overwrite_merit float not null,
    impact_id CHAR(36) not null,
    requirement_id CHAR(36) not null,
    primary key (id)) engine=MyISAM;

create table requirement_variants (
    requirement_id CHAR(36) not null,
    variants_id CHAR(36) not null,
    primary key (requirement_id, variants_id)) engine=MyISAM;

create table stakeholder (
    id CHAR(36) not null,
    realm varchar(255) not null,
    sequence_id integer not null,
    level varchar(255) not null,
    name varchar(255) not null,
    priority varchar(255) not null,
    analysis_id CHAR(36) not null,
    primary key (id)) engine=MyISAM;

create table value (
    id CHAR(36) not null,
    realm varchar(255) not null,
    archived bit not null,
    description varchar(2048) not null,
    name varchar(255) not null,
    type integer not null,
    analysis_id CHAR(36) not null,
    primary key (id)) engine=MyISAM;

create table variant (
    id CHAR(36) not null,
    realm varchar(255) not null,
    sequence_id integer not null,
    archived bit not null,
    description varchar(2048) not null,
    name varchar(255) not null,
    analysis_id CHAR(36) not null,
    primary key (id)) engine=MyISAM;

-- Add foreign key constraints.
alter table impact
    add constraint FK_impact__analysis_id__analysis__id
    foreign key (analysis_id)
    references analysis (id);

alter table impact
    add constraint FK_impact__stakeholder_id__stakeholder__id
    foreign key (stakeholder_id)
    references stakeholder (id);

alter table impact
    add constraint FK_impact__value_id__stakeholder__id
    foreign key (value_id)
    references value (id);

alter table requirement
    add constraint FK_requirement__analysis_id__analysis__id
    foreign key (analysis_id)
    references analysis (id);

alter table requirement_delta
    add constraint FK_requirement_delta__impact_id__impact__id
    foreign key (impact_id)
    references impact (id);

alter table requirement_delta
    add constraint FK_requirement_delta__requirement_id__requirement__id
    foreign key (requirement_id)
    references requirement (id);

alter table requirement_variants
    add constraint FK_requirement_variants__variants_id__variant__id
    foreign key (variants_id)
    references variant (id);

alter table requirement_variants
    add constraint FK_requirement_variants__requirement_id__requirement__id
    foreign key (requirement_id)
    references requirement (id);

alter table stakeholder
    add constraint FK_stakeholder__analysis_id__analysis__id
    foreign key (analysis_id)
    references analysis (id);

alter table value
    add constraint FK_value__analysis_id__analysis__id
    foreign key (analysis_id)
    references analysis (id);

alter table variant
    add constraint FK_variant__analysis_id__analysis__id
    foreign key (analysis_id)
    references analysis (id);
