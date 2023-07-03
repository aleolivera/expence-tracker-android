package com.example.expencetracker.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.expencetracker.R;
import com.example.expencetracker.entities.Transaction;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DecimalFormat;


public class OpenTransactionDialog extends DialogFragment {
    private TextView tvDescription,tvPrice,tvCategory,tvDate;
    private final Transaction transaction;

    public OpenTransactionDialog(Transaction transaction){
        this.transaction = transaction;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_open_transaction,null);

        builder.setView(view)
                .setTitle(transaction.getType().getName())
                .setPositiveButton("Ok",null);

        startUI(view);
        setTextViews();

        return builder.create();
    }

    private void startUI(View view){
        tvDescription = view.findViewById(R.id.tvOpenDescription);
        tvCategory = view.findViewById(R.id.tvOpenCategory);
        tvDate = view.findViewById(R.id.tvOpenDate);
        tvPrice = view.findViewById(R.id.tvOpenPrice);
    }

    private void setTextViews(){
        String dateText = transaction.getDate().getDayOfMonth() +"-"+
                          transaction.getDate().getMonthValue()+"-"+
                          transaction.getDate().getYear();
        tvDate.setText(dateText);
        tvDescription.setText(transaction.getDescription());
        tvCategory.setText(transaction.getCategory().getName());

        String priceText = "$"+ new DecimalFormat("#.##").format(transaction.getPrice());
        tvPrice.setText(priceText);
    }
}
