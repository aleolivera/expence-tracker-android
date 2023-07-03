package com.example.expencetracker.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.expencetracker.R;
import com.example.expencetracker.entities.Transaction;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DecimalFormat;
import java.util.List;

public class MonthSummaryDialog extends DialogFragment {
    private TextView tvSalary,tvPaidJob,tvOtherIncome,tvSupplies, tvServices,
                    tvFun,tvClothes,tvGifts,tvHealth, tvEducation, tvOtherExpences,
                    tvBalance,tvIncomes,tvExpences;
    private final double totalIncomes, totalExpences,balance;
    private final String monthName;
    private final List<Transaction> transactions;
    private final int
            SUPPLIES = 0, SERVICES = 1, FUN = 2, CLOTHES = 3, GIFTS = 4,
            HEALTH = 5, EDUCATION = 6, OTHER_EXPENCES = 7, SALARY = 8,
            PAID_JOBS = 9, OTHER_INCOMES = 10;

    public MonthSummaryDialog(String monthName,double totalIncomes,double totalExpences,double balance,List<Transaction>transactions){
        this.monthName = monthName;
        this.totalIncomes = totalIncomes;
        this.totalExpences = totalExpences;
        this.balance = balance;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_month_summary,null);

        builder.setView(view)
                .setTitle(monthName)
                .setPositiveButton("Ok",null);

        startUI(view);
        setTextViews(transactions);

        return builder.create();
    }

    private void setTextViews(List<Transaction> transactions) {
        double[] totals = sumTotals(transactions);

        String balanceText = "Balance $" + new DecimalFormat("#.##").format(balance);
        tvBalance.setText(balanceText);

        String incomesText = "Incomes $" + new DecimalFormat("#.##").format(totalIncomes);
        tvIncomes.setText(incomesText);

        String expencesText = "Expences $" + new DecimalFormat("#.##").format(totalExpences);
        tvExpences.setText(expencesText);

        String suppliesText = "Supplies $" + new DecimalFormat("#.##").format(totals[SUPPLIES]);
        tvSupplies.setText(suppliesText);

        String servicesText = "Services $" + new DecimalFormat("#.##").format(totals[SERVICES]);
        tvServices.setText(servicesText);

        String funText = "Fun $" + new DecimalFormat("#.##").format(totals[FUN]);
        tvFun.setText(funText);

        String clothesText = "Clothes $" + new DecimalFormat("#.##").format(totals[CLOTHES]);
        tvClothes.setText(clothesText);

        String giftsText = "Gifts $" + new DecimalFormat("#.##").format(totals[GIFTS]);
        tvGifts.setText(giftsText);

        String healthText = "Health $" + new DecimalFormat("#.##").format(totals[HEALTH]);
        tvHealth.setText(healthText);

        String educationText = "Education $" + new DecimalFormat("#.##").format(totals[EDUCATION]);
        tvEducation.setText(educationText);

        String otherExpencesText = "Other $" + new DecimalFormat("#.##").format(totals[OTHER_EXPENCES]);
        tvOtherExpences.setText(otherExpencesText);

        String salaryText = "Salary $" + new DecimalFormat("#.##").format(totals[SALARY]);
        tvSalary.setText(salaryText);

        String paidJobsText = "Paid Jobs $" + new DecimalFormat("#.##").format(totals[PAID_JOBS]);
        tvPaidJob.setText(paidJobsText);

        String otherIncomesText = "Other: $" + new DecimalFormat("#.##").format(totals[OTHER_INCOMES]);
        tvOtherIncome.setText(otherIncomesText);

    }

    private double[] sumTotals(List<Transaction> transactions) {
        double[] totals = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (Transaction item : transactions) {
            switch (item.getCategory().getId()) {
                case 1:
                    totals[SUPPLIES] += item.getPrice();
                    break;
                case 2:
                    totals[SERVICES] += item.getPrice();
                    break;
                case 3:
                    totals[FUN] += item.getPrice();
                    break;
                case 4:
                    totals[CLOTHES] += item.getPrice();
                    break;
                case 5:
                    totals[GIFTS] += item.getPrice();
                    break;
                case 6:
                    totals[HEALTH] += item.getPrice();
                    break;
                case 7:
                    totals[EDUCATION] += item.getPrice();
                    break;
                case 8:
                    totals[OTHER_EXPENCES] += item.getPrice();
                    break;
                case 9:
                    totals[SALARY] += item.getPrice();
                    break;
                case 10:
                    totals[PAID_JOBS] += item.getPrice();
                    break;
                case 11:
                    totals[OTHER_INCOMES] += item.getPrice();
                    break;
            }
        }
        return totals;
    }

    private void startUI(View view){
        tvSalary = view.findViewById(R.id.tvYearSummaryDialogSalary);
        tvPaidJob = view.findViewById(R.id.tvYearSummaryDialogPaidJob);
        tvOtherIncome = view.findViewById(R.id.tvYearSummaryDialogOtherIncome);
        tvSupplies= view.findViewById(R.id.tvYearSummaryDialogSupplies);
        tvServices = view.findViewById(R.id.tvYearSummaryDialogServices);
        tvFun = view.findViewById(R.id.tvYearSummaryDialogFun);
        tvClothes = view.findViewById(R.id.tvYearSummaryDialogClothes);
        tvGifts = view.findViewById(R.id.tvYearSummaryDialogGifts);
        tvHealth = view.findViewById(R.id.tvYearSummaryDialogHealth);
        tvEducation = view.findViewById(R.id.tvYearSummaryDialogEducation);
        tvOtherExpences = view.findViewById(R.id.tvYearSummaryDialogOtherExpence);
        tvBalance = view.findViewById(R.id.tvYearSummaryDialogBalance);
        tvIncomes = view.findViewById(R.id.tvYearSummaryDialogIncomes);
        tvExpences = view.findViewById(R.id.tvYearSummaryDialogExpences);
    }
}
