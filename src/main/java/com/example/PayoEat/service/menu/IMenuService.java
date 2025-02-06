package com.example.PayoEat.service.menu;

import com.example.PayoEat.dto.MenuDto;
import com.example.PayoEat.model.Menu;
import com.example.PayoEat.request.menu.AddMenuRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMenuService {
    Menu getMenuById(String menuId);
    Menu addMenu(AddMenuRequest request, MultipartFile menuImage);
    MenuDto convertToDto(Menu menu);
    List<MenuDto> getConvertedMenus(List<Menu> menus);
    List<Menu> getMenusByRestaurantId(Long restaurantId);
}
