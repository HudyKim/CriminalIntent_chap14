package jaeyeop.co.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID id;
    private String title;
    private Date date;
    private boolean solved;

    public Crime() {
        this(UUID.randomUUID());

        //id = UUID.randomUUID();
        //date = new Date();
    }

    public Crime(UUID id){
        this.id = id;
        date = new Date();
    }


    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
    public boolean isSolved() {
        return solved;
    }
}
