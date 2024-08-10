package com.example.savings.web;

import com.example.savings.entities.Savingstable;
import com.example.savings.repositories.SavingsRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class SavingsController {
    @Autowired

    private SavingsRepository savingsRepository;

    static int num = 0;

    @GetMapping("/")
    public String home(Model model) {
        List<Savingstable> savingstables = savingsRepository.findAll();

        model.addAttribute("SavingsList", savingstables);
        return "home";
    }

    @GetMapping("/newentry")
    public String newEntry(Model model) {

        model.addAttribute("savingsTable", new Savingstable());
        return "newEntry";
    }


    @PostMapping(path = "/saveEntry")
    public String save(Model model, Savingstable savingstable, BindingResult bindingResult, ModelMap mm, HttpSession session, @RequestParam("formType") String formType) {
        if (bindingResult.hasErrors()) {
            return "newEntry";
        } else {
            List<Savingstable> s = savingsRepository.findByCustNo(savingstable.getCustNo());
            if (!"newEntryForm".equals(formType)) {
                savingsRepository.save(savingstable);
                return "redirect:/";
            } else {
                if (!s.isEmpty()) {
                    bindingResult.rejectValue("custNo", "error.custNo", "Customer number already exists.");
                    model.addAttribute("duplicateCustNo", true);
                    model.addAttribute("savingsTable", savingstable);

                    if (num == 2) {
                        mm.put("e", 2);
                        mm.put("a", 0);
                    } else {
                        mm.put("a", 1);
                        mm.put("e", 0);
                    }
                    return "newEntry";
                } else {
                    savingsRepository.save(savingstable);

                }
            }
            return "redirect:/";
        }
    }


    @GetMapping("editentry")
    public String editEntry(Model model, Long id) {

        Optional<Savingstable> savingstable = savingsRepository.findById(id);
        model.addAttribute("savingsTable", savingstable.get());
        return "editEntry";
    }

    @GetMapping("/delete")
    public String delete(Long id) {
        savingsRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/investment")
    public String investment(Model model, Long id) {

        Optional<Savingstable> optionalSavingstable = savingsRepository.findById(id);
        if (optionalSavingstable.isPresent()) {
            Savingstable savingstable = optionalSavingstable.get();
            int nYears = savingstable.getNYears();
            double startingAmount = savingstable.getCDep();
            String savType = savingstable.getSavType();
            double interestRate = "Savings De-luxe".equals(savType) ? 0.15 : 0.10;

            List<List<Object>> rows = new ArrayList<>();
            double amount = startingAmount;

            for (int year = 1; year <= nYears; year++) {
                double interest = amount * interestRate;
                double endingBalance = amount + interest;
                List<Object> row = Arrays.asList(year, amount, interest, endingBalance);
                rows.add(row);
                amount = endingBalance;
            }

            model.addAttribute("rows", rows);
            return "investment";
        } else {
            model.addAttribute("error", "Customer not found.");
            return "errorPage";
        }
    }
}
