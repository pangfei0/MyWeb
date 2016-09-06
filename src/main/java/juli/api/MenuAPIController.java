package juli.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import juli.api.core.APIController;
import juli.api.core.APIResponse;
import juli.api.dto.MenuDto;
import juli.domain.Menu;
import juli.domain.Permission;
import juli.infrastructure.exception.ObjectNotFoundException;
import juli.repository.MenuRepository;
import juli.repository.PermissionRepository;
import juli.service.MenuService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "菜单API", description = " ")
@RestController
@RequestMapping("/api/menu")
public class MenuAPIController extends APIController<Menu, MenuRepository> {

    @Autowired
    MenuService menuService;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @ApiOperation("获取指定分类下的树形菜单")
    @RequestMapping(value = "/category/{categoryName}", method = RequestMethod.GET)
    public APIResponse getMenusByCategory(@PathVariable("categoryName") String categoryName) throws InvocationTargetException, IllegalAccessException {
        List<Menu> treeMenus = menuService.getTreeMenus(categoryName);
        List<MenuDto> menuDtos = treeMenus.stream().map(o -> new MenuDto().mapFrom(o)).collect(Collectors.toList());
        return APIResponse.success(menuDtos);
    }

    @ApiOperation("获取指定分类下的flat菜单")
    @RequestMapping(value = "/category/{categoryName}/flat", method = RequestMethod.GET)
    public APIResponse getFlatMenusByCategory(@PathVariable("categoryName") String categoryName) throws JsonProcessingException {
        List<Menu> treeMenus = menuService.getFlatMenus(categoryName);
        List<MenuDto> menuDtos = treeMenus.stream().map(o -> new MenuDto().mapFrom(o)).collect(Collectors.toList());
        return APIResponse.success(menuDtos);
    }

    @ApiOperation("删除菜单分类以及下面所有的菜单")
    @RequestMapping(value = "/category/delete", method = RequestMethod.POST)
    public APIResponse deleteCategory(@RequestParam("categoryName") String categoryName) {
        menuService.deleteCategory(categoryName);
        return APIResponse.success();
    }

    @ApiOperation("新建一个菜单分组")
    @RequestMapping(value = "/category/new", method = RequestMethod.POST)
    public APIResponse newCategory(@RequestParam("categoryName") String categoryName) {
        menuService.createCategory(categoryName);
        return APIResponse.success();
    }

    @ApiOperation("获得所有菜单分组")
    @RequestMapping(value = "/category/all", method = RequestMethod.GET)
    public APIResponse getAllCategory() {
        return APIResponse.success(menuService.getAllCategories());
    }

    @ApiOperation("获得一个Menu对象")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse get(@ApiParam("Menu ID") @PathVariable("id") String id) {
        Menu menu = menuRepository.findOne(id);
        if (menu == null) {
            return APIResponse.error("不存在此对象：" + id);
        }

        MenuDto menuDto = new MenuDto().mapFrom(menu);
        if (menu.getParent() != null) {
            menuDto.setParentId(menu.getParent().getId());
        }
        if (menu.getPermission() != null) {
            menuDto.setPermissionId(menu.getPermission().getId());
        }
        return APIResponse.success(menuDto);
    }

    @ApiOperation("更新一个Menu对象")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public APIResponse update(@ApiParam("Menu ID") @PathVariable("id") String id,
                              @ApiParam("需要更新的Menu对象") MenuDto menu) throws InvocationTargetException, IllegalAccessException {
        Menu existingMenu = menuRepository.findOne(id);
        if (existingMenu == null) {
            return APIResponse.error("不存在此对象：" + id);
        }

        BeanUtils.copyProperties(existingMenu, menu);

        if (StringUtils.isNotEmpty(menu.getParentId())) {
            existingMenu.setParent(menuRepository.findOne(menu.getParentId()));
        }

        if (StringUtils.isNotEmpty(menu.getPermissionId())) {
            Permission permission = permissionRepository.findOne(menu.getPermissionId());
            if (permission == null) {
                return APIResponse.error("不存在此权限");
            }
            existingMenu.setPermission(permission);
        }

        menuRepository.save(existingMenu);
        return APIResponse.success();
    }

    @ApiOperation("创建菜单")
    @RequestMapping(method = RequestMethod.POST)
    public APIResponse create(@ApiParam("需要添加的Menu对象") MenuDto menuDto) {
        Menu menu = menuDto.mapTo();

        if (StringUtils.isNotEmpty(menuDto.getParentId())) {
            menu.setParent(menuRepository.findOne(menuDto.getParentId()));
        } else {
            menu.setParent(null);
        }

        if (StringUtils.isNotEmpty(menuDto.getPermissionId())) {
            Permission permission = permissionRepository.findOne(menuDto.getPermissionId());
            if (permission == null) {
                return APIResponse.error("不存在此权限");
            }
            menu.setPermission(permission);
        } else {
            menu.setPermission(null);
        }

        menuRepository.save(menu);
        return APIResponse.success(menu.getId());
    }

    @ApiOperation("删除菜单以及其子菜单")
    public APIResponse delete(@ApiParam("对象ID") @PathVariable("id") String id) {
        try {
            menuService.deleteMenu(id);
            return APIResponse.success();
        } catch (ObjectNotFoundException e) {
            return APIResponse.error("不存在此菜单");
        }
    }
}