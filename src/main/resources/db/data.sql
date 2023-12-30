-- 기본 사용자(패스워드 설정- 1234 : $2a$10$s9nPBHKe7mUpZ3kj1lOHheO4mxZEK5c/nI8Tpl/XrwKFIkyLUViMq )
INSERT INTO schul.mngr (mngr_no,  mngr_id, mngr_ty_code, mngr_nm, password,ct_actvty_at) VALUES (1,  'admin', 'SYSTEM', 'ADMIN', '$2a$10$s9nPBHKe7mUpZ3kj1lOHheO4mxZEK5c/nI8Tpl/XrwKFIkyLUViMq', false);
INSERT INTO schul.mngr (mngr_no,  mngr_id, mngr_ty_code, mngr_nm, password,ct_actvty_at) VALUES (2,  'test', 'SCHUL', 'test', '$2a$10$s9nPBHKe7mUpZ3kj1lOHheO4mxZEK5c/nI8Tpl/XrwKFIkyLUViMq', false);

-- 테스트 학교
INSERT INTO schul.schul (schul_no, schul_nm,manage_creat_mngr_no,manage_creat_dt, manage_updt_mngr_no,  manage_updt_dt) VALUES (1, '테스트 학교', 1, now(),  1, now());

-- 테스트 학교와 테스트 사용자 맵핑
INSERT INTO schul.schul_mngr_mapng (schul_no, mngr_no) VALUES (1, 2);
