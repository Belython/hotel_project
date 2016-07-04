package by.booking.services.interfaces;

import by.booking.exceptions.ServiceException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void add(T entity) throws SQLException, ServiceException;

    List<T> getAll() throws SQLException, ServiceException;

    T getById(long id) throws SQLException, ServiceException;

    void update(T entity) throws SQLException, ServiceException;

    void delete(long id) throws SQLException, ServiceException;
}
