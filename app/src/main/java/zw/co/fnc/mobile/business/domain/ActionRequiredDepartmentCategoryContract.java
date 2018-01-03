package zw.co.fnc.mobile.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.List;

@Table(name = "action_required_department")
public class ActionRequiredDepartmentCategoryContract extends Model{

    @Expose
    @Column(name = "action_required")
    public ActionRequired actionRequired;

    @Expose
    @Column(name = "department")
    public DepartmentCategory departmentCategory;

    public ActionRequiredDepartmentCategoryContract(){

    }

    public static List<ActionRequiredDepartmentCategoryContract> findByActionRequired(ActionRequired a){
        return new Select()
                .from(ActionRequiredDepartmentCategoryContract.class)
                .where("action_required = ?", a.getId())
                .execute();
    }
}
