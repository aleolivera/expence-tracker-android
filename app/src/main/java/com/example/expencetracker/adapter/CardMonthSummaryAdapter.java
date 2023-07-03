package com.example.expencetracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencetracker.R;
import com.example.expencetracker.dialog.MonthSummaryDialog;
import com.example.expencetracker.entities.Transaction;

import java.text.DecimalFormat;
import java.util.List;

public class CardMonthSummaryAdapter
        extends RecyclerView.Adapter<CardMonthSummaryAdapter.MyViewHolder>{

    private final Context CONTEXT;
    private final int RESOURCE;
    private final List<List<Transaction>> yearListOfTransactions;

    public CardMonthSummaryAdapter(
            @NonNull Context context,
            int resource,
            @NonNull List<List<Transaction>> yearListOfTransactions) {
        this.CONTEXT = context;
        this.RESOURCE = resource;
        this.yearListOfTransactions = yearListOfTransactions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(CONTEXT).inflate(RESOURCE,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        List<Transaction> monthTransactions = yearListOfTransactions.get(position);

        if(monthTransactions.size() > 0)
            holder.onBind(monthTransactions);
    }

    @Override
    public int getItemCount() {
        return yearListOfTransactions.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvBalance;
        private final TextView tvIncomes;
        private final TextView tvExpences;
        private final TextView tvMonth;
        private final View itemView;
        private final int EXPENCE_INDEX = 0, INCOME_INDEX = 1, BALANCE_INDEX = 2;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvBalance = itemView.findViewById(R.id.tvYearSummaryBalance);
            tvExpences = itemView.findViewById(R.id.tvYearSummaryExpences);
            tvIncomes = itemView.findViewById(R.id.tvYearSummaryIncomes);
            tvMonth = itemView.findViewById(R.id.tvYearSummaryMonth);
        }

        public void onBind(List<Transaction> monthTransactions){
            String month = getMonthName(monthTransactions.get(0).getDate().getMonthValue());
            tvMonth.setText(month);

            double [] totals = getTotals(monthTransactions);

            String incomes = "Incomes $" + new DecimalFormat("#.##").format(totals[INCOME_INDEX]);
            tvIncomes.setText(incomes);

            String expences = "Expences $" + new DecimalFormat("#.##").format(totals[EXPENCE_INDEX]);
            tvExpences.setText(expences);

            String balance = "Balance $" + new DecimalFormat("#.##").format(totals[BALANCE_INDEX]);
            tvBalance.setText(balance);

            itemView.setOnLongClickListener(v -> {
                openDialog(month,totals[INCOME_INDEX],totals[EXPENCE_INDEX],totals[BALANCE_INDEX],monthTransactions);
                return false;
            });


        }

        private void openDialog(String month, double incomes, double expences,
                                double balance, List<Transaction> transactions) {

            FragmentManager fragmentManager = ((AppCompatActivity)CONTEXT).getSupportFragmentManager();
            MonthSummaryDialog dialog = new MonthSummaryDialog(month,incomes,expences,balance,transactions);
            dialog.show(fragmentManager, "cardMonthSummary");
        }


        private double[] getTotals(List<Transaction> monthTransactions) {
            double [] totals ={0,0,0};

            for (Transaction item : monthTransactions) {
                double price = item.getPrice();

                if (item.getType().getId() == 1) {
                    totals[EXPENCE_INDEX] += price;
                    totals[BALANCE_INDEX] -= price;
                }
                else{
                    totals[INCOME_INDEX] += price;
                    totals[BALANCE_INDEX] += price;
                }
            }

            return totals;
        }

        private String getMonthName(int month) {
            switch (month){
                case 1: return "January";
                case 2: return "February";
                case 3: return "March";
                case 4: return "April";
                case 5: return "May";
                case 6: return "June";
                case 7: return "July";
                case 8: return "August";
                case 9: return "September";
                case 10: return "October";
                case 11: return "November";
                default: return "December";
            }
        }
    }
}
