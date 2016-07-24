package by.booking.services.interfaces;

import by.booking.exceptions.ServiceException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void add(T entity) throws ServiceException;

    List<T> getAll() throws ServiceException;

    T getById(long id) throws ServiceException;

    void update(T entity) throws ServiceException;

    void delete(long id) throws ServiceException;
}
