package com.example.expencetracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.expencetracker.R;
import com.example.expencetracker.databinding.FragmentHomeBinding;
import com.example.expencetracker.entities.Transaction;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class HomeFragment extends Fragment {
    private final int
            SUPPLIES = 0, SERVICES = 1, FUN = 2, CLOTHES = 3, GIFTS = 4,
            HEALTH = 5, EDUCATION = 6, OTHER_EXPENCES = 7, SALARY = 8,
            PAID_JOBS = 9, OTHER_INCOMES = 10, TOTAL_EXPENSES = 11,
            TOTAL_INCOMES = 12;

    private TextView tvDate, tvBalance, tvIncomes, tvExpences;
    private TextView tvSalary, tvPaidJob, tvOtherIncome, tvClothes;
    private TextView tvSupplies, tvServices, tvFun, tvGifts, tvHealth, tvEducation, tvOtherExpence;

    private FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getLiveData().observe(getViewLifecycleOwner(), this::setTextViews);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startUI(view);
    }

    private void setTextViews(List<Transaction> transactions) {
        double[] totals = sumTotals(transactions);
        double balance = totals[TOTAL_INCOMES] - totals[TOTAL_EXPENSES];

        String dateText = getMonthName() + " " + LocalDate.now().getYear();
        tvDate.setText(dateText);

        String balanceText = "Balance $" + new DecimalFormat("#.##").format(balance);
        tvBalance.setText(balanceText);

        String incomesText = "Incomes $" + new DecimalFormat("#.##").format(totals[TOTAL_INCOMES]);
        tvIncomes.setText(incomesText);

        String expensesText = "Expenses $" + new DecimalFormat("#.##").format(totals[TOTAL_EXPENSES]);
        tvExpences.setText(expensesText);

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
        tvOtherExpence.setText(otherExpencesText);

        String salaryText = "Salary $" + new DecimalFormat("#.##").format(totals[SALARY]);
        tvSalary.setText(salaryText);

        String paidJobsText = "Paid Jobs $" + new DecimalFormat("#.##").format(totals[PAID_JOBS]);
        tvPaidJob.setText(paidJobsText);

        String otherIncomesText = "Other: $" + new DecimalFormat("#.##").format(totals[OTHER_INCOMES]);
        tvOtherIncome.setText(otherIncomesText);

    }

    private String getMonthName() {
        switch (LocalDate.now().getMonthValue()) {
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

    private double[] sumTotals(List<Transaction> transactions) {
        double[] totals = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (Transaction item : transactions) {
            switch (item.getCategory().getId()) {
                case 1:
                    totals[SUPPLIES] += item.getPrice();
                    totals[TOTAL_EXPENSES] += item.getPrice();
                    break;
                case 2:
                    totals[SERVICES] += item.getPrice();
                    totals[TOTAL_EXPENSES] += item.getPrice();
                    break;
                case 3:
                    totals[FUN] += item.getPrice();
                    totals[TOTAL_EXPENSES] += item.getPrice();
                    break;
                case 4:
                    totals[CLOTHES] += item.getPrice();
                    totals[TOTAL_EXPENSES] += item.getPrice();
                    break;
                case 5:
                    totals[GIFTS] += item.getPrice();
                    totals[TOTAL_EXPENSES] += item.getPrice();
                    break;
                case 6:
                    totals[HEALTH] += item.getPrice();
                    totals[TOTAL_EXPENSES] += item.getPrice();
                    break;
                case 7:
                    totals[EDUCATION] += item.getPrice();
                    totals[TOTAL_EXPENSES] += item.getPrice();
                    break;
                case 8:
                    totals[OTHER_EXPENCES] += item.getPrice();
                    totals[TOTAL_EXPENSES] += item.getPrice();
                    break;
                case 9:
                    totals[SALARY] += item.getPrice();
                    totals[TOTAL_INCOMES] += item.getPrice();
                    break;
                case 10:
                    totals[PAID_JOBS] += item.getPrice();
                    totals[TOTAL_INCOMES] += item.getPrice();
                    break;
                case 11:
                    totals[OTHER_INCOMES] += item.getPrice();
                    totals[TOTAL_INCOMES] += item.getPrice();
                    break;
            }
        }
        return totals;
    }

    private void startUI(View view) {
        tvDate = view.findViewById(R.id.tvDateHomeBalance);
        tvBalance = view.findViewById(R.id.tvHomeCardBalance);
        tvIncomes = view.findViewById(R.id.tvHomeCardIncomes);
        tvExpences = view.findViewById(R.id.tvHomeCardExpences);
        tvSalary = view.findViewById(R.id.tvHomeSalary);
        tvPaidJob = view.findViewById(R.id.tvHomePaidJob);
        tvOtherIncome = view.findViewById(R.id.tvHomeOtherIncome);
        tvSupplies = view.findViewById(R.id.tvHomeSupplies);
        tvServices = view.findViewById(R.id.tvHomeServices);
        tvClothes = view.findViewById(R.id.tvHomeClothes);
        tvFun = view.findViewById(R.id.tvHomeFun);
        tvGifts = view.findViewById(R.id.tvHomeGifts);
        tvHealth = view.findViewById(R.id.tvHomeHealth);
        tvEducation = view.findViewById(R.id.tvHomeEducation);
        tvOtherExpence = view.findViewById(R.id.tvHomeOtherExpence);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}