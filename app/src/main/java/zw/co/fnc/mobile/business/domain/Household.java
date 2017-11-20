package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by User on 3/7/2017.
 */
@Table(name = "house_hold")
public class Household extends Model {

    @Expose
    @Column(name = "uuid")
    private String uuid;
    @Expose
    @Column(name = "date_created")
    private Date dateCreated;
    @Expose
    @Column(name = "date_modified")
    private Date dateModified;
    @Expose
    @Column(name = "version")
    private Long version;
    @Expose
    @Column(name = "active")
    private Boolean active = Boolean.TRUE;
    @Expose
    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;
    @Expose
    @Column(name = "name")
    public String name;
    @Expose
    @Column(name = "description")
    public String description;
    @Expose
    @Column(name = "village")
    public Village village;
    
    @Expose
    @Column(name = "facility")
    public Facility facility;
    
    @Expose
    @Column(name = "house_hold_code")
    public String householdCode;

    public Household(){

    }

    public String toString(){
        return name;
    }
}
