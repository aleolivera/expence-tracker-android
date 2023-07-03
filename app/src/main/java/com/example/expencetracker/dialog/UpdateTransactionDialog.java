package com.example.expencetracker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.expencetracker.R;
import com.example.expencetracker.data.DataCategory;
import com.example.expencetracker.entities.Category;
import com.example.expencetracker.entities.Transaction;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateTransactionDialog extends DialogFragment{
    private final Transaction transaction;
    private UpdateTransactionListener listener;

    private List<Category> categories;
    private TextInputEditText etdescription;
    private TextInputEditText etPrice;
    private AutoCompleteTextView ddCategories;
    private Category selectedCategory;
    private final String originFragment;

    public UpdateTransactionDialog(Transaction transaction,String originFragment) {
        this.transaction = transaction;
        this.originFragment = originFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_transaction,null);

        builder.setView(view)
                .setTitle("Update")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Ok", (dialog, which) -> updateTransaction(view));
        startUI(view);
        setEditText();
        setDropDown();

        return builder.create();
    }

    private void updateTransaction(View view) {
        String description = Objects.requireNonNull(etdescription.getText()).toString();
        String price = Objects.requireNonNull(etPrice.getText()).toString();

        if(description.isEmpty() || price.isEmpty()|| selectedCategory == null)
            Snackbar.make(view,"must complete all fields",Snackbar.LENGTH_SHORT).show();
        else{
            Transaction t = new Transaction(
                    transaction.getId(),
                    description,
                    selectedCategory,
                    transaction.getDate(),
                    transaction.getType(),
                    Double.parseDouble(price),
                    true
            );
            listener.onUpdateDialogPositiveClick(t,originFragment);
        }

    }

    private void startUI(View view){
        etdescription = view.findViewById(R.id.etUpdateDescription);
        etPrice = view.findViewById(R.id.etUpdatePrice);
        ddCategories = view.findViewById(R.id.ddUpdateCategory);
    }

    private void setEditText(){
        etdescription.setText( transaction.getDescription() );
        etPrice.setText( String.valueOf(transaction.getPrice() ));
    }

    private void setDropDown(){
        selectedCategory = transaction.getCategory();
        categories = fetchCategories(transaction.getType().getId());

        ArrayAdapter<Category> adapter =
                new ArrayAdapter<>(
                        requireContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        filterCategories(transaction.getType().getId())
                );
        ddCategories.setAdapter(adapter);

        ddCategories.setText(transaction.getCategory().getName(),false);
        ddCategories.setOnItemClickListener(
                (parent, view, position, id) ->
                        selectedCategory = adapter.getItem(position)
        );
    }

    private List<Category> filterCategories(int idType) {
        List<Category> list = new ArrayList<>();

        for (Category item : categories) {
            if(item.getIdType() == idType)
                list.add(item);
        }

        return list;
    }

    private List<Category> fetchCategories(int idType){
        DataCategory data = new DataCategory(requireContext());
        return data.readAllByTypeOfTransaction(idType);
    }

    public interface UpdateTransactionListener{
        void onUpdateDialogPositiveClick(Transaction transaction, String originFragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateTransactionDialog.UpdateTransactionListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(
                    requireActivity() + " must implement 'UpdateTransactionListener'"
            );
        }
    }

}
