package com.nova.maxis7567.learningblocks.pricing;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hinext.maxis7567.mstools.PriceConvertor;
import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.models.Package;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PriceCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PriceCardFragment extends Fragment {


    public PriceCardFragment() {
        // Required empty public constructor
    }


    public static PriceCardFragment newInstance(Activity context, int position, float scale, Package priceCard) {

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putFloat("scale", scale);
        bundle.putSerializable("priceCard",priceCard);
        PriceCardFragment fragment=new PriceCardFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (LinearLayout) inflater.inflate(R.layout.item_price_card_viewpager, container, false);
        Package p= (Package) getArguments().get("priceCard");
        ((TextView) view.findViewById(R.id.PriceCardPackageName)).setText(p.getTitle());
        ((TextView) view.findViewById(R.id.PriceCardPackageName)).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(p.getColor())));
        TextView desc=view.findViewById(R.id.PriceCardPackageDesc);
        for (int i = 0; i < p.getDescList().size(); i++) {
          desc.setText(desc.getText()+p.getDescList().get(i).getItem()+"\n");
        }
        TextView price,salePrice,discount,date;
        salePrice=view.findViewById(R.id.PriceCardPackagePrice);
        price=view.findViewById(R.id.PriceCardPackageDiscountedPrice);
        discount=view.findViewById(R.id.PricCardDiscount);
        date=view.findViewById(R.id.PricingCardTime);
        if (p.getPrice()==p.getSalePrice()){
            discount.setVisibility(View.INVISIBLE);
            price.setVisibility(View.INVISIBLE);

        }else {
            discount.setText((((p.getPrice() - p.getSalePrice()) * 100) / p.getPrice())+" %");
            price.setText(PriceConvertor.Convert(p.getPrice()));
            price.setPaintFlags(price.getPaintFlags() |  Paint.STRIKE_THRU_TEXT_FLAG);
        }
        salePrice.setText(PriceConvertor.Convert(p.getSalePrice()));
        date.setText("تومان / "+p.getTime()+" روزه");
        PriceCardLinearLayout root = (PriceCardLinearLayout) view.findViewById(R.id.item_root);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);
        return view;
    }
}