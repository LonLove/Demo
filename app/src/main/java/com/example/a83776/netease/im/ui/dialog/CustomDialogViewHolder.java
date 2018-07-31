package com.example.a83776.netease.im.ui.dialog;

import android.util.Pair;
import android.widget.TextView;

import com.example.a83776.demo.R;
import com.example.a83776.netease.base.ui.TViewHolder;


public class CustomDialogViewHolder extends TViewHolder {

	private TextView itemView;

	@Override
	protected int getResId() {
		return R.layout.nim_custom_dialog_list_item;
	}

	@Override
	protected void inflate() {
		itemView = (TextView) view.findViewById(R.id.custom_dialog_text_view);
	}

	@Override
	protected void refresh(Object item) {
        if(item instanceof Pair<?,?>){
            Pair<String,Integer> pair = (Pair<String, Integer>) item;
            itemView.setText(pair.first);
            itemView.setTextColor(context.getResources().getColor(pair.second));
        }
	}

}
