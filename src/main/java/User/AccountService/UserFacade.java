package User.AccountService;

public interface UserFacade {


    public User managerEngagement();
    public void registration(EndUser user) throws Exception;



    public static final String USER_FACADE = "USER_FACADE";
}
