package org.zw.android.framework.util;

import org.android.framework.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlertDialogUtil {
	//确认 --取消弹出框
	public static void ShowAlertDialog(Context context,String msg,String title,OnClickListener confirmlistener,OnClickListener cancellistener){
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton("确认", confirmlistener);
		builder.setNegativeButton("取消", cancellistener);
		builder.create().show();
	}
	
	//显示一个确定按钮的提示框
	public static void ShowConfirmAlertDialog(Context context,String msg,String title,OnClickListener confirmlistener){
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton("确认", confirmlistener);
		builder.create().show();
	}
	
	//显示列表弹出框
	public static void ShowListDialog(Context context,String title,CharSequence[] charSequences,OnClickListener listener){
		AlertDialog.Builder builder= new AlertDialog.Builder(context);
		builder.setTitle(title).setIcon(null)
		.setItems(charSequences, listener).setCancelable(true);
		AlertDialog ad3=builder.create();
		ad3.setCanceledOnTouchOutside(true);
		ad3.show();
		
	}
	
	/** 
     * 得到自定义的progressDialog 
     * @param context 
     * @param msg 
     * @return 
     */  
    public static Dialog createLoadingDialog(Context context) {  
  
        LayoutInflater inflater = LayoutInflater.from(context);  
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view  
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局  
        // main.xml中的ImageView  
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);  
//        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字  
        // 加载动画  
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(  
                context, R.anim.loading_animation);  
        // 使用ImageView显示动画  
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);  
//        tipTextView.setText(msg);// 设置加载信息  
  
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog  
  
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消  
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
                LinearLayout.LayoutParams.FILL_PARENT,  
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局  
        return loadingDialog;  
  
    }  
}
