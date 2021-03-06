package by.kanarski.booking.requestHandler;

import by.kanarski.booking.commands.ICommand;
import by.kanarski.booking.commands.factory.CommandFactory;
import by.kanarski.booking.constants.Parameter;
import by.kanarski.booking.i18n.l10n.fillers.IFiller;
import by.kanarski.booking.i18n.l10n.fillers.factory.FillerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class RequestHandler {
    private RequestHandler() {
    }

    public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        CommandFactory commandFactory = CommandFactory.getInstance();
        ICommand сommand = commandFactory.defineCommand(request);
        ServletAction servletAction = сommand.execute(request, response);
        String page = servletAction.getPage();
        String pageName = "INDEX";
        Locale locale = request.getLocale();
        try {
            switch (servletAction) {
                case FORWARD_PAGE: {
//                    fillPage(pageName, request);
//                    page = PagePath.REGISTRATION_PAGE_PATH;
                    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
                    dispatcher.forward(request, response);
                    break;
                }
                case REDIRECT_PAGE: {
//                    fillPage(pageName, request);
                    response.sendRedirect(request.getContextPath() + page);
                    break;
                }
                case NO_ACTION: {
                    break;
                }
                case CALL_COMMAND: {
                    String newCommandName = servletAction.getCommandName().toLowerCase();
                    request.setAttribute(Parameter.COMMAND, newCommandName);
                    processRequest(request, response);
                    break;
                }
            }
        } catch (IOException e) {
            // TODO: 21.07.2016 запилить
            e.printStackTrace();
        }

    }

    private static void fillPage(String targetPage, HttpServletRequest request) {
        IFiller filler = FillerFactory.getInstance().defineFiller(targetPage);
        filler.execute(request);
    }

    // TODO: 03.08.2016 времено

}
