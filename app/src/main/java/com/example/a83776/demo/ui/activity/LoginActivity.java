package com.example.a83776.demo.ui.activity;

import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.example.a83776.demo.R;
import com.example.a83776.demo.base.BaseActivity;
import com.example.a83776.demo.contract.Logincontract;
import com.example.a83776.demo.model.bean.Book;
import com.example.a83776.demo.presenter.LoginPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description:
 * author: GaoJie
 * created at: 2018/5/25 17:01
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, Logincontract.View {
    @BindView(R.id.btn_login)
    AppCompatButton mBtnLogin;
    @BindView(R.id.text)
    TextView mText;

    @Override
    protected void initEventAndData() {
        ButterKnife.bind(this);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            mPresenter.getSearchBook("金瓶梅", null, 0, 1);
        }
        //        Car car = new Car();
        //        car.getEngineA().printGearName();
        //        car.getEngineB().printGearName();
        //        Car1 car1=new Car1();
        //        System.out.println("car = " +car1.getEngineA().hashCode() );
        //
        //        System.out.println("car = " + car1.getEngineB().hashCode());
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void onGetBookResult(int code, String message, List<Book.BooksBean> books) {
        mText.setText(books.toString());
    }
}
