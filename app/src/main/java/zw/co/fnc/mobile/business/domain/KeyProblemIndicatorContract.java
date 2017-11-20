package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jmuzinda on 11/5/17.
 */
@Table(name = "key_problem_indicator")
public class KeyProblemIndicatorContract extends Model{

    @Expose
    @Column(name = "key_problem")
    public KeyProblem keyProblem;

    @Expose
    @Column(name = "indicator")
    public Indicator indicator;

    public KeyProblemIndicatorContract(){
        super();
    }

    public static List<KeyProblemIndicatorContract> findByKeyProblem(KeyProblem keyProblem){
        return new Select()
                .from(KeyProblemIndicatorContract.class)
                .where("key_problem = ?", keyProblem.getId())
                .execute();
    }

    public static List<KeyProblemIndicatorContract> getAll(){
        return new Select()
                .from(KeyProblemIndicatorContract.class)
                .execute();
    }
}
