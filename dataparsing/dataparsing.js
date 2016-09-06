var protocolJS = require("./protocol");
var Base64 = require("./base64").Base64;

(function () {
    var ProjectName = ProjectName || {}, URL = URL || {}, Constant = Constant || {}, Module = Module || {}, MenuNumber = MenuNumber || {}, MsgId = MsgId || {}, TdSerialType = TdSerialType || {}, TdSerialTag = TdSerialTag || {};

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


    /* 协议类 */
    function Protocol(protocols) {
        if (!hasProtocals()) {
            return;
        }
        this.protocols = protocols || protocolJS.PROTOCOLS;
        this.Map = new Map(this.protocols);
    };
    Protocol.prototype = {
        constructor: Protocol,
        getCtrlTypeVersion: function (ctrlType) {
            return this.Map.getValueByKeyValue('ctrlTypeVersion', 'ctrlType', ctrlType);
        },
        getCtrlTypeDescribe: function (ctrlType) {
            return this.Map.getValueByKeyValue('describe', 'ctrlType', ctrlType);
        },
        getProductFactory: function (ctrlType) {
            return this.Map.getValueByKeyValue('productFactory', 'ctrlType', ctrlType);
        },
        getProtocolTags: function (ctrlType) {
            var ctrlDataProtocolTags = this.Map.getValueByKeyValue('ctrlDataProtocolTags', 'ctrlType', ctrlType);
            if (!isArray(ctrlDataProtocolTags)) {
                return ctrlDataProtocolTags;
            }
            return ctrlDataProtocolTags.sort(function (a, b) {
                return a.value - b.value || ctrlDataProtocolTags.indexOf(a) - ctrlDataProtocolTags.indexOf(b);
            });
        },
        getTagName: function (ctrlType, tagValue) {
            var protocolTags = this.getProtocolTags(ctrlType);
            return this.Map.getValueByKeyValue('name', 'value', tagValue, protocolTags);
        },
        getSamplingPeriod: function (ctrlType, tagValue) {
            var protocolTags = this.getProtocolTags(ctrlType);
            return this.Map.getValueByKeyValue('samplingPeriod', 'value', tagValue, protocolTags);
        },
        getTagDescribe: function (ctrlType, tagValue) {
            var protocolTags = this.getProtocolTags(ctrlType);
            return this.Map.getValueByKeyValue('describe', 'value', tagValue, protocolTags);
        },
        getProtocolValue: function (ctrlType, tagValue) {
            var protocolTags = this.getProtocolTags(ctrlType);
            var protocolValue = this.Map.getValueByKeyValue('ctrlDataProtocolValues', 'value', tagValue, protocolTags);

            if (!isArray(protocolValue)) {
                return protocolValue;
            }

            return protocolValue.sort(function (a, b) {
                return a.seq - b.seq || protocolValue.indexOf(a) - protocolValue.indexOf(b);
            });
        },
        getTagValues: function (ctrlType) {
            var protocolTags = this.getProtocolTags(ctrlType);
            return this.Map.getValuesByKey('value', protocolTags);
        }
    };

    /* 数据源类 */
    function DataSource(dataSource) {
        this.dataSource = null;
        this.realTimeDataArray = null;
        //this.base64 = new Base64();

        if (dataSource) {
            this.init(dataSource);
        }
    };
    DataSource.prototype = {
        constructor: DataSource,
        init: function (data) {
            this.addData(data);
        },
        addData: function (data) {
            if (!data.transferData) {
                return;
            }

            //var realTimeData = '00-2B-00-65-00-00-00-34-50-39-4B-4D-4C-31-41-4A-32-5A-48-41-38-4A-49-01-02-00-10-00-00-00-01-00-32-06-40-06-40-03-E8-13-88-00-3C';
            //var realTimeData = this.base64.decodeHex(data.transferData);
            //this.realTimeDataArray = realTimeData.split('-');

            this.realTimeDataArray = splitStr(data.transferData, 2);
            this.dataSource = data;
        },
        getObj: function () {
            /* 设备运行数据 */
            if (this.getMsgId() == MsgId.RunData) {
                return {
                    'dataLength': this.getDataLength(),
                    'msgId': this.getMsgId(),
                    'protocolVersion': this.getProtocolVersion(),
                    'encryptedVersion': this.getEncryptedVersion(),
                    'securityCodes': this.getSecurityCodes(),
                    'targetDeviceId': this.getTargetDeviceId(23, 24),
                    'tagValue': this.getTagValue(),
                    'getRunDataLength': this.getRunDataLength(),
                    'runDatas': this.getRunDataArray(),
                    'realTimeDataArray': this.getData()
                };
            }

            /* 设备事件数据 */
            if (this.getMsgId() == MsgId.Event) {
                return {
                    'dataLength': this.getDataLength(),
                    'msgId': this.getMsgId(),
                    'protocolVersion': this.getProtocolVersion(),
                    'encryptedVersion': this.getEncryptedVersion(),
                    'targetDeviceId': this.getTargetDeviceId(7, 8),
                    'eventId': this.getEventId(),
                    'eventStauts': this.getEventStatus()
                };
            }

        },
        getData: function () {
            return this.realTimeDataArray;
        },
        getDataLength: function () {
            var result = this.getPartDataArray(0, 2);
            return result ? parseFormat(result.join(''), 1) : 0;
        },
        getMsgId: function () {
            var result = this.getPartDataArray(2, 4);
            return result ? parseFormat(result.join(''), 1) : 0;
        },
        getProtocolVersion: function () {
            var result = this.getPartDataArray(4, 5);
            return result ? parseFormat(result.join(''), 1) : 0;
        },
        getEncryptedVersion: function () {
            var result = this.getPartDataArray(5, 6);
            return result ? parseFormat(result.join(''), 1) : 0;
        },
        getKeepField: function () {
            var result = this.getPartDataArray(6, 7);
            return result ? parseFormat(result.join(''), 1) : 0;
        },
        getTargetDeviceId: function (start, end) {
            if (this.dataSource.tdSerial) {
                return this.dataSource.tdSerial;
            }

            var result = this.getPartDataArray(start, end);
            return result ? parseFormat(result.join(''), 1) : 0;
        },

        /* 安全码 */
        getSecurityCodes: function () {
            return this.getPartDataArray(7, 23);
        },
        /* 目标设备编号 */
        getTdSerial: function () {
            var result = this.getPartDataArray(23, 24);
            return result ? parseFormat(result.join(''), 1) : 0;
        },
        getTagValue: function () {
            var result = this.getPartDataArray(24, 25);
            return result ? parseFormat(result.join(''), 1) : 0;
        },
        getRunDataLength: function () {
            var result = this.getPartDataArray(25, 27);
            return result ? parseFormat(result.join(''), 1) : 0;
        },
        getRunDataArray: function () {
            return this.getPartDataArray(27, 27 + this.getRunDataLength());
        },
        getPartDataArray: function (index, length) {
            return this.realTimeDataArray ? this.realTimeDataArray.slice(index, length) : null;
        },

        /* 设备事件消息 */
        getEventId: function () {
            return this.getPartDataArray(8, 9) * 1;
        },
        getEventStatus: function () {
            return this.getPartDataArray(9, 10) * 1;
        }

    };

    /* 实时数据解析类 */
    function DataParsing(obj) {
        //this.parsedData = [];
        this.parsedData = {};
        this.currentTagValue = 0;
        this.protocol = new Protocol();
        this.base64 = new Base64();
        this.init(obj);
    };

    DataParsing.prototype = {
        constructor: DataParsing,

        /* 初始化函数 */
        init: function (obj) {
            this.addData(obj);
        },

        /* 造数据格式 */
        buildDataFormat: function (id, ctrlType) {
            if (!id || !ctrlType) {
                return;
            }

            var _this = this;
            if (_this.parsedData[id]) {
                return;
            }

            _this.parsedData[id] = {};
            var tagsValue = _this.protocol.getTagValues(ctrlType);
            each(
                tagsValue,
                function (i, v) {
                    _this.parsedData[id][v] = [];
                }
            )
        },

        /**
         * 向对象追加实时数据
         * @input 实时数据源、协议类型组成的Obj:{'id': getID(), dataSource': {...}, 'ctrlType': 1}
         **/
        addData: function (obj) {
            if (!obj) {
                return "";
            }

            var _this = this;
            var id = obj.id;
            var data = obj.dataSource;
            var dataSource = null;
            var ctrlType = obj.ctrlType;

            if (isObject(data)) {
                dataSource = (new DataSource(data)).getObj();

                this.currentTagValue = dataSource.tagValue;
                //console.log('透传数据初步解析：', dataSource);
                _this.buildDataFormat(id, ctrlType);
                if (!dataSource.runDatas.length) {
                    //console.log('运行数据为空，已返回，请检查getRunDataLength', '');
                    return;
                }
                _this.parseData(dataSource.runDatas, id, ctrlType, dataSource.tagValue);
                //console.log('根据协议解析：', _this.parsedData);
            }

            if (isArray(data)) {
                each(
                    data,
                    function (i, o) {
                        dataSource = (new DataSource(o)).getObj();
                        _this.buildDataFormat(id, ctrlType);
                        if (!dataSource.runDatas.length) {
                            //console.log('运行数据为空，已返回，请检查getRunDataLength', '');
                            return;
                        }
                        _this.parseData(dataSource.runDatas, id, ctrlType, dataSource.tagValue);
                        //console.log('根据协议解析：', _this.parsedData);
                    }
                );
            }

            return _this.getAllData()[id][dataSource.tagValue];
        },
        /**
         * 获取解析后实时数据队列中的第一个数据
         * @input 唯一ID
         * @return 数组对象
         **/
        getFirstData: function (id, tagValue) {
            return this.getDataByIdAndTagValue(id, tagValue).shift();
        },

        /**
         * 获取解析后实时数据队列中的所有数据
         * @input 唯一ID
         * @return 数组对象
         **/
        getItemAll: function (id) {
            return this.parsedData[id];
        },

        getAllData: function () {
            return this.parsedData;
        },
        /**
         * 根据ID获取实时数据
         * @input 唯一ID
         * @return 对象
         **/
        getDataByIdAndTagValue: function (id, tagValue) {
            return this.parsedData[id][tagValue];
        },
        /**
         * 根据ID、协议类型、实时数据解析数据添加到队列里
         * @input 唯一ID, 实时数据, 协议类型
         **/
        parseData: function (data, id, ctrlType, tagValue) {
            var _this = this;

            var protocolContent = _this.protocol.getProtocolValue(ctrlType, tagValue);
            if (!protocolContent) {
                return;
            }

            var tagList = _this.getDataByIdAndTagValue(id, tagValue);
            tagList.push(getProtocolsArray(data));

            function getProtocolsArray(d) {
                var list = [];
                each(
                    protocolContent,
                    function (i, o) {
                        list.push({
                            'name': o.name,
                            'value': _this.getValue(o, d),
                            'unit': o.valueUnit || o.valueUnit == 0 ? o.valueUnit : '',
                            'escapeValue': _this.getEscapeValue(o, d),
                            'functionCode': o.functionCode || o.functionCode == 0 ? o.functionCode : '',
                            'transferMethod': o.transferMethod
                        });
                    }
                );

                return list;
            }
        },
        /**
         * 根据转值类型转换后的值，转义方式与转义内容获取最终要显示的值
         * @input 转值类型转换后的值，转义方式，转义内容
         * @return 最终显示在页面的值
         **/
        getTransferContent: function (value, transferMethod, transferContent) {
            /*  transferMethod 转义方式
             1: 不转换
             2: 精度转换
             3: 值枚举
             4: 范围枚举
             5: 日期
             6: 将数字补零后拆分为两位数一组并取值枚举后合并为最终的值
             */
            transferContent = transferContent ? this.base64.decode(transferContent) : null;

            if (transferMethod == 1) {
                return value;
            }

            if (transferMethod == 2) {
                var floatStr = transferContent + '',
                    indexof = floatStr.indexOf('.'),
                    str = floatStr.substr(indexof + 1),
                    len = str.length;

                return (value * transferContent).toFixed(len);
            }

            if (transferMethod == 3) {
                transferContent = isString(transferContent) ? JSON.parse(transferContent) : transferContent;
                return transferContent ? transferContent[value] : '';
            }

            if (transferMethod == 4) {
                transferContent = isString(transferContent) ? JSON.parse(transferContent) : transferContent;
                var fValue = '';

                each(
                    transferContent,
                    function (i, o) {
                        if (value >= o.minValue && value <= o.MaxValue) {
                            fValue = o.value;
                            return false;
                        }
                    }
                );

                return fValue;
            }

            if (transferMethod == 5) {
                return formatDate(value);
            }


            if (transferMethod == 6) {
                value = fillZero(value + '', 4);
                var valueSplit = [value.substring(0, 2), value.substring(2, 4)];
                transferContent = isString(transferContent) ? JSON.parse(transferContent) : transferContent;
                return transferContent ? transferContent[valueSplit[0]] + transferContent[valueSplit[1]] : '';
            }

            return value;
        },
        /**
         * 根据协议获取原始值
         * @input 协议对象，实时数据源
         * @return 原始值
         **/
        getOriginalValue: function (row, data) {

            var byteIndex = row.byteIndex,
                bitIndex = row.bitIndex,
                length = row.length,
                lengthUnit = row.lengthUnit,
                endian = row.endian;
            /*	lengthUnit 长度单位
             1: 字节
             2: bit位
             */
            lengthUnit = lengthUnit || 1;
            if (lengthUnit == 1) {
                var result = data.slice(byteIndex, (byteIndex + length));

                /* 如果小端则翻转 */
                if (endian == 2) {
                    result.reverse();
                }

                return result.join('');
            }

            if (lengthUnit == 2) {
                var str = parseToBits(data)[byteIndex];

                if (!str) {
                    return '';
                }
                return str.substr(str.length - bitIndex - length, length);
            }

            return '';
        },
        /**
         * 获取转义后的值
         * @input 一行协议数据, 实时数据源 '00-01-01-00-00-00-00-00-AF-0A-BE-01-11-00-10-10'
         * @return 转换后的值
         **/
        getEscapeValue: function (row, data) {
            var originalValue = this.getOriginalValue(row, data);
            return parseFormat(originalValue, row.transferDataType);
        },
        /**
         * 获取最终显示值
         * @input 一行协议数据, 实时数据源 '00-01-01-00-00-00-00-00-AF-0A-BE-01-11-00-10-10'
         * @return 转换后的值
         **/
        getValue: function (row, data) {
            var escapeValue = this.getEscapeValue(row, data);
            return this.getTransferContent(escapeValue, row.transferMethod, row.transferContent);
        },
        getCurrentTagValue: function () {
            return this.currentTagValue;
        },
        /* 内召 */
        getInnerData: function (data) {
            if (!data || data.length == 0) {
                return;
            }

            var floors = [];

            for (var i = 0; i < data.length; i++) {
                var v = data[i].value;

                var tobit = fillZero(v.toString(2) + '', 8).split('').reverse(); //翻转

                for (var j = 0; j < tobit.length; j++) {
                    if (tobit[j] == 1) {
                        floors.push((j + tobit.length * i) + 1);
                    }
                }
            }
            return floors;
        },
        /* 外召（上下召）*/
        getOuterData: function (data) {
            if (!data || data.length == 0) {
                return;
            }

            var floorsUp = [],
                floorsDown = [];

            var getGroup = function (str) {
                var newArray = [];

                for (var i = 0; i < str.length / 2; i++) {
                    newArray.push(str.substring(i * 2, i * 2 + 2));
                }
                return newArray;
            }

            for (var i = 0; i < data.length; i++) {
                var v = data[i].value;
                var tobit = fillZero(Number(v).toString(2) + '', 8).split('').reverse().join(''); //翻转

                tobit = getGroup(tobit);
                for (var j = 0; j < tobit.length; j++) {
                    if (tobit[j][0] == 1) {
                        floorsUp.push((j + tobit.length * i) + 1);
                    }
                    if (tobit[j][1] == 1) {
                        floorsDown.push((j + tobit.length * i) + 1);
                    }
                }
            }
            return {
                'callUp': floorsUp,
                'callDown': floorsDown
            };
        }
    }

    /* 地图类 */
    function Map(obj) {
        this.obj = obj;
    };
    Map.prototype = {
        constructor: Map,
        getValueByKey: function (key, obj) {
            obj = obj || this.obj;
            if (!obj) {
                return null;
            }
            return obj[key];
        },
        getValueByKeyValue: function (key, key1, value1, obj) {
            obj = obj || this.obj;
            if (!obj) {
                return null;
            }
            var result = null;

            each(
                obj,
                function (i, o) {
                    if (o[key1] == value1) {
                        result = o[key];
                        return false;
                    }
                }
            );

            return result;
        },
        getValuesByKey: function (key, obj) {
            obj = obj || this.obj;
            if (!obj) {
                return null;
            }
            var result = [];
            var _this = this;
            each(
                obj,
                function (i, o) {
                    result.push(_this.getValueByKey(key, o));
                }
            );

            return result;
        },
        getValuesByKeyValue: function (key, key1, value1, obj) {
            obj = obj || this.obj;
            if (!obj) {
                return null;
            }
            var result = [];
            var _this = this;
            each(
                obj,
                function (i, o) {
                    result.push(_this.getValueByKeyValue(key, key1, value1, o));
                }
            );

            return result;
        }
    };

    /**
     * 转有符号十进制
     * @input 原始值
     * @return 有符号的十进制值
     **/
    function toMinusDecimal(data) {
        var firstBit = data.substr(0, 1);
        if (firstBit == "0") {
            return "+" + parseInt(data.substr(1, data.length - 1), 2).toString();
        }
        var secondBit = data.substr(1, data.length - 1).split('');
        for (var i = 0; i < secondBit.length; i++) {
            if (secondBit[i] == '1') secondBit[i] = '0';
            else secondBit[i] = '1';
        }

        var jinwei = false;
        for (var i = secondBit.length - 1; i > 0; i--) {
            if (i == secondBit.length - 1) {
                if (secondBit[i] == '1') {
                    jinwei = true;
                    secondBit[i] = '0';
                } else {
                    secondBit[i] = '1';
                    break;
                }
            } else if (jinwei) {
                if (secondBit[i] == '1') {
                    jinwei = true;
                    secondBit[i] = '0';
                } else {
                    secondBit[i] = '1';
                    break;
                }
            }

        }

        return "-" + parseInt(secondBit.join(''), 2).toString();
    };

    /**
     * 16制作转字符串
     * @input 原始值
     * @return 字符串
     **/
    function hexToString(str) {
        var val = "";
        if (!str) {
            return val;
        }
        var arr = splitStr(str, 2);
        for (var i = 0; i < arr.length; i++) {
            val += String.fromCharCode(arr[i]);
        }

        return val;
    };

    /**
     * 转成8位的二进制
     * @input 二进制
     * @return 8位的二进制
     **/
    function to8Bit(s) {
        var bitStr = parseInt(s, 16).toString(2);

        while (bitStr.length < 8) {
            bitStr = "0" + bitStr;
        }

        return bitStr;
    };

    function each(obj, callback) {
        if (isArray(obj)) {
            for (var i = 0; i < obj.length; i++) {
                callback(i, obj[i]);
            }
            return;
        }

        if (isObject(obj)) {
            for (var i in obj) {
                callback(i, obj[i]);
            }
            return;
        }
    };

    function isArray(obj) {
        return Object.prototype.toString.call(obj) === '[object Array]';
    };

    function isObject(obj) {
        return Object.prototype.toString.call(obj) === '[object Object]';
    };

    function isString(obj) {
        return Object.prototype.toString.call(obj) === '[object String]';
    };

    /**
     * 将实时数据转成字节数组
     * @input 实时数据源
     * @return 实时数据源数组
     **/
    function parseToBytes(data) {
        return data.split('-');
    };

    /**
     * 将实时数据转成bit位数组
     * @input 实时数据源
     * @return 实时数据源转成bit位的数组
     **/
    function parseToBits(data) {
        var byteLength = data.length;
        var bitArray = [];

        if (byteLength == 0) {
            return;
        }

        for (var i = 0; i < byteLength; i++) {
            bitArray.push(to8Bit(data[i]));
        }

        return bitArray;
    };

    /**
     * 解析格式
     * @input 原始值，转值类型
     * @return 转换后的值
     **/
    function parseFormat(value, parseType) {
        /*	parseType 转值类型
         1: 无符号十进制
         2: 有符号十进制
         3: 二进制
         4: 字符串
         5: ASC码
         6: BCD码
         */
        if (value == '') {
            console.log('对应的字节未找到！');
            return '';
        }

        if (parseType == 1) {
            return parseInt(value, 16);
        }
        if (parseType == 2) {
            return toMinusDecimal(value);
        }
        if (parseType == 3) {
            return value;
        }
        if (parseType == 4) {
            return hexToString(value);
        }
        if (parseType == 5) {
            return value;
        }

        return value;
    };

    function formatDate(now) {
        var beijingTime = 8 * 60 * 60,
            date = new Date((now - beijingTime) * 1000),
            year = date.getFullYear(),
            month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1,
            day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate(),
            hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours(),
            minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes(),
            seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
    };

    function splitStr(str, num) {
        if (!str) {
            return null;
        }
        num = num || 1;

        if (num == 1) {
            return str.split('');
        }

        var result = [];
        var i = 0;

        while (i < str.length) {
            result.push(str.substring(i, i + num));
            i += num;
        }

        return result;
    };

    /* 判断是否有协议 */
    function hasProtocals() {
        return true;

        // if (!window.PROTOCOLS) {
        //     console.log('没有找到协议文件或协议内容为空!');
        //     return false;
        // }
        // return true;
    }

    /* 字符补零 */
    function fillZero(str, len) {
        if (!str) {
            return;
        }
        if (!len) {
            return str;
        }
        var strLen = str.length;
        if (strLen < len) {
            for (var i = 0; i < len - strLen; i++) {
                str = '0' + str;
            }
        }
        return str;
    }

    module.exports = {
        DataParsing: new DataParsing()
    };
})();

