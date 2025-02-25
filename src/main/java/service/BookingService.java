package service;

import dao.BookingDao;
import model.Booking;

import java.util.List;

public class BookingService {
    private BookingDao bookingDao;

    public BookingService() {
        this.bookingDao = new BookingDao();
    }

    public void addBooking(Booking booking) {
        bookingDao.save(booking);
    }

    public void updateBooking(Booking booking) {
        bookingDao.update(booking);
    }

    public void deleteBooking(Booking booking) {
        bookingDao.delete(booking);
    }

    public Booking getBookingById(int id) {
        return bookingDao.findById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingDao.getAll();
    }
}