package javau9.budget.controllers;

import javau9.budget.models.Expenses;
import javau9.budget.models.Income;
import javau9.budget.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class BudgetHomeController {

    @Autowired
    BudgetService budgetService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("allIncomeslist", budgetService.getIncomeList());
        model.addAttribute("allExpenseslist", budgetService.getExpensesList());
        return "index";
    }

    @GetMapping("/incomeList")
    public String getIncomeList(Model model) {
        model.addAttribute("allIncomeslist", budgetService.getIncomeList());
        return "incomeList";
    }

    @GetMapping("/expensesList")
    public String getExpensesList(Model model) {
        model.addAttribute("allExpenseslist", budgetService.getExpensesList());
        return "expensesList";
    }

    @GetMapping("/addNewIncome")
    public String addNewIncome(Model model) {
        Income income = new Income();
        model.addAttribute("income", income);
        return "newIncome";
    }

    @PostMapping("/saveInc")
    public String saveIncome(@ModelAttribute("income") Income income) {
        budgetService.saveInc(income);
        return "redirect:/";
    }

    /*@GetMapping("/getIncome/{id}")
    public String getIncomeById(@PathVariable(value = "id") Long id) {
        budgetService.getIncomeById(id);
        return "findIncome";
    }*/

    /*@GetMapping("/getIncome/{id}")
    public String getIncomeById(@PathVariable(value = "id") Long id, Model model) {
        Optional<Income> income = budgetService.getIncomeById(id);
        model.addAttribute("income", income);
        return "incomeDetails";
    }*/

    @GetMapping("/findIncome")
    public String findIncome() {
        return "findIncome";
    }

    @GetMapping("/getIncome")
    public String getIncomeById(@RequestParam("id") Long id, Model model) {
        try {
            Optional<Income> income = budgetService.getIncomeById(id);
            if (income.isPresent()) {
                model.addAttribute("income", income.get());
                return "incomeDetails";
            } else {
                model.addAttribute("error", "Income not found!");
                return "findIncome";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "findIncome";
        }
    }

    @GetMapping("/deleteIncome/{id}")
    public String deleteIncome(@PathVariable(value = "id") Long id) {
        budgetService.removeIncome(id);
        return "redirect:/";
    }

    @GetMapping("/addNewExpense")
    public String addNewExpense(Model model) {
        Expenses expense = new Expenses();
        model.addAttribute("expense", expense);
        return "newExpense";
    }

    @PostMapping("/saveExp")
    public String saveExpense(@ModelAttribute("expense") Expenses expense) {
        budgetService.saveExp(expense);
        return "redirect:/";
    }

    @GetMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable(value = "id") Long id) {
        budgetService.removeExpense(id);
        return "redirect:/";
    }
    @GetMapping("/findExpense")
    public String findExpense() {
        return "findExpense";
    }

    /*@GetMapping("/getExpense")
    public String getExpenseById(@PathVariable(value = "id") Long id) {
        budgetService.getIncomeById(id);
        return "getExpense";
    }*/

    @GetMapping("/getExpense")
    public String getExpenseById(@RequestParam("id") Long id, Model model) {
        try {
            Optional<Expenses> expense = budgetService.getExpenseById(id);
            if (expense.isPresent()) {
                model.addAttribute("expense", expense.get());
                return "expenseDetails";
            } else {
                model.addAttribute("error", "Expense not found!");
                return "findExpense";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "findExpense";
        }
    }

    @GetMapping("/balance")
    public String getBalance(Model model) {
        double balance = budgetService.getBalance();
        model.addAttribute("balance", balance);
        return "balance";
    }
}