package by.booking.requestHandler;


/**
 * Created by Дмитрий on 19.07.2016.
 */
public enum ServletAction {
    FORWARD_PAGE, REDIRECT_PAGE, NO_ACTION, CALL_COMMAND;

    private String page;
    private String commandName;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
}
