package Models;

public class ModelAppointment {

    private String date, status, timeSlot, service;

    public ModelAppointment() {
    }

    public ModelAppointment(String date, String status, String timeSlot, String service) {
        this.date = date;
        this.status = status;
        this.timeSlot = timeSlot;
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
