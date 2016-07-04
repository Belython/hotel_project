package by.booking.commands.factory;

import by.booking.commands.ICommand;
import by.booking.commands.impl.user.*;

public enum CommandType {
    //user commands
    LOGIN, LOGOUT, REGISTRATION, GOTOREGISTRATION, BACK, SELECTHOTEL, SELECTROOM, MAKEORDER, GOTOACCOUNT, PAYBILL,
    SETLOCALE,

    // client commands
    PAYMENT, GOTOPAYMENT, BALANCE, ADDFUNDS, GOTOADDFUNDS, BLOCK, BACKCLIENT,

    // admin commands
    CLIENTS, OPERATIONS, UNBLOCK, GOTOUNBLOCK, BACKADMIN;

    public ICommand getCurrentCommand() throws EnumConstantNotPresentException{
        switch(this){
            case LOGIN:
                return new LoginUserCommand();

            case LOGOUT:
                return new LogoutUserCommand();

            case REGISTRATION:
                return new RegistrationCommand();

            case GOTOREGISTRATION:
                return new GoToRegistrationCommand();

            case BACK:
                return new GoBackCommand();

            case SETLOCALE:
                return new SetLocaleCommand();

            case SELECTHOTEL:
                return new SelectHotelCommand();

            case SELECTROOM:
                return new SelectRoomCommand();

            case MAKEORDER:
                return new MakeOrderCommand();

            case GOTOACCOUNT:
                return new GoToAccountCommand();

            case PAYBILL:
                return new PayBillCommand();

            default:
                return new LoginUserCommand();
                //throw new EnumConstantNotPresentException(this.getDeclaringClass(), this.name());
        }
    }

}
