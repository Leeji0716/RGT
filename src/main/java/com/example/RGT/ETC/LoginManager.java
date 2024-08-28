package com.example.RGT.ETC;

import com.example.RGT.Entity.MyTable;
import com.example.RGT.Entity.Store;
import com.example.RGT.Entity.StoreTable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class LoginManager {
    private boolean loggedIn = false;
    private StoreTable currentStoreTable;

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        if (!loggedIn) {
            this.currentStoreTable = null;
        }
    }
}