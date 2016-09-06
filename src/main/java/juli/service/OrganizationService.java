package juli.service;

import juli.domain.Organization;
import juli.repository.OrganizationRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;

    public List<Organization> getTreeOrganizations() {
        List<Organization> topOrganizations = IteratorUtils.toList(organizationRepository.findByParentIsNull().iterator());
        sortOrganizations(topOrganizations);
        return topOrganizations;
    }

    public List<Organization> getFlatTreeOrganizations() {
        List<Organization> topOrganizations = IteratorUtils.toList(organizationRepository.findByParentIsNull().iterator());
        List<Organization> flatItems = new ArrayList<>();
        sortOrganizations(topOrganizations);
        topOrganizations.stream().forEach(item -> {
            item.setLevel(0);
            flatItems.add(item);
            loadFlatChildRecursively(item, flatItems, 0);
        });
        return flatItems;
    }

    private void loadFlatChildRecursively(Organization parentItem, List<Organization> flatItems, int parentLevel) {
        int level = parentLevel + 1;
        for (Organization item : parentItem.getChildren()) {
            flatItems.add(item);
            item.setLevel(level);
            loadFlatChildRecursively(item, flatItems, level);
        }
    }

    private void sortOrganizations(List<Organization> organizations) {
        Collections.sort(organizations, (m1, m2) -> m1.getOrderIndex() - (m2.getOrderIndex()));
        for (Organization item : organizations) {
            sortOrganizations(item.getChildren());
        }
    }
}