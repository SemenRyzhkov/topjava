package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.ConcurrentMapStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private ConcurrentMapStorage mealsStorage;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealsStorage = new ConcurrentMapStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        int mealId = Integer.parseInt(request.getParameter("id"));
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"), formatter);
        int calories = Integer.parseInt(request.getParameter("calories"));
        String description = request.getParameter("description");
        Meal meal = new Meal(mealId, localDateTime, description, calories);
        if (mealId != 0) {
            mealsStorage.update(meal);
        } else {
            mealsStorage.save(meal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String id = request.getParameter("mealId");
        String action = request.getParameter("action");
        String forward = "";
        log.debug("redirect to meal");
        if (action == null) {
            action = "";
        }

        Meal meal;
        switch (action) {
            case "delete":
                mealsStorage.delete(Integer.parseInt(id));
                request.setAttribute("", "action");
                response.sendRedirect("meals");

                return;
            case "edit":
                forward = "/edit.jsp";
                meal = mealsStorage.getById(Integer.parseInt(id));
                break;
            case "insert":
                meal = MealsUtil.EMPTY;
                forward = "/edit.jsp";
                break;
            default:
                request.setAttribute("mealToList", MealsUtil.filteredByStreams(
                        mealsStorage.getAllSorted(),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher(forward).forward(request, response);
    }
}
