package juli.service;

import juli.domain.Area;
import juli.repository.AreaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 省市服务
 */
@Service
public class AreaService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AreaRepository areaRepository;

    public List<Area> getAllProvince() {
        return areaRepository.findByParent("0");
    }

    public List<Area> getChildren(String parent) {
        return areaRepository.findByParent(parent);
    }

    /**
     * 根据区域名字得到区域
     */
    public Area findByName(String name) {
        return areaRepository.findByName(name);
    }
}
