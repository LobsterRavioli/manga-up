package User.AccountService;

import Order.DispatchService.ToManage;

import javax.sql.DataSource;
import java.util.List;

public class ManagerSelectionFacadeImp implements ManagerSelectionFacade{

    private DataSource ds;
    private UserDAO uD;


    public ManagerSelectionFacadeImp(DataSource ds){
        this.ds = ds;
        this.uD = new UserDAO(ds);
    }


    public User managerEngagement(){
        try {
            User selectedManager;
            List<User> candidatesManagers = (List<User>) uD.getAllBeginnerOrderManagers();
            if (candidatesManagers != null) {
                selectedManager = candidatesManagers.get(0);
                return selectedManager;
            } else {
                String targetUserUsername = uD.getTargetOrderManagerUserName();
                selectedManager = new User(targetUserUsername, "");
                return selectedManager;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
