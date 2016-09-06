package juli.service;

import juli.domain.Menu;
import juli.infrastructure.annotation.RollbackableTransaction;
import juli.infrastructure.exception.ObjectNotFoundException;
import juli.repository.MenuRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MenuRepository menuRepository;

    /**
     * 得到树形菜单
     */
    public List<Menu> getTreeMenus(String category) {
        List<Menu> allMenus = menuRepository.findByCategory(category);
        List<Menu> rootMenus = new ArrayList<>();
        //首先找到所有的顶级菜单
        allMenus.stream().filter(menu -> menu.getParent() == null).forEach(rootMenu -> {
            rootMenus.add(rootMenu);
            loadChildMenusRecursively(rootMenu, allMenus);
        });
        sortMenus(rootMenus);
        return removeTopMenuHasNoChildren(rootMenus);
    }

    private List<Menu> removeTopMenuHasNoChildren(List<Menu> rootMenus) {
        return rootMenus.stream().filter(menu -> menu.getChildren().size() > 0).collect(Collectors.toList());
    }

    public List<Menu> getAdminTreeMenus() {
        return getTreeMenus("后台菜单");
    }

    private boolean canLoginUserAccessMenu(Menu menu) {
        if (menu.getPermission() == null) return true;
        try {
            return SecurityUtils.getSubject().isPermitted(menu.getPermission().getCode());
        } catch (UnavailableSecurityManagerException e) {
            return false;
        }
    }

    /**
     * 获得展开的菜单
     */
    public List<Menu> getFlatMenus(String category) {
        List<Menu> allMenus = menuRepository.findByCategory(category);
        List<Menu> rootMenus = new ArrayList<>();
        List<Menu> flatMenus = new ArrayList<>();
        //首先找到所有的顶级菜单
        allMenus.stream().filter(menu -> menu.getParent() == null).forEach(rootMenus::add);
        sortMenus(rootMenus);
        rootMenus.stream().forEach(menu -> {
            menu.setLevel(0);
            flatMenus.add(menu);
            loadFlatChildMenusRecursively(menu, flatMenus, allMenus, 0);
        });
        return flatMenus;
    }

    private void loadFlatChildMenusRecursively(Menu parentMenu, List<Menu> flatMenus, List<Menu> allMenus, int parentLevel) {
        int level = parentLevel + 1;
        for (Menu menu : allMenus) {
            if (menu.getParent() != null && menu.getParent().getId().equals(parentMenu.getId()) && canLoginUserAccessMenu(menu)) {
                flatMenus.add(menu);
                menu.setLevel(level);
                loadFlatChildMenusRecursively(menu, flatMenus, allMenus, level);
            }
        }
    }

    private void sortMenus(List<Menu> rootMenus) {
        Collections.sort(rootMenus, (m1, m2) -> m1.getPriority() - (m2.getPriority()));
        for (Menu menu : rootMenus) {
            sortMenus(menu.getChildren());
        }
    }

    /**
     * 递归获取子菜单
     */
    private void loadChildMenusRecursively(Menu parentMenu, Iterable<Menu> allMenus) {
        for (Menu menu : allMenus) {
            if (menu.getParent() != null && menu.getParent().getId().equals(parentMenu.getId()) && canLoginUserAccessMenu(menu)) {
                parentMenu.getChildren().add(menu);
                loadChildMenusRecursively(menu, allMenus);
            }
        }
    }

    /**
     * 获得所有菜单分组
     */
    public List<String> getAllCategories() {
        return menuRepository.findAllCategory();
    }

    /**
     * 删除菜单分类以及下面的菜单
     */
    public void deleteCategory(String category) {
        menuRepository.delete(menuRepository.findByCategory(category));
    }

    public void createCategory(String category) {
        Menu menu = new Menu();
        menu.setName("默认菜单");
        menu.setHref("#");
        menu.setIcon("fa-list");
        menu.setCategory(category);
        menuRepository.save(menu);
    }

    @RollbackableTransaction
    public void deleteMenu(String id) throws ObjectNotFoundException {
        Menu menu = menuRepository.findOne(id);
        if (menu == null) {
            throw new ObjectNotFoundException();
        }

        menuRepository.findByCategory(menu.getCategory()).stream()
                .filter(o -> o.getParent() == menu)
                .forEach(menuRepository::delete);
        menuRepository.delete(menu);
    }


}
