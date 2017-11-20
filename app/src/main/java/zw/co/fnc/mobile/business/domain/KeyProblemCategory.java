package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jmuzinda on 11/4/17.
 */
@Table(name = "key_problem_category")
public class KeyProblemCategory extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column
    public String name;

    public KeyProblemCategory(){
        super();
    }

    public static KeyProblemCategory findById(Long serverId){
        return new Select()
                .from(KeyProblemCategory.class)
                .where("Id = ?", serverId)
                .executeSingle();
    }

    public static KeyProblemCategory findByServerId(Long id) {
        return new Select()
                .from(KeyProblemCategory.class)
                .where("serverId =?", id)
                .executeSingle();
    }

        public static List<KeyProblemCategory> getAll(){
        return new Select()
                .from(KeyProblemCategory.class)
                .execute();
    }

    public String toString(){
        return name;
    }
}
