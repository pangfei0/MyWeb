/* 静态配置对象 */
var	TopWindow = TopWindow || window, ProjectName = ProjectName || {}, URL = URL || {}, Constant = Constant || {}, Module = Module || {}, MenuNumber = MenuNumber || {}, MsgId = MsgId || {}, TdSerialType = TdSerialType || {}, TdSerialTag = TdSerialTag || {};

/* 是否启用调试模式 */
DEBUGGER = false;

/* 时间设置 */
CMD_RES_TIMEOUT = 35000;//远程指令请求响应超时
FUNCTIONCODE_RES_TIMEOUT = 20000;//功能码请求响应超时

/* 目标设备类型 */
TdSerialType.Controller = 0;
TdSerialType.Camera = 1;
TdSerialType.Audio = 2;
TdSerialType.Display = 3;
TdSerialType.GPS = 4;

/* 项目名 */
ProjectName.Main = 'Main';
ProjectName.Basic = 'Main';
ProjectName.Monitor = 'Monitor';
ProjectName.Hotline = 'Hotline';
ProjectName.Upkeep = 'Upkeep';

/* 目标设备请求tag */
TdSerialTag.RealTimeData = 1;//实时数据
TdSerialTag.InnerCall = 2;//内召
TdSerialTag.OuterCall = 3;//外召(上下召)
TdSerialTag.Terminal = 4;//端子
TdSerialTag.StatisticsData = 5;//统计数据
TdSerialTag.FunctionCode = 10;//功能码10+
TdSerialTag.FunctionCodeTerminal = 15;//功能码组端子参数

/* 登录 */
URL.Index = '/sys/login.jsp';
URL.LogIn = '/sys/login.jsp';
URL.LogOut = '/sys/user/user!logout.do';

/* 首页 */
URL.Home = '/sys/page/home.jsp';

/* 电梯维保信息 */
MenuNumber.UpkeepElevatorList = 1200;
MenuNumber.UpkeepElevatorImport = 1210;
MenuNumber.UpkeepElevatorEdit = 1220;
MenuNumber.UpkeepElevatorLogList = 1230;
URL.UpkeepElevatorList = '/upkeep/page/purpose/upkeepElevatorList.jsp?menuNO=1200';
URL.UpkeepElevatorEdit = '/upkeep/page/purpose/upkeepElevatorEdit.jsp?menuNO=1220';
URL.UpkeepElevatorLogList = '/upkeep/page/purpose/upkeepElevatorLogList.jsp?menuNO=1230';
URL.GetUpkeepInfo = '/upkeep/upkeepInfo/upkeepInfo!getUpkeepInfoList.do';
URL.SaveUpkeepInfo = '/upkeep/upkeepInfo/upkeepInfo!saveUpkeepInfo.do';
URL.GetUpkeepInfoById= '/upkeep/upkeepInfo/upkeepInfo!getUpkeepInfoListById.do';
URL.ImportUpkeepInfo = '/upkeep/upkeepInfo/upkeepInfo!importUpkeepInfo.do';




/* 维保合同管理 */
MenuNumber.UpkeepContractList = 1110;
MenuNumber.CustomerReceivableList = 1120;
URL.UpkeepContractList = '/upkeep/page/contract/contractList.jsp?menuNO=1141&parentMenuNO=1110';
URL.CustomerReceivableList = '/upkeep/page/contract/customerReceivableList.jsp?menuNO=1120';

URL.GetContractList = '/upkeep/contract/contract!getContractList.do';
//更改合同状态
URL.GetContractStatusById = '/upkeep/contract/contract!updateContractStatus.do';

URL.GetContractById = '/upkeep/contract/contract!getContractById.do';
URL.GetElevatorsByConId = '/upkeep/contract/contract!getElevatorsByConId.do';

MenuNumber.UnfiledContractAdd = 11410;
MenuNumber.UnfiledContractEdit = 11411;
//查看操作日志
MenuNumber.UnfiledContractSearch = 11412;
MenuNumber.ArchivingContractSearch = 11421;
MenuNumber.IneffectiveStatusSearch = 11511;
MenuNumber.EffectiveStatusSearch = 11520;
MenuNumber.WaitIssueRenewalSearch = 11531;
MenuNumber.IssueRenewalSearch = 11541;
MenuNumber.UnrecoveredExecutiveSearch = 11550;
MenuNumber.RecoveredExecutiveSearch = 11561;
MenuNumber.TheLastFifteenDaysSearch = 11570;
MenuNumber.NotRenewedSuccessSearch = 11581;
MenuNumber.ExecutionCompletedSearch = 11590;
MenuNumber.RenewedSuccessSearch = 11640;
//查看关联电梯
MenuNumber.UnfiledContractAssociateElevator = 11413;
MenuNumber.ArchivingContractAssociateElevator = 11422;
MenuNumber.IneffectiveStatusAssociateElevator = 11512;
MenuNumber.EffectiveStatusAssociateElevator = 11521;
MenuNumber.WaitIssueRenewalAssociateElevator = 11532;
MenuNumber.IssueRenewalAssociateElevator = 11542;
MenuNumber.UnrecoveredExecutiveAssociateElevator = 11551;
MenuNumber.RecoveredExecutiveAssociateElevator = 11562;
MenuNumber.TheLastFifteenDaysAssociateElevator = 11571;
MenuNumber.NotRenewedSuccessAssociateElevator = 11582;
MenuNumber.ExecutionCompletedAssociateElevator = 11591;
MenuNumber.RenewedSuccessAssociateElevator = 11641;

MenuNumber.ArchivingContractAdd = 11420;
MenuNumber.ArchivingContractEdit = 11421;

MenuNumber.IneffectiveContractEdit = 11510;

MenuNumber.ContractIssue = 11530;
MenuNumber.ContractStatusOfTakeBack = 11540;
MenuNumber.RenewSuccess = 11560;
MenuNumber.ArchiveContract = 11580;


URL.UnfiledContractAdd = '/upkeep/page/contract/contractEdit.jsp?menuNO=11410';
URL.ArchivingContractAdd = '/upkeep/page/contract/contractEdit.jsp?menuNO=11420';
URL.UnfiledContractEdit = '/upkeep/page/contract/contractEdit.jsp?menuNO=11411';
URL.ArchivingContractEdit = '/upkeep/page/contract/contractEdit.jsp?menuNO=11421';

URL.AddContract = '/upkeep/contract/contract!addContract.do';
URL.EditContract = '/upkeep/contract/contract!updateContract.do';
URL.GetContractLog = '/upkeep/contract/contract!getContractLog.do';
URL.GetContractsByStatus = '/upkeep/contract/contract!getContractsByStatus.do';

//闪灯
URL.GetContractCount = '/upkeep/contract/contract!getContractCount.do';


/* 保养合同状态 */
MenuNumber.UnfiledContract = 1141;
MenuNumber.ArchivingContract = 1142;
URL.UnfiledContractList = '/upkeep/page/contract/contractList.jsp?menuNO=1141&parentMenuNO=1110';

MenuNumber.IneffectiveStatus = 1151;
MenuNumber.EffectiveStatus = 1152;
MenuNumber.WaitIssueRenewal = 1153;
MenuNumber.IssueRenewal = 1154;
MenuNumber.UnrecoveredExecutive = 1155;
MenuNumber.RecoveredExecutive = 1156;
MenuNumber.TheLastFifteenDays = 1157;
MenuNumber.NotRenewedSuccess = 1158;
MenuNumber.ExecutionCompleted = 1159;
MenuNumber.RenewedSuccess = 1164;

/* 电梯保养管理 */
/** 维保人员**/
URL.GetMaintainerList = '/upkeep/maintainer/maintainer!maintainerList.do';
MenuNumber.UpkeepSiteList = 1430;
MenuNumber.MaintenanceTeamList = 1440;
MenuNumber.MaintenanceBillList = 1460;
MenuNumber.BillChangeList = 1470;

MenuNumber.MaintainerList=1450;
MenuNumber.WorkingMaintainerList=1451;

MenuNumber.MaintainerAdd=14510;
MenuNumber.MaintainerEdit=14511;
MenuNumber.MaintainerDel=14512;
URL.UpkeepSiteList = '/upkeep/page/maintenance/upkeepSiteList.jsp?menuNO=1430';
URL.MaintainerAdd = '/upkeep/maintainer/maintainer!saveMaintainer.do';
URL.MaintainerEdit = '/upkeep/maintainer/maintainer!updateMaintainer.do';
URL.MaintainerDel = '/upkeep/maintainer/maintainer!deleteMaintainer.do';
URL.MaintenanceTeamList = '/upkeep/page/maintenance/maintenanceTeamList.jsp?menuNO=1440';

URL.MaintainerList = '/upkeep/page/maintenance/maintainerList.jsp?menuNO=1451&parentMenuNO=1450';
URL.WorkingMaintainerList = '/upkeep/page/maintenance/maintainerList.jsp?menuNO=1451&parentMenuNO=1450';

URL.MaintenanceBillList = '/upkeep/page/maintenance/maintenanceBillList.jsp?menuNO=1460';
URL.BillChangeList = '/upkeep/page/maintenance/billChangeList.jsp?menuNO=1470';
URL.GetMaintainerInfo = '/upkeep/maintainer/maintainer!getMaintainerById.do';




/**物业单位人员**/

MenuNumber.PropertyPersonAdd=1710;
MenuNumber.PropertyPersonEdit=1701;
MenuNumber.PropertyPersonDel=1702;

URL.PropertyPersonList = '/upkeep/page/maintenance/propertyPersonList.jsp?menuNO=1700';
URL.GetPropertyPersonList = '/upkeep/property/person!getPropertyPersonList.do';
URL.PropertyPersonAdd = '/upkeep/property/person!savePropertyPerson.do';
URL.PropertyPersonEdit = '/upkeep/property/person!updatePropertyPerson.do';
URL.GetPropertyPersonInfo = '/upkeep/property/person!getPropertyPersonById.do';
URL.DelPropertyPerson = '/upkeep/property/person!deletePropertyPerson.do';

/* 电梯维修管理  困人急修 */
URL.TrapsPeopleMaintainList = '/upkeep/page/repair/trapsPeopleMaintainList.jsp?menuNO=1511&parentMenuNO=1510';
URL.PopupTrapsPeopleMaintainEdit = '/upkeep/page/repair/popupTrapsPeopleMaintainEdit.jsp';
URL.TrapsPeopleMaintainAdd = '/upkeep/page/repair/trapsPeopleMaintainEdit.jsp?menuNO=15111';
URL.TrapsPeopleMaintainEdit = '/upkeep/page/repair/trapsPeopleMaintainEdit.jsp?menuNO=15111';
URL.GetTrappedPeopleList = '/upkeep/traps/trapsPeopleMaintain!list.do';
/* 获取困人急修列表 */
URL.GetTrapsPeopleById = '/upkeep/traps/trapsPeopleMaintain!getById.do';
URL.GetTrapsPeopleByElevId = '/upkeep/traps/trapsPeopleMaintain!getTrapsListByElevId.do';
URL.GetSelectedPeopleByTrapsId = '/upkeep/traps/trapsPeopleMaintain!getMaintainerBytrapsId.do';
URL.GetMaintainerListByUpkeepCompanyId= '/upkeep/maintainer/maintainer!getMaintainerListByCompany.do';
URL.AddTrapsPeopleMaintain = '/upkeep/traps/trapsPeopleMaintain!add.do';
URL.EditTrapsPeopleMaintain = '/upkeep/traps/trapsPeopleMaintain!edit.do';
URL.GetTrapsCounts = '/upkeep/traps/trapsPeopleMaintain!getTrapsCounts.do';

MenuNumber.TrapsPeopleMaintainList = 1510;
MenuNumber.TrapsPeopleMaintainAdd = 15110;
MenuNumber.TrapsPeopleMaintainEdit = 15111;
MenuNumber.TrapsPeopleMaintainListOfWaitting = 1511;
MenuNumber.TrapsPeopleMaintainListOfCompleted = 1512;

/* 非困人急修 */
MenuNumber.NonTrapsMonadMaintainList = 1520;
URL.NonTrapsMonadList = '/upkeep/page/repair/nonTrapsMonadMaintainList.jsp?menuNO=1521&parentMenuNO=1520';
URL.NonTrapsPeopleList = '/upkeep/page/repair/nonTrapsPeopleMaintainList.jsp?menuNO=1521&parentMenuNO=1520';
URL.MonadLNonTrapsist = '/upkeep/nontraps/nonTrapsPeopleMaintain!list.do';
URL.PeopleNonTrapsList = '/upkeep/nontraps/nonTrapsPeopleMaintain!maintainerList.do';

URL.GetNontrapsPeopleById = '/upkeep/nontraps/nonTrapsPeopleMaintain!getById.do';
URL.GetNontrapsPeopleByElevId = '/upkeep/nontraps/nonTrapsPeopleMaintain!getTrapsListByElevId.do';
URL.GetSelectedPeopleByNontrapsId = '/upkeep/nontraps/nonTrapsPeopleMaintain!getMaintainerByNontrapsId.do';

MenuNumber.NonTrapsPeopleMaintainOfWaittingAdd = 15211;
MenuNumber.NonTrapsPeopleMaintainOfWaittingEdit = 15210;

URL.NonTrapsPeopleMaintainOfWaittingAdd = '/upkeep/page/repair/nonTrapsPeopleMaintainEdit.jsp?menuNO=15211';
URL.NonTrapsPeopleMaintainOfWaittingEdit = '/upkeep/page/repair/nonTrapsPeopleMaintainEdit.jsp?menuNO=15210';

URL.WaittingAddNonTrapsPeopleMaintain = '/upkeep/nontraps/nonTrapsPeopleMaintain!add.do';
URL.WaittingEditTrapsPeopleMaintain = '/upkeep/nontraps/nonTrapsPeopleMaintain!edit.do';

MenuNumber.NonTrapsPeopleMaintainListOfWaitting = 1521;
MenuNumber.NonTrapsPeopleMaintainListOfFiveMinutesNotAccepted = 1523;
MenuNumber.NonTrapsPeopleMaintainListOfThirtyMinutesNotCheckOut = 1524;
MenuNumber.NonTrapsPeopleMaintainListOfOneHourNotCheckOut = 1525;
MenuNumber.NonTrapsPeopleMaintainListOfTwoHoursNotCheckIn = 1526;
MenuNumber.NonTrapsPeopleMaintainListOfChecking = 1527;
MenuNumber.NonTrapsPeopleMaintainListOfCompleted = 1528;


URL.PlanMaintainList = '/upkeep/page/repair/planMaintainList.jsp?menuNO=1530';
MenuNumber.PlanMaintainList = 1530;

URL.RepairChangeList = '/upkeep/page/repair/repairChangeList.jsp?menuNO=1540';
MenuNumber.RepairChangeList = 1540;




/* 电梯配件管理  */
MenuNumber.ElevatorPartsList = 1300;
URL.ElevatorPartsList = '/upkeep/page/parts/elevatorPartsList.jsp?menuNO=1300';

/* 电梯年检管理 */
URL.YearInspectionList = '/upkeep/page/inspection/yearInspectionList.jsp?menuNO=1900';
URL.YearInspectionEdit = '/upkeep/page/inspection/yearInspectionEdit.jsp?menuNO=1901';
URL.YearInspectionLogList = '/upkeep/page/inspection/yearInspectionLogList.jsp?menuNO=1902';
URL.GetYearInspectionList ='/upkeep/inspection/inspection!inspectionList.do';
URL.YearInspectionRecordEdit = '/upkeep/page/inspection/YearInspectionRecordEdit.jsp?menuNO=1904';
URL.EditYearInspection ='/upkeep/inspection/inspection!checkIn.do';
URL.GETInspectionInfo = '/upkeep/inspection/inspection!getInspectionInfo.do';
URL.GetYearInspectionLogList ='/upkeep/inspection/inspection!inspectionLogList.do';
URL.ImportInspection = '/upkeep/inspection/inspection!importInspection.do';
URL.SaveInsoectionRecordStatus = '/upkeep/inspection/inspection!saveRecordStatus.do';
URL.GetInspectionProcessList = '/upkeep/inspection/inspection!getInspectionProcessList.do';

MenuNumber.YearInspectionList = 1900;
MenuNumber.EditYearInspection = 1901;
MenuNumber.YearInspectionLogList = 1902;
MenuNumber.YearInspectionLogView = 19020;
MenuNumber.YearInspectionBatchImport = 1903;
MenuNumber.YearInspectionRecordEdit = 1904;

/* 二维码管理 */
MenuNumber.QRcodeList = 1600;
URL.QRcodeList = '/baseinfo/page/qrcode/qrcodeList.jsp?menuNO=2750';
URL.GetQRcodeList = '/baseinfo/qrcode/qrcode!qrcodeList.do';
URL.GetNotUseQrcodeList = '/baseinfo/qrcode/qrcode!getNotUseQrcodeList.do';
/* 批量添加二维码 */
MenuNumber.AddBatchQrcode = 1610;
URL.AddBatchQrcode = '/baseinfo/qrcode/qrcode!addBatchQrcode.do';

MenuNumber.qrcodeNotUseList = 1602;
URL.qrcodeNotUseList = '/baseinfo/page/qrcode/qrcodeNotUseList.jsp?menuNO=1602&parentMenuNO=1600';


/* 用户管理 */
URL.UserList = '/sys/page/user/userList.jsp?menuNO=4300';
URL.UserAdd = '/sys/page/user/userEdit.jsp?menuNO=4301';
URL.UserEdit = '/sys/page/user/userEdit.jsp?menuNO=4302';
URL.UserRoleEdit = '/sys/page/user/userRoleEdit.jsp?menuNO=4303';
URL.GetUserList = '/sys/user/user!userList.do';
URL.GetAllUserList = '/sys/user/user!userAllList.do';
URL.GetUserSelect = '/sys/user/user!getUserSelect.do';
URL.GetUserById = '/sys/user/user!getById.do';
URL.AddUser = '/sys/user/user!save.do';
URL.EditUser = '/sys/user/user!update.do';
URL.EditUserRole = '/sys/user/user!grantUserRole.do';
URL.DelUser = '/sys/user/user!delete.do';
URL.SetPassword = '/sys/user/user!doNotNeedSecurity_updateCurrentPwd.do';
URL.ExportUser = '/sys/user/user!exportUserExcel.do';

MenuNumber.UserList = 4300;
MenuNumber.UserAdd = 4301;
MenuNumber.UserEdit = 4302;
MenuNumber.UserRoleEdit = 4303;
MenuNumber.UserDel = 4308;
MenuNumber.ExportUser = 4305;


/* 角色管理 */
URL.RoleList = '/sys/page/role/roleList.jsp?menuNO=4200';
URL.RoleAdd = '/sys/page/role/roleEdit.jsp?menuNO=4201';
URL.RoleEdit = '/sys/page/role/roleEdit.jsp?menuNO=4202';
URL.RoleMenuEdit = '/sys/page/role/roleMenuEdit.jsp?menuNO=4203';
URL.GetRoleListByUserId = '/sys/role/role!getRoleListByUserId.do';
URL.GetRoleList = '/sys/role/role!roleList.do';
URL.GetRoleById = '/sys/role/role!getById.do';
URL.AddRole = '/sys/role/role!save.do';
URL.EditRole = '/sys/role/role!update.do';
URL.DelRole = '/sys/role/role!delete.do';
URL.EditRoleMenu = '/sys/role/role!grantRoleMenu.do';

MenuNumber.RoleList = 4200;
MenuNumber.RoleAdd = 4201;
MenuNumber.RoleEdit = 4202;
MenuNumber.RoleMenuEdit = 4203;
MenuNumber.RoleDel = 4208;

/* 菜单管理 */
URL.MenuList = '/sys/page/menu/menuList.jsp?menuNO=5200';
URL.MenuAdd = '/sys/page/menu/menuEdit.jsp?menuNO=5201';
URL.MenuEdit = '/sys/page/menu/menuEdit.jsp?menuNO=5202';
URL.GetMenuList = '/sys/menu/menu!menuList.do';
URL.GetMenuTree = '/sys/menu/menu!getMenuTree.do';
URL.AddMenu = '/sys/menu/menu!save.do';
URL.EditMenu = '/sys/menu/menu!update.do';
URL.DelMenu = '/sys/menu/menu!delete.do';

/* 保养项管理 */
URL.UpkeepItemList = '/baseinfo/page/upkeep/upkeepItemList.jsp?menuNO=2100';
URL.UpkeepItemAdd = '/baseinfo/page/upkeep/upkeepItemEdit.jsp?menuNO=2101';
URL.UpkeepItemEdit = '/baseinfo/page/upkeep/upkeepItemEdit.jsp?menuNO=2102';
URL.GetUpkeepItemList = '/baseinfo/upkeep/upkeepItem!upkeepItemList.do';
URL.GetUpkeepItemListByUpkeepTypeId= '/baseinfo/upkeep/upkeepItem!findItemListByType.do';
URL.GetUpkeepItemById = '/baseinfo/upkeep/upkeepItem!getById.do';
URL.AddUpkeepItem = '/baseinfo/upkeep/upkeepItem!save.do';
URL.EditUpkeepItem = '/baseinfo/upkeep/upkeepItem!update.do';
URL.DelUpkeepItem = '/baseinfo/upkeep/upkeepItem!delete.do';

MenuNumber.UpkeepItemList = 2100;
MenuNumber.UpkeepItemAdd = 2101;
MenuNumber.UpkeepItemEdit = 2102;
MenuNumber.UpkeepItemDel = 2108;

/* 保养类型管理 */
URL.UpkeepTypeList = '/baseinfo/page/upkeep/upkeepTypeList.jsp?menuNO=2110';
URL.UpkeepTypeAdd = '/baseinfo/page/upkeep/upkeepTypeEdit.jsp?menuNO=2111';
URL.UpkeepTypeEdit = '/baseinfo/page/upkeep/upkeepTypeEdit.jsp?menuNO=2112';
URL.UpkeepTypeItemEdit = '/baseinfo/page/upkeep/upkeepTypeItemEdit.jsp?menuNO=2113';
URL.GetUpkeepTypeList = '/baseinfo/upkeep/upkeepType!upkeepTypeList.do';
URL.GetUpkeepTypeById = '/baseinfo/upkeep/upkeepType!getById.do';
URL.AddUpkeepType = '/baseinfo/upkeep/upkeepType!save.do';
URL.EditUpkeepType = '/baseinfo/upkeep/upkeepType!update.do';
URL.EditUpkeepTypeItem = '/baseinfo/upkeep/upkeepType!grantTypeItem.do';
URL.DelUpkeepType = '/baseinfo/upkeep/upkeepType!delete.do';

MenuNumber.UpkeepTypeList = 2110;
MenuNumber.UpkeepTypeAdd = 2111;
MenuNumber.UpkeepTypeEdit = 2112;
MenuNumber.UpkeepTypeItemEdit = 2113;
MenuNumber.UpkeepTypeDel = 2118;

/* 电梯管理 */
URL.GetSeniorSearchElevList = '/baseinfo/elevator/elevator!seniorSearchElevList.do';
URL.GetSeniorSearchElevUpkeepList = '/baseinfo/elevator/elevator!seniorSearchElevUpkeepList.do';


URL.GetElevatorList = '/baseinfo/elevator/elevator!elevList.do';
URL.GetElevList = '/baseinfo/elevator/elevator!getElevList.do';

URL.GetElevatorMinList = '/baseinfo/elevator/elevator!elevMinList.do';
URL.GetElevatorById = '/baseinfo/elevator/elevator!getById.do';
URL.GetElevatorAllById = '/baseinfo/elevator/elevator!getAllById.do';
URL.GetRelationById = '/baseinfo/elevator/elevator!getRelationById.do';
URL.GETElevListByIds ='/baseinfo/elevator/elevator!getElevListByIds.do';
URL.GetElevInstallById = '/baseinfo/elevator/elevator!getElevInstallById.do';
URL.GetElevFactoryById = '/baseinfo/elevator/elevator!getElevFactoryById.do';
URL.GetElevUpkeepById = '/baseinfo/elevator/elevator!getElevUpKeepById.do';
URL.GetElevatorFloorControl='/baseinfo/floor/compare!getFloorList.do';
URL.GetDisplayFloorMap = '/baseinfo/floor/compare!getDisplayFloorMap.do';
URL.GetSynchronizationFloor = '/monitor/data/realTimeData!initCtrlTypeFlooar.do';
URL.GetElevatorUniqueOutfactoryNo= '/baseinfo/elevator/elevator!getfactoryNoUnique.do'
	
URL.ElevatorList = '/baseinfo/page/elevator/elevatorList.jsp?menuNO=2350';
URL.ElevatorView = '/baseinfo/page/elevator/elevatorView.jsp?menuNO=2301';
URL.ElevatorOutFactoryList = '/baseinfo/page/elevator/elevatorOutFactoryList.jsp?menuNO=2310';
URL.ElevatorOutFactoryAdd = '/baseinfo/page/elevator/elevatorOutFactoryEdd.jsp?menuNO=2311';
URL.ElevatorOutFactoryEdit = '/baseinfo/page/elevator/elevatorOutFactoryEdit.jsp?menuNO=2312';
URL.ElevatorFloorControl = '/baseinfo/page/elevator/ElevatorFloorControl.jsp?menuNO=2319';

URL.ElevatorOutFactoryInstallEdit = '/baseinfo/page/elevator/elevatorInstallEdit.jsp?menuNO=2313';
URL.GetElevatorListByOutFactory = '/baseinfo/elevator/elevator!elevByFactoryList.do';
URL.EditElevatorOutFactory = '/baseinfo/elevator/elevator!setOutFactoryInfo.do';

URL.AddElevatorOutFactory = '/baseinfo/elevator/elevator!setOutFactoryInfo.do';
URL.DelElevator = '/baseinfo/elevator/elevator!delete.do';
URL.DelElevFloor = '/baseinfo/floor/compare!doNotNeedSecurity_delete.do';
URL.SaveOrUpdateFloors = '/baseinfo/floor/compare!saveOrUpdateFloors.do';

URL.ElevatorInstallList = '/baseinfo/page/elevator/elevatorInstallList.jsp?menuNO=2320';
URL.ElevatorInstallEdit = '/baseinfo/page/elevator/elevatorInstallEdit.jsp?menuNO=2321';
URL.GetElevatorListByInstall = '/baseinfo/elevator/elevator!elevByInstallList.do';
URL.EditElevatorInstall = '/baseinfo/elevator/elevator!setInstallInfo.do';


URL.ElevatorUpkeepList = '/baseinfo/page/elevator/elevatorUpkeepList.jsp?menuNO=2330';
URL.ElevatorUpkeepEdit = '/baseinfo/page/elevator/elevatorUpkeepEdit.jsp?menuNO=2332';
URL.GetElevatorListByUpkeep = '/baseinfo/elevator/elevator!elevByUpkeepList.do';
URL.EditElevatorUpkeep = '/baseinfo/elevator/elevator!setUpkeepInfo.do';
URL.EditElevatorBatchUpkeep = '/baseinfo/elevator/elevator!setUpkeepBatchInfo.do';


URL.ElevatorPartitionList = '/baseinfo/page/elevator/elevatorPartitionList.jsp?menuNO=2340';
URL.EditElevatorPartition = '/baseinfo/elevator/elevator!updateUpkeepCompany.do';

URL.GetElevatorMonitorList = '/baseinfo/elevator/elevator!elevMonitorList.do';
URL.GetElevatorMonitorListOfMyFavorite = '/baseinfo/elevator/elevator!elevMonitorByFavoriteList.do';
URL.GetElevatorMonitorListOfVideo = '/baseinfo/elevator/elevator!elevMonitorByVideoList.do';
URL.AddElevatorMonitorOfMyFavorite = '/baseinfo/elevator/elevator!doNotNeedSecurity_favorite.do';
URL.CancelElevatorMonitorOfMyFavorite = '/baseinfo/elevator/elevator!doNotNeedSecurity_deleteFavorite.do';

/**电梯配件管理*/
URL.PartNameIdList="/baseinfo/elevator/elevator!getElevatorPartsNameList.do";
URL.GetElevatorPartsList="/baseinfo/elevator/elevator!getElevatorPartsList.do";
URL.AddElevatorParts="/baseinfo/elevator/elevator!setPartsInfo.do";
URL.GetElevatorParts="/baseinfo/elevator/elevator!getPartsInfo.do";
URL.DelElevatorParts = '/baseinfo/elevator/elevator!doNotNeedSecurity_deleteParts.do';


MenuNumber.ElevatorList = 2350;
MenuNumber.ElevatorView = 2351;

MenuNumber.ElevatorOutFactoryList = 2310;
MenuNumber.ElevatorOutFactoryAdd = 2311;
MenuNumber.ElevatorOutFactoryEdit = 2312;
MenuNumber.ElevatorInstallFactoryEdit = 2313;
MenuNumber.ElevatorPartsEdit = 2314;
MenuNumber.ElevatorViewPartsAdd = 2315;
MenuNumber.ElevatorViewPartsEdit = 2316;
MenuNumber.ElevatorViewPartsDel = 2317;
MenuNumber.ElevatorDel = 2318;
MenuNumber.ElevatorFloorControl = 2319;

MenuNumber.ElevatorInstallList = 2320;
MenuNumber.ElevatorInstallEdit = 2321;

MenuNumber.ElevatorUpkeepList = 2330;
MenuNumber.ElevatorUpkeepEdit = 2331;

MenuNumber.ElevatorPartitionList = 2340;
MenuNumber.ElevatorPartitionEdit = 2341;

/* 批量编辑维保信息 */
MenuNumber.ElevatorUpkeepBatchEdit = 2380;
URL.ElevatorUpkeepBatchEdit = '/baseinfo/page/elevator/elevatorUpkeepBatchEdit.jsp?menuNO=2380';
URL.BatchEditElevatorUpkeep = '/baseinfo/elevator/elevator!setUpkeepBatchInfo.do';

//电梯分配给单位
URL.ElevatorAllocationList = '/baseinfo/page/elevator/elevatorAllocationList.jsp?menuNO=2370';
URL.ElevatorAllocationEdit = '/baseinfo/page/elevator/elevatorAllocationEdit.jsp?menuNO=2371';

URL.GETElevatorAllocationList = '/baseinfo/elevator/elevator!elevList.do';
URL.EditElevatorAllocation = '/baseinfo/elevator/elevAllocation!elevAllocation.do';
URL.GetCompanyListByAllocation = '/baseinfo/elevator/elevAllocation!getCompanyListByAllocation.do';
MenuNumber.ElevatorAllocationList = 2370;
MenuNumber.EditElevatorAllocation = 2371;
MenuNumber.ViewElevatorAllocation = 2372;

/* 楼盘管理 */
URL.BuildingList = '/baseinfo/page/building/buildingList.jsp?menuNO=2650';
URL.BuildingAdd = '/baseinfo/page/building/buildingEdit.jsp?menuNO=2651';
URL.BuildingEdit = '/baseinfo/page/building/buildingEdit.jsp?menuNO=2652';
URL.GetBuildingList = '/baseinfo/building/building!buildingList.do';
URL.GetBuildingById = '/baseinfo/building/building!getById.do';
URL.AddBuilding = '/baseinfo/building/building!save.do';
URL.EditBuilding = '/baseinfo/building/building!update.do';
URL.DelBuilding = '/baseinfo/building/building!delete.do';

MenuNumber.BuildingList = 2650;
MenuNumber.BuildingAdd = 2651;
MenuNumber.BuildingEdit = 2652;
MenuNumber.BuildingView = 2653;
MenuNumber.BuildingDel = 2658;


/* 安装维保公司管理 */
URL.CompanyList = '/baseinfo/page/company/companyList.jsp?menuNO=2410';
URL.InstallCompanyList = '/baseinfo/page/company/companyList.jsp?menuNO=2440';
URL.UpkeepCompanyList = '/baseinfo/page/company/companyList.jsp?menuNO=2430';
URL.OwnerCompanyList = '/baseinfo/page/company/companyList.jsp?menuNO=2460';
URL.PropertyCompanyList = '/baseinfo/page/company/companyList.jsp?menuNO=2450';
URL.CustomerCompanyList = '/baseinfo/page/company/companyList.jsp?menuNO=2410';
URL.CompanyAdd = '/baseinfo/page/company/companyEdit.jsp?menuNO=2411';
URL.CompanyEdit = '/baseinfo/page/company/companyEdit.jsp?menuNO=2412';
URL.CompanyStationList = '/baseinfo/page/station/stationList.jsp?menuNO=2413';
URL.GetCompanyList = '/baseinfo/company/company!companyList.do';//安装公司
URL.GetUpKeepCompanyList = '/baseinfo/company/company!companyList.do';//维保公司

URL.GetParentCompanyList = '/baseinfo/company/company!parentCompanyList.do';
URL.GetCompanyById = '/baseinfo/company/company!getCompany.do';
URL.AddCompany = '/baseinfo/company/company!saveCompany.do';
URL.EditCompany = '/baseinfo/company/company!updateCompany.do';
URL.DelCompany = '/baseinfo/company/company!deleteCompany.do';

URL.GetCompanyIncludeElev='/baseinfo/company/company!getCompanyIncludeElev.do';

/**
 * 使用单位
 */
MenuNumber.CompanyList = 2410;
MenuNumber.CompanyStationList = 2413;
MenuNumber.CompanyAdd = 2411;
MenuNumber.CompanyEdit = 2412;
MenuNumber.CompanyView = 2414;
MenuNumber.CompanyDel = 2418;
/**
 * 维保单位
 */
MenuNumber.CompanyUpkeepList = 2430;
MenuNumber.CompanyUpkeepAdd = 2431;
MenuNumber.CompanyUpkeepEdit = 2432;
MenuNumber.CompanyUpkeepDel = 2433;
MenuNumber.CompanyUpkeepView = 2434;

/**
 * 安装单位
 */
MenuNumber.CompanyManList = 2440;
MenuNumber.CompanyManAdd = 2441;
MenuNumber.CompanyManEdit = 2442;
MenuNumber.CompanyManDel = 2443;
MenuNumber.CompanyManView = 2444;

/**
 * 物业单位
 */
MenuNumber.CompanyWList = 2450;
MenuNumber.CompanyWAdd = 2451;
MenuNumber.CompanyWEdit = 2452;
MenuNumber.CompanyWDel = 2453;
MenuNumber.CompanyWView = 2454;

/**
 * 产权单位
 */
MenuNumber.CompanyCList = 2460;
MenuNumber.CompanyCAdd = 2461;
MenuNumber.CompanyCEdit = 2462;
MenuNumber.CompanyCDel = 2463;
MenuNumber.CompanyCView = 2464;

/* 站点管理 */
URL.StationList = '/upkeep/page/maintenance/stationList.jsp?menuNO=1430';
/*URL.StationAdd = '/baseinfo/page/station/stationEdit.jsp?menuNO=2421';
URL.StationEdit = '/baseinfo/page/station/stationEdit.jsp?menuNO=2422';*/
URL.GetStationList = '/upkeep/upkeep/station!stationList.do';
URL.GetStationById = '/upkeep/upkeep/station!getById.do';
URL.AddStation = '/upkeep/upkeep/station!saveStation.do';
URL.EditStation = '/upkeep/upkeep/station!updateStation.do';
URL.GetStationByCompanyId = '/upkeep/upkeep/station!getStation.do';
URL.DelStation = '/upkeep/upkeep/station!deleteStation.do';


URL.ImportStation = '/upkeep/upkeep/station!importStation.do';


MenuNumber.StationList = 1430;
MenuNumber.StationAdd = 1431;
MenuNumber.StationEdit = 1432;
MenuNumber.StationView = 1433;
MenuNumber.StationDel = 1434;
MenuNumber.StationImport = 1435;

URL.StationView = '/upkeep/page/maintenance/stationView.jsp?menuNO=1430';

URL.GetStationDetail = '/upkeep/upkeep/station!getStationDetail.do';


/*	维保班组 */
MenuNumber.MaintenanceGroupList = 1440;
MenuNumber.MaintenanceGroupAdd = 1441;
MenuNumber.MaintenanceGroupEdit = 1443;
MenuNumber.MaintenanceGroupView = 1444;
MenuNumber.MaintenanceGroupDel = 1445;
MenuNumber.MaintenanceGroupImport = 1442;

URL.MaintenanceGroupList = '/upkeep/page/maintenance/maintenanceGroupList.jsp?menuNO=1440';
URL.GetMaintenanceGroupList = '/upkeep/maintenance/group!MaintenanceGroupList.do';
URL.AddMaintenanceGroup = '/upkeep/maintenance/group!save.do';
URL.EditMaintenanceGroup = '/upkeep/maintenance/group!update.do';
URL.GetMaintenanceGroupById = '/upkeep/maintenance/group!getById.do';
URL.DelMaintenanceGroup = '/upkeep/maintenance/group!delete.do';
URL.ImportMaintenanceGroup = '/upkeep/maintenance/group!importGroup.do';
URL.GetGroupByStationId = '/upkeep/maintenance/group!getGroupByStationId.do';



URL.MaintenanceGroupView = '/upkeep/page/maintenance/maintenanceGroupView.jsp?menuNO=1444';
URL.GetMaintenanceGroupDetail = '/upkeep/maintenance/group!getMaintenanceGroupDetail.do';


/* 设备管理 */
URL.DeviceList = '/baseinfo/page/device/deviceList.jsp?menuNO=2700';
URL.DeviceElevatorEdit = '/baseinfo/page/device/TargetDeviceElevatorEdit.jsp?menuNO=2702';
URL.DeviceCompanyEdit = '/baseinfo/page/device/TargetDeviceCompanyEdit.jsp?menuNO=2703';
URL.GetTargetDeviceList = '/baseinfo/device/targetDevice!targetDeviceList.do';
URL.GetTargetDeviceById = '/baseinfo/device/targetDevice!getById.do';
URL.GetTargetDeviceListByCollectIds = '/baseinfo/device/targetDevice!getTargetDeviceListById.do';
URL.EditDevice = '/baseinfo/device/targetDevice!updateDeviceName.do';
URL.EditDeviceElevator = '/baseinfo/device/targetDevice!saveElevDevice.do';
URL.EditDeviceCompany = '/baseinfo/device/targetDevice!updateDeviceCompany.do';
URL.RemoveDeviceElevator = '/baseinfo/device/targetDevice!removeElevDevice.do';

MenuNumber.DeviceList = 2700;
MenuNumber.DeviceElevatorEdit = 2702;
MenuNumber.DeviceCompanyEdit = 2703;
MenuNumber.DeviceNameEdit = 2704;
MenuNumber.DeviceElevatorRemove = 2705;

/* 制造厂商管理 */
URL.ManufacturerList = '/baseinfo/page/manufacturer/manufacturerList.jsp?menuNO=2800';
URL.ManufacturerAdd = '/baseinfo/page/manufacturer/manufacturerEdit.jsp?menuNO=2801';
URL.ManufacturerEdit = '/baseinfo/page/manufacturer/manufacturerEdit.jsp?menuNO=2802';
URL.GetManufacturerList = '/baseinfo/company/manufacturer!manufacturerList.do';
URL.GetManufacturerById = '/baseinfo/company/manufacturer!getById.do';
URL.AddManufacturer = '/baseinfo/company/manufacturer!saveManufacturer.do';
URL.EditManufacturer = '/baseinfo/company/manufacturer!updateManufacturer.do';
URL.DelManufacturer = '/baseinfo/company/manufacturer!deleteManufacturer.do';

MenuNumber.ManufacturerList = 2800;
MenuNumber.ManufacturerAdd = 2801;
MenuNumber.ManufacturerEdit = 2802;
MenuNumber.ManufacturerDel = 2808;

/* 故障代码级别管理 */
URL.FailureLevelList = '/baseinfo/page/failureLevel/failureLevelList.jsp?menuNO=2900';
URL.FailureLevelAdd = '/baseinfo/page/failureLevel/failureLevelEdit.jsp?menuNO=2901';
URL.FailureLevelEdit = '/baseinfo/page/failureLevel/failureLevelEdit.jsp?menuNO=2902';
URL.GetFailureLevelList = '/baseinfo/fault/failureLevel!failureLevelList.do';
URL.GetFailureLevelById = '/baseinfo/fault/failureLevel!getById.do';
URL.AddFailureLevel = '/baseinfo/fault/failureLevel!saveFailureLevel.do';
URL.EditFailureLevel = '/baseinfo/fault/failureLevel!updateFailureLevel.do';
URL.DelFailureLevel = '/baseinfo/fault/failureLevel!deleteFailureLevel.do';

URL.GetFailureLevelMeth = '/baseinfo/fault/failureLevel!getFailMethByLevelId.do';
//URL.FailureLevelMethEdit='/monitor/data/ctrlEvent!doNotNeedSessionAndSecurity_testCtrlEvent.do';
URL.FailureLevelMethEdit = '/baseinfo/fault/failureLevel!updateFailureLevelMethold.do';
MenuNumber.FailureLevelList = 2900;
MenuNumber.FailureLevelAdd = 2901;
MenuNumber.FailureLevelEdit = 2902;
MenuNumber.FailureLevelMeth = 2903;
MenuNumber.FailureLevelDel = 2908;

/* 故障代码管理 */
URL.FailureCodeList = '/baseinfo/page/failureCode/failureCodeList.jsp?menuNO=2950';
URL.FailureCodeAdd = '/baseinfo/page/failureCode/failureCodeEdit.jsp?menuNO=2951';
URL.FailureCodeEdit = '/baseinfo/page/failureCode/failureCodeEdit.jsp?menuNO=2952';
URL.GetFailureCodeList = '/baseinfo/fault/failureCode!failureCodeList.do';
URL.GetFailureCodeById = '/baseinfo/fault/failureCode!getById.do';
URL.AddFailureCode = '/baseinfo/fault/failureCode!saveFailureCode.do';
URL.EditFailureCode = '/baseinfo/fault/failureCode!updateFailureCode.do';
URL.DelFailureCode = '/baseinfo/fault/failureCode!deleteFailureCode.do';
URL.GetCodeByCtrlType = '/baseinfo/fault/failureCode!getCodeByCtrlType.do';
URL.GETCtrlTypeList = '/monitor/data/ctrlType!ctrlTypeList.do';
URL.EditFailureBatchCode='/baseinfo/fault/failureCode!updateFailureBatchCode.do';

MenuNumber.FailureCodeList = 2950;
MenuNumber.FailureCodeAdd = 2951;
MenuNumber.FailureCodeEdit = 2952;
MenuNumber.FailureCodeBatchEdit = 2953;
MenuNumber.FailureCodeDel = 2958;

/* 故障联系人管理 */

MenuNumber.FailureLinkmanList = 6200;
MenuNumber.FailureLinkmanAdd = 6201;
MenuNumber.FailureLinkmanEdit = 6202;
MenuNumber.FailureLinkmanLinkElevator = 6203;
MenuNumber.FailureLinkmanDelete = 6208;

URL.FailureLinkmanList = '/monitor/page/failure/failureLinkmanList.jsp?menuNO=6200';
URL.FailureLinkmanAdd = '/monitor/failure/failureLinkman!save.do';
URL.FailureLinkmanEdit = '/monitor/failure/failureLinkman!update.do';
URL.FailureLinkmanLinkElevator = '/monitor/failure/failureLinkman!relationElevators.do'; 

URL.GETFailureLinkmanList = '/monitor/failure/failureLinkman!linkmanList.do';
//URL.AddFailureLinkman = '/monitor/failure/failureLinkman!save.do';
//URL.EditFailureLinkman = '/monitor/failure/failureLinkman!update.do';
URL.GETFailureLinkmanInfo = '/monitor/failure/failureLinkman!getById.do';
URL.ViewFailureLinkman = '/monitor/failure/failureLinkman!getById.do';
URL.GetElevListByFailureLinkman = '/monitor/failure/failureLinkman!getElevListById.do';
URL.DeleteFailureLinkman = '/monitor/failure/failureLinkman!delete.do';

/* 短信预览管理 */
MenuNumber.MessagePreviewList = 6300;
URL.MessagePreviewList = '/monitor/page/failure/messagePreviewList.jsp?menuNO=6300';

URL.GetSmsTemplateList='/baseinfo/smsTemplate/smsTemplate!getSmsTemplateList.do';
URL.EditSmsTemplate='/baseinfo/smsTemplate/smsTemplate!updateSmsTemplate.do';

MenuNumber.MessagePreviewView = 6310;
MenuNumber.MessageSetMessageDefault = 6320;

/* 常量 */
Constant.SpecialCode = '※';

/* 实时数据消息ID */
MsgId.RunData = 0x0065;
MsgId.Event = 0x0061;
MsgId.VideoHeader = 0x0071;
MsgId.VideoFlow = 0x0072;
MsgId.AudioHeader = 0x0081;
MsgId.AudioFlow = 0x0083;

/* 实时故障监测 */
MenuNumber.MonitorListOfFault = 5800;
MenuNumber.MonitorListOfFaultEnter = 5810;
URL.MonitorListOfFault = '/monitor/page/monitor/monitorListOfFault.jsp?menuNO=5800';

MenuNumber.MonitorListOfMaintain = 5900;
MenuNumber.MonitorListOfEnter = 5910;
URL.MonitorListOfMaintain = '/monitor/page/monitor/monitorListOfMaintain.jsp?menuNO=5900';

/* 实时数据监控 */
URL.MonitorListOfMap = '/monitor/page/monitor/monitorListOfMap.jsp?menuNO=5100';
URL.MonitorList = '/monitor/page/monitor/monitorListOfRealtimeData.jsp?menuNO=5200';
URL.MonitorListOfVideo = '/monitor/page/monitor/monitorListOfVideo.jsp?menuNO=5500';
URL.MonitorOfMyFavorite = '/monitor/page/monitor/monitorListOfMyFavorite.jsp?menuNO=5600';
URL.MonitorOfDetails = '/monitor/page/monitor/monitorOfDetails.jsp?menuNO=5210';
URL.MonitorOfMyVoice = '/monitor/page/monitor/monitorOfVoice.jsp?menuNO=5700';

URL.MonitorOfTerminals = '/monitor/page/monitor/monitorOfTerminals.jsp?menuNO=5211';
URL.MonitorOfFunctionCodes = '/monitor/page/monitor/monitorOfFunctionCodes.jsp?menuNO=5212';
URL.MonitorOfVideo = '/monitor/page/monitor/monitorOfVideo.jsp?menuNO=5213';
URL.MonitorOfEvents = '/monitor/page/monitor/monitorOfEvents.jsp?menuNO=5214';
URL.MonitorOfInfos = '/monitor/page/monitor/monitorOfInfos.jsp?menuNO=5215';

URL.MonitorOfDeviceSignal = '/monitor/data/realTimeData!getDeviceSignal.do';
URL.GetElevCount = '/monitor/map/map!getElevCount.do';
URL.GetElevByArea = '/monitor/map/map!getElevByArea.do';

/* 电梯信息列表 */
MenuNumber.MonitorListOfElevator = 5950;
MenuNumber.MonitorListOfElevatorOfEnter = 59500;
URL.MonitorListOfElevator = '/monitor/page/monitor/monitorListOfElevator.jsp?menuNO=5950';

/* 实时视频下载、查看帮助 */
URL.MedioOcxOldVersion='http://www.dataserver.cn/mms/VideoOcx/MediaOcx.cab#version=1,0,0,17';
URL.MediaOcx='http://www.dataserver.cn/mms/VideoOcx/MediaOcx.msi ';
URL.AudioOcxVersion='http://www.dataserver.cn/mms/VideoOcx/MediaOcx.cab#version=1,0,0,11';

URL.WebsocketSwf='/sys/flash/WebSocketMain.swf';

URL.VideoDownloadHelp='/monitor/page/monitor/videoDownloadHelp.jsp';

/**模板下载地址**/
URL.StationTemplateDownload = '/upkeep/excel/stationTemplate.xls';
URL.GroupTemplateDownload = '/upkeep/excel/groupTemplate.xls';
URL.YearInspectionTemplateDownload = '/upkeep/excel/yearInspectionTemplate.xls';
URL.UpkeepInfoTemplateDownload ='/upkeep/excel/upkeepInfoTemplate.xls';

/**监控管理*/
URL.GetMonitorListOfVideo = '/baseinfo/elevator/elevator!elevMonitorByVideoList.do';
URL.GetVoiceMonitorList = '/baseinfo/elevator/elevator!voiceMonitorList.do';
URL.AccpetVoiceMonitor = '/baseinfo/elevator/elevator!accpet.do';
URL.GetFailCodeMonitorList = '/monitor/monitor/query!failCodeMonitorList.do';
URL.GetMaintainMonitorList = '/monitor/monitor/query!maintainMonitorList.do';


MenuNumber.MonitorOfTerminals = 5211;
MenuNumber.MonitorOfFunctionCodes = 5212;
MenuNumber.MonitorOfVideo = 5213;
MenuNumber.MonitorOfEvents = 5214;
MenuNumber.MonitorOfInfos = 5215;
MenuNumber.MonitorOfFunctionInput = 5216;

MenuNumber.Maintenance = 5217;
MenuNumber.FailurCode = 5218;
MenuNumber.OverLoad = 5219;
MenuNumber.FullLoad = 5220;
MenuNumber.Fire = 5221;
MenuNumber.Locklift = 5222;
MenuNumber.DoorPositions = 5223;
MenuNumber.FailureTrapped = 5224;
MenuNumber.Communicate = 5225;
MenuNumber.ControllerPassword = 5226;
MenuNumber.Power = 5227;
MenuNumber.HasPeople = 5228;
MenuNumber.Overspeeds = 5229;
MenuNumber.Voice = 5230;
MenuNumber.Floor = 2231;
MenuNumber.Direction = 2232;
MenuNumber.Door = 2233;
MenuNumber.ReportTime = 2234;
MenuNumber.InternetType = 2235;
MenuNumber.SignalStrength = 2236;
MenuNumber.Run = 5227;
MenuNumber.MonitorOfVoice = 5710;

/* 故障列表 */
URL.CtrlEventList = '/monitor/page/data/ctrlEventList.jsp?menuNO=5300';
URL.getCtrlEventList = '/monitor/data/ctrlEvent!ctrlEventList.do';
URL.ExportEvent = '/monitor/data/ctrlEvent!exportEventExcel.do';

/* 导出 excel */
MenuNumber.ExportEvent =6101;
/* 处理 */
MenuNumber.HandleEventOperate = 6102;

MenuNumber.ExportElevEvent =52141;

/* 救援单位管理 */
URL.RescueUnitList = '/monitor/page/control/rescueUnitList.jsp?menuNO=5400';
URL.RescueUnitAdd = '/monitor/page/control/rescueUnitEdit.jsp?menuNO=5401';
URL.RescueUnitEdit = '/monitor/page/control/rescueUnitEdit.jsp?menuNO=5402';
URL.GetRescueUnitList = '/monitor/control/rescueUnit!rescueUnitList.do';
URL.GetRescueUnitById = '/monitor/control/rescueUnit!getById.do';
URL.GetRescueUnitByElevId = '/monitor/control/rescueUnit!getByElevId.do';

MenuNumber.RescueUnitList = 5400;
MenuNumber.RescueUnitAdd = 5401;
MenuNumber.RescueUnitEdit = 5402;

/* 远程指令 */
URL.GetCmdLibList = '/monitor/control/cmdLib!cmdLibList.do';


/* 自定义设置 */
URL.CustomSettingEdit = '/sys/page/setting/customSetEdit.jsp?menuNO=7100';
URL.GetCustomInfo = '/sys/user/user!getSetImgInfo.do';
URL.SetCustomInfo = '/sys/user/user!uploadImg.do';
URL.GetImgInfo = '/sys/user/user!getSetImgInfo.do';
URL.SetImgInfo = '/sys/user/user!uploadImg.do';
URL.HeadImage = '/system/lib/base/images/head.gif';


/* APP管理 */
URL.QrCodeInit='/sys/qrcodeApk/qrcodeApk!qrcodeInit.do';  //初始化二维码
URL.GetUploadApk='/sys/qrcodeApk/qrcodeApk!uploadApk.do'; //上传APK
URL.GetApkVersionInfo='/sys/qrcodeApk/qrcodeApk!apkVersionList.do';//展示上传的历史记录
URL.GetApkVersionList='/sys/page/setting/apkManagerEdit.jsp?menuNO=7200'
URL.GetQrCodeInfo='/sys/images/QrCode.jpg';	//二维码展示路径
URL.GetDownLoadApk='/upload/app/Maintenance.apk';	//二维码下载路径
MenuNumber.getSettingEdit = 7202;	

MenuNumber.CustomSettingEdit = 7100;


/* 南宁的电梯合同管理 */
URL.GetElevContractList = '/upkeep/contract/elevContract!getContractList.do';
URL.GetElevContractById = '/upkeep/contract/elevContract!getContractById.do';
URL.ElevContractList = '/upkeep/page/contract/elevContractList.jsp?menuNO=1143&parentMenuNO=1111';
URL.AddElevContract = '/upkeep/contract/elevContract!addContract.do';
URL.DelElevContract = '/upkeep/contract/elevContract!deleteContract.do';
URL.UpdateElevContract = '/upkeep/contract/elevContract!updateContract.do';
URL.ConfirmElevContract = '/upkeep/contract/elevContract!updateContractStatus.do';
URL.GetElevByRegisterCode = '/upkeep/contract/elevContract!getElevByRegisterCode.do';
URL.GetVoiceList = '/monitor/data/voice!getVoiceList.do';
URL.exportVoiceExcel = '/monitor/data/voice!exportExcel.do';


MenuNumber.DelElevContract = 11431;
MenuNumber.EditElevContract = 11432;
MenuNumber.ConfirmElevContract = 11433;

URL.GetOperationRecordList = '/baseinfo//page/operation/operationRecordList.jsp?menuNO=10001';
MenuNumber.OperationRecordList = 10001;
URL.OperationRecordList  ='/baseinfo/operation/record!OperationRecordList.do';
MenuNumber.OperationRecordExport = 10002;
URL.OperationRecordExport  ='/baseinfo/operation/record!export.do';
MenuNumber.runExcptionList = 10003;
URL.runExcptionList ='/baseinfo/operation/runexception!runExceptionList.do';