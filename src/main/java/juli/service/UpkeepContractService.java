package juli.service;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIResponse;
import juli.api.dto.CompanyDto;
import juli.api.dto.ElevatorDto;
import juli.domain.Company;
import juli.domain.Elevator;
import juli.domain.User;
import juli.domain.contract.BillingRecord;
import juli.domain.contract.CollectingRecord;
import juli.domain.contract.UpkeepContract;
import juli.infrastructure.exception.JuliException;
import juli.repository.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/7/22.
 */
@Service
public class UpkeepContractService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UpkeepContractRepository upkeepContractRepository;
    @Autowired
    private BillingRecordRepository billingRecordRepository;
    @Autowired
    private CollectingRecordRepository collectingRecordRepository;

    @ApiOperation("甲方")
    @RequestMapping(value = "/partyAName", method = RequestMethod.POST)
    public APIResponse getPartyA(@ApiParam("partyAName") String partyAName)
    {
        String signNumber = "%" + partyAName + "%";
        List<Company> companies = companyRepository.findByType(30, 50, signNumber);//甲方为物业单位或使用单位
        List<CompanyDto> list = new ArrayList<>();
        for (Company c : companies) {
            CompanyDto companyDto = new CompanyDto();
            companyDto.setId(c.getId());
            companyDto.setName(c.getName());
            list.add(companyDto);
        }
        return APIResponse.success(list);
    }
    @ApiOperation("乙方自动补全")
    @RequestMapping(value = "/partyBName", method = RequestMethod.POST)
    public APIResponse getPartyB(@ApiParam("partyBName") String partyBName)
    {
        try{
            String signNumber = "%" + partyBName + "%";
            User user = userService.getCurrentUser();
            List<Company> companies=new ArrayList<>();
            if (user.getCompanyType()!=null) {
                Company company = companyRepository.findById(user.getCompanyId());//乙方为己方公司
                companies.add(company);
            }
            else
            {
                companies = companyRepository.findByType(20,signNumber);//乙方为维保公司
            }
            List<CompanyDto> list = new ArrayList<>();
            for (Company c : companies) {
                CompanyDto companyDto = new CompanyDto();
                companyDto.setId(c.getId());
                companyDto.setName(c.getName());
                list.add(companyDto);
            }
            return APIResponse.success(list);
        }
        catch (JuliException e){
            return APIResponse.error(e.getMessage());
        }

    }

    public APIResponse searchProjectName(String partyA,String partyB,String projectName){
        List<String> buildingNames = new ArrayList<>();
        String name="%"+projectName+"%";
        buildingNames = upkeepContractRepository.getMaintainerProjectNames(partyA,partyB,name);
        if(CollectionUtils.isNotEmpty(buildingNames))
        {
            return APIResponse.success(buildingNames);
        }
        else{
            return APIResponse.error("没有匹配的项目名称");
        }
    }

    public APIResponse removeUpkeepContrat(String id){
        UpkeepContract upkeepContract= upkeepContractRepository.findById(id);
        List<BillingRecord> billingRecords= billingRecordRepository.findByUpkeepContract(upkeepContract);
        billingRecordRepository.delete(billingRecords);
        List<CollectingRecord> collectingRecords=collectingRecordRepository.findByUpkeepContract(upkeepContract);
        collectingRecordRepository.delete(collectingRecords);
        upkeepContractRepository.delete(upkeepContract);
        return APIResponse.success();
    }
}
