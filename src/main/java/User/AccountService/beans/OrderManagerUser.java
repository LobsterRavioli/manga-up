package User.AccountService.beans;

import java.util.Locale;

public class OrderManagerUser extends User{


    @Override
    public String getRole() {
        return this.getClass().getSimpleName().toUpperCase(Locale.ROOT);
    }
}
