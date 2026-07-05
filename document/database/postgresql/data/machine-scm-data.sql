DELETE FROM t_scm_back_category;
INSERT INTO t_scm_back_category (id, parent_id, name, code, sort,
                                create_by, create_time, update_by, update_time)
VALUES ('back_category', 'root', '后台分类', 'BC202605290001',  300, 'system', 1729736640000,
        'system', 1729736640000);

DELETE FROM t_scm_front_category;
INSERT INTO t_scm_front_category (id, parent_id, name, code, sort,
                                 create_by, create_time, update_by, update_time)
VALUES ('front_category', 'root', '前台分类', 'FC202605290001',  300, 'system', 1729736640000,
        'system', 1729736640000);

