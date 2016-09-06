package juli.controller;

import juli.domain.Collector;
import juli.service.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CollectorController {

    @Autowired
    private CollectorService collectorService;

    /**
     * 跳转到添加界面
     * @param id
     * @return
     */
    @RequestMapping("/collector/addPage")
    public String createOrUpdateWorkBill(String id)
    {
        return "collectorAdd";
    }


//    /**
//     * 添加采集器
//     *
//     * @param collector      采集器
//     * @param elevatorNumber 电梯工号
//     * @return
//     */
//    @RequestMapping(value = "/collector/add", method = RequestMethod.POST)
//    public String addCollector(Collector collector, @RequestParam(value = "elevatorNumber", required = true) String elevatorNumber) {
//        if (collector != null) {
//            collectorService.addCollector(collector, elevatorNumber);
//            return "";
//        }
//        return null;
//    }
}
