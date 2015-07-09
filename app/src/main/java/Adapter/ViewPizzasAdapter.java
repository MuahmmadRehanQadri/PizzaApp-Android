package Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.muhammadrehanqadri.iwantpizza.R;

import java.util.ArrayList;

import Model.Pizza;

/**
 * Created by Muhammad Rehan Qadri on 6/25/2015.
 */
public class ViewPizzasAdapter implements ListAdapter {
    private final ArrayList<Pizza> pizzaArrayList;
    private final Context context;

    public ViewPizzasAdapter(Context context, ArrayList<Pizza> pizzaArrayList) {
        this.pizzaArrayList = pizzaArrayList;
        this.context = context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return pizzaArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      //  CheckableLayout checkableLayout = new CheckableLayout(context);
        View view;

        if (convertView == null){
            view = new CheckableLayout(context);
            view = layoutInflater.inflate(R.layout.pizza, null);
            TextView tvName =  ((TextView)view.findViewById(R.id.editTextName));
            TextView tvPrice = ((TextView)view.findViewById(R.id.editTextPrice));
            ImageView iv = (ImageView)view.findViewById(R.id.imageView);
            tvName.setText(pizzaArrayList.get(position).getName());
            tvPrice.setText(pizzaArrayList.get(position).getPrice().toString());
            byte[] imageBytes = pizzaArrayList.get(position).getImage();
            iv.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length)));

//            NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
//            numberPicker.setMinValue(0);
//            numberPicker.setMaxValue(10000);
        }
        else{
            view = convertView;
        }
      //  checkableLayout.addView(view);
        return  view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}


class CheckableLayout extends View implements Checkable {
    private boolean mChecked;

    public CheckableLayout(Context context) {
        super(context);
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
        setBackgroundDrawable(checked ?
                getResources().getDrawable(R.mipmap.blue)
                : null);
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void toggle() {
        setChecked(!mChecked);
    }

}