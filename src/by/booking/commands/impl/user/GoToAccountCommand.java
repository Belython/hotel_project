package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.constants.MessageConstants;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.entities.Bill;
import by.booking.entities.User;
import by.booking.exceptions.ServiceException;
import by.booking.managers.MessageManager;
import by.booking.requestHandler.ServletAction;
import by.booking.services.impl.BillServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GoToAccountCommand implements ICommand {

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction = ServletAction.FORWARD_PAGE;
        String page = null;
        HttpSession session = request.getSession();
        try {

            User user = (User) session.getAttribute(Parameters.USER);
            List<Bill> billList = BillServiceImpl.getInstance().getByUserId(user.getId());
            session.setAttribute(Parameters.BILL_LIST, billList);
            page = PagePath.ACCOUNT_PAGE_PATH;
        }
        catch (ServiceException e) {
            page = PagePath.ERROR_PAGE_PATH;
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        servletAction.setPage(page);
        return servletAction;
    }

}
