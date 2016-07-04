package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.constants.MessageConstants;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.constants.Statuses;
import by.booking.entities.*;
import by.booking.exceptions.ServiceException;
import by.booking.managers.MessageManager;
import by.booking.services.impl.BillServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class PayBillCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute(Parameters.USER);
            long billId = Long.valueOf(request.getParameter(Parameters.BILL_TO_PAY));
            Bill billToPay = BillServiceImpl.getInstance().getById(billId);
            billToPay.setStatus(Statuses.BILL_PAID);
            BillServiceImpl.getInstance().update(billToPay);
            List<Bill> billList = BillServiceImpl.getInstance().getByUserId(user.getId());
            session.setAttribute(Parameters.BILL_LIST, billList);
            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
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
