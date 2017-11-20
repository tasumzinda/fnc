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
 * Created by jmuzinda on 11/5/17.
 */
@Table(name = "department_category")
public class DepartmentCategory extends Model implements Serializable{

    @SerializedName("id")
    @Expose
    @Column
    public Long serverId;

    @Expose
    @Column
    public String name;

    public DepartmentCategory(){
        super();
    }

    public static DepartmentCategory findById(Long serverId){
        return new Select()
                .from(DepartmentCategory.class)
                .where("Id = ?", serverId)
                .executeSingle();
    }

    public static DepartmentCategory findByServerId(Long id){
        return new Select()
                .from(DepartmentCategory.class)
                .where("serverId =?", id)
                .executeSingle();
    }

    public static List<DepartmentCategory> getAll(){
        return new Select()
                .from(DepartmentCategory.class)
                .execute();
    }

    public static List<DepartmentCategory> findByActionRequired(ActionRequired action){
        return new Select()
                .from(DepartmentCategory.class)
                .innerJoin(ActionRequiredDepartmentCategoryContract.class)
                .on("action_required_department.department = department_category.id")
                .where("action_required_department.action_required = ?", action.getId())
                .execute();
    }

    @Override
    public String toString() {
        return name;
    }
}
