package com.example.a83776.demo.ui.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.a83776.demo.R;
import com.example.a83776.demo.ui._interface.OnConfirmClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * 确定取消对话框
 */
public class ConfirmDialogFragment extends BlurDialogFragment {

    public static final String ARG_MESSAGE = "arg_message";
    @BindView(R.id.message)
    TextView mMessage;
    @BindView(R.id.ok)
    Button mOk;
    @BindView(R.id.cancel)
    Button mCancel;
    private String mString;
    private OnConfirmClickListener onConfirmClickListener;

    public static ConfirmDialogFragment newInstance() {
        return new ConfirmDialogFragment();
    }

    public static ConfirmDialogFragment newInstance(String message) {
        ConfirmDialogFragment fragment = new ConfirmDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Mdialog);
        if (getArguments() != null) {
            mString = getArguments().getString(ARG_MESSAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_confirm_dialog, container);
        ButterKnife.bind(this, view);
        if (!TextUtils.isEmpty(mString)) {
            mMessage.setText(mString);
        }
        return view;
    }

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 5.0f;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 7;
    }

    @Override
    protected boolean isActionBarBlurred() {//模糊效果
        // Enable or disable the blur effect on the action bar.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isDimmingEnable() {//调光效果
        // Enable or disable the dimming effect.
        // Disabled by default.
        return true;
    }

    public void setOnclickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    @OnClick(R.id.ok)
    void onPclick(View view) {
        if (onConfirmClickListener != null) {
            onConfirmClickListener.onPClick(view);
        }
        dismiss();
    }
    @OnClick(R.id.cancel)
    void onNclick(View view) {
        if (onConfirmClickListener != null) {
            onConfirmClickListener.onNclick(view);
        }
        dismiss();
    }
}
