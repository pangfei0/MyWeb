package juli.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.domain.Reports;
import juli.repository.ReportRepository;
import juli.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by pf on 2016/2/4.
 */
@Api(value = "报告API", description = " ")
@RestController
@RequestMapping("/api/report")
public class IOSReportAPIController extends APIController<Reports, ReportRepository> {
    @Autowired
    ReportService reportService;

    @ApiOperation("获取当前维修人员的所有报告")
    @RequestMapping(value="/ios/getReportByStatusAndCategory",method = RequestMethod.GET)
    public APIResponse getReportByStatusAndCategory(
            HttpSession session,
            @ApiParam("唯一客户端")@RequestParam("sid")String sid,
            @ApiParam(value="员工编号")@RequestParam(value = "id")String id,
            @ApiParam(value="报告状态")@RequestParam(value = "reportStatus") Integer reportStatus,
            @ApiParam(value="报告类别")@RequestParam(value = "reportCategory") Integer  reportCategory,
            @ApiParam(value="从当前页开始",defaultValue ="1")@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
            @ApiParam(value="每页条数",defaultValue ="15")@RequestParam(value = "pageSize",defaultValue = "1000") Integer pageSize) throws Exception {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                PageImpl<Map> reportPage = reportService.findPersonalReportByCategroyAndStatus(id, reportCategory, reportStatus, pageNumber, pageSize);
                return APIResponse.success(reportPage);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }
    }
    @ApiOperation("根据id获取唯一报告详细")
    @RequestMapping(value="/ios/getReportById",method = RequestMethod.GET)
    public APIResponse getDetailById( HttpSession session,
                                      @ApiParam("唯一客户端")@RequestParam("sid") String sid,
                                      @ApiParam(value="报告编号")@RequestParam(value = "id") String id) throws Exception {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                return reportService.getReportById(id);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }

    }
    //@ApiParam(value="报告时间")@RequestParam(value = "reportTime")Date reportTime
    @ApiOperation("提交报告")
    @RequestMapping(value="/ios/commitReport",method = RequestMethod.GET)
    public APIResponse commitReport(HttpSession session,
                                    @ApiParam("唯一客户端")@RequestParam("sid") String sid,
                                    @ApiParam(value="报告编号")@RequestParam(value = "id") String id,
                                    @ApiParam(value="服务态度")@RequestParam(value = "serviceAttitude")Integer serviceAttitude,
                                    @ApiParam(value="服务水平")@RequestParam(value = "servicLevel")Integer servicLevel,
                                    @ApiParam(value="维护环境和安全")@RequestParam(value = "envAndSafe")Integer envAndSafe,
                                    @ApiParam(value="解决问题")@RequestParam(value = "resolveQuestion")Integer resolveQuestion ,
                                    @ApiParam(value="综合评价")@RequestParam(value = "generalEvaluate")Integer generalEvaluate ,
                                    @ApiParam(value="建议")@RequestParam(value = "suggestions")String suggestions,
                                    @ApiParam(value="图片路径")@RequestParam(value = "picPath") String picPath
                                    ) throws IOException {
        String ssid = (String) session.getAttribute("sid");
        if(ssid!=null) {
            if (ssid.equals(sid)) {
                return reportService.commitReport(id, serviceAttitude, servicLevel, envAndSafe, resolveQuestion, suggestions,picPath,generalEvaluate);
            } else {
                return APIResponse.error("请重新登录");
            }

        }
        else
        {
            return  APIResponse.error("请重新登录");
        }
    }
    @ApiOperation("报告图片提交")
    @RequestMapping(value="/ios/commitReport1",method = RequestMethod.GET)
    public APIResponse commitReport(
                                    @ApiParam(value="报告编号")@RequestParam(value = "id") String id,
                                    @ApiParam(value="图片路径")@RequestParam(value = "picPath") String picPath

    ) throws IOException {
                return reportService.commitReportPicture(id,picPath);

    }
    //web 端接口
    @ApiOperation("获取报告")
    @RequestMapping(value = "/getReports", method=RequestMethod.POST)
    public APIResponse findReports(ServletRequest request,
                                    @ApiParam(value="从当前页开始",defaultValue ="1")@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                    @ApiParam(value="每页条数",defaultValue ="15")@RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize)
    {

            return reportService.findReports(request,pageNumber,pageSize);


    }
    @ApiOperation("获取报告详细")
    @RequestMapping(value = "/detail/{id}", method=RequestMethod.GET)
    public APIResponse getDetailById(@ApiParam(value="报告唯一id")@PathVariable(value = "id") String id) throws Exception {
        return reportService.getReportById(id);
    }
    @ApiOperation("导出报告excel")
    @RequestMapping(value = "/exportReportExcel", method=RequestMethod.GET)
    public APIResponse exportExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
            reportService.exportExcel(request, response);
        return  APIResponse.success();
    }
}
