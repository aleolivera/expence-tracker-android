package com.example.expencetracker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencetracker.R;
import com.example.expencetracker.async.ExecutorUpdateTransaction;
import com.example.expencetracker.dialog.OpenTransactionDialog;
import com.example.expencetracker.dialog.UpdateTransactionDialog;
import com.example.expencetracker.entities.CustomViewModelList;
import com.example.expencetracker.entities.Transaction;
import com.example.expencetracker.ui.home.HomeViewModel;
import com.example.expencetracker.ui.summary.MonthSummaryViewModel;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DecimalFormat;

public class CardTransactionSummaryAdapter
        extends RecyclerView.Adapter<CardTransactionSummaryAdapter.MyViewHolder> {
    private final Context CONTEXT;
    private final CustomViewModelList MODEL;
    private final int RESOURCE;
    private final String ORIGIN_FRAGMENT;

    public CardTransactionSummaryAdapter(@NonNull Context context, @NonNull CustomViewModelList model, int resource, String originFragment) {
        this.CONTEXT = context;
        this.MODEL = model;
        this.RESOURCE = resource;
        this.ORIGIN_FRAGMENT = originFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(CONTEXT).inflate(RESOURCE,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Transaction transaction = getTransactionFromModel(position);
        holder.onBind(transaction);
    }

    private Transaction getTransactionFromModel(int position) {
        try {
            switch (ORIGIN_FRAGMENT) {
                case "Home":
                    return ((HomeViewModel) MODEL).getReversedList().get(position);
                case "Summary":
                    return  ((MonthSummaryViewModel) MODEL).getReversedList().get(position);
                default:
                    return Transaction.newInstance();
            }
        }
        catch (ClassCastException e) {
            throw new ClassCastException(e +
                    " Couldn´t cast to TransactionViewModel");
        }
        catch (IndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException(e +
                    " index: " + position + ", size: " + MODEL.size());
        }
    }

    @Override
    public int getItemCount() {
        return MODEL.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvDescription;
        private final TextView tvPrice;
        private final TextView tvDay;
        private final View itemView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvCardTransactionDescription);
            tvPrice = itemView.findViewById(R.id.tvCardTransactionPrice);
            tvDay = itemView.findViewById(R.id.tvCardTransactionDay);
            this.itemView = itemView;
        }

        public void onBind(Transaction transaction) {
            tvDescription.setText(transaction.getDescription());

            String priceText = "$" + new DecimalFormat("#.##").format(transaction.getPrice());
            tvPrice.setText(priceText);

            String dayText = "Day " + transaction.getDate().getDayOfMonth();
            tvDay.setText(dayText);

            styleCard(transaction.getType().getId());

            itemView.setOnLongClickListener(v -> {
                createPopUpMenu(transaction);
                return false;
            });
        }

        private void createPopUpMenu(Transaction transaction){
            PopupMenu menu = new PopupMenu(CONTEXT,itemView);
            MenuInflater inflater = new MenuInflater(CONTEXT);
            inflater.inflate(R.menu.menu_home_summary_recycler,menu.getMenu());

            menu.setOnMenuItemClickListener(item -> {
                onMenuItemSelected(item,transaction);
                return false;
            });
            menu.show();
        }

        @SuppressLint("NonConstantResourceId")
        private void onMenuItemSelected(MenuItem item, Transaction transaction){
            switch (item.getItemId()){
                case R.id.menu_item_home_open:
                    openTransaction(transaction);
                    break;
                case R.id.menu_item_home_update:
                    updateTransaction(transaction);
                    break;
                case R.id.menu_item_home_delete:
                    deleteTransaction(transaction);
                    break;
            }
        }

        private void openTransaction(Transaction transaction){
            FragmentManager manager = ((AppCompatActivity)CONTEXT).getSupportFragmentManager();
            OpenTransactionDialog dialog = new OpenTransactionDialog(transaction);
            dialog.show(manager, null);
        }

        private void updateTransaction(Transaction transaction){
            FragmentManager manager = ((AppCompatActivity)CONTEXT).getSupportFragmentManager();
            UpdateTransactionDialog dialog = new UpdateTransactionDialog(transaction,ORIGIN_FRAGMENT);
            dialog.show(manager,null);
        }

        private void deleteTransaction(Transaction transaction){
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(CONTEXT);
            builder.setTitle("Delete")
                    .setMessage("Do you want to delete this " + transaction.getCategory().getName()+"?")
                    .setPositiveButton("Ok", (dialog, which) -> {

                        transaction.setState(false);
                        ExecutorUpdateTransaction task =
                                new ExecutorUpdateTransaction(
                                        CONTEXT, itemView, transaction, MODEL
                                );
                        task.runAsync();

                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }

        private void styleCard(int idType){
            int INCOME = 2;
            CardView c = (CardView) itemView;
            if(idType == INCOME)
                c.setCardBackgroundColor(MaterialColors.getColor(itemView, com.google.android.material.R.attr.colorTertiaryContainer));
            else
                c.setCardBackgroundColor(MaterialColors.getColor(itemView, com.google.android.material.R.attr.colorPrimaryContainer));
        }
    }
}
