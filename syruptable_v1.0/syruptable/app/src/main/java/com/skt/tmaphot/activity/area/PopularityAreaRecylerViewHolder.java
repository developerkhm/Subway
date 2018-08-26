package com.skt.tmaphot.activity.area;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.tmaphot.R;

import java.util.HashMap;

public class PopularityAreaRecylerViewHolder extends RecyclerView.ViewHolder {
    private TextView areaName;
    private CheckBox areaCheckbox;
    private TextView id;
    private HashMap<Integer, String> selectValueMap;
    private int selec_count = 0;

    public PopularityAreaRecylerViewHolder(View itemView) {
        super(itemView);

        if (itemView != null) {
            areaName = (TextView) itemView.findViewById(R.id.popularity_area_name_txt);
            areaCheckbox = (CheckBox) itemView.findViewById(R.id.popularity_area_name_chbox);

            areaCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        areaCheckbox.setChecked(true);
                        selectValueMap.put(getAdapterPosition(), (String) areaName.getText());
                        Log.d("TEST12", "체크박스 에서만 체크");
                        selec_count++;
                    } else {
                        areaCheckbox.setChecked(false);
                        selectValueMap.remove(getAdapterPosition());
                        Log.d("TEST12", "체크박스 에서만 체크 해제");
                        selec_count--;
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("TEST12", "이이템 클릭");

                    if (!areaCheckbox.isChecked()) {  //체크
                        areaCheckbox.setChecked(true);
                        selectValueMap.put(getAdapterPosition(), (String) areaName.getText());
                        Log.d("TEST12", "체크");
                        selec_count++;
                    } else {  //체크 해제
                        areaCheckbox.setChecked(false);
                        selectValueMap.remove(getAdapterPosition());
                        Log.d("TEST12", "체크해제");
                        selec_count--;
                    }
                    // 리스트에 값 추가 및 check 박스 선택 해제 등등등등..
//                    areaCheckbox.setChecked(!areaCheckbox.isChecked());
                }
            });
        }
    }

    public TextView getAreaName() {
        return areaName;
    }

    public TextView getId() {
        return id;
    }

    public CheckBox getAreaCheckbox() {
        return areaCheckbox;
    }

    public void setSelectValueMap(HashMap<Integer, String> selectValueMap) {
        this.selectValueMap = selectValueMap;
    }
}
