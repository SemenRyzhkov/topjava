package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealsStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MealsStorage mealsStorage;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealsStorage = new MealsStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        String id = request.getParameter("id");
        int mealId = Integer.parseInt(id);
        meal.setId(mealId);
        String date = request.getParameter("date");
//        date = date.replaceAll("T", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(localDateTime);
        meal.setDateTime(localDateTime);
        String calories = request.getParameter("calories");
        meal.setCalories(Integer.parseInt(calories));
        meal.setDescription(request.getParameter("description"));
        if (mealId != 0 && id.trim().length() != 0) {
            mealsStorage.updateMeal(meal);
        } else {
           mealsStorage.saveMeal(meal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");
        String id = request.getParameter("mealId");
        String action = request.getParameter("action");
        String forward = "";
        if (action == null){
            request.setAttribute(
                    "mealToList", MealsUtil.excessMeal(mealsStorage.showAllMeals(), mealsStorage.getCALORIES_PER_DAY()));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        Meal meal;
        switch (action) {
            case "delete":
                mealsStorage.deleteMeal(Integer.parseInt(id));
                response.sendRedirect("meals");
                return;
            case "edit":
                forward = "/edit.jsp";
                meal = mealsStorage.getMealById(Integer.parseInt(id));
                break;
            case "insert":
                meal = Meal.EMPTY;
                forward = "/edit.jsp";
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher(forward).forward(request, response);
    }
}
