package zw.co.fnc.mobile.business.domain.util;

import zw.co.fnc.mobile.util.StringUtils;

/**
 * Created by User on 3/7/2017.
 */
public enum PeriodType {

    MONTHLY(1), QUARTERLY(2), HALF_YEARLY(3), YEARLY(4);

    private final Integer code;

    private PeriodType(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }

    public static PeriodType get(Integer code){
        switch(code){
            case 1:
                return MONTHLY;
            case 2:
                return QUARTERLY;
            case 3:
                return HALF_YEARLY;
            case 4:
                return YEARLY;
            default:
                throw new IllegalArgumentException("Illegal parameter passed to method :" + code);
        }
    }

    public String getName(){
        return StringUtils.toCamelCase3(super.name());
    }
}
