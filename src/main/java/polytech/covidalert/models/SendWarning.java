package polytech.covidalert.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name="send_warning")
@Access(AccessType.FIELD)
public class SendWarning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long send_warning_id;
    private long user_infected_id;
    private long user_contacted_id;
    private long warning_id;
    private Date warning_date;

    public long getSend_warning_id() {
        return send_warning_id;
    }

    public void setSend_warning_id(long send_warning_id) {
        this.send_warning_id = send_warning_id;
    }

    public long getUser_infected_id() {
        return user_infected_id;
    }

    public void setUser_infected_id(long user_infected_id) {
        this.user_infected_id = user_infected_id;
    }

    public long getUser_contacted_id() {
        return user_contacted_id;
    }

    public void setUser_contacted_id(long user_contacted_id) {
        this.user_contacted_id = user_contacted_id;
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
