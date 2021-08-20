create table analysis (id CHAR(36) not null, realm varchar(255) not null, sequence_id integer not null, description varchar(2048) not null, image_url varchar(255), is_template bit not null, last_updated bigint not null, name varchar(255) not null, primary key (id)) engine=MyISAM;

create table impact (id CHAR(36) not null, realm varchar(255) not null, sequence_id integer not null, description varchar(2048) not null, merit float not null, analysis_id CHAR(36) not null, stakeholder_id CHAR(36) not null, value_id CHAR(36) not null, primary key (id)) engine=MyISAM;

create table parent_child_counter (id integer not null auto_increment, child_class varchar(255) not null, counter integer not null, parent_class varchar(255) not null, parent_id varchar(255) not null, primary key (id)) engine=MyISAM;

create table requirement (id CHAR(36) not null, realm varchar(255) not null, sequence_id integer not null, description varchar(2048) not null, analysis_id CHAR(36) not null, primary key (id)) engine=MyISAM;

create table requirement_delta (id CHAR(36) not null, realm varchar(255) not null, overwrite_merit float not null, impact_id CHAR(36) not null, requirement_id CHAR(36) not null, primary key (id)) engine=MyISAM;

create table requirement_variants (requirement_id CHAR(36) not null, variants_id CHAR(36) not null, primary key (requirement_id, variants_id)) engine=MyISAM;

create table stakeholder (id CHAR(36) not null, realm varchar(255) not null, sequence_id integer not null, level varchar(255) not null, name varchar(255) not null, priority varchar(255) not null, analysis_id CHAR(36) not null, primary key (id)) engine=MyISAM;

create table value (id CHAR(36) not null, realm varchar(255) not null, archived bit not null, description varchar(2048) not null, name varchar(255) not null, type integer not null, analysis_id CHAR(36) not null, primary key (id)) engine=MyISAM;

create table variant (id CHAR(36) not null, realm varchar(255) not null, sequence_id integer not null, archived bit not null, description varchar(2048) not null, name varchar(255) not null, analysis_id CHAR(36) not null, primary key (id)) engine=MyISAM;



alter table impact add constraint FKe7apmw7cpv478vo5x3d4q3lbw foreign key (analysis_id) references analysis (id);

alter table impact add constraint FKdtk3nnqsg6cljg5cjvpv1wlm4 foreign key (stakeholder_id) references stakeholder (id);

alter table impact add constraint FKcg1spk9s8r6xqajn497s5dwyk foreign key (value_id) references value (id);

alter table requirement add constraint FKkw46kdgifhvjaaby42u1u3wjt foreign key (analysis_id) references analysis (id);

alter table requirement_delta add constraint FK2mhidj7ipryoo42dfnlclxwxh foreign key (impact_id) references impact (id);

alter table requirement_delta add constraint FKgjjvtl4oayi94xd6u1d6rl5g foreign key (requirement_id) references requirement (id);

alter table requirement_variants add constraint FKq9hordtsc5j8ykv9g0m3k7l9x foreign key (variants_id) references variant (id);

alter table requirement_variants add constraint FK3gfj6pputbpei0n2s58w30w06 foreign key (requirement_id) references requirement (id);

alter table stakeholder add constraint FKeu9u8bsuiw6go5wybsttb22hg foreign key (analysis_id) references analysis (id);

alter table value add constraint FK7gnbcp977ep1mouuwrluh93eo foreign key (analysis_id) references analysis (id);

alter table variant add constraint FKatbux9h5969b92kfltk4n3tvs foreign key (analysis_id) references analysis (id);
