package app.food.delivery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.food.delivery.R;
import app.food.delivery.activity.OrderDoneActivity;
import app.food.delivery.model.AddNewAddressModel;
import app.food.delivery.model.OderConformModel;
import app.food.delivery.sessionmanager.SessionManager;
import app.food.delivery.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFragment extends Fragment {

    View mView;
    EditText txtUserAddress;
    Button btnPayment;
    String mAddress;
    SessionManager manager;
    String mFoodIDS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_payment, container, false);
        getActivity().setTitle("Payment");
        Bundle args = getArguments();
        mFoodIDS = args.getString("Food_ids");
        initialization();
        manager = new SessionManager(getActivity());
        Constant.setSession(getActivity());
        return mView;
    }

    private void initialization() {
        txtUserAddress = mView.findViewById(R.id.txtUserAddress);
        btnPayment = mView.findViewById(R.id.btnPayment);
        getValue();

    }

    private void getValue() {

        setAddress();
    }

    private void setAddress() {
        txtUserAddress.setText(Constant.mAddress);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

    private void validation() {
        mAddress = txtUserAddress.getText().toString().trim();

        if (mAddress.equals("")) {
            txtUserAddress.setError("Required");
        } else {
            OderConformAPICALL();
            AddressAPICALL();

        }
    }

    private void OderConformAPICALL() {
        Call<OderConformModel> conformModelCall=Constant.apiService.oderConform(mFoodIDS,Constant.mUserId);
        conformModelCall.enqueue(new Callback<OderConformModel>() {
            @Override
            public void onResponse(Call<OderConformModel> call, Response<OderConformModel> response) {
              //  Constant.toast(response.body().getData().getMessage(),getActivity());
            }

            @Override
            public void onFailure(Call<OderConformModel> call, Throwable t) {

            }
        });

    }

    private void AddressAPICALL() {
        Call<AddNewAddressModel> newAddressModelCall = Constant.apiService.updateAddress(Constant.mUserId, mAddress);
        newAddressModelCall.enqueue(new Callback<AddNewAddressModel>() {
            @Override
            public void onResponse(Call<AddNewAddressModel> call, Response<AddNewAddressModel> response) {
                manager.createLoginSession(Constant.mUserId, Constant.mUserName, Constant.mUserEmail, Constant.mUserMobile, Constant.mPassword
                        , Constant.mDeviceId, Constant.mUserFirebaseID, mAddress, Constant.mImages, Constant.mGender);
                Constant.intent(getActivity(), OrderDoneActivity.class);
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<AddNewAddressModel> call, Throwable t) {

            }
        });
    }

}
