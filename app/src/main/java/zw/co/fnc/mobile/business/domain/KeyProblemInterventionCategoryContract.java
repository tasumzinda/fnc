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
@Table(name = "key_problem_intervention")
public class KeyProblemInterventionCategoryContract extends Model implements Serializable{

    @Expose
    @Column(name = "key_problem")
    public KeyProblem keyProblem;

    @Column(name = "intervention")
    @Expose
    public InterventionCategory interventionCategory;

    public KeyProblemInterventionCategoryContract(){
        super();
    }

    public static List<KeyProblemInterventionCategoryContract> findByKeyProblem(KeyProblem keyProblem){
        return new Select()
                .from(KeyProblemInterventionCategoryContract.class)
                .where("key_problem = ?", keyProblem.getId())
                .execute();
    }

    public static List<KeyProblemInterventionCategoryContract> getAll(){
        return new Select()
                .from(KeyProblemInterventionCategoryContract.class)
                .execute();
    }
}
