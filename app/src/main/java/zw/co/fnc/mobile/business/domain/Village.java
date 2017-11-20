package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 3/7/2017.
 */
@Table(name = "village")
public class Village extends Model implements Serializable{

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "ward")
    private Ward ward;

    @Expose
    @Column(name = "facility")
    private Facility facility;

    @Expose
    private List<Household> households;

    public Village(){
        super();
    }

    public String toString(){
        return name;
    }
}
