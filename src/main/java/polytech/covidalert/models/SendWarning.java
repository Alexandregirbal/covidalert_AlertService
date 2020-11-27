package polytech.covidalert.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name="send_warning")
@Access(AccessType.FIELD)
public class SendWarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long send_warning_id;
    private long useridinfected;
    private long useridcontacted;
    private long warning_id;
    private Date warning_date;


    public long getUserIdInfected() {
        return useridinfected;
    }

    public void setUserIdInfected(long userIdInfected) {
        this.useridinfected = userIdInfected;
    }

    public long getUserIdContacted() {
        return useridcontacted;
    }

    public void setUserIdContacted(long userIdContacted) {
        this.useridcontacted = userIdContacted;
    }

    public long getSend_warning_id() {
        return send_warning_id;
    }

    public void setSend_warning_id(long send_warning_id) {
        this.send_warning_id = send_warning_id;
    }

    public long getWarning_id() {
        return warning_id;
    }

    public void setWarning_id(long warning_id) {
        this.warning_id = warning_id;
    }

    public Date getWarning_date() {
        return warning_date;
    }

    public void setWarning_date(Date warning_date) {
        this.warning_date = warning_date;
    }
}
