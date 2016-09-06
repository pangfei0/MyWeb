--基本数据初始化
DELETE from user; --password:123456
INSERT INTO user (id, nick, password, user_name,in_use) VALUES ('user-a99ad071-b7d7-4748-ad50-b2a1ab216cec', '管理员小李', 'c7122a1349c22cb3c009da3613d242ab', 'admin','1');
INSERT INTO user (id, nick, password, user_name,in_use) VALUES ('user-06bab60d-e3b0-4cb2-be06-df573eb07a90', '员工小赵', '74da9f9bfcb4d0894cea88a36b89c2a9', 'staff','1');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cc1','查看用户','user:view','用户');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cc2','添加用户','user:new','用户');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cc3','禁用用户','user:forbid','用户');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cc4','编辑用户','user:edit','用户');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cc5','登录','user:login','用户');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cc6','审核用户','auditPermission:view','用户');

-- INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cc6','查看单位类型','unitRole:view','主数据');
-- INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cc7','添加单位类型','unitRole:new','主数据');
-- INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cc8','删除单位类型','unitRole:delete','主数据');
-- INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cc9','编辑单位类型','unitRole:edit','主数据');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07c1c','查看组织架构','organization:view','组织架构');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07c2c','添加组织架构','organization:new','组织架构');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07c3c','删除组织架构','organization:delete','组织架构');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07c4c','编辑组织架构','organization:edit','组织架构');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb071cc','查看角色','role:view','角色');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb072cc','添加角色','role:new','角色');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb073cc','禁用角色','role:forbid','角色');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb074cc','编辑角色','role:edit','角色');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb080cc','查看网格救援','gridRescue:view','网格救援');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb081cc','查看在线监控','onlineMonitoring:view','在线监控');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec181cc','查看员工控制台','personnelBoard:view','维保员工管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec281cc','查看工单控制台','workbillBoard:view','工单管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec281c1','新增工单','workbillBoard:new','工单管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec281c2','派发工单','workbillBoard:send','工单管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec281c3','删除工单','workbillBoard:delete','工单管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec381cc','查看报告控制台','reportBoard:view','报告管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec381c1','报告','report','报告管理');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481c0','保养合同控制台','upkeepContract:search','保养合同管理');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481cc','查看保养合同','upkeepContract:view','保养合同管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481c1','添加保养合同','upkeepContract:new','保养合同管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481c2','编辑保养合同','upkeepContract:edit','保养合同管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481c3','删除保养合同','upkeepContract:delete','保养合同管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481c4','查看保养日程','upkeepContract:Scheduler','保养合同管理');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481c5','查看维保批次','upkeepContractPlanBath:view','维保计划管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec48115','删除维保批次','upkeepContractPlanBath:delete','维保计划管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481c6','查看维保计划','upkeepContractPlan:view','维保计划管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481c7','添加维保计划','upkeepContractPlan:new','维保计划管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481c8','编辑维保计划','upkeepContractPlan:edit','维保计划管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573ec481c9','删除维保计划','upkeepContractPlan:delete','维保计划管理');


INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573e0481cc','查看楼盘','premise:view','楼盘管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573e1481cc','添加楼盘','premise:new','楼盘管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573e2481cc','删除楼盘','premise:delete','楼盘管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573e3481cc','编辑楼盘','premise:edit','楼盘管理');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cd6','查看公司','companyMaintain:view','公司管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cd7','添加公司','companyMaintain:new','公司管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cd8','删除公司','companyMaintain:delete','公司管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cd9','编辑公司','companyMaintain:edit','公司管理');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07ce6','查看维保人员','maintainPerson:view','维保人员管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07ce7','添加维保人员','maintainPerson:new','维保人员管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07ce8','删除维保人员','maintainPerson:delete','维保人员管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07ce9','编辑维保人员','maintainPerson:edit','维保人员管理');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df57300481cc','查看电梯','elevator:view','电梯管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df57311481cc','添加电梯','elevator:new','电梯管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df57322481cc','删除电梯','elevator:delete','电梯管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df57333481cc','编辑电梯','elevator:edit','电梯管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df57334481cc','查看多品牌电梯','elevator:particular:view','电梯管理');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df57334482cc','查看采集器','intelHardware:view','采集器管理');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df57300481c5','查看电梯品牌','elevatorBrand:view','电梯品牌管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df57300481c6','添加电梯品牌','elevatorBrand:new','电梯品牌管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df57300481c7','删除电梯品牌','elevatorBrand:delete','电梯品牌管理');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df57300481c8','编辑电梯品牌','elevatorBrand:edit','电梯品牌管理');

INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07ccd','导出数据','export','其他');
INSERT INTO permission(id,name,code,category) VALUES ('permission-06bab60d-e3b0-4cb2-be06-df573eb07cce','同步数据','data:sync','其他');



INSERT INTO role(id,name,parent_id,in_use) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','超级管理员',null,'1');
INSERT INTO role(id,name,parent_id,order_index) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00011','IT经理','role-06bab60d-e3b0-4cb2-be06-df573eb00001',1);
INSERT INTO role(id,name,parent_id,order_index) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00111','程序员','role-06bab60d-e3b0-4cb2-be06-df573eb00011',1);
INSERT INTO role(id,name,parent_id,order_index) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00211','测试','role-06bab60d-e3b0-4cb2-be06-df573eb00011',2);
INSERT INTO role(id,name,parent_id,order_index) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00021','客服经理','role-06bab60d-e3b0-4cb2-be06-df573eb00001',2);
INSERT INTO role(id,name,parent_id,order_index) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00121','客服','role-06bab60d-e3b0-4cb2-be06-df573eb00021',1);
INSERT INTO role(id,name,parent_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00031','游客',null);

INSERT  INTO organization(id,name,parent_id,order_index) VALUES ('organization-e4dd2ca7-9013-4bae-a038-b3f22f145148','巨立电梯',null,1);
INSERT  INTO organization(id,name,parent_id,order_index) VALUES ('organization-e4dd2ca7-9013-4bae-a038-b3f22f145001','信息技术部','organization-e4dd2ca7-9013-4bae-a038-b3f22f145148',1);
INSERT  INTO organization(id,name,parent_id,order_index) VALUES ('organization-e4dd2ca7-9013-4bae-a038-b3f22f145011','技术1部','organization-e4dd2ca7-9013-4bae-a038-b3f22f145001',1);
INSERT  INTO organization(id,name,parent_id,order_index) VALUES ('organization-e4dd2ca7-9013-4bae-a038-b3f22f145002','运营部','organization-e4dd2ca7-9013-4bae-a038-b3f22f145148',3);
INSERT  INTO organization(id,name,parent_id,order_index) VALUES ('organization-e4dd2ca7-9013-4bae-a038-b3f22f145003','客服部','organization-e4dd2ca7-9013-4bae-a038-b3f22f145148',2);

INSERT INTO USER_ROLES(user_id,roles_id) VALUES ('user-06bab60d-e3b0-4cb2-be06-df573eb07a90','role-06bab60d-e3b0-4cb2-be06-df573eb00121');
INSERT INTO USER_ROLES(user_id,roles_id) VALUES ('user-a99ad071-b7d7-4748-ad50-b2a1ab216cec','role-06bab60d-e3b0-4cb2-be06-df573eb00001');


-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cc1');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cc2');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cc3');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cc4');

INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cc5');

-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cc6');
--
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07c1c');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07c2c');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07c3c');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07c4c');
--
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb071cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb072cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb073cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb074cc');
--
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573e0481cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573e1481cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573e2481cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573e3481cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df57300481cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df57311481cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df57322481cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df57333481cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df57334481cc');
--
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07ccd');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cce');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb081cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec181cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec281cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec281c1');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec281c2');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec281c3');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec381cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec381c1');
--
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec481c0');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec481cc');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec481c1');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec481c2');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec481c3');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573ec481c4');
--
-- -- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cc6');
-- -- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cc7');
-- -- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cc8');
-- -- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cc9');
--
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cd6');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cd7');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cd8');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07cd9');
--
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07ce6');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07ce7');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07ce8');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb07ce9');
--
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df57334482cc');
--
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df57300481c5');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df57300481c6');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df57300481c7');
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df57300481c8');
--
-- INSERT INTO ROLE_PERMISSIONS(role_id,permissions_id) VALUES ('role-06bab60d-e3b0-4cb2-be06-df573eb00001','permission-06bab60d-e3b0-4cb2-be06-df573eb080cc');
--
-- INSERT INTO MAINTENANCE_PERSONNEL(id,name,lat,lng,current_state,telephone,number) VALUES ('maintenance-personnel-06bab60d-e3b0-4cb2-be06-df573eb07cc1','张三',31.315226,120.582744,10,'15946895674','001');

delete from elevator_brand;
INSERT INTO ELEVATOR_BRAND (ID ,CREATED_BY ,CREATED_DATE ,NAME ,NUMBER ) VALUES ('brand67f61987-9d2e-4c2a-be7c-09957140d00015' ,'Hello',null,'巨立','TL000B5') ;

-- --维修技能等级
delete from repair_level;
INSERT INTO REPAIR_LEVEL (ID ,CREATED_BY ,CREATED_DATE ,LAST_MODIFIED_DATE ,LAST_MODIFY_BY ,NAME ,ELEVATOR_BRAND_ID ) VALUES('level-67f61987-9d2e-4c2a-be7c-09957140d0009',NULL,NULL,NULL,NULL,'低','brand67f61987-9d2e-4c2a-be7c-09957140d00015');
INSERT INTO REPAIR_LEVEL (ID ,CREATED_BY ,CREATED_DATE ,LAST_MODIFIED_DATE ,LAST_MODIFY_BY ,NAME ,ELEVATOR_BRAND_ID ) VALUES('level-67f61987-9d2e-4c2a-be7c-09957140d0010',NULL,NULL,NULL,NULL,'中','brand67f61987-9d2e-4c2a-be7c-09957140d00015');
INSERT INTO REPAIR_LEVEL (ID ,CREATED_BY ,CREATED_DATE ,LAST_MODIFIED_DATE ,LAST_MODIFY_BY ,NAME ,ELEVATOR_BRAND_ID ) VALUES('level-67f61987-9d2e-4c2a-be7c-09957140d0011',NULL,NULL,NULL,NULL,'高','brand67f61987-9d2e-4c2a-be7c-09957140d00015');

