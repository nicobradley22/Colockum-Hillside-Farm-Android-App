package com.example.colockumhillsidefarmapp.vendor.update_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

public class EditProductActivity extends AppCompatActivity {

    private static final String PRODUCT = "product";

    private EditText txtNameEditProdAct, txtQuantityEditProdAct,
            txtImageUrlEditProdAct, txtShortDescEditProdAct,
            txtLongDescEditProdAct, txtPriceEditProdAct, txtPackageQuantityEditProdAct;
    private Button btnUpdateProductEditProdAct;

    private Product productToEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        Intent intent = getIntent();
        if(getIntent() != null){
            productToEdit = intent.getParcelableExtra(PRODUCT);
            if(productToEdit != null){
                setData(productToEdit);
            }
        }

        btnUpdateProductEditProdAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    Product editedProduct = new Product(productToEdit.getId(), txtNameEditProdAct.getText().toString(), Integer.parseInt(txtQuantityEditProdAct.getText().toString()), txtImageUrlEditProdAct.getText().toString(),
                            txtShortDescEditProdAct.getText().toString(), txtLongDescEditProdAct.getText().toString(), Double.parseDouble(txtPriceEditProdAct.getText().toString()), txtPackageQuantityEditProdAct.getText().toString());

                    DBInterface.getInstance().editProduct(productToEdit, editedProduct);
                    Toast.makeText(EditProductActivity.this, productToEdit.getName() + " edited.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), VendorDashboardActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initVariables() {
        txtNameEditProdAct = findViewById(R.id.txtNameEditProdAct);
        txtQuantityEditProdAct = findViewById(R.id.txtQuantityEditProdAct);
        txtImageUrlEditProdAct = findViewById(R.id.txtImageUrlEditProdAct);
        txtShortDescEditProdAct = findViewById(R.id.txtShortDescEditProdAct);
        txtLongDescEditProdAct = findViewById(R.id.txtLongDescEditProdAct);
        txtPriceEditProdAct = findViewById(R.id.txtPriceEditProdAct);
        txtPackageQuantityEditProdAct = findViewById(R.id.txtPackageQuantityEditProdAct);
        btnUpdateProductEditProdAct = findViewById(R.id.btnUpdateProductEditProdAct);
    }

    private void setData(Product product){
        txtNameEditProdAct.setText(product.getName());
        txtQuantityEditProdAct.setText(String.valueOf(product.getQuantity()));
        txtImageUrlEditProdAct.setText(product.getImageUrl());
        txtShortDescEditProdAct.setText(product.getShortDesc());
        txtLongDescEditProdAct.setText(product.getLongDesc());
        txtPriceEditProdAct.setText(String.valueOf(product.getPrice()));
        txtPackageQuantityEditProdAct.setText(product.getPackageQuantity());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateData() {
        if (TextUtils.isEmpty(txtNameEditProdAct.getText())) {
            txtNameEditProdAct.setError("Please provide a name.");
            txtNameEditProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtQuantityEditProdAct.getText())) {
            txtQuantityEditProdAct.setError("Please provide a quantity.");
            txtQuantityEditProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtImageUrlEditProdAct.getText())) {
            txtImageUrlEditProdAct.setError("Please provide an image url.");
            txtImageUrlEditProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtShortDescEditProdAct.getText())) {
            txtShortDescEditProdAct.setError("Please provide a short description.");
            txtShortDescEditProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtLongDescEditProdAct.getText())) {
            txtLongDescEditProdAct.setError("Please provide a long description.");
            txtLongDescEditProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtPriceEditProdAct.getText())) {
            txtPriceEditProdAct.setError("Please provide a price.");
            txtPriceEditProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtPackageQuantityEditProdAct.getText())) {
            txtPackageQuantityEditProdAct.setError("Please provide a package quantity.");
            txtPackageQuantityEditProdAct.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

}