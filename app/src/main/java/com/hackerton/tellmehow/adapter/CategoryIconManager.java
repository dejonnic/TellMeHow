package com.hackerton.tellmehow.adapter;

import com.hackerton.tellmehow.R;

public class CategoryIconManager {
    public static int getIcon(int materialId, int categoryId) {
        switch (materialId){
            case 1:
                return R.drawable.prod_ic_glass_bottle;
            case 2:
                return R.drawable.prod_ic_metal_can;
            case 3:
                if(categoryId == 1) {
                    return R.drawable.prod_ic_paper_bottle;
                } else {
                    return R.drawable.prod_ic_hosehold_paper;
                }
            case 4:
                return R.drawable.prod_ic_plastic_bottle;
            case 5:
                return R.drawable.prod_ic_batteries;
            case 6:
                return R.drawable.prod_ic_ceramic;
            case 7:
                return R.drawable.prod_ic_household_light;
            case 8:
                return R.drawable.prod_ic_wearable_cloth;
            case 9:
                return R.drawable.prod_ic_electronic;
            case 11:
                return R.drawable.prod_ic_electronic;
            case 12:
                    return R.drawable.prod_ic_food_waste;
            default:
                return R.drawable.prod_ic_null;
        }
    }

}
