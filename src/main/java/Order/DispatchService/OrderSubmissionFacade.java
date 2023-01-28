package Order.DispatchService;

public interface OrderSubmissionFacade {
    void OrderCreation(Order order) throws Exception;

    public static final String ORDER_SUBMISSION_FACADE = "ORDER_SUBMISSION_FACADE";
}
