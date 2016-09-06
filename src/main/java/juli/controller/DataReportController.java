package juli.controller;

import juli.domain.Elevator;
import juli.infrastructure.excel.ExcelUtil;
import juli.infrastructure.persist.DynamicSpecification;
import juli.repository.ElevatorRepository;
import juli.service.ElevatorService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dataReport")
public class DataReportController {

    @Autowired
    ElevatorRepository elevatorRepository;

    @Autowired
    ElevatorService elevatorService;

    @RequestMapping("")
    public String index() {
        return "dataReport";
    }

    @RequiresPermissions("export")
    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook(getClass().getResourceAsStream("/excelTemplates/template.xlsx"));

        Map<String, Object> searchParams = new HashMap<>();
        String serviceLife = request.getParameter("serviceLife");
        String brand = request.getParameter("brand");
        String number = request.getParameter("number");
        String eleStatus = request.getParameter("eleStatus");

        if (StringUtils.isNotEmpty(number)) {
            searchParams.put("number_LIKE", number);
        }
        if (StringUtils.isNotEmpty(eleStatus) && !"0".equals(eleStatus)) {
            searchParams.put("status_EQ", eleStatus);
        }
        if (StringUtils.isNotEmpty(brand)) {
            searchParams.put("brand_LIKE", brand);
        }
        Specification specification = DynamicSpecification.buildSpecification(searchParams);
        List<Elevator> elevators = elevatorRepository.findAll(specification);

        generateControllerTypeSheet(request, elevators, wb);
        generateElevatorStatusSheet(request, elevators, wb);
    }

    private void generateControllerTypeSheet(HttpServletRequest request, List<Elevator> elevators, XSSFWorkbook wb) {
        Map<String, Integer> groups = elevatorService.groupControllerSystem(elevators);
        boolean generateSheet = "true".equals(request.getParameter("generateControlSystem"));

        XSSFSheet sheet = wb.getSheet("控制系统统计");
        if (generateSheet) {
            Object[] keys = groups.keySet().toArray();
            for (int i = 0; i < keys.length; i++) {
                XSSFRow row = sheet.createRow(i + 1);//建立新行
                XSSFCell cell = row.createCell(0);//建立新cell
                cell.setCellValue(keys[i].toString());

                XSSFCell cell1 = row.createCell(1);//建立新cell
                cell1.setCellValue(groups.get(keys[i]));
            }
        } else {
            wb.removeSheetAt(wb.getSheetIndex(sheet));
        }
    }

    private void generateElevatorStatusSheet(HttpServletRequest request, List<Elevator> elevators, XSSFWorkbook wb) {
        Map<String, Integer> groups = elevatorService.groupStatus(elevators);
        boolean generateSheet = "true".equals(request.getParameter("generateElevatorStatus"));

        XSSFSheet sheet = wb.getSheet("电梯状态统计");
        if (generateSheet) {
            Object[] keys = groups.keySet().toArray();
            for (int i = 0; i < keys.length; i++) {
                XSSFRow row = sheet.createRow(i + 1);//建立新行
                XSSFCell cell = row.createCell(0);//建立新cell
                cell.setCellValue(keys[i].toString());

                XSSFCell cell1 = row.createCell(1);//建立新cell
                cell1.setCellValue(groups.get(keys[i]));
            }
        } else {
            wb.removeSheetAt(wb.getSheetIndex(sheet));
        }
    }
}
