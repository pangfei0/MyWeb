package juli.service;

import juli.api.core.APIResponse;
import juli.domain.Company;
import juli.domain.User;
import juli.infrastructure.excel.ExcelUtil;
import juli.infrastructure.exception.JuliException;
import juli.repository.CompanyRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CompanyService {
    Logger logger = Logger.getLogger(getClass());

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    UserService userService;

    public List<Company> findByType(Integer type){
        return companyRepository.findByType(type);
    }

    public LinkedHashMap<String,List<?>> getCompanies(String type,String companyName,String companyAddress) throws Exception{
        try
        {
            User user = userService.getCurrentUser();
            List<Company> companies=new ArrayList<>();
            Company company;
            if(user.getCompanyType()!=null&&user.getCompanyId()!=null){
                company=companyRepository.findById(user.getCompanyId());
               if(null!=company)
               {
                   companies.add(company);
               }
            }else
            {
                if(companyName.isEmpty()&&companyAddress.isEmpty()&&type.equals("-1")){
                    //未限定公司类型
                    companies = IteratorUtils.toList(companyRepository.findAll().iterator());
                }else if(companyName.isEmpty()&&companyAddress.isEmpty()&&!type.equals("-1")){
                    companies=companyRepository.findByType(Integer.parseInt(type));
                }
                else if(!companyName.isEmpty())
                {
                    List<Company> companyList=companyRepository.findByName(companyName);
                    if(CollectionUtils.isNotEmpty(companyList)){
                        companies.add(companyList.get(0));
                    }
                }
                else if(companyName.isEmpty()&&!companyAddress.isEmpty())
                {
                    company=companyRepository.findByAddress(companyAddress);
                   if(null!=company){
                       companies.add(company);
                   }
                }
            }
            companies= IteratorUtils.toList(companies.iterator());
            LinkedHashMap<String, List<?>> map = new LinkedHashMap<>();
            map.put("公司数据", companies);
            return map;
        }catch (JuliException e)
        {
            return null;
        }
    }
    public void export(HttpServletResponse response,String type,String companyName,String companyAddress)throws Exception
    {
        LinkedHashMap<String, List<?>> map=getCompanies(type,companyName,companyAddress);
        ExcelUtil.ExcelExportData setInfo = new ExcelUtil.ExcelExportData();
        List<String[]> fieldNames = new ArrayList<>();
        fieldNames.add(new String[]{"name", "address", "phone", "contact", "mobile"});
        List<String[]> columNames = new ArrayList<>();
        columNames.add(new String[]{"名称", "详细地址", "公司固话", "联系人", "联系人电话"});
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[]{"公司数据"});
        setInfo.setColumnNames(columNames);
        ExcelUtil.export2Browser(response, setInfo, "公司数据");
    }

    public APIResponse searchProjectName(String companyType,String projectName ){

        List<String> buildingNames = new ArrayList<>();
        String name="%"+projectName+"%";
        switch (companyType) {
            case "10":
                buildingNames = companyRepository.getInstallCompanyUseProjectNames(name);
                break;
            case "20":
                buildingNames = companyRepository.getMaintainerCompanyUseProjectNames(name);
                break;
            case "30":
                buildingNames = companyRepository.getUserCompanyUseProjectNames(name);
                break;
            case "40":
                buildingNames = companyRepository.getManufacturerCompanyUseProjectNames(name);
                break;
            case "50":
                buildingNames = companyRepository.getOwnerCompanyUseProjectNames(name);
                break;
            case "60":
                buildingNames = companyRepository.getRegulatorCompanyUseProjectNames(name);
                break;
            case "70":
                buildingNames = companyRepository.getPersonalCompanyUseProjectNames(name);
                break;
            case "80":
                buildingNames = companyRepository.getOthersCompanyUseProjectNames(name);
        }
        if(CollectionUtils.isNotEmpty(buildingNames))
        {
            return APIResponse.success(buildingNames);
        }
        else{
            return APIResponse.error("没有匹配的项目名称");
        }

    }

}
