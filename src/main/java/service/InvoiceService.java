package service;

import dao.InvoiceDao;
import model.Invoice;

import java.util.List;

public class InvoiceService {
    private InvoiceDao invoiceDao;

    public InvoiceService() {
        this.invoiceDao = new InvoiceDao();
    }

    public void addInvoice(Invoice invoice) {
        invoiceDao.save(invoice);
    }

    public void updateInvoice(Invoice invoice) {
        invoiceDao.update(invoice);
    }

    public void deleteInvoice(Invoice invoice) {
        invoiceDao.delete(invoice);
    }

    public Invoice getInvoiceById(int id) {
        return invoiceDao.findById(id);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceDao.getAll();
    }
}