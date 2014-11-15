package com.nine.viewpaperdemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class VideoPlayDialog extends Dialog {

    Context context;
    private View dialogView;

    public VideoPlayDialog(Context context) {
        super(context);
        this.context = context;
    }
    
    public VideoPlayDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
        LayoutInflater inflater= LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.videoplaydialog, null);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(dialogView);
    }
    
    @Override
    public View findViewById(int id) {
	    //��дfindViewById������ȡ�Ի����пؼ�
	    return super.findViewById(id);
    }
    
    public View getDialogView() {
    	//��öԻ���view
    	return dialogView;
    }

}

