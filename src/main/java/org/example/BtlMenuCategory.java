package org.example;

public enum BtlMenuCategory {
    MIZOY_ZCHOYUT("מיצוי זכויות"),
    KITSBAOT("קצבאות והטבות"),
    BITOACH("דמי ביטוח"),
    CONTACT("יצירת קשר");

    private final String menuTitle;

    BtlMenuCategory(String menuTitle) {
        this.menuTitle = menuTitle;
    }
    public String getMenuTitle() {
        return this.menuTitle;
    }
}
