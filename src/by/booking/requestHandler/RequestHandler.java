package by.booking.requestHandler;

import by.booking.commands.ICommand;
import by.booking.commands.factory.CommandFactory;
import by.booking.constants.Parameters;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestHandler {
    private RequestHandler() {
    }

    public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        CommandFactory commandFactory = CommandFactory.getInstance();
        ICommand сommand = commandFactory.defineCommand(request);
        ServletAction servletAction = сommand.execute(request, response);
        try {
            switch (servletAction) {
                case FORWARD_PAGE: {
                    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(servletAction.getPage());
                    dispatcher.forward(request, response);
                    break;
                }
                case REDIRECT_PAGE: {
                    response.sendRedirect(request.getContextPath() + servletAction.getPage());
                    break;
                }
                case NO_ACTION: {
                    break;
                }
                case CALL_COMMAND: {
                    String newCommandName = servletAction.getCommandName().toLowerCase();
                    request.setAttribute(Parameters.COMMAND, newCommandName);
                    processRequest(request, response);
                    break;
                }
            }
        } catch (IOException e) {
            // TODO: 21.07.2016 запилить
            e.printStackTrace();
        }

    }

}
