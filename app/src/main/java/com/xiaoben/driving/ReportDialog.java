package com.xiaoben.driving;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
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
    private RecyclerView recyclerView;
    private ReportRecyclerViewAdapter recyclerViewAdapter;
    private ViewSwitcher viewSwitcher;
    private String eventType;

    private Button backButton;
    private Button sendButton;
    private EditText commentEditText;
    private ImageView eventTypeImg;
    private TextView typeTextView;
    private DialogCallBack dialogCallBack;
    private String prefillText;

    //event specs
    private ImageView imageCamera;

    public void setVocieInfor(String event_type, String prefill_text) {
        eventType = event_type;
        prefillText = prefill_text;

    }

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
//        setOnShowListener(new DialogInterface.OnShowListener() {
//
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                animateDialog(dialogView, true);
//            }
//        });
//
//        setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                if (i == KeyEvent.KEYCODE_BACK) {
//                    animateDialog(dialogView, false);
//                    return true;
//                }
//                return false;
//            }
//        });
//        setupRecyclerView(dialogView);
//        mViewSwitcher = (ViewSwitcher) dialogView.findViewById(R.id.viewSwitcher);
//        setUpEventSpecs(dialogView);
//    });

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
            typeTextView.setText(eventType);
            eventTypeImg.setImageDrawable(ContextCompat.getDrawable(getContext(), Config.trafficMap.get(eventType)));
        }
    }

    private void setupRecyclerView(View dialogView){
        recyclerView = dialogView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerViewAdapter = new ReportRecyclerViewAdapter(getContext(), Config.listItems);
        recyclerViewAdapter.setClickListener(new ReportRecyclerViewAdapter.OnClickListener() {
            @Override
            public void setItem(String item) {
                //for switch item
                showNextViewSwitcher(item);
            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void updateImage(Bitmap bitmap){
        imageCamera.setImageBitmap(bitmap);
    }

    private void setUpEventSpecs(final View dialogView) {
        imageCamera = dialogView.findViewById(R.id.event_camera_img);
        backButton =  dialogView.findViewById(R.id.event_back_button);
        sendButton = dialogView.findViewById(R.id.event_send_button);
        commentEditText = dialogView.findViewById(R.id.event_comment);
        eventTypeImg = dialogView.findViewById(R.id.event_type_img);
        typeTextView = dialogView.findViewById(R.id.event_type);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCallBack.onSubmit(commentEditText.getText().toString(), eventType);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCallBack.onSubmit(commentEditText.getText().toString(), eventType);

            }
        });

        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallBack.startCamera();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (eventType != null) {
            showNextViewSwitcher(eventType);
        }
        if (prefillText != null) {
            commentEditText.setText(prefillText);
        }
    }


}
