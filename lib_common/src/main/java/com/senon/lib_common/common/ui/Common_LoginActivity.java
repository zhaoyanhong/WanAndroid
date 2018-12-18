package com.senon.lib_common.common.ui;

import android.graphics.Paint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.ConstantLoginArouter;
import com.senon.lib_common.R;
import com.senon.lib_common.base.BaseActivity;
import com.senon.lib_common.base.BaseResponse;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.bean.Login;
import com.senon.lib_common.common.contract.LoginContract;
import com.senon.lib_common.common.presenter.LoginPresenter;
import com.senon.lib_common.utils.ToastUtil;

/**
 * 所有模块统一登录页面
 */
@Route(path = ConstantLoginArouter.PATH_COMMON_LOGINACTIVITY)
public class Common_LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements
        LoginContract.View {

    @Autowired
    String targetUrl;
    private EditText account_edt,password_edt;
    private TextView register_tv;


    @Override
    public int getLayoutId() {
        return R.layout.activity_common__login;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        if (targetUrl == null) {
            //默认跳转到MainActivity
            targetUrl = ConstantLoginArouter.PATH_APP_MAINACTIVITY;
        }
        account_edt = findViewById(R.id.account_edt);
        password_edt = findViewById(R.id.password_edt);
        register_tv = findViewById(R.id.register_tv);

        register_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

    }

    @Override
    public void getLoginResult(BaseResponse<Login> data) {
        //登录成功时
        //保存参数


        //跳转到目标页
        ARouter.getInstance().build(targetUrl).navigation();
        finish();
    }

    @Override
    public void getRegisterResult(BaseResponse<Login> data) {
    }

    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.login_btn){
            String account = account_edt.getText().toString();
            String password = password_edt.getText().toString();
            if (account.isEmpty()) {
                ToastUtil.initToast("请输入账号");
                return;
            }
            if (password.isEmpty()) {
                ToastUtil.initToast("请输入密码");
                return;
            }
            getPresenter().getLogin(ComUtil.getMd5Str(
                    new String[]{"username", "password"},
                    new String[]{account.trim(), password.trim()})
                    , true, true);
        }else if(id == R.id.visitor_tv){
            // 跳转到目标页
            ARouter.getInstance().build(targetUrl).navigation();
            finish();
        }else if(id == R.id.register_tv){
            //跳转到注册页 同意用arouter跳转，便于以后移动模块的改动
            ARouter.getInstance().build(ConstantArouter.PATH_COMMON_REGISTERACTIVITY)
                    .withString("targetUrl",targetUrl)
                    .navigation();
        }

    }

}