package com.example.expencetracker.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.expencetracker.R;
import com.example.expencetracker.async.ExecutorStartYearDD;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FilterMonthTransactionDialog extends DialogFragment {

    private ArrayAdapter<String> monthAdapter;

    private AutoCompleteTextView ddMonth;
    private AutoCompleteTextView ddYears;
    private final FilterMonthTransactionViewModel model;

    public FilterMonthTransactionDialog(FilterMonthTransactionViewModel model){
        this.model = model;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter_month_transaction,null);

        builder.setView(view)
                .setTitle("Filter")
                .setNegativeButton("Cancel",(dialog, which) ->{})
                .setPositiveButton("Ok", (dialog,which) -> filter(view));

        ddMonth = view.findViewById(R.id.ddMonthYearDialog);
        ddYears = view.findViewById(R.id.ddYearYearDialog);

        startDropdowns(view);

        return builder.create();
    }

    private void filter(View view) {
        String monthText = ddMonth.getText().toString();
        String yearText = ddYears.getText().toString();

        if(!monthText.equals("") && !yearText.equals("")){
            ArrayList<Integer> list = new ArrayList<>(2);
            list.add(monthAdapter.getPosition(monthText) + 1);
            list.add(Integer.parseInt(yearText));

            model.setData(list);
        }
        else
            Snackbar.make(view,"position: "+monthAdapter.getPosition(monthText),Snackbar.LENGTH_SHORT).show();

    }

    private void startDropdowns(View view){
        ArrayList<String> months = getMonthsNames();
        monthAdapter = new ArrayAdapter<>(
                        requireContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                months
                );
        ddMonth.setAdapter(monthAdapter);
        ddMonth.setText(monthAdapter.getItem(0),false);

        ExecutorStartYearDD task = new ExecutorStartYearDD(requireContext(), view, ddYears);
        task.runAsync();
    }

    private ArrayList<String> getMonthsNames() {
        ArrayList<String> list = new ArrayList<>();
        list.add("January");
        list.add("February");
        list.add("March");
        list.add("April");
        list.add("May");
        list.add("June");
        list.add("July");
        list.add("August");
        list.add("September");
        list.add("October");
        list.add("November");
        list.add("December");
        return list;
    }
}
