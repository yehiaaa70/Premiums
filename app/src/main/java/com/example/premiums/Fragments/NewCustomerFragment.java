package com.example.premiums.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premiums.Adapters.CusCategory_Adapter;
import com.example.premiums.Models.CusCategory_model;
import com.example.premiums.Models.Information_Users_Model;
import com.example.premiums.R;
import com.example.premiums.RoomDatabase.AsyncTask.CategoryAsync.AddCategory_Async;
import com.example.premiums.RoomDatabase.AsyncTask.PersonalAsync.AddPersonal_Async;
import com.example.premiums.RoomDatabase.RoomFactory;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class NewCustomerFragment extends Fragment {

    private Uri resultUri;
    private TextInputEditText name_ed, age_ed, address_ed, phone_ed, ID_ed, price_ed, percent_ed,cateName_ed,cateAmount_ed,catePrice_ed;
    private FloatingActionButton addNewCategory, uploadImage;
    private TextView Date_tv;
    private ImageButton Date_btn;
    private CircleImageView UploadImage;
    private CircularProgressButton SaveCustomer;

    private RecyclerView categoryRecycler;
    private CusCategory_Adapter categoryAdapter;
    private List<CusCategory_model> modelList = new ArrayList<>();
    private CusCategory_model model;

    private int fullScreenTheme;
    private MaterialDatePicker<?> picker;



    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                UploadImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_customer, container, false);

        name_ed = view.findViewById(R.id.name_ed);
        age_ed = view.findViewById(R.id.age_ed);
        address_ed = view.findViewById(R.id.Address_ed);
        phone_ed = view.findViewById(R.id.phone_ed);
        ID_ed = view.findViewById(R.id.ID_ed);
        price_ed = view.findViewById(R.id.CustomerPrice_ed);
        percent_ed = view.findViewById(R.id.Percentage_ed);
        cateName_ed = view.findViewById(R.id.CateName_ed);
        cateAmount_ed = view.findViewById(R.id.CateAmount_ed);
        catePrice_ed = view.findViewById(R.id.CatePrice_ed);

        addNewCategory = view.findViewById(R.id.addNewCategory_btn);
        uploadImage = view.findViewById(R.id.add_image_Fbtn);
        Date_btn = view.findViewById(R.id.Date_btn);
        Date_tv = view.findViewById(R.id.Date_tv);
        categoryRecycler = view.findViewById(R.id.Custom_Recycler);
        UploadImage = view.findViewById(R.id.choose_Customer_iv);
        SaveCustomer = view.findViewById(R.id.cirSaveCustomer_btn);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullScreenTheme = resolveOrThrow(requireContext(), R.attr.materialCalendarFullscreenTheme);

        categoryAdapter = new CusCategory_Adapter(modelList, requireContext());

        categoryRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        categoryRecycler.setAdapter(categoryAdapter);

        addNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(cateName_ed.getText().toString().trim())) {
                    cateName_ed.setError("Enter Category Name");
                } else if (TextUtils.isEmpty(catePrice_ed.getText().toString().trim())) {
                    catePrice_ed.setError("Enter Category Amount");
                } else if (TextUtils.isEmpty(cateAmount_ed.getText().toString().trim())) {
                    cateAmount_ed.setError("Enter Category Price");
                }else {
                    addCategory(cateName_ed.getText().toString(),cateAmount_ed.getText().toString(),catePrice_ed.getText().toString());
                }

            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser();
            }
        });

        Date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullDateRangePicker(v);
            }
        });

        SaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData(v);
            }
        });
    }

    //Save to Room Database
    private void SaveData(View view) {
        if (TextUtils.isEmpty(name_ed.getText().toString().trim())) {
            name_ed.setError("Enter your Name");
        } else if (TextUtils.isEmpty(age_ed.getText().toString().trim())) {
            age_ed.setError("Enter your Age");
        } else if (TextUtils.isEmpty(address_ed.getText().toString().trim())) {
            address_ed.setError("Enter your Address");
        } else if (TextUtils.isEmpty(phone_ed.getText().toString().trim())) {
            phone_ed.setError("Enter your Phone");
        } else if (TextUtils.isEmpty(ID_ed.getText().toString().trim())) {
            ID_ed.setError("Enter your National ID");
        } else if (TextUtils.isEmpty(price_ed.getText().toString().trim())) {
            price_ed.setError("Enter Customer First Payment");
        } else if (TextUtils.isEmpty(percent_ed.getText().toString().trim())) {
            percent_ed.setError("Enter percentage of  The Category");
        } else if (resultUri == null) {
            Toast.makeText(requireContext(), "Please Choose Customer Photo", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Date_tv.getText().toString().trim())) {
            Toast.makeText(requireContext(), "Enter The Date", Toast.LENGTH_SHORT).show();
        } else {
            SaveCustomer.startAnimation();

            new AddPersonal_Async(RoomFactory.getPersonalDatabase(requireContext()).getPersonalDAO())
                    .execute(new Information_Users_Model(name_ed.getText().toString()
                            , phone_ed.getText().toString()
                            , address_ed.getText().toString()
                            , age_ed.getText().toString()
                            , ID_ed.getText().toString()
                            , price_ed.getText().toString()
                            , "0"
                            , percent_ed.getText().toString()
                            , Date_tv.getText().toString()
                            , resultUri.toString()
                            ,false));



            for (int i = 0; i < modelList.size(); i++) {
                new AddCategory_Async(RoomFactory.getPersonalDatabase(requireContext()).categoryDAO())
                        .execute(new CusCategory_model(modelList.get(i).getCateName()
                                , modelList.get(i).getCateAmount()
                                , modelList.get(i).getCatePrice()
                                , phone_ed.getText().toString()));
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Navigation.findNavController(view).popBackStack(R.id.homePageFragment, false);

                }

            }, 3000);

        }


    }

    //For Choose Image From Your Phone
    private void OpenFileChooser() {
        CropImage.activity(resultUri).start(getContext(), this);
        UploadImage.setImageURI(resultUri);
    }



    //Add new line to of Category
    private void addCategory(String name,String amount,String price) {
        model=new CusCategory_model(name,amount,price);
        modelList.add(model);
        categoryAdapter.notifyDataSetChanged();
        cateName_ed.setText("");
        cateAmount_ed.setText("");
        catePrice_ed.setText("");
    }


    //////////////////////////////////////////////////////////////

    public void openFullDateRangePicker(View view) {
        MaterialDatePicker.Builder<?> builder = setDateBuilder(false);
        builder.setTheme(fullScreenTheme);
        picker = builder.build();
        picker.show(requireFragmentManager(), picker.toString());
        addListener(picker);
    }

    private void addListener(MaterialDatePicker<?> picker) {

        picker.addOnPositiveButtonClickListener(selection ->
                Date_tv.setText(picker.getHeaderText()));

        picker.addOnNegativeButtonClickListener(selection ->
                Toast.makeText(requireContext(), picker.getHeaderText() + "yyyy2", Toast.LENGTH_SHORT).show());

        picker.addOnCancelListener(selection ->
                Toast.makeText(requireContext(), picker.getHeaderText() + "yyyy3", Toast.LENGTH_SHORT).show());
    }

    private MaterialDatePicker.Builder<?> setDateBuilder(boolean isDatePicker) {
        int inputMode = MaterialDatePicker.INPUT_MODE_CALENDAR;

        if (isDatePicker) {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            builder.setInputMode(inputMode);
            return builder;
        } else {
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
            builder.setInputMode(inputMode);
            return builder;
        }
    }

    private int resolveOrThrow(Context context, @AttrRes int attributeResId) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attributeResId, typedValue, true)) {
            return typedValue.data;
        }
        throw new IllegalArgumentException(context.getResources().getResourceName(attributeResId));
    }

    /////////////////////////////////////////////////////////////////
}