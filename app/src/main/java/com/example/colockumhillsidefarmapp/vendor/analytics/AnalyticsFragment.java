package com.example.colockumhillsidefarmapp.vendor.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.Transaction;
import com.example.colockumhillsidefarmapp.customer.store.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AnalyticsFragment extends Fragment {

    private RecyclerView transactionRecView;
    private SwipeRefreshLayout layout;
    private ArrayList<Transaction> transactionList;
    private TransactionRecViewAdapter adapter;
    private Spinner spnrSortAnalytics;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_analytics, container, false);

        setHasOptionsMenu(true);

        adapter = new TransactionRecViewAdapter(root.getContext());
        transactionList = DBInterface.getInstance().getAllTransactions(adapter);
        transactionRecView = root.findViewById(R.id.analyticsRecView);
        spnrSortAnalytics = root.findViewById(R.id.spnrSortAnalytics);

        transactionRecView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(root.getContext());
        transactionRecView.setLayoutManager(manager);

        adapter.setTransactions(transactionList);

        layout = root.findViewById(R.id.swipeRefreshLayoutAnalytics);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                transactionList = DBInterface.getInstance().getAllTransactions(adapter);
                adapter.setTransactions(transactionList);
                spnrSortAnalytics.setSelection(0);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = root.findViewById(R.id.txtSearchUpdateAnalytics);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString().trim());
            }
        });

        txtSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    closeKeyboard();
                    return true;
                }
                return false;
            }
        });

        ArrayList<String> sortBy = new ArrayList<>();
        sortBy.add("New->Old");
        sortBy.add("Old->New");
        sortBy.add("A->Z");
        sortBy.add("Z->A");
        sortBy.add("$->$$");
        sortBy.add("$$->$");
        ArrayAdapter<String> sortByAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_search_criteria,
                sortBy);
        spnrSortAnalytics.setAdapter(sortByAdapter);

        setSpinnerOnItemSelectedLister(transactionList);

        return root;
    }

    private void setSpinnerOnItemSelectedLister(ArrayList<Transaction> list) {
        spnrSortAnalytics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSort = spnrSortAnalytics.getSelectedItem().toString();
                switch (selectedSort) {
                    case "A->Z":
                        Collections.sort(list, Transaction.TransactionNameAZComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    case "Z->A":
                        Collections.sort(list, Transaction.TransactionNameZAComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    case "$->$$":
                        Collections.sort(list, Transaction.TransactionPriceAscendingComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    case "$$->$":
                        Collections.sort(list, Transaction.TransactionPriceDescendingComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    case "Old->New":
                        Collections.sort(list, Transaction.TransactionDateAscendingComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    case "New->Old":
                        Collections.sort(list, Transaction.TransactionDateDescendingComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void filter(String text) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            Product product = transaction.getProduct();
            if (product != null) {
                String input = product.getName();
                if (input != null) {
                    if (input.toLowerCase().contains(text.toLowerCase())) {
                        filteredTransactions.add(transaction);
                    }
                }
            }
            Date time = transaction.getTime();
            if (time != null) {
                String input = time.toString();
                if (input != null) {
                    if (input.toLowerCase().contains(text.toLowerCase())) {
                        filteredTransactions.add(transaction);
                    }
                }
            }
            String input = transaction.getUser();
            if (input != null) {
                if (input.toLowerCase().contains(text.toLowerCase())) {
                    filteredTransactions.add(transaction);
                }
            }


        }
        adapter.filterList(filteredTransactions);
        setSpinnerOnItemSelectedLister(filteredTransactions);
    }

    private void closeKeyboard () {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
