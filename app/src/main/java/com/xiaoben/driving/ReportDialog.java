package com.xiaoben.driving;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReportDialog extends Dialog {

    private int cx;
    private int cy;
    private RecyclerView mRecyclerView;
    private ReportRecyclerViewAdapter mRecyclerViewAdapter;
    private ViewSwitcher viewSwitcher;
    private String eventType;
    private ImageView mImageCamera;
    private Button mBackButton;
    private Button mSendButton;
    private EditText mCommentEditText;
    private ImageView eventTypeImg;
    private TextView mTypeTextView;
    private DialogCallBack dialogCallBack;

    interface DialogCallBack{
        void onSubmit(String editString, String event_type);
        void startCamera();

    }
    public void setDialogCallBack(DialogCallBack dialog) {
        dialogCallBack = dialog;
    }




    public ReportDialog(@NonNull Context context) {
        this(context, R.style.MyAlertDialogStyle);
    }

    public ReportDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ReportDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View dialogView = View.inflate(getContext(), R.layout.dialog, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(dialogView);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

//        //set up animation
//        setOnShowListener(new DialogInterface.OnShowListener(){
//
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                animateDialog(dialogView, true);
//            }
//        });

//        setOnKeyListener(new DialogInterface.OnKeyListener(){
//
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (i == KeyEvent.KEYCODE_BACK){
//                    animateDialog(dialogView, false);
//                    return true;
//                }
//                return false;
//            }
//        });

        setupRecyclerView(dialogView);
        viewSwitcher = dialogView.findViewById(R.id.viewSwitcher);
        Animation slide_in_left = AnimationUtils.loadAnimation(getContext(),
                android.R.anim.slide_in_left);
        Animation slide_out_right = AnimationUtils.loadAnimation(getContext(),
                android.R.anim.slide_out_right);
        viewSwitcher.setInAnimation(slide_in_left);
        viewSwitcher.setOutAnimation(slide_out_right);
        setUpEventSpecs(dialogView);

    }

    private void showNextViewSwitcher(String item){
        eventType = item;
        if (viewSwitcher != null){
            viewSwitcher.showNext();
            mTypeTextView.setText(eventType);
            eventTypeImg.setImageDrawable(ContextCompat.getDrawable(getContext(), Config.trafficMap.get(eventType)));
        }
    }

    private void setupRecyclerView(View dialogView){
        mRecyclerView = dialogView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerViewAdapter = new ReportRecyclerViewAdapter(getContext(), Config.listItems);
        mRecyclerViewAdapter.setClickListener(new ReportRecyclerViewAdapter.OnClickListener() {
            @Override
            public void setItem(String item) {
                //for switch item
                showNextViewSwitcher(item);
            }
        });
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void setUpEventSpecs(final View dialogView) {
        mImageCamera = dialogView.findViewById(R.id.event_camera_img);
        mBackButton =  dialogView.findViewById(R.id.event_back_button);
        mSendButton = dialogView.findViewById(R.id.event_send_button);
        mCommentEditText = dialogView.findViewById(R.id.event_comment);
        eventTypeImg = dialogView.findViewById(R.id.event_type_img);
        mTypeTextView = dialogView.findViewById(R.id.event_type);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCallBack.onSubmit(mCommentEditText.getText().toString(), eventType);

            }
        });

    }


}
