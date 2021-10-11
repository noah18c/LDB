import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Delivery {

    private DeliveryPerson deliveryPerson;
    private int deliveryId, deliveryPersonId;
    private Timestamp deliveryTime;
    private boolean out;

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    private DeliveryDataMapper deliveryDataMapper;
    private DeliveryPersonMapper deliveryPersonMapper;
    Delivery del;

    public Delivery(int deliveryId, DeliveryPerson deliveryPerson, Timestamp deliveryTime, DeliveryDataMapper deliveryDataMapper, DeliveryPersonMapper deliveryPersonMapper){
        this.deliveryId = deliveryId;
        this.deliveryDataMapper = deliveryDataMapper;
        this.deliveryPersonMapper = deliveryPersonMapper;

        this.deliveryPerson = deliveryPerson;
        this.deliveryPersonId = deliveryPerson.getDeliveryPersonId();
        this.deliveryTime = deliveryTime;
        out = false;
        del = this;

        setDeliveryGoesOut();
        setDeliveryPersonUnavailable();
    }

    public Delivery(DeliveryPerson deliveryPerson, Timestamp deliveryTime, DeliveryDataMapper deliveryDataMapper, DeliveryPersonMapper deliveryPersonMapper){
        this.deliveryDataMapper = deliveryDataMapper;
        this.deliveryPerson = deliveryPerson;
        this.deliveryPersonId = deliveryPerson.getDeliveryPersonId();
        this.deliveryTime = deliveryTime;
        this.deliveryPersonMapper = deliveryPersonMapper;
        out = false;
        del = this;

        setDeliveryGoesOut();
        setDeliveryPersonUnavailable();
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(int deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setOut(Boolean bool) {this.out = bool;}

    public boolean getOut() {return out;}

    private void setDeliveryGoesOut() {
        final Runnable deliveryGoesOut = new Runnable() {
            @Override
            public void run() {
                setOut(true);
                deliveryDataMapper.update(del);
            }
        };
        scheduler.schedule(deliveryGoesOut, 5, TimeUnit.MINUTES);
    }

    private void setDeliveryPersonUnavailable() {
        deliveryPerson.setAvailable(false);
        final Runnable personUnavailable = new Runnable() {
            @Override
            public void run() {
                deliveryPerson.setAvailable(true);
                deliveryPersonMapper.update(deliveryPerson);
            }
        };
        scheduler.schedule(personUnavailable, 30, TimeUnit.MINUTES);
    }
}
