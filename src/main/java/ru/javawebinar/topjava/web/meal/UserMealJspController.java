package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by Mikhail on 11.04.2016.
 */
@Controller
@RequestMapping(value = "/meals")
public class UserMealJspController extends AbstractUserMealRestController {

    @RequestMapping(method = RequestMethod.GET)
    public String getMealList(Model model) {
        model.addAttribute("mealList", super.getAll());
        return "mealList";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteMeal(HttpServletRequest servletRequest) {
        super.delete(getId(servletRequest));
        return "redirect:meals";
    }

    private int getId(HttpServletRequest servletRequest) {
        return Integer.valueOf(Objects.requireNonNull(servletRequest.getParameter("id")));
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String editAndUpdate(HttpServletRequest servletRequest, Model model) {
        model.addAttribute("meal", super.get(getId(servletRequest)));
        return "mealEdit";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String editAndCreate(Model model) {
        model.addAttribute("meal", new UserMeal(LocalDateTime.now(), "", 500));
        return "mealEdit";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getBetween(HttpServletRequest servletRequest, Model model) {
        LocalDate startDate = TimeUtil.parseLocalDate(servletRequest.getParameter("startDate"));
        LocalDate endDate = TimeUtil.parseLocalDate(servletRequest.getParameter("endDate"));
        LocalTime startTime = TimeUtil.parseLocalTime(servletRequest.getParameter("startTime"));
        LocalTime endTime = TimeUtil.parseLocalTime(servletRequest.getParameter("endTime"));
        model.addAttribute("mealList", super.getBetween(startDate,startTime, endDate,  endTime));
        return "mealList";
    }
}
