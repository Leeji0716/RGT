package com.example.RGT.Service;

import com.example.RGT.DTO.*;
import com.example.RGT.ETC.LoginManager;
import com.example.RGT.ETC.Status;
import com.example.RGT.Entity.*;
import com.example.RGT.Service.Module.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MultiService {
    private final LoginManager loginManager;
    private final SiteUserService siteUserService;
    private final MenuService menuService;
    private final StoreService storeService;
    private final MyTableService myTableService;
    private final StoreTableService storeTableService;
    private final StoreMenuService storeMenuService;
    private final CartService cartService;
    private final MyOrderService myOrderService;
    private final OrderMenuService orderMenuService;

    private Boolean loginIO() throws Exception {
        if (loginManager.isLoggedIn()) {
            return true;
        } else {
            throw new Exception("please log in");
        }
    }

    /*
     * SiteUser
     */

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws Exception {
        if (!userRequestDTO.username().isEmpty() && !userRequestDTO.password().isEmpty()) {
            try {
                SiteUser siteUser = SiteUser.builder()
                        .username(userRequestDTO.username())
                        .password(userRequestDTO.password())
                        .build();
                siteUserService.save(siteUser);

                return UserResponseDTO.builder().response("signup success").build();
            } catch (Exception ex) {
                throw new Exception("ID already exists");
            }
        } else {
            throw new Exception("signup failed");
        }
    }

    public UserResponseDTO login(UserRequestDTO userRequestDTO) throws Exception {
        SiteUser siteUser = siteUserService.getUser(userRequestDTO.username());
        if (siteUser != null && siteUser.getPassword().equals(userRequestDTO.password())) {
            loginManager.setLoggedIn(true);
            return UserResponseDTO.builder().response("login success").build();
        } else {
            loginManager.setLoggedIn(false);
            throw new Exception("login failed");
        }
    }

    public UserResponseDTO logout() {
        loginManager.setLoggedIn(false);
        return UserResponseDTO.builder().response("logout").build();
    }

    /*
     * Menu
     */

    public MenuResponseDTO createMenu(MenuRequestDTO menuRequestDTO) throws Exception {
        if (loginIO()) {
            Menu menu = Menu.builder()
                    .name(menuRequestDTO.name())
                    .price(menuRequestDTO.price())
                    .content(menuRequestDTO.content())
                    .build();

            menuService.save(menu);

            return menuService.MenuDTO(menu);
        }
        return null;
    }

    public MenuResponseDTO getMenu(Long menuId) throws Exception {
        if (loginIO()) {
            Menu menu = menuService.getMenuById(menuId);
            return menuService.MenuDTO(menu);
        }
        return null;
    }

    public MenuResponseDTO updateMenu(Long menuId, MenuRequestDTO menuRequestDTO) throws Exception {
        if (loginIO()) {
            Menu menu = menuService.getMenuById(menuId);
            menu.setName(menuRequestDTO.name());
            menu.setPrice(menuRequestDTO.price());
            menu.setContent(menuRequestDTO.content());

            menuService.save(menu);
            return menuService.MenuDTO(menu);
        }
        return null;
    }

    /*
     * MyTable
     */

    public List<MyTableResponseDTO> createMyTable() throws Exception {
        if (loginIO()) {
            String tableNumber;

            try {
                List<MyTable> myTableList = myTableService.getAllTable();
                if (myTableList.isEmpty()) {
                    tableNumber = "1";
                } else {
                    tableNumber = String.valueOf(myTableList.size() + 1);
                }
            } catch (Exception ex) {
                // 여기서 예외를 잡고 초기값 설정
                tableNumber = "1";
            }

            MyTable myTable = MyTable.builder().tableNumber(tableNumber).build();
            myTableService.save(myTable);

            return myTableService.getMyTableDTOList(myTableService.getAllTable());
        }
        return null;
    }

    /*
     * Store
     */

    @Transactional // 트랜잭션을 보장하기 위해 @Transactional 추가
    public StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO) throws Exception {
        if (loginIO()) {
            Store store = Store.builder()
                    .name(storeRequestDTO.name())
                    .build();

            storeService.save(store);

            List<StoreTable> storeTables = new ArrayList<>();
            for (Long id = 1L; id <= storeRequestDTO.myTables(); id++) {
                MyTable myTable = myTableService.getTable(id);

                StoreTable storeTable = storeTableService.create(store, myTable);
                storeTables.add(storeTable);

                store.addStoreTable(storeTable);
                myTable.addStoreTable(storeTable);
            }

            storeService.save(store);
            List<StoreTableResponseDTO> storeTableResponseDTOList = storeTableService.StoreTableDTOList(storeTables);

            return storeService.storeDTO(store, storeTableResponseDTOList);
        }
        return null;
    }

    /*
     * StoreMenu
     */

    public List<StoreMenuResponseDTO> getStoreMenuList(Long restaurantId) throws Exception {
        if (loginIO()) {
            Store store = storeService.getStore(restaurantId);
            return storeMenuService.getStoreMenueList(store);
        }
        return null;
    }

    @Transactional
    public List<StoreMenuResponseDTO> registration(Long storeId, Long menuId) throws Exception {
        if (loginIO()) {
            Store store = storeService.getStore(storeId);
            Menu menu = menuService.getMenuById(menuId);

            StoreMenu storeMenu = storeMenuService.create(store, menu);

            store.addStoreMenu(storeMenu);
            menu.addStoreMenu(storeMenu);

            storeService.save(store);

            return storeMenuService.getStoreMenueList(store);
        }
        return null;
    }

    public MenuResponseDTO getStoreMenuDetail(Long storeMenuId) throws Exception {
        if (loginIO()) {
            StoreMenu storeMenu = storeMenuService.getStoreMenu(storeMenuId);
            return this.getMenu(storeMenu.getMenu().getId());
        }
        return null;
    }

    public StoreTableResponseDTO select(Long storeTableId) throws Exception {
        if (loginIO()) {
            StoreTable storeTable = storeTableService.getTable(storeTableId);

            loginManager.setCurrentStoreTable(storeTable);
            return storeTableService.StoreTableDTO(storeTable);
        }
        return null;
    }

    /*
     * Cart
     */

    public CartResponseDTO addToCart(CartRequestDTO cartRequestDTO) throws Exception {
        if (loginIO()) {
            StoreTable storeTable = loginManager.getCurrentStoreTable();
            if (storeTable == null) {
                throw new Exception("현재 스토어 테이블이 설정되지 않았습니다.");
            }

            StoreMenu storeMenu = storeMenuService.getStoreMenu(cartRequestDTO.storeMenuId());

            Optional<Cart> existingCart = cartService.findByStoreTableAndStoreMenu(storeTable, storeMenu);
            if (existingCart.isPresent()) {
                throw new Exception("이 메뉴는 이미 카트에 추가되어 있습니다.");
            }

            if (storeMenu.getStore().getId() == storeTable.getStore().getId()){
                Cart cart = Cart.builder()
                        .storeTable(storeTable)
                        .storeMenu(storeMenu)
                        .count(cartRequestDTO.count())
                        .build();

                cartService.save(cart);

                StoreTableResponseDTO storeTableResponseDTO = storeTableService.StoreTableDTO(storeTable);
                StoreMenuResponseDTO storeMenuResponseDTO = storeMenuService.StoreMenuDTO(storeMenu);

                return cartService.CartDTO(cart, storeTableResponseDTO, storeMenuResponseDTO);
            }else {
                throw new Exception("이 메뉴는 이 가게에서 판매하고 있지 않습니다.");
            }
        }
        return null;
    }

    public List<StoreTableResponseDTO> getStoreTableList(Long storeId) throws Exception {
        if (loginIO()) {
            Store store = storeService.getStore(storeId);

            return storeTableService.findTableByStore(store);
        }
        return null;
    }

    public CartResponseDTO updateCart(Long cartId, Long count) throws Exception {
        if (loginIO()) {
            Cart cart = cartService.getCart(cartId);
            cart.setCount(count);
            cartService.save(cart);

            StoreTableResponseDTO storeTableResponseDTO = storeTableService.StoreTableDTO(cart.getStoreTable());
            StoreMenuResponseDTO storeMenuResponseDTO = storeMenuService.StoreMenuDTO(cart.getStoreMenu());

            return cartService.CartDTO(cart, storeTableResponseDTO, storeMenuResponseDTO);
        }
        return null;
    }

    public List<CartResponseDTO> getCartMenuList() throws Exception {
        if (loginIO()) {
            StoreTable storeTable = loginManager.getCurrentStoreTable();

            List<CartResponseDTO> cartResponseDTOList = new ArrayList<>();
            List<Cart> cartList = cartService.getStoreList(storeTable);

            for (Cart cart : cartList) {
                StoreTableResponseDTO storeTableResponseDTO = storeTableService.StoreTableDTO(cart.getStoreTable());
                StoreMenuResponseDTO storeMenuResponseDTO = storeMenuService.StoreMenuDTO(cart.getStoreMenu());
                cartResponseDTOList.add(cartService.CartDTO(cart, storeTableResponseDTO, storeMenuResponseDTO));
            }
            return cartResponseDTOList;

        }
        return null;
    }

    public List<CartResponseDTO> deleteCart(Long cartId) throws Exception {
        if (loginIO()) {
            Cart cart = cartService.getCart(cartId);
            cartService.delete(cart);

            return getCartMenuList();
        }
        return null;
    }

    /*
     * Order
     */
    @Transactional
    public MyOrderResponseDTO order() throws Exception {
        if (loginIO()) {
            StoreTable storeTable = loginManager.getCurrentStoreTable();
            List<Cart> cartList = cartService.getStoreList(storeTable);
            if(!cartList.isEmpty()){
                MyOrder myOrder = myOrderService.create();

                long totalPrice = 0L;
                for (Cart cart : cartList) {
                    long total = cart.getStoreMenu().getMenu().getPrice() * cart.getCount();
                    totalPrice += total;

                    OrderMenu orderMenu = OrderMenu.builder()
                            .menuName(cart.getStoreMenu().getMenu().getName())
                            .price(cart.getStoreMenu().getMenu().getPrice())
                            .count(cart.getCount())
                            .total(total)
                            .myOrder(myOrder)
                            .build();

                    myOrder.addOrderMenu(orderMenu);
                }
                myOrder.setTotalPrice(totalPrice);
                myOrderService.save(myOrder);

                cartService.remove(cartList);

                List<OrderMenuDTO> orderMenuDTOList = orderMenuService.OrderMenuDTOList(myOrder.getOrderMenus());
                return myOrderService.OrderDTO(myOrder, orderMenuDTOList);
            }else {
                throw new Exception("장바구니가 비어있습니다.");
            }
        }
        return null;
    }

    public MyOrderResponseDTO complete(Long orderId) throws Exception {
        if (loginIO()) {
            MyOrder myOrder = myOrderService.getMyOrder(orderId);
            myOrder.setStatus(Status.COMPLETE);
            myOrderService.save(myOrder);
            List<OrderMenuDTO> orderMenuDTOList = orderMenuService.OrderMenuDTOList(myOrder.getOrderMenus());
            return myOrderService.OrderDTO(myOrder, orderMenuDTOList);
        }
        return null;
    }

    public MyOrderResponseDTO cancel(Long orderId) throws Exception {
        if (loginIO()) {
            MyOrder myOrder = myOrderService.getMyOrder(orderId);
            myOrder.setStatus(Status.CANCEL);
            myOrderService.save(myOrder);
            List<OrderMenuDTO> orderMenuDTOList = orderMenuService.OrderMenuDTOList(myOrder.getOrderMenus());
            return myOrderService.OrderDTO(myOrder, orderMenuDTOList);
        }
        return null;
    }

    public MyOrderResponseDTO checkOrder(Long orderId) throws Exception {
        if (loginIO()) {
            MyOrder myOrder = myOrderService.getMyOrder(orderId);
            List<OrderMenuDTO> orderMenuDTOList = orderMenuService.OrderMenuDTOList(myOrder.getOrderMenus());
            return myOrderService.OrderDTO(myOrder, orderMenuDTOList);
        }
        return null;
    }

    public List<StoreResponseDTO> getStoreList() throws Exception{
        if(loginIO()){
            List<Store> stores = storeService.getAll();
            List<StoreResponseDTO> storeResponseDTOList = new ArrayList<>();
            for (Store store : stores){
                List<StoreTable> storeTables = store.getStoreTables();
                List<StoreTableResponseDTO> storeTableResponseDTOList = storeTableService.StoreTableDTOList(storeTables);
                storeResponseDTOList.add(storeService.storeDTO(store, storeTableResponseDTOList));
            }
            return storeResponseDTOList;
        }
        return null;
    }
}
