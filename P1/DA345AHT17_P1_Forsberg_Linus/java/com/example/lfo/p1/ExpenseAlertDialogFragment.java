package com.example.lfo.p1;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;


public class ExpenseAlertDialogFragment extends Fragment {
    private TextView tvDate;
    private TextView tvTitle;
    private ImageView ivCategory;
    private TextView tvPrice;
    private String date;
    private String title;
    private int category;
    private double price;
    private Controller controller;

    public ExpenseAlertDialogFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_alert_dialog, container, false);
        tvDate = (TextView)view.findViewById(R.id.tvDateTo);
        tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        ivCategory = (ImageView) view.findViewById(R.id.ivCategory);
        tvPrice = (TextView)view.findViewById(R.id.tvPrice);
        setTextViews(savedInstanceState);
        return view;
    }

    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
        onSavedInstanceState.putString("date", tvDate.getText().toString());
        onSavedInstanceState.putString("title", tvTitle.getText().toString());
        onSavedInstanceState.putByteArray("category", getImageInBytes());
        onSavedInstanceState.putString("price", tvPrice.getText().toString());
    }

    public void setText(String date, String title, int category, double price) {
        this.date = date;
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public byte[] getImageInBytes() {
        ivCategory.setDrawingCacheEnabled(true);
        ivCategory.buildDrawingCache();
        Bitmap bitmap = ivCategory.getDrawingCache();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageInByte=stream.toByteArray();
        return imageInByte;
    }

    public Bitmap convertBytesToBitmap(Bundle savedInstanceState) {
        byte[] imageInByte = savedInstanceState.getByteArray("category");
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        return bitmap;
    }

    public void setTextViews(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            tvDate.setText(savedInstanceState.getString("date"));
            tvTitle.setText(savedInstanceState.getString("title"));
            ivCategory.setImageBitmap(convertBytesToBitmap(savedInstanceState));
            tvPrice.setText(savedInstanceState.getString("price"));
        } else {
            tvDate.setText(date);
            tvTitle.setText(title);
            ivCategory.setImageResource(category);
            tvPrice.setText(String.valueOf(price));
        }
    }
}
