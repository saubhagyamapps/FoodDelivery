package app.food.delivery.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import app.food.delivery.R;
import app.food.delivery.model.ResetPasswordModel;
import app.food.delivery.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordFragment extends Fragment {
    private static final String TAG = "ResetPasswordFragment";
    EditText edt_profOldPass, edt_profNewPass, edt_profConfirmNewPass;
    Button btn_ResetPassword;
    String mOldPassword, mNewPassword, mConfirmNewPAssword;

    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.activity_forgot_password, container, false);
        initializtion();
        Constant.setSession(getActivity());
        return mView;
    }

    private void initializtion() {
        edt_profOldPass = mView.findViewById(R.id.edt_profOldPass);
        edt_profNewPass = mView.findViewById(R.id.edt_profNewPass);
        edt_profConfirmNewPass = mView.findViewById(R.id.edt_profConfirmNewPass);
        btn_ResetPassword = mView.findViewById(R.id.btn_ResetPassword);
        btnsubmit();
    }

    private void btnsubmit() {
        btn_ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOldPassword = edt_profOldPass.getText().toString().trim();
                mNewPassword = edt_profNewPass.getText().toString().trim();
                mConfirmNewPAssword = edt_profConfirmNewPass.getText().toString().trim();
                validation();
            }
        });

    }

    private void validation() {
        if (mOldPassword.equals("")) {
            edt_profOldPass.setError("Required");

        } else if (mNewPassword.equals("")) {
            edt_profNewPass.setError("Required");

        } else if (mConfirmNewPAssword.equals("")) {
            edt_profConfirmNewPass.setError("Required");

        } else if (!mNewPassword.equals(mConfirmNewPAssword)) {
            Constant.toast("New password and confirm password does't match", getActivity());

        } else {
            getResetPassword();
        }
    }

    private void getResetPassword() {
        Call<ResetPasswordModel> modelCall = Constant.apiService.getResetPassword(Constant.mUserId, mOldPassword, mNewPassword);
        modelCall.enqueue(new Callback<ResetPasswordModel>() {
            @Override
            public void onResponse(Call<ResetPasswordModel> call, Response<ResetPasswordModel> response) {
                String result = response.body().getStatus();
                Log.d(TAG, "MainActivity" + result);

                if (response.body().getStatus().equals("0")) {
                    Constant.toast(response.body().getMessage(), getActivity());
                } else {
                    Constant.toast(response.body().getMessage(), getActivity());
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordModel> call, Throwable t) {

            }
        });
    }
}
