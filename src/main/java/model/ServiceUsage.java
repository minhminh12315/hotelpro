package model;

    import java.util.Date;

    public class ServiceUsage {
        private int serviceUsageID;
        private Booking booking;
        private Service service;
        private Product product;
        private int serviceUsagePrice;
        private int quantity;
        private Date usageDate;

        // Getters and setters...

        public int getServiceUsageID() {
            return serviceUsageID;
        }

        public void setServiceUsageID(int serviceUsageID) {
            this.serviceUsageID = serviceUsageID;
        }

        public Booking getBooking() {
            return booking;
        }

        public void setBooking(Booking booking) {
            this.booking = booking;
        }

        public Service getService() {
            return service;
        }

        public void setService(Service service) {
            this.service = service;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getServiceUsagePrice() {
            return serviceUsagePrice;
        }

        public void setServiceUsagePrice(int serviceUsagePrice) {
            this.serviceUsagePrice = serviceUsagePrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Date getUsageDate() {
            return usageDate;
        }

        public void setUsageDate(Date usageDate) {
            this.usageDate = usageDate;
        }
    }