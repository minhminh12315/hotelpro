package dao;

import model.Booking;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class BookingDao implements BaseDao<Booking> {

    @Override
    public void save(Booking booking) {

    }

    @Override
    public void update(Booking booking) {

    }

    @Override
    public void delete(Booking booking) {

    }

    @Override
    public Booking findById(int id) {

        return null;
    }

    @Override
    public List<Booking> getAll() {

        return List.of();
    }
}