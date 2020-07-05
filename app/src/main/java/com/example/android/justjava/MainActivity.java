package com.example.android.justjava;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
         int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox=(CheckBox)findViewById(R.id.whipped_cream_view);
        boolean haswhippedcream=whippedCreamCheckBox.isChecked();
        CheckBox ChocolatecreamCheckBox=(CheckBox)findViewById(R.id.chocolate_view);
        boolean haschocolate=ChocolatecreamCheckBox.isChecked();
        EditText typeName=(EditText) findViewById(R.id.album_description_view);
        String name =typeName.getText().toString();
        int price=calculatePrice(haswhippedcream,haschocolate);
        String summaryOrder=createOrderSummary(name,price,haswhippedcream,haschocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For "+name);
        intent.putExtra(Intent.EXTRA_TEXT,summaryOrder );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displaymessage(summaryOrder);



    }
    private String createOrderSummary(String name,int price,boolean cream,boolean chocolate){
        String pricemessage=name;
        pricemessage+="\nAdd Whipped Cream? "+cream;
        pricemessage+="\nAdd Chocolate Toppping? "+chocolate;
        pricemessage=pricemessage+"\nQuantity: "+quantity;
        pricemessage+="\nTotal:  $"+price ;
        pricemessage=pricemessage+"\nThankyou!";
     return pricemessage;
    }

    private int calculatePrice(boolean haswhippedcream,boolean haschocolate) {
        int baseprice=5;
        if(haswhippedcream){
            baseprice=baseprice+1;
        }
        if(haschocolate){
            baseprice=baseprice+2;
        }

            return baseprice*quantity;
    }
    public void increment(View view){

        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view) {
        if (quantity > 0) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            quantity=0;
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

private void displaymessage( String message){
    TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
    orderSummaryTextView.setText(message);
}

}
