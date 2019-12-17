package com.tlongx.common.enums;

public enum SupplyTime {
    早餐(1),
    午餐(2),
    晚餐(3),
    水果超市(4),
    便利小超(5),
    特色早餐(6),
    特色午餐(7),
    特色晚餐(8),
    ;

    private Integer type;

    public Integer getType() {
        return type;
    }

    SupplyTime(Integer type) {
        this.type = type;
    }
}
