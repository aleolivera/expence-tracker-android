package com.example.expencetracker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.expencetracker.R;
import com.example.expencetracker.data.DataCategory;
import com.example.expencetracker.data.DataType;
import com.example.expencetracker.entities.Category;
import com.example.expencetracker.entities.Transaction;
import com.example.expencetracker.entities.Type;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NewTransactionDialog extends DialogFragment {
    private TextInputEditText etdescription;
    private TextInputEditText etPrice;
    private AutoCompleteTextView ddTypes;
    private AutoCompleteTextView ddCategories;
    private List<Category> categories;
    private List<Type> types;
    private ArrayAdapter<Type> adapterType;
    private ArrayAdapter<Category> adapterCategories;
    private Category selectedCategory;
    private Type selectedType;
    private NewAddTransactionListener listener;
    private String originFragment;

   public NewTransactionDialog(String originFragment){
       this.originFragment = originFragment;
   }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_transaction,null);

        builder.setView(view)
                .setTitle("New Transaction")
                .setNegativeButton("Cancel", (dialog,which)->{})
                .setPositiveButton("Add",(dialog,which)-> saveTransaction(view));

        etdescription = view.findViewById(R.id.etNewTransactionDescription);
        etPrice = view.findViewById(R.id.etNewTransactionPrice);
        ddCategories = view.findViewById(R.id.ddNewTransactionCategory);
        ddTypes = view.findViewById(R.id.ddNewTransactionType);

        startDropDowns();

        return builder.create();
    }

    private void saveTransaction(View v) {
       String description = Objects.requireNonNull(etdescription.getText()).toString();
       String price = Objects.requireNonNull(etPrice.getText()).toString();

       if(!description.equals("") || !price.equals("")){
           Transaction transaction = new Transaction(
                   0,
                   description,
                   selectedCategory,
                   LocalDate.now(),
                   selectedType,
                   Double.parseDouble(price),
                   true
           );
           listener.onNewDialogPositiveClick(transaction,originFragment);
       }
       else
           Snackbar.make(v,"Must complete all fields",Snackbar.LENGTH_SHORT).show();
    }

    private void startDropDowns(){
       categories = fetchCategories();
       types = fetchTypes();

       setDropDownType();
       setDropDownCategories(1);
    }

    private void setDropDownType() {
        adapterType =
                new ArrayAdapter<>(
                        requireContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        types);
        ddTypes.setAdapter(adapterType);

        selectedType = adapterType.getItem(0);
        ddTypes.setText(types.get(0).getName(),false);

        ddTypes.setOnItemClickListener((parent, view, position, id) -> {
            selectedType = adapterType.getItem(position);
            setDropDownCategories(selectedType.getId());
        });
    }

    private void setDropDownCategories(int idType){
        int resource = androidx.appcompat.R.layout.support_simple_spinner_dropdown_item;
        adapterCategories =
                new ArrayAdapter<>(
                        requireContext(),
                        resource,
                        filterCategories(idType));
        ddCategories.setAdapter(adapterCategories);

        ddCategories.setText(adapterCategories.getItem(0).getName(),false);
        selectedCategory = adapterCategories.getItem(0);

        ddCategories.setOnItemClickListener(
                (parent, view, position, id) ->
                        selectedCategory = adapterCategories.getItem(position));
    }

    private List<Category> filterCategories(int idType) {
       List<Category> list = new ArrayList<>();

        for (Category item : categories) {
            if(item.getIdType() == idType)
                list.add(item);
        }

        return list;
    }

    private List<Category> fetchCategories(){
        DataCategory data = new DataCategory(requireContext());
        return data.readAll();
    }

    private List<Type> fetchTypes(){
        DataType data = new DataType((requireContext()));
        return data.readAll();
    }

    public interface NewAddTransactionListener{
        void onNewDialogPositiveClick(Transaction transaction,String originFragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (NewAddTransactionListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(
                    requireActivity() + " must implement 'NewAddTransactionListener'"
            );
        }
    }
}
