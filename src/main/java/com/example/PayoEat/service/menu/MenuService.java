package com.example.PayoEat.service.menu;

import com.example.PayoEat.dto.ImageDto;
import com.example.PayoEat.dto.MenuDto;
import com.example.PayoEat.exceptions.NotFoundException;
import com.example.PayoEat.model.Image;
import com.example.PayoEat.model.Menu;
import com.example.PayoEat.model.Restaurant;
import com.example.PayoEat.repository.MenuRepository;
import com.example.PayoEat.repository.RestaurantRepository;
import com.example.PayoEat.request.menu.AddMenuRequest;
import com.example.PayoEat.request.menu.UpdateMenuRequest;
import com.example.PayoEat.service.image.ImageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class MenuService implements IMenuService{
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Override
    public Menu getMenuById(String menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu not found with id: " + menuId));
    }

    @Override
    public Menu addMenu(AddMenuRequest request, MultipartFile menuImage) {
        return menuRepository.save(createMenu(request, menuImage));
    }

    @Override
    public MenuDto convertToDto(Menu menu) {
        return modelMapper.map(menu, MenuDto.class);
    }

    @Override
    public List<MenuDto> getConvertedMenus(List<Menu> menus) {
        return menus.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Menu> getMenusByRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByIdAndIsActiveTrue(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant not found with id: " + restaurantId));

        return menuRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public void deleteMenu(String menuCode) {
        menuRepository.findByMenuCodeAndIsActiveTrue(menuCode)
                .map(currentMenu -> {
                    deleteExistingMenu(currentMenu);
                    return menuRepository.save(currentMenu);
                })
                .orElseThrow(() -> new NotFoundException("Menu not found"));

    }

    private void deleteExistingMenu(Menu existingMenu) {
        existingMenu.setUpdatedAt(LocalDateTime.now());
        existingMenu.setIsActive(false);
    }


    @Override
    public Menu updateMenu(String menuCode, UpdateMenuRequest request, MultipartFile menuImage) {
       return menuRepository.findByMenuCodeAndIsActiveTrue(menuCode)
                .map(existingMenu -> updateExistingMenu(existingMenu, request, menuImage))
                .map(menuRepository::save)
                .orElseThrow(() -> new NotFoundException("Menu not found with code: " + menuCode));

    }

    private Menu updateExistingMenu(Menu existingMenu, UpdateMenuRequest request, MultipartFile menuImage) {

        if ((request.getMenuName() == null || request.getMenuName().isEmpty()) &&
                request.getMenuPrice() == null &&
                (request.getMenuDetail() == null || request.getMenuDetail().isEmpty()) &&
                menuImage == null) {
            throw new IllegalArgumentException("No valid fields provided to update the menu.");
        }

        if (request.getMenuName() != null && !request.getMenuName().isEmpty()) {
            existingMenu.setMenuName(request.getMenuName());
        }

        if (request.getMenuPrice() != null) {
            existingMenu.setMenuPrice(request.getMenuPrice());
        }

        if (request.getMenuDetail() != null && !request.getMenuDetail().isEmpty()) {
            existingMenu.setMenuDetail(request.getMenuDetail());
        }

        if (menuImage != null) {
            imageService.updateImage(menuImage, existingMenu.getMenuImage().getId());
        }

        existingMenu.setUpdatedAt(LocalDateTime.now());

        return existingMenu;
    }

    private Menu createMenu(AddMenuRequest request, MultipartFile menuImage) {
        Restaurant restaurant = restaurantRepository.findByIdAndIsActiveTrue(request.getRestaurantId())
                .orElseThrow(() -> new NotFoundException("Restaurant not found with id: " + request.getRestaurantId()));

        String randomSuffix = UUID.randomUUID().toString().substring(0, 5);
        String menuCode = restaurant.getId() + "_" + request.getMenuName().toUpperCase().replace(" ", "_") + "_" + randomSuffix;

        Menu menu = new Menu(
                request.getMenuName(),
                request.getMenuDetail(),
                request.getMenuPrice()
        );

        menu.setMenuCode(menuCode);
        menu.setCreatedAt(LocalDateTime.now());
        menu.setIsActive(true);
        menu.setRestaurant(restaurant);

        Image image = imageService.saveImage(menuImage, menuCode);
        image.setMenu(menu);
        menu.setMenuImage(image);

        return menuRepository.save(menu);
    }
}
