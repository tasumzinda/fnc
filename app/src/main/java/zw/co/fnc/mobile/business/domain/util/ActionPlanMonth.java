package zw.co.fnc.mobile.business.domain.util;

/**
 * Created by User on 3/7/2017.
 */
public enum ActionPlanMonth {

    FIRST(1), SECOND(2), THIRD(3);

    private final Integer code;

    private ActionPlanMonth(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }

    public static ActionPlanMonth get(Integer code){
        switch(code){
            case 1:
                return FIRST;
            case 2:
                return SECOND;
            case 3:
                return THIRD;
            default:
                throw new IllegalArgumentException("Illegal paratmeter passed to method :"+code);
        }
    }
}
