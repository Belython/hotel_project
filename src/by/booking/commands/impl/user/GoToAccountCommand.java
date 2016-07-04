package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.constants.MessageConstants;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.entities.Bill;
import by.booking.entities.Order;
import by.booking.entities.User;
import by.booking.exceptions.ServiceException;
import by.booking.managers.MessageManager;
import by.booking.services.impl.BillServiceImpl;
import by.booking.services.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class GoToAccountCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        try {

            User user = (User) session.getAttribute(Parameters.USER);
            List<Order> orderList = OrderServiceImpl.getInstance().getByUserId(user.getId());
            List<Bill> billList = BillServiceImpl.getInstance().getByUserId(user.getId());
            session.setAttribute(Parameters.ORDER_LIST, orderList);
            session.setAttribute(Parameters.BILL_LIST, billList);
            page = PagePath.ACCOUNT_PAGE_PATH;
        }
        catch (ServiceException | SQLException e) {
            page = PagePath.ERROR_PAGE_PATH;
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        return page;
    }

}
